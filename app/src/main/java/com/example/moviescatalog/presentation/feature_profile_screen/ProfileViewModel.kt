package com.example.moviescatalog.presentation.feature_profile_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.feature_profile_screen.usecase.ChangeProfileUseCase
import com.example.domain.feature_profile_screen.usecase.GetProfileUseCase
import com.example.domain.feature_profile_screen.usecase.LogoutUseCase
import com.example.domain.feature_user_auth.usecase.FormatDateUseCase
import com.example.domain.feature_user_auth.usecase.ValidateEmailUseCase
import com.example.domain.feature_user_auth.usecase.ValidateFirstNameUseCase
import com.example.domain.common.model.Profile
import com.example.domain.feature_profile_screen.usecase.ValidateUrlUseCase
import com.example.moviescatalog.R
import com.example.moviescatalog.presentation.util.UiText
import com.example.moviescatalog.presentation.feature_profile_screen.state.Gender
import com.example.moviescatalog.presentation.feature_profile_screen.state.ProfileIsNotEmptyState
import com.example.moviescatalog.presentation.feature_profile_screen.state.ProfileSimilarity
import com.example.moviescatalog.presentation.feature_profile_screen.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val changeProfileUseCase: ChangeProfileUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateUrlUseCase: ValidateUrlUseCase,
    private val formatDateUseCase: FormatDateUseCase
) : ViewModel() {
    private lateinit var profile: Profile
    private var profileSimilarity = ProfileSimilarity()
    private var isNotEmptyState = ProfileIsNotEmptyState()

    private var _state = MutableLiveData<ProfileState>(ProfileState.Initial)
    val state: LiveData<ProfileState> = _state

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.ShowProfile -> showProfile()
            is ProfileEvent.AvatarChanged -> avatarChanged(event.avatarLink)
            is ProfileEvent.BirthdayChanged -> birthdayChanged(
                event.year,
                event.monthOfYear,
                event.dayOfMonth
            )

            is ProfileEvent.EmailChanged -> emailChanged(event.email)
            is ProfileEvent.FirstNameChanged -> firstNameChanged(event.firstName)
            is ProfileEvent.GenderChanged -> genderChanged(event.gender)
            is ProfileEvent.ChangeProfile -> changeProfile(
                avatarLink = event.avatarLink,
                email = event.email,
                name = event.name,
            )

            is ProfileEvent.Cancel -> showCachedProfile()
            is ProfileEvent.Exit -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            try {
                logoutUseCase()
                _state.value = ProfileState.Exit
            } catch (e: HttpException) {
                if (e.code() == UNAUTHORIZED) {
                    _state.value = ProfileState.AuthorisationError
                } else {
                    throw e
                }
            }
        }
    }

    private fun showCachedProfile() {
        profileSimilarity = ProfileSimilarity()
        _state.value = ProfileState.Profile(
            avatarLink = profile.avatarLink,
            birthDate = profile.birthDate,
            email = profile.email,
            gender = if (profile.gender == MALE_GENDER) Gender.MALE else Gender.FEMALE,
            name = profile.name,
            nickName = profile.nickName
        )
    }

    private fun changeProfile(
        avatarLink: String?,
        email: String,
        name: String,
    ) {
        val profileChanged = (_state.value as ProfileState.ProfileChanged)
        viewModelScope.launch {
            try {
                profile = Profile(
                    avatarLink = avatarLink,
                    birthDate = profileChanged.birthDate,
                    email = email,
                    gender = profileChanged.gender.ordinal,
                    id = profile.id,
                    name = name,
                    nickName = profile.nickName
                )
                changeProfileUseCase(profile)
                showCachedProfile()
            } catch (e: HttpException) {
                if (e.code() == UNAUTHORIZED) {
                    _state.value = ProfileState.AuthorisationError
                } else {
                    throw e
                }
            }
        }
    }

    private fun showProfile() {
        viewModelScope.launch {
            try {
                profileSimilarity = ProfileSimilarity()

                _state.value = ProfileState.Loading

                profile = getProfileUseCase()
                _state.value = ProfileState.Profile(
                    avatarLink = profile.avatarLink,
                    birthDate = profile.birthDate,
                    email = profile.email,
                    gender = if (profile.gender == MALE_GENDER) Gender.MALE else Gender.FEMALE,
                    name = profile.name,
                    nickName = profile.nickName
                )
            } catch (e: HttpException) {
                if (e.code() == UNAUTHORIZED) {
                    _state.value = ProfileState.AuthorisationError
                } else {
                    throw e
                }
            }
        }
    }

    private fun genderChanged(gender: Gender) {
        if (_state.value !is ProfileState.ProfileChanged) setProfileChanged()

        profileSimilarity.gender = gender.ordinal == profile.gender

        _state.value = (_state.value as ProfileState.ProfileChanged).copy(
            gender = gender,
            isValid = false
        )

        if (!profileSimilarity.gender) checkError()
    }


    private fun avatarChanged(avatarLink: String) {
        if (_state.value !is ProfileState.ProfileChanged) setProfileChanged()

        profileSimilarity.avatarLink =
            avatarLink == profile.avatarLink || (avatarLink.isBlank() && profile.avatarLink == null)
        val isSuccess = validateUrlUseCase(avatarLink)

        _state.value = (_state.value as ProfileState.ProfileChanged).copy(
            avatarLinkError = if (isSuccess || avatarLink.isBlank()) null else UiText.StringResource(
                R.string.url_error
            ),
            isValid = false
        )

        checkError()
    }


    private fun birthdayChanged(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if (_state.value !is ProfileState.ProfileChanged) setProfileChanged()
        val birthDate = formatDateUseCase(
            year,
            monthOfYear,
            dayOfMonth
        )
        profileSimilarity.birthDate = birthDate == profile.birthDate

        _state.value = (_state.value as ProfileState.ProfileChanged).copy(
            birthDate = birthDate,
            isValid = false
        )
        if (!profileSimilarity.birthDate) checkError()
    }

    private fun firstNameChanged(firstName: String) {
        if (_state.value !is ProfileState.ProfileChanged) setProfileChanged()

        profileSimilarity.name = firstName == profile.name
        isNotEmptyState = isNotEmptyState.copy(firstName = firstName.isNotBlank())

        val isSuccess = validateFirstNameUseCase(firstName)

        _state.value = (_state.value as ProfileState.ProfileChanged).copy(
            firstNameError = if (isSuccess) null else UiText.StringResource(
                R.string.min_first_name_length_error,
                MIN_FIRST_NAME_LENGTH
            ),
            isValid = false
        )

        if (isSuccess) checkError()
    }

    private fun emailChanged(email: String) {
        if (_state.value !is ProfileState.ProfileChanged) setProfileChanged()

        profileSimilarity.email = email == profile.email
        isNotEmptyState = isNotEmptyState.copy(email = email.isNotBlank())

        val isSuccess = validateEmailUseCase(email)
        _state.value = (_state.value as ProfileState.ProfileChanged).copy(
            emailError = if (isSuccess) null else UiText.StringResource(
                R.string.email_error
            ),
            isValid = false
        )

        if (isSuccess) checkError()
    }

    private fun checkError() {
        if (_state.value !is ProfileState.ProfileChanged) setProfileChanged()

        val profileState = (_state.value as ProfileState.ProfileChanged)

        val hasError = listOf(
            profileState.emailError,
            profileState.firstNameError,
            profileState.avatarLinkError
        ).any { it != null }


        val hasDifferences = listOf(
            profileSimilarity.avatarLink,
            profileSimilarity.birthDate,
            profileSimilarity.email,
            profileSimilarity.gender,
            profileSimilarity.name,
            profileSimilarity.nickName,
        ).any { !it }

        if (!hasError && hasDifferences && isNotEmptyState.email && isNotEmptyState.firstName) {
            _state.value = (_state.value as ProfileState.ProfileChanged).copy(
                isValid = true
            )
        }
    }

    private fun setProfileChanged() {
        _state.value = ProfileState.ProfileChanged(
            gender = if (profile.gender == MALE_GENDER) Gender.MALE else Gender.FEMALE,
            birthDate = profile.birthDate,
            isValid = false
        )
    }

    private companion object {
        const val MIN_FIRST_NAME_LENGTH = 2
        const val UNAUTHORIZED = 401
        const val MALE_GENDER = 0
    }
}