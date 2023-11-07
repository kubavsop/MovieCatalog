package com.example.moviescatalog.presentation.feature_main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.liveData
import androidx.paging.map
import com.example.data.feature_main_screen.local.entity.MovieElementEntity
import com.example.data.common.mapper.toMovieElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    pager: Pager<Int, MovieElementEntity>
) : ViewModel() {

    var moviePagingFlow = pager
        .flow
        .map { value -> value.map { it.toMovieElement() } }
        .cachedIn(viewModelScope)
}