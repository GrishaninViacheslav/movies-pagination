package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.IMoviesRemoteDataSource
import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.RankedMovie
import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.MovieService
import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.MoviesRemoteDataSourceImpl
import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.retrofit.IMovieDataSource
import kotlinx.coroutines.flow.Flow

class MainViewModel(val api: IMovieDataSource) : ViewModel() {
    private val moviesRepository: IMoviesRemoteDataSource = MoviesRemoteDataSourceImpl(MovieService(api))

    fun getMovies(): Flow<PagingData<RankedMovie>> {
        return moviesRepository.getMovies().cachedIn(viewModelScope)
    }
}