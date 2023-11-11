package com.example.data.feature_main_screen.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.data.feature_main_screen.local.MovieDatabase
import com.example.data.feature_main_screen.local.entity.MovieElementEntity
import com.example.data.common.mapper.toMovieElementEntity
import com.example.data.feature_user_auth.local.UserStorage
import kotlinx.coroutines.CancellationException
import java.util.UUID

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieDatabase: MovieDatabase,
    private val userStorage: UserStorage,
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

            val moviesPagedList = moviesApi.getMoviesByPage(loadKey)
            val currentPage = moviesPagedList.pageInfo.currentPage

            movieDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    movieDatabase.dao.clearAll()
                }

                movieDatabase.dao.upsertAll(movies = moviesPagedList.movies.map { movie ->

                    val reviews = moviesApi.getMovieDetailsById(movie.id).reviews
                    var userRating: Int? = null

                    for (review in reviews) {
                        if (review.author == null) continue
                        if (review.author.userId == userStorage.getUser()
                                ?.let { UUID.fromString(it.id) }
                        ) {
                            userRating = review.rating
                            break
                        }
                    }

                    movie.toMovieElementEntity(currentPage, userRating)
                })
            }
            MediatorResult.Success(
                endOfPaginationReached = moviesPagedList.pageInfo.currentPage > moviesPagedList.pageInfo.pageCount
            )
        } catch (e: CancellationException) {
            throw e
        }
    }

    private companion object {
        const val FIRST_PAGE = 2
    }
}