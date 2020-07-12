package com.agrebennicov.autoplayinglist

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*

class DefaultImageViewHolder(itemView: View) : AutoPlayingViewHolder<AdapterItem>(itemView) {
    override fun bind(data: AdapterItem) {
        with(itemView) {
            Glide.with(this).load(data.file.url).centerCrop().into(cover)
            username.text = data.username
            likeCounter.text = data.likeCount.toString()
        }
    }
}
