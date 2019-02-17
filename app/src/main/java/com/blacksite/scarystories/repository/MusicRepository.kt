package com.blacksite.scarystories.repository

import android.app.Application
import com.blacksite.scarystories.R
import com.blacksite.scarystories.asyncTask.InsertAsyncTask
import com.blacksite.scarystories.model.database.AppDatabase
import com.blacksite.scarystories.model.database.Music
import com.blacksite.scarystories.model.interfaces.MusicDao
import java.util.*

class MusicRepository {
    private var musicDao:MusicDao? = null
    constructor(application: Application){
        this.musicDao = AppDatabase.getDatabase(application).musicDao()
        this.insert(Music(1, "darkshadow"))
        this.insert(Music(2, "ayasiikuuki"))
        this.insert(Music(3, "radionoise"))
        this.insert(Music(4, "darkshadow_loop"))
        this.insert(Music(5, "deadend_long"))
        this.insert(Music(6, "episode3"))
    }
    fun getRandomMusic():Music{
        var randomGenerator = Random()
        var index = randomGenerator.nextInt(this.getMusics().size)
        var music = this.getMusics()[index]
        return music
    }
    fun getMusics():ArrayList<Music>{
        return this.musicDao!!.getAll() as ArrayList<Music>
    }
    fun insert(music: Music){
        InsertAsyncTask(this.musicDao!!, music).execute()
    }
}