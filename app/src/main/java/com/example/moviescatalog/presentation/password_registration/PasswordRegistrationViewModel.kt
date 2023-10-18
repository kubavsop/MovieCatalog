package com.example.moviescatalog.presentation.password_registration

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordRegistrationViewModel @Inject constructor(
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

}