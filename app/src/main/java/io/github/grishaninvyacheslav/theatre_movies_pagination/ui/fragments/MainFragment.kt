package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.fragments

import android.os.Bundle
import android.util.Size
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import io.github.grishaninvyacheslav.theatre_movies_pagination.R
import io.github.grishaninvyacheslav.theatre_movies_pagination.databinding.FragmentMainBinding
import io.github.grishaninvyacheslav.theatre_movies_pagination.domain.RankedMovie
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.extensions.convertDpToPixel
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.rv_utils.GridSpacingItemDecoration
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.rv_utils.SnapToBlock
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.rv_utils.adapters.IMovieItemView
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.rv_utils.adapters.IMoviesDataModel
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.rv_utils.adapters.MovieListAdapter
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.view_models.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.ceil
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        collectUiState()
    }

    private val viewModel: MainViewModel by viewModel()

    private fun initList() = with(binding) {
        moviesList.layoutManager = GridLayoutManager(requireContext(), 1)
        adapter = MovieListAdapter(
            moviesDataModel,
            onItemClick = { view -> /* TODO("NOT YET IMPLEMENTED") */ },
        )
        moviesList.adapter = adapter
        moviesList.viewTreeObserver.addOnGlobalLayoutListener(
            object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if(moviesList.childCount == 0){
                        return
                    }
                    val itemView = moviesList.getChildAt(0)
                        .findViewById<View>(R.id.container)
                    placeListItemsToFillGrid(
                        Size(moviesList.width, moviesList.height),
                        Size(itemView.width, itemView.height)
                    )
                    moviesList.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
    }

    private var adapter: MovieListAdapter? = null

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMovies().collectLatest { movies ->
                adapter?.submitData(movies)
            }
        }
    }

    private fun placeListItemsToFillGrid(rootViewSize: Size, itemViewSize: Size) = with(binding) {
        val columnCount = with(rootViewSize.width / itemViewSize.width) {
            if (this == 0) 1 else this
        }
        (moviesList.layoutManager as GridLayoutManager).spanCount = columnCount
        moviesList.addItemDecoration(
            GridSpacingItemDecoration(
                columnCount,
                getFillSpacing(rootViewSize.height, itemViewSize.height)
            )
        )
        if (rootViewSize.height > itemViewSize.height
        ) {
            SnapToBlock(columnCount).attachToRecyclerView(moviesList)
        }
    }

    private fun getFillSpacing(rootHeight: Int, itemHeight: Int): Int {
        val rowCount: Int = with(rootHeight / itemHeight) { if (this == 0) 1 else this }
        val fillSpacing =
            ceil((rootHeight - itemHeight * rowCount).toDouble() / (rowCount + 1)).toInt()
        return if (fillSpacing < 0) convertDpToPixel(
            5f,
            requireContext()
        ).toInt() else fillSpacing
    }

    private val moviesDataModel = object : IMoviesDataModel {
        override fun bindView(view: IMovieItemView, data: RankedMovie?) {
            data?.let {
                view.setPoster(data.image)
                view.setScore(data.imDbRating.toFloat())
                view.setTitle(data.title)
                view.setReleaseYear(data.year)
            } ?: run {
                view.setTitle("loading")
            }
        }
    }
}