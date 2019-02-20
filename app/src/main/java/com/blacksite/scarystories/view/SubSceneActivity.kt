package com.blacksite.scarystories.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RelativeLayout
import com.blacksite.scarystories.R
import com.blacksite.scarystories.application.Global
import com.blacksite.scarystories.application.PrefManager
import com.blacksite.scarystories.viewModel.SubSceneViewModel
import com.schibsted.spain.parallaxlayerlayout.SensorTranslationUpdater
import com.blacksite.scarystories.databinding.ActivitySubSceneBinding
import com.blacksite.scarystories.observer.SubSceneObserver
import kotlinx.android.synthetic.main.activity_sub_scene.*
import com.blacksite.scarystories.application.Settings
import com.blacksite.scarystories.customView.MuseoTextView
import com.blacksite.scarystories.model.ParagraphScene
import com.blacksite.scarystories.model.Scene
import com.blacksite.scarystories.model.SubScene
import com.blacksite.scarystories.multimedia.SoundManager
import com.blacksite.scarystories.services.SoundService
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd


class SubSceneActivity : AppCompatActivity(){
    private lateinit var activitySubSceneBinding: ActivitySubSceneBinding
    private lateinit var viewModel: SubSceneViewModel
    private lateinit var sensorTranslationUpdater: SensorTranslationUpdater

    private lateinit var nextSubSceneActivity:AppCompatActivity
    private lateinit var currentSubScene:SubScene
    private lateinit var currentScene:Scene
    private var currentSubSceneIndex:Int = 0
    private var overallTimeSubScene:Int = 0

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
        loadData()
        createBackground()
        //playMusic()
        play()
        playSound()
    }
    private fun setup() {
        activitySubSceneBinding = DataBindingUtil.setContentView(this, R.layout.activity_sub_scene)
        activitySubSceneBinding.setLifecycleOwner(this)
        viewModel = ViewModelProviders.of(this).get(SubSceneViewModel::class.java)
        activitySubSceneBinding.subSceneViewModel = viewModel
        supportActionBar!!.hide()

        sensorTranslationUpdater = SensorTranslationUpdater(this)
        sub_scene_parallax.setTranslationUpdater(sensorTranslationUpdater)

        lifecycle.addObserver(SubSceneObserver(viewModel))

        home_layout.setOnClickListener {
            var intent = Intent(this@SubSceneActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this@SubSceneActivity.finish()
            stopService(Intent(this@SubSceneActivity, SoundService::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            showAd()
        }


        //Test ad unit ID ca-app-pub-3940256099942544/1033173712
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-5294521785008289/8972762228"
        if(viewModel.prefManager.adStatus) {
            mInterstitialAd.loadAd(AdRequest.Builder().build())
        }
    }
    private fun loadData(){
        val intent = intent
        currentSubSceneIndex = intent.getIntExtra(Settings.SUB_SCENE_INDEX, 0)
        currentScene = intent.getSerializableExtra(Settings.SCENE) as Scene
        currentSubScene = currentScene.subSceneList[currentSubSceneIndex]
    }
    private fun playSound(){
        if(this.viewModel.prefManager.soundStatus) {
            if (currentSubScene.soundSceneList != null) {
                for (i in currentSubScene.soundSceneList) {
                    var soundManager = SoundManager(this, i)
                    soundManager.play()
                }
            }
        }
    }
    private fun play(){
//        heartBeatPlay()
        for(i in currentSubScene.paragraphSceneList){
            createAddParagraphView(i)
        }
        overallTimeSubScene = currentSubScene.paragraphSceneList[currentSubScene.paragraphSceneList.size-1].playTime
    }
    private fun createBackground(){
        var backgroundResource = currentSubScene.backgroundResource
        sub_scene_background.setImageResource(resources.getIdentifier(backgroundResource, "drawable", packageName))
        sub_scene_background.alpha = 0f
        var fadeIn = ObjectAnimator.ofFloat(sub_scene_background, "alpha", 0f,1f)
        fadeIn.duration = Settings.PARAGRAPH_FADE_DURATION
        fadeIn.startDelay = Settings.BACKGROUND_FADE_DELAY
        fadeIn.start()
    }
//    private fun heartBeatPlay(){
//        var start = 0L
//        for(i in 0..19){
//            var fadeIn = ObjectAnimator.ofFloat(blood, "alpha", 0f, 1f)
//            var fadeOut = ObjectAnimator.ofFloat(blood, "alpha", 1f, 0f)
//            var fadeIn2 = ObjectAnimator.ofFloat(blood, "alpha", 0f, 1f)
//            var fadeOut2 = ObjectAnimator.ofFloat(blood, "alpha", 1f, 0f)
//            fadeIn.addListener(object : Animator.AnimatorListener{
//                override fun onAnimationRepeat(animation: Animator?) {
//                }
//
//                override fun onAnimationCancel(animation: Animator?) {
//                }
//
//                override fun onAnimationStart(animation: Animator?) {
//                }
//
//                override fun onAnimationEnd(animation: Animator?) {
//                    fadeOut.start()
//
//                    fadeIn2.start()
//                }
//
//            })
//
//            fadeIn2.addListener(object : Animator.AnimatorListener{
//                override fun onAnimationRepeat(animation: Animator?) {
//                }
//
//                override fun onAnimationEnd(animation: Animator?) {
//                    fadeOut2.start()
//                }
//
//                override fun onAnimationCancel(animation: Animator?) {
//                }
//
//                override fun onAnimationStart(animation: Animator?) {
//                }
//
//            })
//
//            start += 1000L
//            fadeIn.startDelay = start
//            fadeIn.duration = 100
//            fadeOut.duration = 100
//
//            fadeIn2.duration = 100
//            fadeOut2.duration = 100
//            fadeIn2.startDelay = start + 500
//            fadeIn.start()
//        }
//    }
    private fun createAddParagraphView(paragraphScene: ParagraphScene){
        var textView = MuseoTextView(this)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.viewModel.story_text_size_1)
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        textView.text = paragraphScene.paragraph.text
        textView.alpha = 0f
        textView.background = ContextCompat.getDrawable(this, R.drawable.paragraph_background)
        var layoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
//        var lp = textView.layoutParams as RelativeLayout.LayoutParams
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
        layoutParams.topMargin = (this.viewModel.textViewOffset * paragraphScene.position).toInt()
        var padding = Global.dp_to_px(10)
        textView.setPadding(padding,padding,padding,padding)
        textView.layoutParams = layoutParams
        sub_scene_paragraph_layout.addView(textView)


        var fadeIn = ObjectAnimator.ofFloat(textView, "alpha", 0f,1f)
        var fadeOut = ObjectAnimator.ofFloat(textView, "alpha", 1f,1f)
        fadeIn.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }
            override fun onAnimationEnd(animation: Animator) {
                fadeOut.start()
                if(fadeOut.startDelay == Settings.INFINITY){
                    var fadeIn = ObjectAnimator.ofFloat(tap_continue, "alpha", 0f,1f)
                    fadeIn.duration = Settings.PARAGRAPH_FADE_DURATION
                    fadeIn.start()
                    var fadeInHomeLayout = ObjectAnimator.ofFloat(home_layout, "alpha", 0f,1f)
                    fadeInHomeLayout.duration = Settings.PARAGRAPH_FADE_DURATION
                    fadeInHomeLayout.start()
                    sub_scene_paragraph_layout.setOnClickListener {
                        val intent:Intent
                        currentSubSceneIndex++
                        if(currentSubSceneIndex < currentScene.subSceneList.size){
                            intent = Intent(this@SubSceneActivity, SubSceneActivity::class.java)
                            intent.putExtra(Settings.SCENE, currentScene)
                            intent.putExtra(Settings.SUB_SCENE_INDEX, currentSubSceneIndex)
                            startActivity(intent)
                            this@SubSceneActivity.finish()
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                        } else{
                            intent = Intent(this@SubSceneActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            this@SubSceneActivity.finish()
                            stopService(Intent(this@SubSceneActivity, SoundService::class.java))
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                            showAd()
                        }
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        fadeIn.duration = Settings.PARAGRAPH_FADE_DURATION
        fadeOut.duration = Settings.PARAGRAPH_FADE_DURATION
        fadeIn.startDelay = paragraphScene.playTime.toLong()
        fadeOut.startDelay = paragraphScene.duration.toLong()
        fadeIn.start()
    }


    override fun onBackPressed(){
        currentSubSceneIndex--
        if(currentSubSceneIndex >= 0){
            intent = Intent(this@SubSceneActivity, SubSceneActivity::class.java)
            intent.putExtra(Settings.SCENE, currentScene)
            intent.putExtra(Settings.SUB_SCENE_INDEX, currentSubSceneIndex)
            startActivity(intent)
            this@SubSceneActivity.finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        else{
            intent = Intent(this@SubSceneActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this@SubSceneActivity.finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            stopService(Intent(this, SoundService::class.java))
            showAd()
        }
    }
    override fun onResume() {
        super.onResume()
        sensorTranslationUpdater.registerSensorManager()
    }

    override fun onPause() {
        super.onPause()
        sensorTranslationUpdater.unregisterSensorManager()
    }

    fun showAd(){
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("logger", "The interstitial wasn't loaded yet.")
        }
    }
}
