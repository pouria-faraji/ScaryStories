package com.blacksite.scarystories.asyncTask

import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.blacksite.scarystories.model.database.Music

import com.blacksite.scarystories.model.interfaces.MusicDao


class InsertAsyncTask internal constructor(val musicDao: MusicDao, val music: Music) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg params: Void?): Void? {
        musicDao.insert(music) // This line throws the exception
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
    }
}