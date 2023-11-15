package com.example.moviescatalog.presentation.util

import android.content.Context
import androidx.annotation.StringRes



sealed class UiText {

    data class DynamicString(val value: String): UiText()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UiText()

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}
//class UiText(
//    @StringRes private val resId: Int,
//    private vararg val args: Any
//) {
//    fun asString(context: Context): String {
//        return context.getString(resId, *args)
//    }
//}
