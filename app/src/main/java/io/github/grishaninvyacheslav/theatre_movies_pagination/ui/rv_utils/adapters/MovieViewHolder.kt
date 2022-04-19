package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.rv_utils.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.grishaninvyacheslav.theatre_movies_pagination.databinding.ItemMovieBinding

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private var onItemClick: ((view: IMovieItemView) -> Unit)?,
) :
    RecyclerView.ViewHolder(binding.root),
    IMovieItemView {
    init {
        itemView.setOnClickListener { onItemClick?.invoke(this) }
    }

    override var id: String? = null

    override fun setPoster(url: String) {
        Glide.with(binding.root.context).load(url).into(binding.poster)
    }

    override fun setScore(score: Float) {
        binding.score.value = score*10
    }

    override fun setTitle(title: String) {
        binding.title.text = title
    }

    override fun setReleaseYear(year: String) {
        binding.releaseDate.text = year
    }
}