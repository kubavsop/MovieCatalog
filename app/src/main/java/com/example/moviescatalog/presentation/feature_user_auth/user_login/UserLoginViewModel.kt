package com.example.moviescatalog.presentation.feature_user_auth.user_login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.model.LoginRequest
import com.example.domain.feature_user_auth.usecase.LoginUserUseCase
import com.example.domain.feature_user_auth.usecase.ValidateLoginUseCase
import com.example.domain.feature_user_auth.usecase.ValidatePasswordUseCase
import com.example.moviescatalog.R
import com.example.moviescatalog.presentation.util.UiText
import com.example.moviescatalog.presentation.feature_user_auth.user_login.state.LoginIsNotEmptyState
import com.example.moviescatalog.presentation.feature_user_auth.user_login.state.UserLoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {
    private var isNotEmptyState = LoginIsNotEmptyState()

    private val _state: MutableLiveData<UserLoginState> = MutableLiveData(UserLoginState.Initial)
    val state: LiveData<UserLoginState> = _state


    fun onEvent(event: UserLoginEvent) {
        when(event) {
            UserLoginEvent.UserLogin-> initial()
            is UserLoginEvent.Login -> login(username = event.username, password = event.password)
            is UserLoginEvent.LoginChanged -> loginChanged(event.login)
            is UserLoginEvent.PasswordChanged -> passwordChanged(event.password)
        }
    }

    private fun passwordChanged(password: String) {
        if (state.value !is UserLoginState.UserLogin) {
            _state.value = UserLoginState.UserLogin()
        }

        isNotEmptyState = isNotEmptyState.copy(password = password.isNotBlank())

        val isSuccess = validatePasswordUseCase(password)
        _state.value = (_state.value as UserLoginState.UserLogin).copy(
            passwordError = if (isSuccess) null else UiText.StringResource(
                R.string.min_password_length_error,
                MIN_PASSWORD_LENGTH
            ),
            isValid = false
        )
        checkError()
    }

    private fun loginChanged(login: String) {
        if (state.value !is UserLoginState.UserLogin) {
            _state.value = UserLoginState.UserLogin()
        }

        isNotEmptyState = isNotEmptyState.copy(login = login.isNotBlank())

        val isSuccess = validateLoginUseCase(login)
        _state.value = (_state.value as UserLoginState.UserLogin).copy(
            loginError = if (isSuccess) null else UiText.StringResource(
                R.string.min_login_length_error,
                MIN_LOGIN_LENGTH
            ),
            isValid = false
        )
        checkError()
    }

    private fun checkError() {
        val userLoginState = _state.value as UserLoginState.UserLogin
        val hasError = listOf(
            userLoginState.passwordError,
            userLoginState.loginError,
        ).any { it != null }

        if (!hasError && isNotEmptyState.login && isNotEmptyState.password) {
            _state.value = (_state.value as UserLoginState.UserLogin).copy(
                isValid = true
            )
        }
    }

    private fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                _state.value = UserLoginState.Loading
                val loginRequest = LoginRequest(
                    username = username,
                    password = password
                )
                loginUserUseCase(loginRequest)
                _state.value = UserLoginState.Success
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = UserLoginState.LoginError(msg = UiText.StringResource(R.string.login_failed))
            }
        }
    }

    private fun initial() {
        _state.value = UserLoginState.UserLogin()
    }

    private companion object {
        const val MIN_LOGIN_LENGTH = 2
        const val MIN_PASSWORD_LENGTH = 6
    }
}