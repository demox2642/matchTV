package com.skilbox.matchtv

import android.app.Activity
import android.content.Intent
import android.media.MediaDrm
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.SurfaceHolder
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import androidx.navigation.fragment.navArgs
import com.skilbox.matchtv.databinding.FragmentShowMatchBinding
import com.skilbox.matchtv.network.plugins.ViewBindingFragment
import kotlinx.android.synthetic.main.fragment_show_match.*

class ShowMatchFragment :
    ViewBindingFragment<FragmentShowMatchBinding>(FragmentShowMatchBinding::inflate),
    SurfaceHolder.Callback,
    SeekBar.OnSeekBarChangeListener,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnDrmInfoListener {

    private val args: ShowMatchFragmentArgs by navArgs()
    private var URL: String ? = null

    companion object {
        const val GET_VIDEO = 123
        const val SECOND = 1000
    }

    private val mediaPlayer = MediaPlayer()
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var selectedVideoUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        URL = args.matchLinc
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnDrmInfoListener(this)
        video_view.holder.addCallback(this)
        seek_bar.setOnSeekBarChangeListener(this)
        play_button.isEnabled = false

        play_button.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                play_button.setImageResource(android.R.drawable.ic_media_play)
            } else {
                mediaPlayer.start()
                play_button.setImageResource(android.R.drawable.ic_media_pause)
            }
        }
    }
    private fun timeInString(seconds: Int): String {
        return String.format(
            "%02d:%02d",
            (seconds / 3600 * 60 + ((seconds % 3600) / 60)),
            (seconds % 60)
        )
    }

    private fun initializeSeekBar() {
        seek_bar.max = mediaPlayer.seconds
        text_progress.text = getString(R.string.default_value)
        text_total_time.text = timeInString(mediaPlayer.seconds)
        progress_bar.visibility = View.GONE
        play_button.isEnabled = true
    }

    private fun updateSeekBar() {
        runnable = Runnable {
            text_progress.text = timeInString(mediaPlayer.currentSeconds)
            seek_bar.progress = mediaPlayer.currentSeconds
            handler.postDelayed(runnable, SECOND.toLong())
        }
        handler.postDelayed(runnable, SECOND.toLong())
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        mediaPlayer.apply {

            setOnDrmInfoListener(this@ShowMatchFragment)
            setDataSource(URL)
            setDisplay(surfaceHolder)
            prepareAsync()
        }
    }

    // Ignore
    override fun surfaceChanged(surfaceHolder: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    // Ignore
    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
    }

    override fun onDrmInfo(mediaPlayer: MediaPlayer?, drmInfo: MediaPlayer.DrmInfo?) {
        mediaPlayer?.apply {
            val key = drmInfo?.supportedSchemes?.get(0)
            key?.let {
                prepareDrm(key)
                val keyRequest = getKeyRequest(
                    null, null, null,
                    MediaDrm.KEY_TYPE_STREAMING, null
                )
                provideKeyResponse(null, keyRequest.data)
            }
        }
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        initializeSeekBar()
        updateSeekBar()
        mediaPlayer!!.start()
        play_button.setImageResource(android.R.drawable.ic_media_pause)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser)
            mediaPlayer.seekTo(progress * SECOND)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GET_VIDEO) {
                selectedVideoUri = data?.data!!
                video_view.holder.addCallback(this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        mediaPlayer.release()
    }

    private val MediaPlayer.seconds: Int
        get() {
            return this.duration / SECOND
        }

    private val MediaPlayer.currentSeconds: Int
        get() {
            return this.currentPosition / SECOND
        }
}
