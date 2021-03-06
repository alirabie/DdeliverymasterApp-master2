package app.appsmatic.com.driversapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.ChangeStautMsg;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import app.appsmatic.com.driversapp.API.Models.OrderDetail;
import app.appsmatic.com.driversapp.API.Models.ResConfirmOrder;
import app.appsmatic.com.driversapp.API.Models.ResOrderDetails;
import app.appsmatic.com.driversapp.Adabters.Items_Adb;
import app.appsmatic.com.driversapp.GPS.GPSTracker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Orders_info extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Float lat;
    Float lng;
    int statusId;
    Float totalprice;
    String orderId,custmerName,phoneNum,time,comit;
    RecyclerView itemslist;
    TextView name,id2,duration;
    TextView deleveredbtn,onwaybtn,donebtn,failedbtn,tv_total_price,coment_tv;

    private ImageView sms,phone,backbtn,mapBtn;
    private TextView delevered,packed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_info);


        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        //Back button setup
        backbtn=(ImageView)findViewById(R.id.back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Receive Data from Orders
        Bundle extras = getIntent().getExtras();
        lat=Float.parseFloat(extras.get("lat").toString());
        lng=Float.parseFloat(extras.get("lng").toString());
        orderId=extras.getString("orderId");
        custmerName=extras.getString("custname");
        phoneNum=extras.getString("phone");
        statusId=extras.getInt("statusID");
        totalprice=extras.getFloat("totalPrice");
        time=extras.getString("ordertime");
        comit=extras.getString("comment");

        //user comment
        coment_tv=(TextView)findViewById(R.id.coment);
        coment_tv.setText(comit);




        //Packed And Delevered Order Config


        deleveredbtn=(TextView)findViewById(R.id.order_details_delevered);
        donebtn=(TextView)findViewById(R.id.order_details_done);
        failedbtn=(TextView)findViewById(R.id.order_details_failed_btn);

        //Set Buttons Invisible

        donebtn.setVisibility(View.INVISIBLE);













        //Failed Button

        failedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(Orders_info.this);
                builder.setMessage("Are you Sure ??")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                HashMap code2=new HashMap();
                                code2.put("orderid", orderId);
                                code2.put("status", "4");

                                Genrator.createService(DriversApi.class).changeStautMsg(code2).enqueue(new Callback<ResConfirmOrder>() {
                                    @Override
                                    public void onResponse(Call<ResConfirmOrder> call, Response<ResConfirmOrder> response) {
                                        if(response.isSuccess()) {
                                            Toast.makeText(getApplicationContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResConfirmOrder> call, Throwable t) {

                                        Toast.makeText(getApplicationContext(),t.getMessage()+"", Toast.LENGTH_SHORT).show();

                                    }
                                });

                                failedbtn.setVisibility(View.INVISIBLE);
                                deleveredbtn.setVisibility(View.INVISIBLE);
                                donebtn.setVisibility(View.VISIBLE);
                                donebtn.setText("Failed");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();

                            }
                        }).setIcon(android.R.drawable.alert_light_frame);
                AlertDialog alert = builder.create();
                alert.show();


            }

        });



        //Delivered Button
        deleveredbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Orders_info.this);
                builder.setMessage("Are you Sure ??")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HashMap code=new HashMap();
                                code.put("orderid", orderId);
                                code.put("status", "3");

                                Genrator.createService(DriversApi.class).changeStautMsg(code).enqueue(new Callback<ResConfirmOrder>() {
                                    @Override
                                    public void onResponse(Call<ResConfirmOrder> call, Response<ResConfirmOrder> response) {

                                        if(response.isSuccess()){
                                        Toast.makeText(getApplicationContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                                        finish();}else {
                                            Toast.makeText(getApplicationContext(), response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<ResConfirmOrder> call, Throwable t) {

                                        Toast.makeText(getApplicationContext(),t.getMessage()+"",Toast.LENGTH_SHORT).show();

                                    }
                                });


                                deleveredbtn.setVisibility(View.INVISIBLE);
                                failedbtn.setVisibility(View.INVISIBLE);
                                donebtn.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();

                            }
                        }).setIcon(android.R.drawable.alert_light_frame);
                AlertDialog alert = builder.create();
                alert.show();


            }
        });



        HashMap id=new HashMap();
        id.put("OrderID", orderId);


        // Order Items List
        Genrator.createService(DriversApi.class).getOrderDetails(id).enqueue(new Callback<ResOrderDetails>() {
            @Override
            public void onResponse(Call<ResOrderDetails> call, Response<ResOrderDetails> response) {

                if(response.isSuccess()) {
                    itemslist = (RecyclerView) findViewById(R.id.item_order_list);
                    itemslist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    itemslist.setAdapter(new Items_Adb(getApplicationContext(), response.body().getMessage()));
                    name = (TextView) findViewById(R.id.tv_info_cust_name);
                    id2 = (TextView) findViewById(R.id.tv_orser_info_id);
                    tv_total_price = (TextView) findViewById(R.id.order_details_tv_totalprice);

                    id2.setText("Order# " + orderId);
                    name.setText(custmerName);
                    tv_total_price.setText("Total Price : " + totalprice + " SR");
                }else {


                    final AlertDialog.Builder builder = new AlertDialog.Builder(Orders_info.this);
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
            public void onFailure(Call<ResOrderDetails> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage()+"",Toast.LENGTH_SHORT).show();
            }
        });



        //Sms Button
        sms=(ImageView)findViewById(R.id.order_details_sms_btn);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNum)));

            }
        });



        //Map Button
        mapBtn=(ImageView)findViewById(R.id.order_details_MAP_btn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                GPSTracker gpsTracker=new GPSTracker(getApplicationContext());
                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+gpsTracker.getLatitude()+","+gpsTracker.getLongitude()+"&daddr="+lat+","+lng;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                try
                {
                   startActivity(intent);
                }
                catch(ActivityNotFoundException ex)
                {
                    try
                    {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(unrestrictedIntent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        Toast.makeText(getApplicationContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });



        //Call Button
        phone=(ImageView)findViewById(R.id.order_details_call_btn);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String uri = "tel:"+phoneNum;
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
                    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    callIntent.addFlags(Intent.FLAG_FROM_BACKGROUND);

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);

                } catch (Exception e)

                {

                    Toast.makeText(getApplicationContext(), "Your call has failed...", Toast.LENGTH_LONG).show();
                    e.printStackTrace();


                }

            }
        });








        //Set Time to Order in orders info
        duration=(TextView)findViewById(R.id.timetodlver);

        if(time==null){

            duration.setText("Not Set");

        }else{
/*
            String ackwardDate=time;
            Calendar calendar = Calendar.getInstance();
            String ackwardRipOff = ackwardDate.replace("/Date(", "").replace(")/", "");
            Long timeInMillis = Long.valueOf(ackwardRipOff);
            calendar.setTimeInMillis(timeInMillis);
            */
            duration.setText(time+"");

        }












        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in city and move the camera
        LatLng latLng = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(latLng).title(custmerName));
        float zoomLevel = (float) 16.0; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

    }
}
