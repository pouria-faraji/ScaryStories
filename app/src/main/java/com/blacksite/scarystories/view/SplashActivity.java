package com.blacksite.scarystories.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.blacksite.scarystories.R;
import com.blacksite.scarystories.customView.MuseoTextView;

public class SplashActivity extends AppCompatActivity {

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        MuseoTextView iv_title = (MuseoTextView) findViewById(R.id.splash_title);
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha_screen_splash);
        anim.reset();
        RelativeLayout l = (RelativeLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.text_fade_in);
        anim.reset();
        anim.setStartOffset(1600);
        MuseoTextView iv_title = (MuseoTextView) findViewById(R.id.splash_title);
        iv_title.clearAnimation();
        iv_title.startAnimation(anim);

        splashThread = new Thread() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 5000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } catch (InterruptedException e) {
                    // do nothing
                    Log.e("logger", "Interrupted");
                } finally {
                    SplashActivity.this.finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        };
        splashThread.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("logger", "onPause");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.e("logger", "onStop");
        if (splashThread.isAlive()) {
            splashThread.interrupt();
        }
    }
}
