package com.example.moviescatalog.presentation.user_login

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserLoginViewModel @Inject constructor(
    loginUserUseCase: LoginUserUseCase
) : ViewModel() {

}