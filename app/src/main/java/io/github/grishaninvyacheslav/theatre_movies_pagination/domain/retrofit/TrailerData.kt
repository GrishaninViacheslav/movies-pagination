package io.github.grishaninvyacheslav.theatre_movies_pagination.domain.retrofit

data class TrailerData(
    val imDbId: String,
    val title: String,
    val fullTitle: String,
    val type: String,
    val year: String,
    val videoId: String,
    val videoTitle: String,
    val videoDescription: String,
    val thumbnailUrl: String,
    val uploadDate: String,
    val link: String,
    val linkEmbed: String
)