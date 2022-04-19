package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.rv_utils.adapters

import androidx.recyclerview.widget.DiffUtil
import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.RankedMovie

class MovieDiffCallBack : DiffUtil.ItemCallback<RankedMovie>() {
    override fun areItemsTheSame(oldItem: RankedMovie, newItem: RankedMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RankedMovie, newItem: RankedMovie): Boolean {
        return oldItem == newItem
    }
}