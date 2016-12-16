package app.appsmatic.com.driversapp.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import app.appsmatic.com.driversapp.API.Models.Order;
import app.appsmatic.com.driversapp.API.Models.ResOrders;
import app.appsmatic.com.driversapp.Adabters.OrdersAdb;
import app.appsmatic.com.driversapp.HomeActivty;
import app.appsmatic.com.driversapp.LoginActivity;
import app.appsmatic.com.driversapp.MapsActivity;
import app.appsmatic.com.driversapp.Orders_info;
import app.appsmatic.com.driversapp.R;
import app.appsmatic.com.driversapp.SharedPref.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Orders extends Fragment {

    private RecyclerView ordersList;
    private OrdersAdb adb;
    public static List<Order>orders;
    private TextView noOrdersLable;
    private Button refresh;



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

        HashMap id=new HashMap();
        id.put("UserID", HomeActivty.id);

        Genrator.createService(DriversApi.class).getOrders(id).enqueue(new Callback<ResOrders>() {
            @Override
            public void onResponse(Call<ResOrders> call, Response<ResOrders> response) {

                if (response.isSuccess()) {
                    orders.addAll(response.body().getMessage());
                    if (orders.isEmpty()) {
                        noOrdersLable.setVisibility(View.VISIBLE);
                    }

                    //Log.e("hhhhhh",response.body().getMessage().get(0).getCustomer()+"");

                    //Setup Orders List
                    adb = new OrdersAdb(orders, getActivity());
                    ordersList = (RecyclerView) getActivity().findViewById(R.id.orderlist);
                    ordersList.setAdapter(adb);
                    ordersList.setLayoutManager(new LinearLayoutManager(getActivity()));
                } else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
            public void onFailure(Call<ResOrders> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage() + "hhhhhhhhh", Toast.LENGTH_SHORT).show();

            }
        });


        refresh=(Button)getActivity().findViewById(R.id.ref);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frg = null;
                frg = getFragmentManager().findFragmentByTag(getTag());
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
            }
        });



    }





    @Override
    public void onResume() {
        super.onResume();



    }
}
