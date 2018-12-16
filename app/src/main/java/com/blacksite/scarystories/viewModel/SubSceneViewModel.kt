package com.blacksite.scarystories.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.blacksite.scarystories.application.PrefManager
import com.blacksite.scarystories.application.Settings

class SubSceneViewModel(application: Application) : AndroidViewModel(application) {
    var prefManager = PrefManager(application)
    var backgroundWidth:Float = ((Settings.APP_WIDTH + Settings.APP_WIDTH/8).toFloat())
    var backgroundHeight:Float = ((Settings.APP_HEIGHT + Settings.APP_HEIGHT/9).toFloat())
    var footer_height:Float = ((Settings.APP_HEIGHT/10).toFloat())

    var story_text_size_1:Float = ((Settings.APP_HEIGHT * (0.03)).toFloat())
    var textViewOffset:Float = ((Settings.APP_HEIGHT /10).toFloat())

}