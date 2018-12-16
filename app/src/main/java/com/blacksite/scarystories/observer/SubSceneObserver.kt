package com.blacksite.scarystories.observer

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.blacksite.scarystories.viewModel.SubSceneViewModel

class SubSceneObserver: LifecycleObserver {
    var viewModel: SubSceneViewModel

    constructor(viewModel: SubSceneViewModel) {
        this.viewModel = viewModel
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.e("logger", "created")
    }
}