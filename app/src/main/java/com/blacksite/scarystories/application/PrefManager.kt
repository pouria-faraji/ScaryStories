package com.blacksite.scarystories.application

import android.content.Context
import android.content.SharedPreferences

class PrefManager(internal var _context: Context) {
    internal var pref: SharedPreferences
    internal var editor: SharedPreferences.Editor

    // shared pref mode
    internal var PRIVATE_MODE = 0

    var soundStatus: Boolean
        get() = pref.getBoolean(SOUND_STATUS, true)
        set(soundStatus){
            editor.putBoolean(SOUND_STATUS, soundStatus)
            editor.commit()
        }

    var adStatus: Boolean
        get() = pref.getBoolean(AD_STATUS, true)
        set(adStatus){
            editor.putBoolean(AD_STATUS, adStatus)
            editor.commit()
        }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object {
        // Shared preferences file name
        private const val PREF_NAME = "scary_preference"
        private const val SOUND_STATUS = "sound_status"
        private const val AD_STATUS = "ad_status"
    }
}