package com.example.moviescatalog.presentation.feature_film_screen

import androidx.lifecycle.ViewModel
import com.example.domain.feature_film_screen.usecase.AddMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.DeleteMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.EditMovieReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilmViewModel @Inject constructor(
    addMovieReviewUseCase: AddMovieReviewUseCase,
    deleteMovieReviewUseCase: DeleteMovieReviewUseCase,
    editMovieReviewUseCase: EditMovieReviewUseCase
):ViewModel()