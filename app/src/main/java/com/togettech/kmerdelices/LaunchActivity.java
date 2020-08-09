package com.togettech.kmerdelices;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.togettech.kmerdelices.Carousel.CarouselActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends AppCompatActivity {

    @BindView(R.id.splash)
    ImageView splash;
    @BindView(R.id.slogan)
    TextView slogan;
    @BindView(R.id.togettech)
    TextView togettech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition);
        splash.startAnimation(animation);
        slogan.startAnimation(animation);
        togettech.startAnimation(animation);


        final Intent intent = new Intent(this, CarouselActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }



}
