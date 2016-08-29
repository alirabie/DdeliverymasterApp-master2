package app.appsmatic.com.driversapp.Fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.ArchivedOrder;
import app.appsmatic.com.driversapp.Adabters.ArchivedOrdersAdb;
import app.appsmatic.com.driversapp.HomeActivty;
import app.appsmatic.com.driversapp.MapsActivity;
import app.appsmatic.com.driversapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Archived extends Fragment {

    private String driverID;
    TextView txt;
    RecyclerView arclist;
    ArchivedOrdersAdb adb;
    TextView total;
    float totalvalue=0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archived, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        driverID= HomeActivty.id;


        total=(TextView)getActivity().findViewById(R.id.ar_total);
        arclist = (RecyclerView) getActivity().findViewById(R.id.archived_list);


        Genrator.createService(DriversApi.class).getArchivedOrders(driverID).enqueue(new Callback<List<ArchivedOrder>>() {
            @Override
            public void onResponse(Call<List<ArchivedOrder>> call, Response<List<ArchivedOrder>> response) {

                adb = new ArchivedOrdersAdb(response.body(), getContext());
                arclist.setAdapter(adb);
                arclist.setLayoutManager(new LinearLayoutManager(getContext()));

                for (int i = 0; i < response.body().size(); i++) {

                    totalvalue += response.body().get(i).getTotalAmount();
                }

                total.setText("TOTAL PRICE : " + totalvalue + " SR");

            }

            @Override
            public void onFailure(Call<List<ArchivedOrder>> call, Throwable t) {

            }
        });














    }

    @Override
    public void onResume() {
        super.onResume();


        Genrator.createService(DriversApi.class).getArchivedOrders(driverID).enqueue(new Callback<List<ArchivedOrder>>() {
            @Override
            public void onResponse(Call<List<ArchivedOrder>> call, Response<List<ArchivedOrder>> response) {
                totalvalue=0;
                adb = new ArchivedOrdersAdb(response.body(), getContext());
                arclist.setAdapter(adb);
                arclist.setLayoutManager(new LinearLayoutManager(getContext()));

                for (int i = 0; i < response.body().size(); i++) {

                    totalvalue += response.body().get(i).getTotalAmount();
                }

                total.setText("TOTAL PRICE : " + totalvalue + " SR");

            }

            @Override
            public void onFailure(Call<List<ArchivedOrder>> call, Throwable t) {

            }
        });




    }
}
