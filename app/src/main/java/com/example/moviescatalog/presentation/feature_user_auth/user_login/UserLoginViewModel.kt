package com.example.moviescatalog.presentation.feature_user_auth.user_login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.feature_user_auth.model.LoginRequest
import com.example.domain.feature_user_auth.usecase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _state: MutableLiveData<UserLoginState> = MutableLiveData(UserLoginState.Initial)
    val state: LiveData<UserLoginState> = _state

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = UserLoginState.Loading
            try {
                val loginRequest = LoginRequest(
                    username = username,
                    password = password
                )
                val tokenResponse = loginUserUseCase(loginRequest)
                _state.value = UserLoginState.Success
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = UserLoginState.Error(msg = e.message.toString())
            }
        }
    }
}