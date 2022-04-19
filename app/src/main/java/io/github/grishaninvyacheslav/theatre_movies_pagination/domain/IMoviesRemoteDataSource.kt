package io.github.grishaninvyacheslav.theatre_movies_pagination.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface IMoviesRemoteDataSource {
    fun getMovies(): Flow<PagingData<RankedMovie>>
}