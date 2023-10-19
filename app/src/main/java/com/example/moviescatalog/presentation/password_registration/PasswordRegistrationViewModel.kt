package com.example.moviescatalog.presentation.password_registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Profile
import com.example.domain.usecase.RegisterUserUseCase
import com.example.domain.usecase.ValidatePasswordUseCase
import com.example.domain.usecase.ValidateRepeatedPasswordUseCase
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
            PasswordRegistrationEvent.Register -> Unit
            is PasswordRegistrationEvent.PasswordChanged -> passwordChanged(event.password, event.repeatedPassword)
        }
    }

    private fun register(
        userName: String,
        name: String,
        password: String,
        email: String,
        birthDate: String,
        gender: Int
    ) {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true)
            try {
                val profile = Profile(
                    userName,
                    name,
                    password,
                    email,
                    birthDate,
                    gender
                )
                registerUserUseCase(profile)
                _state.value = _state.value?.copy(isLoading = false)
                _state.value = _state.value?.copy(isRegistered = true)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = _state.value?.copy(isLoading = false)
                _state.value = _state.value?.copy(registrationError = e.message.toString())
            }
        }
    }


    private fun passwordChanged(password: String, repeatedPassword: String) {
        val isPasswordSuccess = validatePasswordUseCase(password)
        val isRepeatedPasswordSuccess = validateRepeatedPasswordUseCase(password, repeatedPassword)
        _state.value = _state.value?.copy(
            repeatedPasswordError = if (isRepeatedPasswordSuccess) null else UiText.StringResource(
                R.string.repeated_password_error
            ),
            passwordError = if (isPasswordSuccess) null else UiText.StringResource(
                R.string.password_error
            ),
            isValid = isPasswordSuccess && isRepeatedPasswordSuccess
        )
    }
}