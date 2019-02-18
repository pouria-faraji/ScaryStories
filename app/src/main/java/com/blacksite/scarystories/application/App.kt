package com.blacksite.scarystories.application

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.blacksite.scarystories.repository.MusicRepository
import com.google.android.gms.ads.MobileAds
import java.util.*

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        changeSystemLocaleToEN()
        App.appContext = applicationContext
        var musicRepository = MusicRepository(this)
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")
    }
    private fun changeSystemLocaleToEN() {
        val languageToLoad = "en"
        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    companion object {
        var test = false
        var appContext: Context? = null
            private set
    }
}