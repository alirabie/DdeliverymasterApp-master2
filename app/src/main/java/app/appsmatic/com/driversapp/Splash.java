package app.appsmatic.com.driversapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import app.appsmatic.com.driversapp.SharedPref.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                        //Check Saved Username in preference if is empty transfer to login activity
                    if(SaveSharedPreference.getUserName(getApplicationContext()).length() == 0)
                    {
                        Intent i = new Intent(Splash.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        //if saved username in prefs is not empty use it to login
                        Genrator.createService(DriversApi.class).login(SaveSharedPreference.getUserName(getApplicationContext())+"",SaveSharedPreference.getUserPassword(getApplicationContext())+"")
                                .enqueue(new Callback<DriverID>() {
                                    @Override
                                    public void onResponse(Call<DriverID> call, Response<DriverID> response) {

                                        if(response.body().getMessage()==null){

                                            Splash.this.finish();
                                            startActivity(new Intent(getApplicationContext(), HomeActivty.class).putExtra("DriverID", response.body().getDriverid()));

                                        }else {

                                            new AlertDialog.Builder(Splash.this)
                                                    .setTitle("Authentication Error")
                                                    .setMessage(response.body().getMessage()+"")
                                                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            //if user in prefs not valid go to login activity again
                                                           startActivity(new Intent(Splash.this,LoginActivity.class));
                                                        }
                                                    }).setIcon(android.R.drawable.ic_dialog_alert).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DriverID> call, Throwable t) {

                                        new AlertDialog.Builder(Splash.this)
                                                .setTitle("Connection Error")
                                                .setMessage(t.getMessage())
                                                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).setIcon(android.R.drawable.ic_dialog_alert).show();

                                    }
                                });



                    }

                }
            }
        };
        timer.start();





    }

}
