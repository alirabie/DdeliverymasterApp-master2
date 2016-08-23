package app.appsmatic.com.driversapp;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);




        //Splash Duration
        Thread timer = new Thread() {
            public void run() {
                try {
                    //Animation
                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
                    anim.reset();
                    LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
                    l.clearAnimation();
                    l.startAnimation(anim);
                    anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
                    anim.reset();
                    ImageView iv = (ImageView) findViewById(R.id.logo);
                    iv.clearAnimation();
                    iv.startAnimation(anim);
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //Finally Go to Login Activity
                    Intent i = new Intent(Splash.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();





    }

}
