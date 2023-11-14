package com.example.moviescatalog.presentation.feature_user_auth.password_registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.model.UserRegistration
import com.example.domain.feature_user_auth.usecase.RegisterUserUseCase
import com.example.domain.feature_user_auth.usecase.ValidatePasswordUseCase
import com.example.domain.feature_user_auth.usecase.ValidateRepeatedPasswordUseCase
import com.example.moviescatalog.R
import com.example.moviescatalog.presentation.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordRegistrationViewModel @Inject constructor(
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {
    private val _state = MutableLiveData(PasswordRegistrationState())
    val state: LiveData<PasswordRegistrationState> = _state


    fun onEvent(event: PasswordRegistrationEvent) {
        when (event) {
            is PasswordRegistrationEvent.Register -> register(
                event.userName,
                event.name,
                event.password,
                event.email,
                event.birthDate,
                event.gender
            )

            is PasswordRegistrationEvent.PasswordChanged -> passwordChanged(
                event.password, event.repeatedPassword
            )
            is PasswordRegistrationEvent.Content -> showContent()
        }
    }

    private fun showContent() {
        _state.value = PasswordRegistrationState()
    }

    private fun register(
        userName: String,
        name: String,
        password: String,
        email: String,
        birthDate: String,
        gender: String
    ) {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(isLoading = true)
                val profile = UserRegistration(
                    userName, name, password, email, birthDate, if (gender == MALE) 0 else 1
                )
                registerUserUseCase(profile)
                _state.value = _state.value?.copy(
                    isLoading = false,
                    isRegistered = true
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = _state.value?.copy(
                    registrationError = UiText(R.string.registration_error), isLoading = false
                )
            }
        }
    }


    private fun passwordChanged(password: String, repeatedPassword: String) {
        val isPasswordSuccess = validatePasswordUseCase(password)
        val isRepeatedPasswordSuccess = validateRepeatedPasswordUseCase(password, repeatedPassword)
        _state.value = _state.value?.copy(
            repeatedPasswordError = if (isRepeatedPasswordSuccess) null else UiText(
                R.string.repeated_password_error
            ),
            passwordError = if (isPasswordSuccess) null else UiText(
                R.string.password_error
            ),
            isValid = isPasswordSuccess && isRepeatedPasswordSuccess,
            registrationError = if (_state.value?.registrationError != null && isPasswordSuccess && isRepeatedPasswordSuccess) _state.value?.registrationError else null
        )
    }

    private companion object {
        const val MALE = "Mужской"
    }
}