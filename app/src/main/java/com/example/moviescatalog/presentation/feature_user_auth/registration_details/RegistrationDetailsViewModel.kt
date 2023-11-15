package com.example.moviescatalog.presentation.feature_user_auth.registration_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.feature_user_auth.usecase.FormatDateUseCase
import com.example.domain.feature_user_auth.usecase.ValidateEmailUseCase
import com.example.domain.feature_user_auth.usecase.ValidateFirstNameUseCase
import com.example.domain.feature_user_auth.usecase.ValidateLoginUseCase
import com.example.moviescatalog.R
import com.example.moviescatalog.presentation.util.UiText
import com.example.moviescatalog.presentation.feature_user_auth.registration_details.state.DetailsIsNotEmptyState
import com.example.moviescatalog.presentation.feature_user_auth.registration_details.state.RegistrationDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationDetailsViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val formatDateUseCase: FormatDateUseCase
) : ViewModel() {
    private var emptyState = DetailsIsNotEmptyState()

    private val _state = MutableLiveData(RegistrationDetailsState())
    val state: LiveData<RegistrationDetailsState> = _state

    fun onEvent(event: RegistrationDetailsEvent) {
        when (event) {
            is RegistrationDetailsEvent.BirthdayChanged -> birthdayChanged(event.year, event.monthOfYear, event.dayOfMonth)
            is RegistrationDetailsEvent.LoginChanged -> loginChanged(event.login)
            is RegistrationDetailsEvent.EmailChanged -> emailChanged(event.email)
            is RegistrationDetailsEvent.FirstNameChanged -> firstNameChanged(event.firstName)
            is RegistrationDetailsEvent.RegistrationDetails -> _state.value = RegistrationDetailsState()
        }
    }

    private fun birthdayChanged(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        emptyState = emptyState.copy(birthday = true)
        _state.value = _state.value?.copy(birthday = formatDateUseCase(year, monthOfYear, dayOfMonth))
        checkError()
    }

    private fun firstNameChanged(firstName: String) {
        emptyState = emptyState.copy(firstName = firstName.isNotBlank())

        val isSuccess = validateFirstNameUseCase(firstName)

        _state.value = _state.value?.copy(
            firstNameError = if (isSuccess) null else UiText.StringResource(
                R.string.min_first_name_length_error,
                MIN_FIRST_NAME_LENGTH
            ),
            isValid = false
        )

        checkError()
    }

    private fun loginChanged(login: String) {
        emptyState = emptyState.copy(login = login.isNotBlank())

        val isSuccess = validateLoginUseCase(login)

        _state.value = _state.value?.copy(
            loginError = if (isSuccess) null else UiText.StringResource(
                R.string.min_login_length_error,
                MIN_LOGIN_LENGTH
            ),
            isValid = false
        )

        checkError()
    }

    private fun emailChanged(email: String) {
        emptyState = emptyState.copy(email = email.isNotBlank())

        val isSuccess = validateEmailUseCase(email)

        _state.value = _state.value?.copy(
            emailError = if (isSuccess) null else UiText.StringResource(
                R.string.email_error
            ),
            isValid = false
        )

        checkError()
    }

    private fun checkError() {
        val hasError = listOf(
            _state.value?.emailError,
            _state.value?.loginError,
            _state.value?.firstNameError
        ).any { it != null }

        val hasEmpty = listOf(
            emptyState.login,
            emptyState.firstName,
            emptyState.birthday,
            emptyState.email
        ).any { !it }

        if (!hasError && !hasEmpty) {
            _state.value = _state.value?.copy(
                isValid = true
            )
        }
    }

    private companion object {
        const val MIN_LOGIN_LENGTH = 2
        const val MIN_FIRST_NAME_LENGTH = 2
    }
}