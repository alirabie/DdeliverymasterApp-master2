package app.appsmatic.com.driversapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.Locale;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import app.appsmatic.com.driversapp.API.Models.LoginData;
import app.appsmatic.com.driversapp.SharedPref.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    LoginData loginData;
    private EditText user;
    private EditText pass;
    private ImageView logbtn;
    private String msg="";

    int code=1111;



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_main);
        loginData=new LoginData();


        //set local default english
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//Check if GPS is on or not
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Application could not be started because GPS not Enabled")
                    .setCancelable(false)
                    .setPositiveButton("Turn on Gps", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    }).setIcon(android.R.drawable.alert_light_frame);
            AlertDialog alert = builder.create();
            alert.show();
        }








        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));



        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        logbtn = (ImageView) findViewById(R.id.lognbutton);


        //Login Button
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginData.setUserName(user.getText().toString() + "");
                loginData.setPassword(pass.getText().toString() + "");


                //Loading Dialog
                final ProgressDialog mProgressDialog = new ProgressDialog(LoginActivity.this);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setIcon(android.R.drawable.ic_lock_idle_alarm);
                mProgressDialog.setTitle("Please Wait ..");
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.show();

                Genrator.createService(DriversApi.class).login(loginData).enqueue(new Callback<DriverID>() {
                    @Override
                    public void onResponse(Call<DriverID> call, Response<DriverID> response) {

                       
                        if(response.isSuccess()) {
                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();


                           String code=response.body().getCode()+"";
                            if (!code.equals("0")) {
                                LoginActivity.this.finish();
                                startActivity(new Intent(getApplicationContext(), HomeActivty.class).putExtra("DriverID", response.body().getDriverid()));
                                //Save UserName And Password in Shared Prefs
                                SaveSharedPreference.setUserName(getApplicationContext(), user.getText().toString() + "", pass.getText().toString() + "");
                            } else {
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("Authentication Error")
                                        .setMessage(response.body().getMessage()+"")
                                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
                            }
                        }else{

                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();

                            final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Response From Server Not success try again later ... !")
                                    .setCancelable(false)
                                    .setTitle("Server Not Responding")
                                    .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    }).setIcon(R.drawable.cast_ic_stop_circle_filled_grey600);
                            AlertDialog alert = builder.create();
                            alert.show();

                        }


                    }

                    @Override
                    public void onFailure(Call<DriverID> call, Throwable t) {


                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();


                        new AlertDialog.Builder(LoginActivity.this)
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


        });






    }


}



























