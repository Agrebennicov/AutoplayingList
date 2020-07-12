package com.agrebennicov.autoplayinglist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player

/**
 * AutoPlayingViewHolder just a simple "Base" viewHolder class
 */
abstract class AutoPlayingViewHolder<T : AutoPlayingViewModel>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: T)
}

/**
 * Child of AutoPlayingViewHolder with some features to handle video playback
 */
abstract class AutPlayingVideoViewHolder<T : AutoPlayingViewModel>(itemView: View) :
    AutoPlayingViewHolder<T>(itemView) {
    abstract fun removePlayerAndShowCover()
    abstract fun setPlayerAndHideCover(player: Player)
    abstract fun pausePlayback()
    abstract fun resumePlayback()
}