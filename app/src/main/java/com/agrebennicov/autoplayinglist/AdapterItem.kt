package com.agrebennicov.autoplayinglist

import android.net.Uri
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil

data class AdapterItem(
    val id: Long,
    val username: String,
    val likeCount: Int,
    val file: AssetFile
) : AutoPlayingViewModel {
    override val isVideo: Boolean = file.url.isNotEmpty() && file.cover.isNotEmpty()
    override val videoUrl: Uri = file.url.toUri()
}

data class AssetFile(
    val url: String,
    val cover: String
)

class AdapterItemDiffUtil : DiffUtil.ItemCallback<AdapterItem>() {
    override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean {
        return oldItem.username == newItem.username && oldItem.likeCount == newItem.likeCount
                && oldItem.file == newItem.file
    }
}