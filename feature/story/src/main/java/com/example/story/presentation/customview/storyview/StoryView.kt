package com.example.story.presentation.customview.storyview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.example.domain.model.story.StoryUiData
import com.example.story.databinding.ItemStoryViewBinding
import com.example.story.util.Constants
import com.example.ui.extensions.extension.gone
import com.example.ui.extensions.extension.loadImageWithGlide
import com.example.ui.extensions.extension.visible
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoryView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var binding: ItemStoryViewBinding? = null
    private var player: ExoPlayer? = null


    private var startY: Float = 0f
    private var endY: Float = 0f
    private var startX: Float = 0f
    private var endX: Float = 0f


    private val swipeThreshold = 50f


    var listener: StoryViewListener? = null

    private val screenWidth = context.resources.displayMetrics.widthPixels.toFloat()
    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop


    private var startTime = 0L
    private var isVideo = false

    private var imageProgressJob: Job? = null
    private var videoProgressJob: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        binding = ItemStoryViewBinding.inflate(LayoutInflater.from(context), this, true)
        initOnclickListener()
    }

    private fun initOnclickListener() {
        binding?.buttonClose?.setOnClickListener { listener?.onSwipeDown() }
    }

    fun initUi(storyUiData: StoryUiData){
        if (storyUiData.isVideo){
            initVideoView(storyUiData.contentUrl)
        }else{
            initImageView(storyUiData.contentUrl)
        }

        binding?.textView?.text=storyUiData.title
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startY = event.y
                startX = event.x

            }

            MotionEvent.ACTION_UP -> {
                endY = event.y
                endX = event.x

                if (startY - endY > swipeThreshold) {
                    listener?.onSwipeUp()
                } else if (endY - startY > swipeThreshold) {
                    listener?.onSwipeDown()
                } else if (Math.abs(endX - startX) < touchSlop) {
                    if (startX < screenWidth / 3) {
                        listener?.onClickLeft()

                    } else {
                        listener?.onClickRight()

                    }
                }

            }

        }
        return true
    }


    fun initVideoView(url: String) {
        isVideo = true
        binding?.vvStoryVideo?.visibility = VISIBLE
        binding?.vvStoryVideo?.useController = false
        binding?.let { binding ->
            player = ExoPlayer.Builder(context).build()
            binding.vvStoryVideo.player = player
            val mediaItem = MediaItem.fromUri(url)
            val dataSourceFactory = DefaultDataSource.Factory(context)
            val mediaSource =
                ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
            player?.setMediaSource(mediaSource)

            player?.prepare()
            binding.progressCircular.visible()

            player?.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_BUFFERING -> {
                            binding.progressCircular.visible()
                        }

                        Player.STATE_READY -> {
                            binding.progressCircular.gone()
                            val videoDuration = player?.duration ?: 0
                            binding.storyProgressBar.max = videoDuration.toInt()
                            binding.storyProgressBar.visibility = ProgressBar.VISIBLE
                        }

                        Player.STATE_ENDED -> {
                            listener?.onClickRight()
                        }
                    }
                }

                override fun onPlayerError(error: com.google.android.exoplayer2.PlaybackException) {
                    binding.progressCircular.gone()
                }
            })
        }
    }


    fun initImageView(url: String) {
        binding?.ivStoryImage?.visibility = VISIBLE
        binding?.ivStoryImage?.loadImageWithGlide(url)
    }

    private fun updateImageProgressBar() {
        imageProgressJob?.cancel()
        startTime = System.currentTimeMillis()
        imageProgressJob = coroutineScope.launch {
            while (isActive) {
                val elapsedTime = System.currentTimeMillis() - startTime
                withContext(Dispatchers.Main) {
                    binding?.storyProgressBar?.max = Constants.STORY_IMAGE_DURATION
                    binding?.storyProgressBar?.progress = elapsedTime.toInt()
                }
                if (elapsedTime >= Constants.STORY_IMAGE_DURATION) {
                    listener?.onClickRight()

                    break
                }
                delay(50)
            }
        }
    }


    private fun updateProgressBar() {
        videoProgressJob?.cancel()
        videoProgressJob = coroutineScope.launch {
            while (isActive) {
                withContext(Dispatchers.Main) {
                    binding?.storyProgressBar?.progress = player?.currentPosition?.toInt() ?: 0
                }
                delay(1000)
            }
        }
    }

    fun pause() {
        player?.pause()
        player?.seekTo(0)
        videoProgressJob?.cancel()
        imageProgressJob?.cancel()
    }

    fun start() {
        if (isVideo) {
            player?.play()
            updateProgressBar()
        } else {
            updateImageProgressBar()
        }
    }

    fun release() {
        player?.release()
        player = null
        videoProgressJob?.cancel()
        imageProgressJob?.cancel()
        coroutineScope.cancel()
    }



}

interface StoryViewListener {
    fun onSwipeUp()
    fun onSwipeDown()
    fun onClickLeft()
    fun onClickRight()
}