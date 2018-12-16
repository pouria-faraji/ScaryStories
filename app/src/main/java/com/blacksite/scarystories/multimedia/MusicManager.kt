package com.blacksite.scarystories.multimedia

import android.content.Context
import com.blacksite.scarystories.model.database.Music
import android.media.MediaPlayer


class MusicManager(context: Context, var currentMusic: Music, var loop:Boolean) {
    var soundResource = context.resources.getIdentifier(currentMusic.resourceID, "raw", context.packageName)
    var mp = MediaPlayer.create(context, soundResource)
    init {
        mp.isLooping = loop
    }
    var currentPosition = 0
    fun play(){
        mp.start()
    }

    fun stop(){
        if (mp.isPlaying) {
            currentPosition = mp.currentPosition
            mp.stop()
        }
    }
    fun pause(){
        if(mp.isPlaying) {
            mp.pause()
            currentPosition = mp.currentPosition
        }
    }
    fun resume(){
        if (!mp.isPlaying){
//            mp.prepare();
            mp.seekTo(currentPosition)
            mp.start()
        }
    }
    fun release(){
        if(mp != null){
            mp.release()
            mp = null
        }
    }
}