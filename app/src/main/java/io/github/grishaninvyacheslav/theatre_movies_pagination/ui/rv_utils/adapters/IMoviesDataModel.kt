package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.rv_utils.adapters

import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.RankedMovie

interface IMoviesDataModel {
    fun bindView(view: IMovieItemView, data: RankedMovie?)
}
