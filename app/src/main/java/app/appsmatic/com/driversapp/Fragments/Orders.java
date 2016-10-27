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
import android.widget.ToggleButton;

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

    private RecyclerView ordersList;
    private OrdersAdb adb;
    public static List<Order>orders;
    private TextView noOrdersLable;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orders=new ArrayList<Order>();
        noOrdersLable=(TextView)getActivity().findViewById(R.id.no_orders);
        noOrdersLable.setVisibility(View.INVISIBLE);



        Genrator.createService(DriversApi.class).getOrders(HomeActivty.id).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {

                orders.addAll(response.body());
                if (orders.isEmpty()) {
                    noOrdersLable.setVisibility(View.VISIBLE);
                }

                //Setup Orders List
                adb = new OrdersAdb(orders, getActivity());
                ordersList = (RecyclerView) getActivity().findViewById(R.id.orderlist);
                ordersList.setAdapter(adb);
                ordersList.setLayoutManager(new LinearLayoutManager(getActivity()));

            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });




    }





    @Override
    public void onResume() {
        super.onResume();



    }
}
