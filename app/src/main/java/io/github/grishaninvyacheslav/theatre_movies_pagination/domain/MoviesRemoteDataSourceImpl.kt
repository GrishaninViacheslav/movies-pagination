package io.github.grishaninvyacheslav.theatre_movies_pagination.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlin.math.ceil

const val NETWORK_ITEMS_COUNT = 250
const val NETWORK_PAGE_SIZE = 25
val networkPageCount = ceil(NETWORK_ITEMS_COUNT / NETWORK_PAGE_SIZE.toFloat())

internal class MoviesRemoteDataSourceImpl(
    private val movieService: MovieService
) : IMoviesRemoteDataSource {

    override fun getMovies(): Flow<PagingData<RankedMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service = movieService)
            }
        ).flow
    }
}