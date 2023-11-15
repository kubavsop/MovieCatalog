package com.example.moviescatalog.presentation.util

import com.example.moviescatalog.R

fun getAverageRatingColor(rating: Double) = when (rating) {
    in 9.0..10.0 -> R.color.color_rating_9_to
    in 8.0..8.9 -> R.color.color_rating_8_to_9
    in 6.0..7.9 -> R.color.color_rating_6_to_8
    in 4.0..5.9 -> R.color.color_rating_4_to_6
    in 3.0..3.9 -> R.color.color_rating_3_to_4
    in 0.0..2.9 -> R.color.color_rating_to_3
    else -> R.color.color_rating_9_to
}

fun getRatingColor(rating: Int) = when(rating) {
    in 9..10 -> R.color.color_rating_9_to
    in 8 until 9 -> R.color.color_rating_8_to_9
    in 6 until 8 -> R.color.color_rating_6_to_8
    in 4 until 6 -> R.color.color_rating_4_to_6
    in 3 until 4 -> R.color.color_rating_3_to_4
    in 0 until 3 -> R.color.color_rating_to_3
    else -> R.color.color_rating_9_to
}