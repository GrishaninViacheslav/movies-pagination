package io.github.grishaninvyacheslav.theatre_movies_pagination.domain.retrofit

data class RatingData(
    val imDbId: String,
    val title: String,
    val fullTitle: String,
    val type: String,
    val year: String,
    val imDb: String,
    val metacritic: String,
    val theMovieDb: String,
    val rottenTomatoes: String,
    val filmAffinity: String
)