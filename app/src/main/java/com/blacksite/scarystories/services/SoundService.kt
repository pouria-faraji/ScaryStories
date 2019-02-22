package com.blacksite.scarystories.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Message
import com.blacksite.scarystories.application.Settings
import com.blacksite.scarystories.model.Scene

class SoundService : Service() {
    private var mMediaPlayer: MediaPlayer? = null
    private val mRedrawHandler = RefreshHandler()
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var currentScene = intent!!.getSerializableExtra(Settings.SCENE) as Scene
        var soundResource = resources.getIdentifier(currentScene.backgroundMusic, "raw", packageName)
        mMediaPlayer = MediaPlayer.create(this, soundResource)
        mMediaPlayer!!.isLooping = true
        mRedrawHandler.sleep(Settings.BACKGROUND_MUSIC_DELAY)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy()
        mMediaPlayer!!.stop()
        mMediaPlayer!!.release()
        mRedrawHandler.removeCallbacksAndMessages(null)
    }

    internal inner class RefreshHandler : Handler() {
        override fun handleMessage(msg: Message) {
            mMediaPlayer!!.setVolume(0.3f,0.3f)
            mMediaPlayer!!.start()
        }

        fun sleep(delayMillis: Long) {
            this.removeMessages(0)
            sendMessageDelayed(obtainMessage(0), delayMillis)
        }
    }
}
