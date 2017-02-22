package app.appsmatic.com.driversapp;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.Order;
import app.appsmatic.com.driversapp.API.Models.ResLocationUpdate;
import app.appsmatic.com.driversapp.Adabters.OrdersAdb;
import app.appsmatic.com.driversapp.Adabters.VeiwpagerAdb;
import app.appsmatic.com.driversapp.FCM.Config;
import app.appsmatic.com.driversapp.FCM.NotificationUtils;
import app.appsmatic.com.driversapp.Fragments.Archived;
import app.appsmatic.com.driversapp.Fragments.Orders;
import app.appsmatic.com.driversapp.Fragments.Profile;
import app.appsmatic.com.driversapp.GPS.GPSTracker;
import app.appsmatic.com.driversapp.SharedPref.SaveSharedPreference;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivty extends AppCompatActivity {

    static double x;
    static double y;
    private int[] tabIcons = {R.drawable.orders_tab_icon,R.drawable.archived_tab_icon,R.drawable.profile_tab_icon};
    public NotificationManager manager;
    static int notId=0;
    static int count;
    private static final String TAG = HomeActivty.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Activity Animated Transition
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activty);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);



        //Check location permissions for Marshmallow
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }


        /*


       to send sound from back end

        "notification":{
    "sound":"default"
}
         */



        //Notification Receiver
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received


                        String message = intent.getStringExtra("message");



                    //Notification Message Dialog
                    final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivty.this);
                    builder.setMessage(message)
                            .setCancelable(false)
                            .setTitle("New Message")
                            .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            }).setIcon(R.drawable.icon);
                    AlertDialog alert = builder.create();
                    alert.show();


                   // Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                  //  Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                }
            }
        };

        displayFirebaseRegId();





        //set local default english
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        //Check Gps Status
        checkGpsStatus();


        //Check Os Ver For Set Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }








            //Send Location Every 5 m
            final android.os.Handler mHandler = new android.os.Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    while (true) {
                        try {
                            Thread.sleep(300000);

                            mHandler.post(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                                    x = gpsTracker.getLatitude();
                                    y = gpsTracker.getLongitude();
                                    Log.d("Locationnnnnnnnnn  ", Guid.driverGuid + "   " + x + "++++++++" + y);

                                    HashMap data = new HashMap();
                                    data.put("id", Guid.driverGuid);
                                    data.put("longtitude", y);
                                    data.put("latitude", x);

                                    Genrator.createService(DriversApi.class).updateDriverLocation(data).enqueue(new Callback<ResLocationUpdate>() {
                                        @Override
                                        public void onResponse(Call<ResLocationUpdate> call, Response<ResLocationUpdate> response) {
                                            if (response.isSuccess()) {

                                                if (response.body().getCode() == 0) {
                                                    Toast.makeText(HomeActivty.this, response.body().getMessage() + " Please login", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(HomeActivty.this, "Location Updated", Toast.LENGTH_LONG).show();
                                                }

                                            } else {
                                                Toast.makeText(HomeActivty.this, "Response not Success", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResLocationUpdate> call, Throwable t) {
                                            Toast.makeText(HomeActivty.this, "Connection Lost : " + t.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

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


    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        //  NotificationUtils.clearNotifications(getApplicationContext());




    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            Log.e(TAG, "Firebase reg id: " + regId);
        else
            Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT);
    }




public  void pushNotification(String title,String text){

    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder builder =
            (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.icon)
                    .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                    .setSound(alarmSound)
                    .setContentTitle(title)
                    .setContentText(text);

    Intent notificationIntent = new Intent(getApplicationContext(),Notification_info.class).putExtra("info", text);

    TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(getApplicationContext());
    taskStackBuilder.addParentStack(Notification_info.class);
    taskStackBuilder.addNextIntent(notificationIntent);


    PendingIntent contentIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
    builder.setContentIntent(contentIntent);
    manager=(NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
    manager.notify(notId,builder.build());
    notId++;

}

    }











