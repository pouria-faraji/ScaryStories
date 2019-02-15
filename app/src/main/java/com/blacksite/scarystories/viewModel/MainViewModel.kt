package com.blacksite.scarystories.viewModel

import android.app.Application

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import com.blacksite.scarystories.R
import com.blacksite.scarystories.application.Global
import com.blacksite.scarystories.application.PrefManager
import com.blacksite.scarystories.application.Settings
import com.blacksite.scarystories.multimedia.MusicManager
import com.blacksite.scarystories.repository.MusicRepository
class MainViewModel(application: Application) : AndroidViewModel(application){
    var prefManager = PrefManager(application)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    var  backgroundImg1:Drawable = application.getDrawable(R.drawable.girl_1000)

    var backgroundWidth:Float = ((Settings.APP_WIDTH + Settings.APP_WIDTH/8).toFloat())
    var backgroundHeight:Float = ((Settings.APP_HEIGHT + Settings.APP_HEIGHT/9).toFloat())

    var mainMenuWidth:Float = (((Settings.APP_WIDTH.toFloat()/1.5).toFloat()))

    var soundStatus = MutableLiveData<Boolean>()

    var story_text_size_1:Float = ((Settings.APP_HEIGHT * (0.06)).toFloat())
    var story_text_size_2:Float = ((Settings.APP_HEIGHT * (0.08)).toFloat())
    var story_text_size_3:Float = ((Settings.APP_HEIGHT * (0.10)).toFloat())
    var story_text_size_4:Float = ((Settings.APP_HEIGHT * (0.10)).toFloat())


    init {
        soundStatus.value = prefManager.soundStatus
    }
    var musicRepository = MusicRepository(application)
    var musicManager = MusicManager(application, musicRepository.getRandomMusic(), loop = true)
    fun playMusic(){
        if(soundStatus.value!!) {
            musicManager.play()
        }
    }
    fun stopMusic(){
        musicManager.stop()
    }
    fun pauseMusic(){
        musicManager.pause()
    }
    fun resumeMusic(){
        if(soundStatus.value!!) {
            musicManager.resume()
        }
    }

    fun destroyMusic(){
        musicManager.release()
    }

    fun toggleSound(){
        prefManager.soundStatus = !soundStatus.value!!
        soundStatus.value = !soundStatus.value!!
    }
}