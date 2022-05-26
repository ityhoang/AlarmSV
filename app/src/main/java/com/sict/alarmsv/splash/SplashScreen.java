package com.sict.alarmsv.splash;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sict.alarmsv.R;
import com.sict.alarmsv.activity.Login;

public class SplashScreen extends AppCompatActivity {
    ImageView imgview;
    AnimationDrawable anime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        int SPLASH_TIME_OUT = 5000;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreen.this, Login.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
        imgview = (ImageView) findViewById(R.id.imageView);
        imgview.setBackgroundResource(R.drawable.animation);
        anime = (AnimationDrawable) imgview.getBackground();

//        Glide.with(getApplicationContext())
//                .load(R.drawable.cl11)
//                .error(R.drawable.splashimg)
//                .into(imgview);

        imgview.setBackgroundResource(R.drawable.animation);
        imgview.setVisibility(View.VISIBLE);

        anime = (AnimationDrawable) imgview.getBackground();
        imgview.setAnimation(null);
        anime.start();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        anime.start();
    }
}
