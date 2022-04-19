package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import io.github.grishaninvyacheslav.theatre_movies_pagination.R
import io.github.grishaninvyacheslav.theatre_movies_pagination.databinding.FragmentDetailsBinding
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.view_models.details.DetailsViewModel
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.view_models.details.MovieDetails
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    companion object {
        private const val MOVIE_ID_KEY = "MOVIE_ID"

        fun newInstance(id: String) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putString(MOVIE_ID_KEY, id)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectUiState()
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMovieDetails(requireArguments().getString(MOVIE_ID_KEY)!!)
                .collectLatest { movieDetails ->
                    renderMovieDetails(movieDetails)
                }
        }
    }

    private fun renderMovieDetails(movieDetails: MovieDetails) = with(binding) {
        movieDetails.title?.let { title.text = it }
        movieDetails.year?.let {
            releaseDate.text = String.format(getString(R.string.release_date), it)
        }
        movieDetails.score?.let { score.value = it.toFloat() * 10 }
        movieDetails.thumbnail?.let { Glide.with(thumbnail).load(it).into(thumbnail) }
        movieDetails.poster?.let { Glide.with(poster).load(it).into(poster) }
        movieDetails.overview?.let { overview.text = String.format(getString(R.string.overview), it) }
    }

    private val viewModel: DetailsViewModel by viewModel()
}