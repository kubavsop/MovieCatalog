package com.example.moviescatalog.presentation.feature_film_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.feature_film_screen.usecase.AddMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.DeleteMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.EditMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.GetMovieDetailsByIdUseCase
import com.example.domain.model.MovieDetails
import com.example.moviescatalog.presentation.feature_film_screen.recycler_view.FilmRecyclerViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmViewModel @Inject constructor(
    private val addMovieReviewUseCase: AddMovieReviewUseCase,
    private val deleteMovieReviewUseCase: DeleteMovieReviewUseCase,
    private val editMovieReviewUseCase: EditMovieReviewUseCase,
    private val getMovieDetailsByIdUseCase: GetMovieDetailsByIdUseCase
) : ViewModel() {

    private val _state = MutableLiveData<FilmState>(FilmState.Initial)
    val state: LiveData<FilmState> = _state

    fun movieDetails(id: String) {
        try {
            viewModelScope.launch {
                _state.value = FilmState.Loading
                val movieDetails = getMovieDetailsByIdUseCase(id)
                _state.value = FilmState.Content(movieDetails.toFilmItem())
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Unit // TODO
        }
    }

    private fun MovieDetails.toFilmItem(): List<FilmRecyclerViewItem> {
        return listOf(
            FilmRecyclerViewItem.HeaderItem(
                ageLimit = ageLimit,
                budget = budget,
                country = country,
                description = description,
                director = director,
                fees = fees,
                genres = genres,
                id = id,
                name = name,
                poster = poster,
                tagline = tagline,
                time = time,
                year = year,
                averageRating = String.format("%.1f", reviews.map { it.rating }.average()).toDouble()
            )
        ) + reviews.map { FilmRecyclerViewItem.ReviewItem(it) }
    }
}