package io.github.grishaninvyacheslav.theatre_movies_pagination.domain

import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.retrofit.IMovieDataSource

class MovieService(private val api: IMovieDataSource) {
    private var top250MoviesBuffer: List<RankedMovie>? = null

    suspend fun getTopRatedMovies(
        page: Int
    ): List<RankedMovie> {
        if (page > networkPageCount) {
            return listOf()
        }
        val top250Movies = top250MoviesBuffer ?: api.top250Movies().items.also { top250MoviesBuffer = it }
        val endIndex = if(page * NETWORK_PAGE_SIZE < top250Movies.size) page * NETWORK_PAGE_SIZE else top250Movies.size
        return top250Movies.subList((page - 1) * NETWORK_PAGE_SIZE, endIndex)
    }
}