package app.appsmatic.com.driversapp.Fragments;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.ArchivedOrder;
import app.appsmatic.com.driversapp.API.Models.Archived_enhanc;
import app.appsmatic.com.driversapp.API.Models.ResArchived;
import app.appsmatic.com.driversapp.Adabters.ArchivedOrdersAdb;
import app.appsmatic.com.driversapp.HomeActivty;
import app.appsmatic.com.driversapp.MapsActivity;
import app.appsmatic.com.driversapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Archived extends Fragment {

    private TextView noAchived,total;
    private RecyclerView arclist;
    private ArchivedOrdersAdb adb;
    private float totalvalue=0;
    private List<ArchivedOrder>archivedOrders;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archived, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        archivedOrders=new ArrayList<ArchivedOrder>();
        total=(TextView)getActivity().findViewById(R.id.ar_total);
        noAchived=(TextView)getActivity().findViewById(R.id.no_archived);
        noAchived.setVisibility(View.INVISIBLE);
      //  fillarchivedDb();


        HashMap id=new HashMap();
        id.put("UserID", HomeActivty.id);






        Genrator.createService(DriversApi.class).getArchivedOrders(id).enqueue(new Callback<ResArchived>() {
            @Override
            public void onResponse(Call<ResArchived> call, Response<ResArchived> response) {
                if(response.isSuccess()) {
                    archivedOrders.addAll(response.body().getMessage());
                    if (archivedOrders.size() == 0) {
                        noAchived.setVisibility(View.VISIBLE);
                    }
                }else{

                    Toast.makeText(getContext(),"Response from server not Success Try again later !!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResArchived> call, Throwable t) {

                Toast.makeText(getContext(),t.getMessage().toString(),Toast.LENGTH_SHORT).show();

            }
        });

        arclist = (RecyclerView) getActivity().findViewById(R.id.archived_list);
        adb = new ArchivedOrdersAdb(archivedOrders, getContext());
        arclist.setAdapter(adb);
        arclist.setLayoutManager(new LinearLayoutManager(getContext()));
        for (int i = 0; i < archivedOrders.size(); i++) {
            totalvalue += archivedOrders.get(i).getTotalAmount();
        }
        total.setText("TOTAL : " + totalvalue + " SR");
















    }

    @Override
    public void onResume() {
        super.onResume();
        HashMap id=new HashMap();
        id.put("UserID", HomeActivty.id);

        Genrator.createService(DriversApi.class).getArchivedOrders(id).enqueue(new Callback<ResArchived>() {
            @Override
            public void onResponse(Call<ResArchived> call, Response<ResArchived> response) {

              if(response.isSuccess()) {
                  totalvalue = 0;
                  adb = new ArchivedOrdersAdb(response.body().getMessage(), getContext());

                  arclist.setAdapter(adb);
                  arclist.setLayoutManager(new LinearLayoutManager(getContext()));

                  for (int i = 0; i < response.body().getMessage().size(); i++) {

                      totalvalue += response.body().getMessage().get(i).getTotalAmount();
                  }

                  total.setText("TOTAL : " + totalvalue + " SR");
              }else {

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
            public void onFailure(Call<ResArchived> call, Throwable t) {

            }
        });




    }








}
