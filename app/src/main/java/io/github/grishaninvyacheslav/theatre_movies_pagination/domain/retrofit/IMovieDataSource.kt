package io.github.grishaninvyacheslav.theatre_movies_pagination.domain.retrofit

import retrofit2.http.*

interface IMovieDataSource {
    @GET("Top250Movies/{api_key}")
    suspend fun top250Movies(): Top250Data

    @GET("Trailer/{api_key}/{movieId}")
    suspend fun trailer(@Path("movieId") movieId: String): TrailerData

    @GET("Posters/{api_key}/{movieId}")
    suspend fun poster(@Path("movieId") movieId: String): PosterData

    @GET("Ratings/{api_key}/{movieId}")
    suspend fun ratings(@Path("movieId") movieId: String): RatingData

    @GET("Wikipedia/{api_key}/{movieId}")
    suspend fun wikipedia(@Path("movieId") movieId: String): WikipediaData
}