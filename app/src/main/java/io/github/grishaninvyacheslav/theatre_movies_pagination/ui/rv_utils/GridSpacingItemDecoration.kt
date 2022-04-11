package io.github.grishaninvyacheslav.theatre_movies_pagination.ui.rv_utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        outRect.left =
            spacing - column * spacing / spanCount
        outRect.right =
            (column + 1) * spacing / spanCount
        outRect.top = spacing
    }
}