package com.blacksite.scarystories.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.blacksite.scarystories.R
import com.blacksite.scarystories.application.SceneManager
import com.blacksite.scarystories.application.Settings
import com.blacksite.scarystories.viewModel.MainViewModel
import com.blacksite.scarystories.databinding.ActivityMainBinding
import com.blacksite.scarystories.observer.MainObserver
import com.blacksite.scarystories.viewModel.BillingViewModel
import com.blacksite.scarystories.viewModel.BillingViewModelFactory
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.schibsted.spain.parallaxlayerlayout.SensorTranslationUpdater
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private lateinit var billingViewModel: BillingViewModel
    private lateinit var sensorTranslationUpdater: SensorTranslationUpdater

    lateinit var mAdView : AdView

    internal var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setup()
        prepareObservers()
        prepareAnimation()
    }

    private fun setup() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.setLifecycleOwner(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        billingViewModel = ViewModelProviders.of(this, BillingViewModelFactory(application, this, viewModel)).get(BillingViewModel::class.java)
        activityMainBinding.mainViewModel = viewModel
        supportActionBar!!.hide()

        sensorTranslationUpdater = SensorTranslationUpdater(this)
        parallax.setTranslationUpdater(sensorTranslationUpdater)

        lifecycle.addObserver(MainObserver(viewModel))

        randomBackground()

        sound_layout.setOnClickListener(soundClickListener)
        story_1.setOnClickListener(story_1_clickListener)
        story_2.setOnClickListener(story_2_clickListener)
        story_3.setOnClickListener(story_3_clickListener)
        story_4.setOnClickListener(story_4_clickListener)
        story_5.setOnClickListener(story_5_clickListener)
        story_6.setOnClickListener(story_6_clickListener)
        story_7.setOnClickListener(story_7_clickListener)
        story_8.setOnClickListener(story_8_clickListener)
        story_9.setOnClickListener(story_9_clickListener)
        story_10.setOnClickListener(story_10_clickListener)

        no_ad_layout.setOnClickListener(noAdClickListener)
        rate_app_layout.setOnClickListener(rateAppClickListener)
        other_apps_layout.setOnClickListener(otherAppsClickListener)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        if(viewModel.prefManager.adStatus) {
            mAdView.loadAd(adRequest)
        }
    }


    fun randomBackground(){
        var backgrounds = ArrayList<Int>(Arrays.asList(
                R.drawable.main2_1000, R.drawable.main3_1000, R.drawable.main4_1000, R.drawable.main5_1000,
                R.drawable.main6_1000, R.drawable.main7_1000, R.drawable.main8_1000, R.drawable.main9_1000))
        var randomGenerator = Random()
        var index = randomGenerator.nextInt(backgrounds.size)
        main_img.setImageResource(backgrounds[index])
    }
    fun prepareObservers(){
        this.viewModel.soundStatus.observe(this, Observer {
            if(it!!){
                sound_img.setImageResource(R.drawable.baseline_volume_up_white_24)
                sound_txt.setText(R.string.sound_on)
                this.viewModel.playMusic()
            }else{
                sound_img.setImageResource(R.drawable.baseline_volume_off_white_24)
                sound_txt.setText(R.string.sound_off)
                this.viewModel.pauseMusic()
            }
        })

        this.viewModel.adStatus.observe(this, Observer {
            if(it!!){
                no_ad_layout.visibility = View.VISIBLE
                other_apps_layout.visibility = View.GONE
            }else{
                no_ad_layout.visibility = View.GONE
                other_apps_layout.visibility = View.VISIBLE
            }
        })
    }
    fun prepareAnimation(){
        var stories = ArrayList<TextView>(Arrays.asList(story_1, story_2,
                story_3,story_4,story_5,story_6,story_7,story_8,story_9,story_10/*,story_11,story_12,story_13*/))
        var offset = 1000
        for(i in 0..9){
            var generator = Random()
            var index = generator.nextInt(stories.size)
            var anim = AnimationUtils.loadAnimation(this, R.anim.text_fade_in)
            anim.reset()
            anim.startOffset = offset.toLong()
            stories[index].clearAnimation()
            stories[index].startAnimation(anim)
            stories.removeAt(index)
            offset += 500
        }
    }
    override fun onResume() {
        super.onResume()
        sensorTranslationUpdater.registerSensorManager();
    }

    override fun onPause() {
        super.onPause()
        sensorTranslationUpdater.unregisterSensorManager();
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    private val soundClickListener = View.OnClickListener {
        viewModel.toggleSound()
    }

    private val story_1_clickListener = View.OnClickListener {
        var sceneManager = SceneManager(this, R.raw.scene_1)
        sceneManager.play()
    }
    private val story_2_clickListener = View.OnClickListener {
        var sceneManager = SceneManager(this, R.raw.scene_2)
        sceneManager.play()
    }
    private val story_3_clickListener = View.OnClickListener {
        var sceneManager = SceneManager(this, R.raw.scene_3)
        sceneManager.play()
    }
    private val story_4_clickListener = View.OnClickListener {
        var sceneManager = SceneManager(this, R.raw.scene_4)
        sceneManager.play()
    }
    private val story_5_clickListener = View.OnClickListener {
        var sceneManager = SceneManager(this, R.raw.scene_5)
        sceneManager.play()
    }
    private val story_6_clickListener = View.OnClickListener {
        var sceneManager = SceneManager(this, R.raw.scene_6)
        sceneManager.play()
    }
    private val story_7_clickListener = View.OnClickListener {
        var sceneManager = SceneManager(this, R.raw.scene_7)
        sceneManager.play()
    }
    private val story_8_clickListener = View.OnClickListener {
        var sceneManager = SceneManager(this, R.raw.scene_8)
        sceneManager.play()
    }
    private val story_9_clickListener = View.OnClickListener {
        var sceneManager = SceneManager(this, R.raw.scene_9)
        sceneManager.play()
    }
    private val story_10_clickListener = View.OnClickListener {
        var sceneManager = SceneManager(this, R.raw.scene_10)
        sceneManager.play()
    }
    private val noAdClickListener = View.OnClickListener {
        billingViewModel.purchase(this, Settings.NO_AD_SKU)
    }
    private val rateAppClickListener = View.OnClickListener {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
//        billingViewModel.cancelPurchases()
    }
    private val otherAppsClickListener = View.OnClickListener {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=BlacksiteApps")))
    }
}
