package app.appsmatic.com.driversapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Handler;

import app.appsmatic.com.driversapp.Adabters.VeiwpagerAdb;
import app.appsmatic.com.driversapp.Fragments.Archived;
import app.appsmatic.com.driversapp.Fragments.Orders;
import app.appsmatic.com.driversapp.Fragments.Profile;
import app.appsmatic.com.driversapp.GPS.GPSTracker;

public class HomeActivty extends AppCompatActivity {
    public static String id;
    static double x;
    static double y;
    private int[] tabIcons = {R.drawable.orders_tab_icon,R.drawable.archived_tab_icon,R.drawable.profile_tab_icon};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Activity Animated Transition
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activty);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //Check Os Ver For Set Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }




        //Get Driver Id
        id=this.getIntent().getStringExtra("DriverID");




        //Send Location Every 10 S
        final android.os.Handler mHandler=new android.os.Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    try {
                        Thread.sleep(10000);

                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                GPSTracker gpsTracker=new GPSTracker(getApplicationContext());
                                 x=gpsTracker.getLatitude();
                                 y=gpsTracker.getLongitude();
                                Log.d("Locationnnnnnnnnn",x+"++++++++"+y);

                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        }).start();





        //Tab Layout With icons
        ViewPager p=(ViewPager)findViewById(R.id.veiwp);
        setupViewPager(p);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(p);

        //Add Icons To tabs
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);



    }



    //Setup View Pager With Fragments
    private void setupViewPager(ViewPager viewPager) {
        VeiwpagerAdb adapter = new VeiwpagerAdb(getSupportFragmentManager());
        adapter.addFragment(new Orders(), "Orders");
        adapter.addFragment(new Archived(), "Archived");
        adapter.addFragment(new Profile(), "Profile");
        viewPager.setAdapter(adapter);
    }




    //Check Gps Connection
    public void checkGpsStatus(){

        final android.os.Handler mHandler=new android.os.Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    try {
                        Thread.sleep(5000);

                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub

                                final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivty.this);
                                    builder.setMessage("Application Stopped because GPS Turned Off")
                                            .setCancelable(false)
                                            .setPositiveButton("Turn on Gps", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                    startActivity(intent);
                                                }
                                            }).setIcon(android.R.drawable.alert_light_frame);
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                }

                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        }).start();



    }











}
