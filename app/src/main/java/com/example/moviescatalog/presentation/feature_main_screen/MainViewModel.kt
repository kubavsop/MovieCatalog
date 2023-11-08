package com.example.moviescatalog.presentation.feature_main_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.data.feature_main_screen.local.entity.MovieElementEntity
import com.example.data.common.mapper.toMovieElement
import com.example.domain.feature_main_screen.usecase.GetMoviesByPageUseCase
import com.example.domain.model.MovieElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    pager: Pager<Int, MovieElementEntity>,
    private val getMoviesByPageUseCase: GetMoviesByPageUseCase
) : ViewModel() {

    private var _state = MutableLiveData<List<MovieElement>>(emptyList())
    val state = _state


    var moviePagingFlow = pager
        .flow
        .map { value -> value.map { it.toMovieElement() } }
        .cachedIn(viewModelScope)

    fun carouselMovies() {
        viewModelScope.launch {
            _state.value =
                getMoviesByPageUseCase(CAROUSEL_MOVIES_PAGE).movies.take(MOVIES_IN_THE_CAROUSEL)
        }
    }

    private companion object {
        const val CAROUSEL_MOVIES_PAGE = 1
        const val MOVIES_IN_THE_CAROUSEL = 4
    }
}