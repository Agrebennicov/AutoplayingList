package com.agrebennicov.autoplayinglist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player

abstract class AutoPlayingViewHolder<T : AutoPlayingViewModel>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: T)
}

abstract class AutPlayingVideoViewHolder<T : AutoPlayingViewModel>(itemView: View) :
    AutoPlayingViewHolder<T>(itemView) {
    abstract fun removePlayerAndShowCover()
    abstract fun setPlayerAndHideCover(player: Player)
    abstract fun pausePlayback()
    abstract fun resumePlayback()
}