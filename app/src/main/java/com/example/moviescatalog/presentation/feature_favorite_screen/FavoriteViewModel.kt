package com.example.moviescatalog.presentation.feature_favorite_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.feature_favorite_screen.usecase.GetFavoriteMoviesUseCase
import com.example.moviescatalog.presentation.feature_profile_screen.state.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {

    private var _state = MutableLiveData<FavoriteState>(FavoriteState.Initial)
    val state: LiveData<FavoriteState> = _state

    fun loadMovies() {
        viewModelScope.launch {
            try {
                _state.value = FavoriteState.Loading

                val movies = getFavoriteMoviesUseCase()

                _state.value = if (movies.isEmpty()) {
                    FavoriteState.Empty
                } else {
                    FavoriteState.Content(movies)
                }
            } catch (e: HttpException) {
                if (e.code() == UNAUTHORIZED) {
                    _state.value = FavoriteState.AuthorisationError
                }
            }
        }
    }

    private companion object {
        const val UNAUTHORIZED = 401
    }
}