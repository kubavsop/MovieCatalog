package com.example.moviescatalog.presentation.util

import androidx.fragment.app.Fragment
import com.example.moviescatalog.presentation.MainActivity

val Fragment.mainActivity: MainActivity
    get() = requireActivity() as MainActivity