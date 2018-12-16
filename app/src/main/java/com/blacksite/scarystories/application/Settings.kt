package com.blacksite.scarystories.application

class Settings {
    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "ScaryDB"
        val APP_VERSION_NAME = Global.getAppVersionName()
        val APP_WIDTH = Global.getAppWidth()
        val APP_HEIGHT = Global.getAppHeight()

        const val SUB_SCENE = "SubScene"
        const val SUB_SCENE_INDEX = "SubSceneIndex"
        const val SCENE = "Scene"
        const val SUB_SCENE_DELAY = 4000
        const val INFINITY = 99999L
        const val PARAGRAPH_FADE_DURATION = 2000L
        const val BACKGROUND_FADE_DELAY = 1000L
        const val BACKGROUND_MUSIC_DELAY = 3000L
    }
}