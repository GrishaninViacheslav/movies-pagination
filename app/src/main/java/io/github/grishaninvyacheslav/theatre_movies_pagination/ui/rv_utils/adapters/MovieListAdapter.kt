package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.rv_utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import io.github.grishaninvyacheslav.theatre_movies_pagination.databinding.ItemMovieBinding
import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.RankedMovie

class MovieListAdapter(
    private val dataModel: IMoviesDataModel,
    private var onItemClick: ((view: IMovieItemView) -> Unit)
) : PagingDataAdapter<RankedMovie, MovieViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )

    override fun onBindViewHolder(holderMarker: MovieViewHolder, position: Int) =
        dataModel.bindView(holderMarker, getItem(position))
}