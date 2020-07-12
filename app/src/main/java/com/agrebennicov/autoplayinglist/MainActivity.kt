package com.agrebennicov.autoplayinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val exoPlayerCache = SimpleCache(
            cacheDir,
            LeastRecentlyUsedCacheEvictor(1024 * 1024 * 1024),
            ExoDatabaseProvider(this)
        )
        val dataSourceFactory = DefaultDataSourceFactory(
            this,
            Util.getUserAgent(this, getString(R.string.app_name))
        )
        val cachedDataSource = CacheDataSourceFactory(
            exoPlayerCache,
            dataSourceFactory
        )
        val progressiveMediaSource = ProgressiveMediaSource.Factory(cachedDataSource)
        val viewHolderCreator = DefaultViewHolderCreator()
        val player = ExoPlayerFactory.newSimpleInstance(this)
        val adapter: AutoPlayingRecyclerAdapter<AdapterItem> = AutoPlayingRecyclerAdapter(
            progressiveMediaSource,
            viewHolderCreator,
            emptyMap(),
            player,
            lifecycle,
            AdapterItemDiffUtil()
        )

        with(player) {
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ALL
            addListener(adapter)
        }

        val scrollListener = AutoPlayingScrollListener(
            { adapter.playAtPosition(it) },
            R.dimen.min_view_space
        )

        val data = mutableListOf(
            AdapterItem(
                1L,
                "This is a video",
                191,
                AssetFile(
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg"
                )
            ),
            AdapterItem(
                2L,
                "And this is a video",
                135,
                AssetFile(
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg"
                )
            ),
            AdapterItem(
                3L,
                "This is a picture",
                212,
                AssetFile(
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg",
                    ""
                )
            ), AdapterItem(
                4L,
                "And another picture",
                31,
                AssetFile(
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerFun.jpg",
                    ""
                )
            ),
            AdapterItem(
                5L,
                "Last item that is a video",
                71,
                AssetFile(
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerMeltdowns.jpg"
                )
            )
        )
        list.adapter = adapter
        adapter.submitList(data)
        list.setHasFixedSize(true)
        list.addOnScrollListener(scrollListener)
    }
}
