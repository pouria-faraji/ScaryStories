package com.blacksite.scarystories.multimedia

import android.content.Context
import android.os.Handler
import android.util.Log
import com.blacksite.scarystories.model.SoundScene
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource



class SoundManager(context: Context, var currentSound: SoundScene){
    var soundResource = context.resources.getIdentifier(currentSound.sound.resource, "raw", context.packageName)
    var player = ExoPlayerFactory.newSimpleInstance(context)!!
    var dataSpec = DataSpec(RawResourceDataSource.buildRawResourceUri(soundResource))
    private val rawResourceDataSource = RawResourceDataSource(context)
    private var audioSource_: MediaSource

    init {
        try {
            rawResourceDataSource.open(dataSpec)
        } catch (e: RawResourceDataSource.RawResourceDataSourceException) {
            e.printStackTrace()
        }
        val factory = DataSource.Factory { rawResourceDataSource }
        audioSource_= ExtractorMediaSource.Factory(factory)
                .createMediaSource(rawResourceDataSource.uri)
    }
    private var currentPosition = 0

    fun play(){
        Log.e("logger", "will start :" + currentSound.playTime.toLong() + "-----")
        val handler = Handler()
        handler.postDelayed({
            Log.e("logger", "playing ---")
            player.prepare(audioSource_)
            player.playWhenReady = true
            player.addListener(object : Player.EventListener {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        //do something
                        Log.e("logger", "finished ---")
                        player.stop()
                        player.release()
                    }
                }
            })

        }, currentSound.playTime.toLong())
    }
//    fun stop(){
//        if (player.isPlayingAd) {
//            currentPosition = mp.currentPosition
//            mp.stop()
//        }
//    }
//    fun pause(){
//        if(mp.isPlaying) {
//            mp.pause()
//            currentPosition = mp.currentPosition
//        }
//    }
//    fun resume(){
//        if (!mp.isPlaying){
////            mp.prepare();
//            mp.seekTo(currentPosition)
//            mp.start()
//        }
//    }
//
//    fun release(){
//        if(mp != null){
//            mp.release()
//            mp = null
//        }
//    }
}