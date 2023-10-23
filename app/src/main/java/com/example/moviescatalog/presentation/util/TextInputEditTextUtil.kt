package com.example.moviescatalog.presentation.util

import android.view.inputmethod.EditorInfo
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.setClearFocusOnDoneClick() {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            this.clearFocus()
        }
        false
    }
}