package com.blacksite.scarystories.observer

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import android.util.Log
import com.blacksite.scarystories.viewModel.MainViewModel

class MainObserver: LifecycleObserver {
    var mainViewModel: MainViewModel

    constructor(viewModel: MainViewModel) {
        this.mainViewModel = viewModel
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.e("logger", "created")
        this.mainViewModel.playMusic()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.e("logger", "started")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        this.mainViewModel.pauseMusic()
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        this.mainViewModel.resumeMusic()
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        this.mainViewModel.stopMusic()
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        this.mainViewModel.destroyMusic()
    }
}