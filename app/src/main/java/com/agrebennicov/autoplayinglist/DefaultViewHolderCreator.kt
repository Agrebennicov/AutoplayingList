package com.agrebennicov.autoplayinglist

import android.view.LayoutInflater
import android.view.ViewGroup

class DefaultViewHolderCreator : AutoPlayingViewHolderCreator<AdapterItem>() {
    override fun createVideoHolder(
        parent: ViewGroup,
        listeners: Map<String, Any>
    ): AutoPlayingViewHolder<AdapterItem> = DefaultVideoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
    )

    override fun createImageHolder(
        parent: ViewGroup,
        listeners: Map<String, Any>
    ): AutoPlayingViewHolder<AdapterItem> = DefaultImageViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
    )
}