package com.example.moviescatalog.presentation.util

import android.content.Context

fun com.google.android.material.textfield.TextInputLayout.setContainerError(uiText: UiText?, context: Context) {
    this.isErrorEnabled = false
    this.error = uiText?.asString(context)
    this.isErrorEnabled = uiText != null
}