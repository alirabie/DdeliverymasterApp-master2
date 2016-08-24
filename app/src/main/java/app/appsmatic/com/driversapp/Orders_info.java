package app.appsmatic.com.driversapp;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.List;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.ChangeStautMsg;
import app.appsmatic.com.driversapp.API.Models.OrderDetail;
import app.appsmatic.com.driversapp.Adabters.Items_Adb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Orders_info extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Float lat;
    Float lng;
    int statusId;
    String orderId,custmerName,phoneNum;
    RecyclerView itemslist;
    TextView name,id,duration;
    TextView deleveredbtn,packedbtn;

    private ImageView sms,phone;
    private TextView delevered,packed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_info);

        Bundle extras = getIntent().getExtras();
        lat=Float.parseFloat(extras.get("lat").toString());
        lng=Float.parseFloat(extras.get("lng").toString());
        orderId=extras.getString("orderId");
        custmerName=extras.getString("custname");
        phoneNum=extras.getString("phone");
        statusId=extras.getInt("statusID");






        //Packed And Delevered Order Config

        packedbtn=(TextView)findViewById(R.id.order_details_picked);
        deleveredbtn=(TextView)findViewById(R.id.order_details_delevered);

        packedbtn.setVisibility(View.INVISIBLE);
        deleveredbtn.setVisibility(View.INVISIBLE);

        //Check Order Status to set Button
        switch (statusId){
            case 0:
                packedbtn.setVisibility(View.VISIBLE);
                break;
            case 1:
                deleveredbtn.setVisibility(View.VISIBLE);
                break;

        }

        //When Button Packed Clicked send Code
        packedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Genrator.createService(DriversApi.class).changeStautMsg(orderId,"1").enqueue(new Callback<ChangeStautMsg>() {
                    @Override
                    public void onResponse(Call<ChangeStautMsg> call, Response<ChangeStautMsg> response) {

                        Toast.makeText(getApplicationContext(),response.body().getMessage()+"",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<ChangeStautMsg> call, Throwable t) {

                    }
                });



                packedbtn.setVisibility(View.INVISIBLE);
                deleveredbtn.setVisibility(View.VISIBLE);
            }
        });



        //When Delevered Btn Clicked send Status Id Code
        deleveredbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Genrator.createService(DriversApi.class).changeStautMsg(orderId,"2").enqueue(new Callback<ChangeStautMsg>() {
                    @Override
                    public void onResponse(Call<ChangeStautMsg> call, Response<ChangeStautMsg> response) {

                        Toast.makeText(getApplicationContext(),response.body().getMessage()+"",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<ChangeStautMsg> call, Throwable t) {

                    }
                });

                deleveredbtn.setVisibility(View.INVISIBLE);
            }
        });















        Genrator.createService(DriversApi.class).getOrderDetails(orderId).enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {

                itemslist=(RecyclerView)findViewById(R.id.item_order_list);
                itemslist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                itemslist.setAdapter(new Items_Adb(getApplicationContext(), response.body()));

                name=(TextView)findViewById(R.id.tv_info_cust_name);
                id=(TextView)findViewById(R.id.tv_orser_info_id);


                id.setText("Order# "+orderId);
                name.setText(custmerName);

            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable t) {

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













        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in city and move the camera
        LatLng latLng = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
        float zoomLevel = (float) 16.0; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

    }
}
