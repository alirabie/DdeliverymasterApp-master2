package app.appsmatic.com.driversapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import app.appsmatic.com.driversapp.API.Models.Order;
import app.appsmatic.com.driversapp.HomeActivty;
import app.appsmatic.com.driversapp.MapsActivity;
import app.appsmatic.com.driversapp.Orders_info;
import app.appsmatic.com.driversapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Orders extends Fragment {

    private String driverID;
    TextView txt;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);







    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        driverID=HomeActivty.id;
        txt=(TextView)view.findViewById(R.id.test);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MapsActivity.class).putExtra("lat","30.5475485").putExtra("lng","31.0079185"));
            }
        });





        Genrator.createService(DriversApi.class).getOrders(driverID).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {

                txt.setText(response.body().get(0).getCustomer());

            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });







    }

}
