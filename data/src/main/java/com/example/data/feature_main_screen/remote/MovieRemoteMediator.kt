package com.example.data.feature_main_screen.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.data.feature_main_screen.local.MovieDatabase
import com.example.data.feature_main_screen.local.entity.MovieElementEntity
import com.example.data.common.mapper.toMovieElementEntity
import kotlinx.coroutines.CancellationException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieDatabase: MovieDatabase,
    private val moviesApi: MoviesApi
) : RemoteMediator<Int, MovieElementEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieElementEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) FIRST_PAGE
                    else lastItem.page + 1
                }
            }

            var moviesPagedList = moviesApi.getMoviesByPage(loadKey)

            if (loadKey == FIRST_PAGE) {
                moviesPagedList = moviesPagedList.copy(
                    movies = moviesPagedList.movies.drop(MOVIES_TO_SKIP)
                )
            }

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDatabase.dao.clearAll()
                }
                val currentPage = moviesPagedList.pageInfo.currentPage
                movieDatabase.dao.upsertAll(movies = moviesPagedList.movies.map {
                    it.toMovieElementEntity(currentPage)
                })
            }
            MediatorResult.Success(
                endOfPaginationReached = moviesPagedList.pageInfo.currentPage > moviesPagedList.pageInfo.pageCount
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private companion object {
        const val FIRST_PAGE = 1
        const val MOVIES_TO_SKIP = 4
    }

}