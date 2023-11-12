package com.example.moviescatalog.presentation.feature_main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import androidx.paging.map
import com.example.data.feature_main_screen.local.entity.MovieElementEntity
import com.example.data.common.mapper.toMovieElement
import com.example.domain.feature_main_screen.usecase.GetMoviesByPageUseCase
import com.example.domain.feature_main_screen.usecase.GetRatingByMovieIdUseCase
import com.example.domain.model.MovieElement
import com.example.moviescatalog.presentation.feature_main_screen.recycler_view.MainRecyclerViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    pager: Pager<Int, MovieElementEntity>,
    private val getMoviesByPageUseCase: GetMoviesByPageUseCase,
    private val getRatingByMovieIdUseCase: GetRatingByMovieIdUseCase
) : ViewModel() {

    val state: StateFlow<PagingData<MainRecyclerViewItem>> = pager
        .flow
        .flowOn(Dispatchers.IO)
        .map { value ->
            val movies = getMoviesByPageUseCase(FIRST_PAGE).movies
            var mappedValue: PagingData<MainRecyclerViewItem> =
                value.map { MainRecyclerViewItem.MovieItem(it.toMovieElement()) }

            mappedValue = movies.drop(MOVIES_IN_THE_CAROUSEL).reversed()
                .fold(mappedValue) { acc, movieElement ->
                    acc.insertHeaderItem(
                        TerminalSeparatorType.SOURCE_COMPLETE,
                        MainRecyclerViewItem.MovieItem(movieElement.copy(userRating = getRatingByMovieIdUseCase(movieElement.id)))
                    )
                }

            mappedValue.insertHeaderItem(
                TerminalSeparatorType.SOURCE_COMPLETE,
                MainRecyclerViewItem.HeaderItem(movies.take(MOVIES_IN_THE_CAROUSEL))
            )
        }.cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())

    private companion object {
        const val MOVIES_IN_THE_CAROUSEL = 4
        const val FIRST_PAGE = 1
    }
}