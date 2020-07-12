package com.agrebennicov.autoplayinglist

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource

class AutoPlayingRecyclerAdapter<T : AutoPlayingViewModel>(
    private val progressiveMediaSource: ProgressiveMediaSource.Factory,
    private val viewHolderCreator: AutoPlayingViewHolderCreator<T>,
    private val listeners: Map<String, Any>,
    private val player: ExoPlayer,
    lifecycle: Lifecycle,
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, AutoPlayingViewHolder<T>>(diffUtil), LifecycleObserver, Player.EventListener {
    init {
        lifecycle.addObserver(this)
    }

    private val playableHolders = hashMapOf<Int, AutPlayingVideoViewHolder<T>>()
    private var currentlyPlaying: AutPlayingVideoViewHolder<T>? = null

    override fun getItemViewType(position: Int) = viewHolderCreator.getViewType(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoPlayingViewHolder<T> {
        return viewHolderCreator.createViewHolder(parent, viewType, listeners)
    }

    override fun onBindViewHolder(holder: AutoPlayingViewHolder<T>, position: Int) {
        if (viewHolderCreator.isVideoHolder(holder.itemViewType) && holder is AutPlayingVideoViewHolder) {
            playableHolders[position] = holder
        }
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: AutoPlayingViewHolder<T>) {
        super.onViewRecycled(holder)
        playableHolders.remove(holder.adapterPosition)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        when (playbackState) {
            Player.STATE_READY -> {
                if (playWhenReady) {
                    currentlyPlaying?.setPlayerAndHideCover(player)
                }
            }
        }
    }

    fun playAtPosition(position: Int) {
        val holderToBePlayed = playableHolders[position]
        if (holderToBePlayed != null && holderToBePlayed != currentlyPlaying) {
            player.stop()
            player.prepare(progressiveMediaSource.createMediaSource(getItem(position).videoUrl))
            playableHolders.forEach { it.value.removePlayerAndShowCover() }
            currentlyPlaying?.removePlayerAndShowCover()
            holderToBePlayed.setPlayerAndHideCover(player)
            currentlyPlaying = holderToBePlayed
        } else if (holderToBePlayed == null) {
            player.stop()
            playableHolders.forEach { it.value.removePlayerAndShowCover() }
            currentlyPlaying = null
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun resumeCurrentPlayback() {
        currentlyPlaying?.resumePlayback()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun pauseCurrentPlayback() {
        currentlyPlaying?.pausePlayback()
    }
}
