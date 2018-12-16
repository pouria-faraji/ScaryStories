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
import com.blacksite.scarystories.viewModel.MainViewModel
import com.blacksite.scarystories.databinding.ActivityMainBinding
import com.blacksite.scarystories.observer.MainObserver
import com.schibsted.spain.parallaxlayerlayout.SensorTranslationUpdater
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private lateinit var sensorTranslationUpdater: SensorTranslationUpdater
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
        activityMainBinding.mainViewModel = viewModel
        supportActionBar!!.hide()

        sensorTranslationUpdater = SensorTranslationUpdater(this)
        parallax.setTranslationUpdater(sensorTranslationUpdater)

        lifecycle.addObserver(MainObserver(viewModel))

        sound_layout.setOnClickListener(soundClickListener)
        story_1.setOnClickListener(story_1_clickListener)
        story_2.setOnClickListener(story_2_clickListener)
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
    }
    fun prepareAnimation(){
        var stories = ArrayList<TextView>(Arrays.asList(story_1, story_2,
                story_3,story_4,story_5,story_6,story_7,story_8,story_9,story_10,story_11,story_12,story_13))
        var offset = 1000
        for(i in 0..12){
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
}
