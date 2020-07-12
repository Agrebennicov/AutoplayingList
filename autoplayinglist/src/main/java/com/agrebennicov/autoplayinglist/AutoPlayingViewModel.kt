package com.agrebennicov.autoplayinglist

import android.net.Uri

interface AutoPlayingViewModel {
    val isVideo: Boolean
    val videoUrl: Uri
}
