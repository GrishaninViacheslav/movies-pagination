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
import io.github.grishaninvyacheslav.theatre_movies_pagination.ui.view_models.main.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.ceil
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    companion object {
        const val GRID_SPAN_COUNT_KEY = "GRID_SPAN_COUNT"
        const val GRID_SPACING_KEY = "GRID_SPACING"
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            gridSpanCount = savedInstanceState.getInt(GRID_SPAN_COUNT_KEY)
            gridSpacing = savedInstanceState.getInt(GRID_SPACING_KEY)
        }
        initList()
        collectUiState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(GRID_SPAN_COUNT_KEY, gridSpanCount)
        outState.putInt(GRID_SPACING_KEY, gridSpacing)
    }

    private fun initList() = with(binding) {
        moviesList.layoutManager = GridLayoutManager(requireContext(), gridSpanCount)
        adapter = MovieListAdapter(
            moviesDataModel,
            onItemClick = { view -> view.id?.let { router.navigateTo(screens.details(it))  } },
        )
        moviesList.adapter = adapter
        moviesList.itemAnimator = null
        moviesList.addItemDecoration(
            GridSpacingItemDecoration(
                gridSpanCount,
                gridSpacing
            )
        )
        moviesList.viewTreeObserver.addOnGlobalLayoutListener(
            object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (moviesList.childCount == 0) {
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

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMovies().collectLatest { movies ->
                adapter?.submitData(movies)
            }
        }
    }

    private fun placeListItemsToFillGrid(rootViewSize: Size, itemViewSize: Size) = with(binding) {
        gridSpanCount = with(rootViewSize.width / itemViewSize.width) {
            if (this == 0) 1 else this
        }
        (moviesList.layoutManager as GridLayoutManager).spanCount = gridSpanCount
        gridSpacing = getFillSpacing(rootViewSize.height, itemViewSize.height)
        moviesList.addItemDecoration(
            GridSpacingItemDecoration(
                gridSpanCount,
                gridSpacing
            )
        )
        if (rootViewSize.height > itemViewSize.height
        ) {
            SnapToBlock(gridSpanCount).attachToRecyclerView(moviesList)
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
                view.id = data.id
                view.setPoster(data.image)
                view.setScore(data.imDbRating.toFloat())
                view.setTitle(data.title)
                view.setReleaseYear(data.year)
            } ?: run {
                view.setTitle(getString(R.string.loading))
            }
        }
    }

    private val viewModel: MainViewModel by viewModel()

    private var adapter: MovieListAdapter? = null

    private var gridSpanCount = 1
    private var gridSpacing = 0
}