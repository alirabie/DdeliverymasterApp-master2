package app.appsmatic.com.driversapp.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.util.HashMap;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.DriverProfile;
import app.appsmatic.com.driversapp.API.Models.ResProfile;
import app.appsmatic.com.driversapp.SharedPref.SaveSharedPreference;
import app.appsmatic.com.driversapp.HomeActivty;
import app.appsmatic.com.driversapp.LoginActivity;
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
    public static DriverProfile driverProfiledata;
    public HashMap<String,Object> hash =new HashMap<>();
    public Gson gson;
    public  String dataGson;
    public TextView change_name_btn,change_phone_btn,signoutBtn;




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
        // change_name_btn=(TextView)getActivity().findViewById(R.id.changedname_lbl);
        //  change_phone_btn=(TextView)getActivity().findViewById(R.id.changednumlbl);
        signoutBtn=(TextView)getActivity().findViewById(R.id.signoutBtn);
        profileImg.setBorderColor(R.color.colorPrimary);
        profileImg.setBorderWidth(3);
        driverProfiledata=new DriverProfile();
        gson=new Gson();
        dataGson=gson.toJson(hash);


/*

        change_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChangeDriverName.class));
            }
        });


        change_phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Notification_info.class));
            }
        });

*/



        //Loading Dialog
        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setIcon(android.R.drawable.ic_lock_idle_alarm);
        mProgressDialog.setTitle("Please Wait ..");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        HashMap id=new HashMap();
        id.put("UserID", HomeActivty.id);

        Genrator.createService(DriversApi.class).getProfile(id).enqueue(new Callback<ResProfile>() {
            @Override
            public void onResponse(Call<ResProfile> call, Response<ResProfile> response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                if (response.isSuccess()) {

                    driverProfiledata = response.body().getMessage();
                    name.setText(response.body().getMessage().getFullName() + " ");
                    phone.setText(response.body().getMessage().getMobileNo() + " ");
                    car.setText(response.body().getMessage().getVehiclePlateNo() + " ");
                    status.setChecked(response.body().getMessage().getAvailable());
                    byte[] decodedString = Base64.decode(response.body().getMessage().getPersonalPhoto(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    profileImg.setImageBitmap(decodedByte);
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
            public void onFailure(Call<ResProfile> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage()+"jjjjjjjj", Toast.LENGTH_LONG).show();

            }
        });




        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.isChecked()) {

                    //Loading Dialog
                    final ProgressDialog loading = new ProgressDialog(getContext());
                    loading.setIndeterminate(true);
                    loading.setIcon(android.R.drawable.ic_lock_idle_alarm);
                    loading.setTitle("Please Wait ..");
                    loading.setMessage("Loading...");
                    loading.show();


                    hash.put("UserID", driverProfiledata.getUserID());
                    hash.put("DriverID", driverProfiledata.getDriverID());
                    hash.put("FullName", driverProfiledata.getFullName());
                    hash.put("Address", driverProfiledata.getAddress());
                    hash.put("VehiclePlateNo", driverProfiledata.getVehiclePlateNo());
                    hash.put("MobileNo", driverProfiledata.getMobileNo());
                    hash.put("PersonalPhoto", driverProfiledata.getPersonalPhoto());
                    hash.put("BranchCode", driverProfiledata.getBranchCode());
                    hash.put("Available", true);
                    hash.put("ObjectState", driverProfiledata.getObjectState());



                    Log.e("DATAJSON", dataGson);


                    Genrator.createService(DriversApi.class).updateDriverinfo(hash).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (response.isSuccess()) {
                                loading.dismiss();
                                Toast.makeText(getContext(), "ON LINE MODE", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {


                        }
                    });


                } else if (!status.isChecked()) {


                    //Loading Dialog
                    final ProgressDialog loading2 = new ProgressDialog(getContext());
                    loading2.setIndeterminate(true);
                    loading2.setIcon(android.R.drawable.ic_lock_idle_alarm);
                    loading2.setTitle("Please Wait ..");
                    loading2.setMessage("Loading...");
                    loading2.show();


                    hash.put("UserID", driverProfiledata.getUserID());
                    hash.put("DriverID", driverProfiledata.getDriverID());
                    hash.put("FullName", driverProfiledata.getFullName());
                    hash.put("Address", driverProfiledata.getAddress());
                    hash.put("VehiclePlateNo", driverProfiledata.getVehiclePlateNo());
                    hash.put("MobileNo", driverProfiledata.getMobileNo());
                    hash.put("PersonalPhoto", driverProfiledata.getPersonalPhoto());
                    hash.put("BranchCode", driverProfiledata.getBranchCode());
                    hash.put("Available", false);
                    hash.put("ObjectState", driverProfiledata.getObjectState());


                    // Log.e("DATAJSON", dataGson);


                    Genrator.createService(DriversApi.class).updateDriverinfo(hash).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccess()) {
                                loading2.dismiss();
                                Toast.makeText(getContext(), "OFF LINE MODE", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {


                        }
                    });


                }
            }
        });




        //SignOut btn

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Clear Login data from prefs
                SaveSharedPreference.clearUserName(getContext());
                //Start Login activity
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();


            }
        });


























    }
}
