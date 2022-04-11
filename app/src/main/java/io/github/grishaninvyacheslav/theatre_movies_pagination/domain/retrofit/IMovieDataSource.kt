package io.github.grishaninvyacheslav.theatre_movies_pagination.domain.retrofit

import retrofit2.http.*

interface IMovieDataSource {
    @GET("Top250Movies/k_83zoojen")
    suspend fun top250Movies(): Top250Data
}