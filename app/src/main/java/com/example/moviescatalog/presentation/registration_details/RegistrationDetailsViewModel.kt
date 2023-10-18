package com.example.moviescatalog.presentation.registration_details

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.ValidateEmailUseCase
import com.example.domain.usecase.ValidateFirstNameUseCase
import com.example.domain.usecase.ValidateLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationDetailsViewModel @Inject constructor(
    validateEmailUseCase: ValidateEmailUseCase,
    validateFirstNameUseCase: ValidateFirstNameUseCase,
    validateLoginUseCase: ValidateLoginUseCase
) : ViewModel() {

}