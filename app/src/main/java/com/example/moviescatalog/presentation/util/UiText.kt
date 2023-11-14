package com.example.moviescatalog.presentation.util

import android.content.Context
import androidx.annotation.StringRes

class UiText(
    @StringRes private val resId: Int,
    private vararg val args: Any
) {
    fun asString(context: Context): String {
        return context.getString(resId, *args)
    }
}
