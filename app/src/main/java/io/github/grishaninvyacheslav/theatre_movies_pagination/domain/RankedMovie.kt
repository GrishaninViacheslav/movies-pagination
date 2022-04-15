package io.github.grishaninvyacheslav.theatre_movies_pagination.domain

data class RankedMovie(
    val id: String,
    val rank: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val crew: String,
    val imDbRating: String,
    val imDbRatingCount: String
)