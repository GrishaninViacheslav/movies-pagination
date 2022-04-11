package io.github.grishaninvyacheslav.theatre_movies_pagination.domain

data class RankedMovie(
    val id: String = "1",
    val rank: String = "1",
    val title: String = "Star Wars",
    val fullTitle: String = "Star Wars fullTitle",
    val year: String = "1999",
    val image: String = "https://m.media-amazon.com/images/M/MV5BMWU4N2FjNzYtNTVkNC00NzQ0LTg0MjAtYTJlMjFhNGUxZDFmXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_UX128_CR0,3,128,176_AL_.jpg",
    val crew: String = "Dude",
    val imDbRating: String = "9.5",
    val imDbRatingCount: String = "5"
)