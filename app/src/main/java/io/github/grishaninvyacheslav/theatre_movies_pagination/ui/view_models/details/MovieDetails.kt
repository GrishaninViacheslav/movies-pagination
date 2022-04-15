package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.view_models.details

data class MovieDetails(
    val id: String,
    val title: String?,
    val year: String?,
    val score: String?,
    val poster: String?,
    val thumbnail: String?,
    val overview: String?
)