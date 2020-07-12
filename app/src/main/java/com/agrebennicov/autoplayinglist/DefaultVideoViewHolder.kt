package com.agrebennicov.autoplayinglist

import android.view.View
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.Player
import kotlinx.android.synthetic.main.item_video.view.*

class DefaultVideoViewHolder(itemView: View) : AutPlayingVideoViewHolder<AdapterItem>(itemView) {
    override fun bind(data: AdapterItem) {
        with(itemView) {
            Glide.with(this).load(data.file.cover).centerCrop().into(cover)
            username.text = data.username
            likeCounter.text = data.likeCount.toString()
        }
    }

    override fun removePlayerAndShowCover() {
        with(itemView) {
            playerView.player = null
            cover.visibility = View.VISIBLE
        }
    }

    override fun setPlayerAndHideCover(player: Player) {
        with(itemView) {
            playerView.player = player
            cover.visibility = View.INVISIBLE
        }
    }

    override fun pausePlayback() {
        itemView.playerView.player.playWhenReady = false
    }

    override fun resumePlayback() {
        itemView.playerView.player.playWhenReady = true
    }
}
