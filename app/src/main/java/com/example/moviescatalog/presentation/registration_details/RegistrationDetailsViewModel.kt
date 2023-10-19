package com.example.moviescatalog.presentation.registration_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.usecase.FormatDateUseCase
import com.example.domain.usecase.ValidateEmailUseCase
import com.example.domain.usecase.ValidateFirstNameUseCase
import com.example.domain.usecase.ValidateLoginUseCase
import com.example.moviescatalog.R
import com.example.moviescatalog.presentation.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationDetailsViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateFirstNameUseCase: ValidateFirstNameUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val formatDateUseCase: FormatDateUseCase
) : ViewModel() {

    private val _state = MutableLiveData(RegistrationDetailsFormState())
    val state: LiveData<RegistrationDetailsFormState> = _state

    fun onEvent(event: RegistrationDetailsEvent) {
        when (event) {
            is RegistrationDetailsEvent.BirthdayChanged -> birthdayChanged(event.year, event.monthOfYear, event.dayOfMonth)
            is RegistrationDetailsEvent.LoginChanged -> loginChanged(event.login)
            is RegistrationDetailsEvent.EmailChanged -> emailChanged(event.email)
            is RegistrationDetailsEvent.GenderChanged -> genderChanged(event.gender)
            is RegistrationDetailsEvent.FirstNameChanged -> firstNameChanged(event.firstName)
        }
    }

    private fun birthdayChanged(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        _state.value = _state.value?.copy(birthday = formatDateUseCase(year, monthOfYear, dayOfMonth))
    }

    private fun genderChanged(gender: String) {
        _state.value = _state.value?.copy(
            gender = gender
        )
    }

    private fun firstNameChanged(firstName: String) {
        val isSuccess = validateFirstNameUseCase(firstName)
        _state.value = _state.value?.copy(
            firstNameError = if (isSuccess) null else UiText.StringResource(
                R.string.min_first_name_length_error,
                MIN_FIRST_NAME_LENGTH
            ),
            isValid = isSuccess
        )
        if (isSuccess) checkError()
    }

    private fun loginChanged(login: String) {
        val isSuccess = validateLoginUseCase(login)
        _state.value = _state.value?.copy(
            loginError = if (isSuccess) null else UiText.StringResource(
                R.string.min_login_length_error,
                MIN_LOGIN_LENGTH
            ),
            isValid = isSuccess
        )
        if (isSuccess) checkError()
    }

    private fun emailChanged(email: String) {
        val isSuccess = validateEmailUseCase(email)
        _state.value = _state.value?.copy(
            emailError = if (isSuccess) null else UiText.StringResource(
                R.string.email_error
            ),
            isValid = isSuccess
        )
        if (isSuccess) checkError()
    }

    private fun checkError() {
        val hasError = listOf(
            _state.value?.emailError,
            _state.value?.loginError,
        ).any { it != null } && !state.value?.gender.isNullOrEmpty()

        if (!hasError) {
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