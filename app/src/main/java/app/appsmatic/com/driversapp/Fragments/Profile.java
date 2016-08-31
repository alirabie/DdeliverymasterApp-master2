package app.appsmatic.com.driversapp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.io.StringReader;
import java.util.HashMap;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import app.appsmatic.com.driversapp.API.Models.DriverProfile;
import app.appsmatic.com.driversapp.API.Models.Msg;
import app.appsmatic.com.driversapp.HomeActivty;
import app.appsmatic.com.driversapp.R;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends Fragment {

    CircleImageView profileImg;
    private TextView name,phone,car;
    private ToggleButton status;
    public DriverProfile driverProfiledata;
    public HashMap<String,Object> hash =new HashMap<>();
    public Gson gson;
    public  String dataGson;










    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        name=(TextView)getActivity().findViewById(R.id.pr_driver_name);
        phone=(TextView)getActivity().findViewById(R.id.pr_driver_phone);
        car=(TextView)getActivity().findViewById(R.id.pr_driver_car);
        profileImg=(CircleImageView)getActivity().findViewById(R.id.profilepic);
        status=(ToggleButton)getActivity().findViewById(R.id.driver_status_profile);
        profileImg.setBorderColor(R.color.colorPrimary);
        profileImg.setBorderWidth(3);
        driverProfiledata=new DriverProfile();
        gson=new Gson();
        dataGson=gson.toJson(hash);





        //Loading Dialog
        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setIcon(android.R.drawable.ic_lock_idle_alarm);
        mProgressDialog.setTitle("Please Wait ..");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();


        Genrator.createService(DriversApi.class).getProfile(HomeActivty.id).enqueue(new Callback<DriverProfile>() {
            @Override
            public void onResponse(Call<DriverProfile> call, Response<DriverProfile> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                driverProfiledata = response.body();


                name.setText(response.body().getFullName() + "");
                phone.setText(response.body().getMobileNo() + "");
                car.setText(response.body().getVehiclePlateNo() + "");
                status.setChecked(response.body().getAvailable());

                byte[] decodedString = Base64.decode(response.body().getPersonalPhoto(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                profileImg.setImageBitmap(decodedByte);

            }

            @Override
            public void onFailure(Call<DriverProfile> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.isChecked()){

                    //Loading Dialog
                    final ProgressDialog loading = new ProgressDialog(getContext());
                    loading.setIndeterminate(true);
                    loading.setIcon(android.R.drawable.ic_lock_idle_alarm);
                    loading.setTitle("Please Wait ..");
                    loading.setMessage("Loading...");
                    loading.show();



                    hash.put("UserID",driverProfiledata.getUserID());
                    hash.put("DriverID", driverProfiledata.getDriverID());
                    hash.put("FullName", driverProfiledata.getFullName());
                    hash.put("Address",driverProfiledata.getAddress());
                    hash.put("VehiclePlateNo",driverProfiledata.getVehiclePlateNo());
                    hash.put("MobileNo",driverProfiledata.getMobileNo());
                    hash.put("PersonalPhoto",driverProfiledata.getPersonalPhoto());
                    hash.put("BranchCode",driverProfiledata.getBranchCode());
                    hash.put("Available",true);
                    hash.put("ObjectState",driverProfiledata.getObjectState());
                    gson=new Gson();
                    dataGson=gson.toJson(hash);



                    Log.e("DATAJSON", dataGson);


                    Genrator.createService(DriversApi.class).updateDriverinfo(dataGson).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (loading.isShowing())
                                loading.dismiss();

                            Toast.makeText(getContext(),"ON LINE MODE",Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {


                        }
                    });



                }else if (!status.isChecked()){


                    //Loading Dialog
                    final ProgressDialog loading2 = new ProgressDialog(getContext());
                    loading2.setIndeterminate(true);
                    loading2.setIcon(android.R.drawable.ic_lock_idle_alarm);
                    loading2.setTitle("Please Wait ..");
                    loading2.setMessage("Loading...");
                    loading2.show();




                    hash.put("UserID",driverProfiledata.getUserID());
                    hash.put("DriverID", driverProfiledata.getDriverID());
                    hash.put("FullName", driverProfiledata.getFullName());
                    hash.put("Address",driverProfiledata.getAddress());
                    hash.put("VehiclePlateNo",driverProfiledata.getVehiclePlateNo());
                    hash.put("MobileNo","0482233222");
                    hash.put("PersonalPhoto",driverProfiledata.getPersonalPhoto());
                    hash.put("BranchCode",driverProfiledata.getBranchCode());
                    hash.put("Available",false);
                    hash.put("ObjectState",driverProfiledata.getObjectState());
                    gson=new Gson();
                    dataGson=gson.toJson(hash);



                    Log.e("DATAJSON", dataGson);


                    Genrator.createService(DriversApi.class).updateDriverinfo(dataGson).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (loading2.isShowing())
                                loading2.dismiss();
                                Toast.makeText(getContext(),"OFF LINE MODE",Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {



                        }
                    });






                }
            }
        });





































    }
}
