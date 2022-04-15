package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.rv_utils.adapters

interface IMovieItemView {
    var id: String? // Если null, то View это placeholder
    fun setPoster(url: String)
    fun setScore(score: Float)
    fun setTitle(title: String)
    fun setReleaseYear(year: String)
}