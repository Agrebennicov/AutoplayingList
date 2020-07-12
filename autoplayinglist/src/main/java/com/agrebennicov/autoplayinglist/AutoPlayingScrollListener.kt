package com.agrebennicov.autoplayinglist

import android.graphics.Rect
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @param onFullyVisibleItem is being triggered when next/prev viewHolder's visible space was shown
 * @param  minimumVisibleSpace amount of space of viewHolder to show before triggering the event
 */
class AutoPlayingScrollListener(
    private val onFullyVisibleItem: ((position: Int) -> Unit)? = null,
    @DimenRes private val minimumVisibleSpace: Int
) : RecyclerView.OnScrollListener() {
    private var scrollDirection: ScrollDirection = UNDEFINED
    private var lastVisiblePosition = -100
    private var rect = Rect()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
        if (dy > 0) {
            scrollDirection = DOWN
        } else if (dy < 0) {
            scrollDirection = UP
        }
        if (onFullyVisibleItem != null) {
            with(layoutManager) {
                val newVisiblePosition = when (scrollDirection) {
                    UP, UNDEFINED -> findFirstVisibleItemPosition()
                    DOWN -> findLastVisibleItemPosition()
                }
                findViewByPosition(newVisiblePosition)?.getGlobalVisibleRect(rect)
                if (rect.height() > recyclerView.resources.getDimensionPixelSize(minimumVisibleSpace)) {
                    lastVisiblePosition = newVisiblePosition
                    onFullyVisibleItem.invoke(newVisiblePosition)
                }
            }
        }
    }
}
