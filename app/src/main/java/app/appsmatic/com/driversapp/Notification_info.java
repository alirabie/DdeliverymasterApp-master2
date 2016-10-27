package app.appsmatic.com.driversapp;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Locale;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.Fragments.Profile;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification_info extends AppCompatActivity {

    private EditText numtxt;
    private Button save;
    private HashMap<String,Object> hash =new HashMap<>();
    private Gson gson;
    private  String dataGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_driver_number);

        //set local default english
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        numtxt=(EditText)findViewById(R.id.Change_num);
        save=(Button)findViewById(R.id.Change_phone_btn);

        save.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View v) {

                if(numtxt.getText().toString().isEmpty()){

                    Toast.makeText(Notification_info.this, "Please Insert New Phone Num ", Toast.LENGTH_SHORT).show();


                }else {

                    //Loading Dialog
                    final ProgressDialog loading = new ProgressDialog(Notification_info.this);
                    loading.setIndeterminate(true);
                    loading.setIcon(android.R.drawable.ic_lock_idle_alarm);
                    loading.setTitle("Please Wait ..");
                    loading.setMessage("Loading...");
                    loading.show();


                    hash.put("UserID", Profile.driverProfiledata.getUserID());
                    hash.put("DriverID", Profile.driverProfiledata.getDriverID());
                    hash.put("FullName", Profile.driverProfiledata.getFullName());
                    hash.put("Address", Profile.driverProfiledata.getAddress());
                    hash.put("VehiclePlateNo", Profile.driverProfiledata.getVehiclePlateNo());
                    hash.put("MobileNo", numtxt.getText() + "");
                    hash.put("PersonalPhoto", Profile.driverProfiledata.getPersonalPhoto());
                    hash.put("BranchCode", Profile.driverProfiledata.getBranchCode());
                    hash.put("Available", Profile.driverProfiledata.getAvailable());
                    hash.put("ObjectState", Profile.driverProfiledata.getObjectState());
                    gson = new Gson();

                    dataGson = gson.toJson(hash);


                    //    Log.e("DATAJSON", dataGson);


                    Genrator.createService(DriversApi.class).updateDriverinfo(dataGson).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (loading.isShowing())
                                loading.dismiss();
                            finish();
                            Toast.makeText(Notification_info.this, "Phone Num Changed Successfully", Toast.LENGTH_SHORT).show();

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
