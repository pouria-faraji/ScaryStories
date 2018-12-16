package com.blacksite.scarystories.application

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.blacksite.scarystories.R
import com.blacksite.scarystories.model.Scene
import com.blacksite.scarystories.services.SoundService
import com.blacksite.scarystories.view.MainActivity
import com.blacksite.scarystories.view.SubSceneActivity
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader


class SceneManager(var context: Context, var sceneResource:Int) {
    var scene:Scene
    var gson = Gson()
    var prefManager = PrefManager(context)
    init {
        var inputStream = context.resources.openRawResource(sceneResource)
        val rd = BufferedReader(InputStreamReader(inputStream))
        scene = gson.fromJson(rd, Scene::class.java)
    }

    fun play(){
        val intent = Intent(context as Activity,
                SubSceneActivity::class.java)
        intent.putExtra(Settings.SCENE, scene)
        if(prefManager.soundStatus){
            val serviceIntent = Intent(context as Activity, SoundService::class.java)
            serviceIntent.putExtra(Settings.SCENE, scene)
            context.startService(serviceIntent)
        }
        (context as Activity).startActivity(intent)
        (context as Activity).overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

//        (context as MainActivity).viewModel.destroyMusic()
    }
}