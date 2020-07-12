package com.agrebennicov.autoplayinglist

import android.net.Uri


/**
 * AutoPlayingViewModel must be implemented by your data class that
 * you are going to pass into the adapter
 * @see AutoPlayingRecyclerAdapter
 */
interface AutoPlayingViewModel {
    val isVideo: Boolean
    val videoUrl: Uri
}
