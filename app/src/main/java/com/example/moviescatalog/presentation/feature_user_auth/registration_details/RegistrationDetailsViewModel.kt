package com.example.moviescatalog.presentation.feature_user_auth.registration_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.feature_user_auth.usecase.FormatDateUseCase
import com.example.domain.feature_user_auth.usecase.ValidateEmailUseCase
import com.example.domain.feature_user_auth.usecase.ValidateFirstNameUseCase
import com.example.domain.feature_user_auth.usecase.ValidateLoginUseCase
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

    private val _state = MutableLiveData(RegistrationDetailsState())
    val state: LiveData<RegistrationDetailsState> = _state

    fun onEvent(event: RegistrationDetailsEvent) {
        when (event) {
            is RegistrationDetailsEvent.BirthdayChanged -> birthdayChanged(event.year, event.monthOfYear, event.dayOfMonth)
            is RegistrationDetailsEvent.LoginChanged -> loginChanged(event.login)
            is RegistrationDetailsEvent.EmailChanged -> emailChanged(event.email)
            is RegistrationDetailsEvent.FirstNameChanged -> firstNameChanged(event.firstName)
        }
    }

    private fun birthdayChanged(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        _state.value = _state.value?.copy(birthday = formatDateUseCase(year, monthOfYear, dayOfMonth))
    }

    private fun firstNameChanged(firstName: String) {
        val isSuccess = validateFirstNameUseCase(firstName)
        _state.value = _state.value?.copy(
            firstNameError = if (isSuccess) null else UiText(
                R.string.min_first_name_length_error,
                MIN_FIRST_NAME_LENGTH
            ),
            isValid = false
        )
        if (isSuccess) checkError()
    }

    private fun loginChanged(login: String) {
        val isSuccess = validateLoginUseCase(login)
        _state.value = _state.value?.copy(
            loginError = if (isSuccess) null else UiText(
                R.string.min_login_length_error,
                MIN_LOGIN_LENGTH
            ),
            isValid = false
        )
        if (isSuccess) checkError()
    }

    private fun emailChanged(email: String) {
        val isSuccess = validateEmailUseCase(email)
        _state.value = _state.value?.copy(
            emailError = if (isSuccess) null else UiText(
                R.string.email_error
            ),
            isValid = false
        )
        if (isSuccess) checkError()
    }

    private fun checkError() {
        val hasError = listOf(
            _state.value?.emailError,
            _state.value?.loginError,
        ).any { it != null }

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