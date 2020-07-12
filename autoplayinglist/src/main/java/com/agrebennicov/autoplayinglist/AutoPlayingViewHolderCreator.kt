package com.agrebennicov.autoplayinglist

import android.view.ViewGroup

private const val TYPE_VIDEO = 9123
private const val TYPE_IMAGE = 9321

abstract class AutoPlayingViewHolderCreator<T : AutoPlayingViewModel> {
    fun isVideoHolder(viewType: Int) = viewType == TYPE_VIDEO

    fun getViewType(item: T): Int = when {
        item.isVideo -> TYPE_VIDEO
        else -> TYPE_IMAGE
    }

    fun createViewHolder(
        parent: ViewGroup,
        viewType: Int,
        listeners: Map<String, Any>
    ): AutoPlayingViewHolder<T> {
        return when (viewType) {
            TYPE_VIDEO -> createVideoHolder(parent, listeners)
            TYPE_IMAGE -> createImageHolder(parent, listeners)
            else -> throw IllegalStateException("Current viewType:$viewType is not supported")
        }
    }

    protected abstract fun createVideoHolder(
        parent: ViewGroup,
        listeners: Map<String, Any>
    ): AutoPlayingViewHolder<T>

    protected abstract fun createImageHolder(
        parent: ViewGroup,
        listeners: Map<String, Any>
    ): AutoPlayingViewHolder<T>
}
