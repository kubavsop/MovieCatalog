package com.example.moviescatalog.presentation.user_login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LoginRequest
import com.example.domain.usecase.LoginUserUseCase
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
            try {
                val loginRequest = LoginRequest(
                    username = username,
                    password = password
                )
                loginUserUseCase(loginRequest)
                _state.value = UserLoginState.Success
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = UserLoginState.Error(msg = e.message.toString())
            }
        }
    }
}