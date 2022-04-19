package io.github.grishaninvyacheslav.theatre_movies_pagination.domain.retrofit

data class PosterData(val posters: List<Poster>) {
    data class Poster(val link: String)
}

