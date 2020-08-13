package com.WizGuys.eStudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static  int SPLASH_SCREEN = 3000;

    //Variables
    Animation topAnimation, bottomAnimation;
    //Set animation to out text and image
    ImageView image;
    TextView topText, bottomText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide time battery icons etc...
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

       setContentView(R.layout.activity_main);

        //Set Animations
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageLogo);
        topText = findViewById(R.id.textViewTop);
        bottomText = findViewById(R.id.textViewBottom);

        //Assign Animation
        image.setAnimation(topAnimation);
        topText.setAnimation(topAnimation);
        bottomText.setAnimation(bottomAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
              finish();
            }
        },SPLASH_SCREEN);
    }
}