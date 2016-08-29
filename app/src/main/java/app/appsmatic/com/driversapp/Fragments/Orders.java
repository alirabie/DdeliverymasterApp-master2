package app.appsmatic.com.driversapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import app.appsmatic.com.driversapp.API.Models.Order;
import app.appsmatic.com.driversapp.Adabters.OrdersAdb;
import app.appsmatic.com.driversapp.HomeActivty;
import app.appsmatic.com.driversapp.MapsActivity;
import app.appsmatic.com.driversapp.Orders_info;
import app.appsmatic.com.driversapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Orders extends Fragment {

    private String driverID;
    RecyclerView ordersList;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);








    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        driverID=HomeActivty.id;






        Genrator.createService(DriversApi.class).getOrders(driverID).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                ordersList = (RecyclerView) getActivity().findViewById(R.id.orderlist);
                ordersList.setAdapter(new OrdersAdb(response.body(), getContext()));
                ordersList.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });







    }

    @Override
    public void onResume() {
        super.onResume();
        Genrator.createService(DriversApi.class).getOrders(driverID).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                OrdersAdb adb=new OrdersAdb(response.body(), getContext());
                ordersList = (RecyclerView) getActivity().findViewById(R.id.orderlist);
                ordersList.setAdapter(adb);
                ordersList.setLayoutManager(new LinearLayoutManager(getContext()));

            }
            //k

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }
}
