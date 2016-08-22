package app.appsmatic.com.driversapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.ArchivedOrder;
import app.appsmatic.com.driversapp.HomeActivty;
import app.appsmatic.com.driversapp.MapsActivity;
import app.appsmatic.com.driversapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Archived extends Fragment {

    private String driverID;
    TextView txt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archived, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        driverID= HomeActivty.id;
        txt=(TextView)view.findViewById(R.id.ar);






        Genrator.createService(DriversApi.class).getArchivedOrders(driverID).enqueue(new Callback<List<ArchivedOrder>>() {
            @Override
            public void onResponse(Call<List<ArchivedOrder>> call, Response<List<ArchivedOrder>> response) {

                txt.setText(response.body().get(0).getCustomer());

            }

            @Override
            public void onFailure(Call<List<ArchivedOrder>> call, Throwable t) {

            }
        });

    }
}
