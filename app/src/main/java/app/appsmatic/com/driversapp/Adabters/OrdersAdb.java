package app.appsmatic.com.driversapp.Adabters;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.ChangeStautMsg;
import app.appsmatic.com.driversapp.API.Models.DriverID;
import app.appsmatic.com.driversapp.API.Models.Order;
import app.appsmatic.com.driversapp.API.Models.ResConfirmOrder;
import app.appsmatic.com.driversapp.GPS.GPSTracker;
import app.appsmatic.com.driversapp.HomeActivty;
import app.appsmatic.com.driversapp.MapsActivity;
import app.appsmatic.com.driversapp.Orders_info;
import app.appsmatic.com.driversapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mido PC on 8/17/2016.
 */
public class OrdersAdb extends RecyclerView.Adapter<OrdersAdb.vh> {

     List<Order>orders=new ArrayList<>();
     Context context;


    public OrdersAdb(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }


    @Override
    public vh onCreateViewHolder(ViewGroup parent, int viewType) {
        return new vh(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final vh holder, final int position) {

        holder.confirmed.setVisibility(View.INVISIBLE);
        holder.confirm.setVisibility(View.INVISIBLE);


        if(orders.get(position).getConfirmed()){
            holder.confirmed.setVisibility(View.VISIBLE);
        }



        //Check order Status To Set button Status
        switch (orders.get(position).getStatusID()){
            case 0 :
                holder.confirm.setVisibility(View.VISIBLE);
                break;
            case 5:
                holder.confirmed.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.confirmed.setVisibility(View.VISIBLE);
                break;

        }







        //Fill tv Id and tv Name and tv Total price

        holder.orderId.setText(orders.get(position).getOrderID()+"");
        holder.custName.setText(orders.get(position).getCustomer()+"");
        holder.totalPrice.setText("Total Price : " + orders.get(position).getTotalAmount() + " SR");

        //Call Button

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uri = "tel:" + orders.get(position).getMobileNo().toString();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
                    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    callIntent.addFlags(Intent.FLAG_FROM_BACKGROUND);

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // Check the SDK version and whether the permission is already granted or not.
                            ActivityCompat.requestPermissions((Activity)context,new String[]{Manifest.permission.CALL_PHONE},100);

                    }
                    context.startActivity(callIntent);

                } catch (Exception e)

                {

                    Toast.makeText(context, "Your call has failed...", Toast.LENGTH_LONG).show();
                    e.printStackTrace();


                }
            }

        });

        //SmS Button

        holder.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                        + orders.get(position).getMobileNo().toString())));
            }
        });

        //Map Button

        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GPSTracker gpsTracker=new GPSTracker(context);
                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+gpsTracker.getLatitude()+","+gpsTracker.getLongitude()+"&daddr="+orders.get(position).getLatitude()+","+orders.get(position).getLongtitude();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                try
                {
                    context.startActivity(intent);
                }
                catch(ActivityNotFoundException ex)
                {
                    try
                    {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        context.startActivity(unrestrictedIntent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        Toast.makeText(context, "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }


/*
                context.startActivity(new Intent(context, MapsActivity.class)
                        .putExtra("custname",orders.get(position).getCustomer())
                        .putExtra("lat", orders.get(position).getLatitude())
                        .putExtra("lng", orders.get(position).getLongtitude()));

                        */
            }
        });



        //When confirm Button is clicked Show dialog to ensure
        
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do You Want to Confirm This Order ??")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                HashMap id2=new HashMap();
                                id2.put("DriverID", HomeActivty.id);
                                id2.put("OrderID", orders.get(position).getOrderID());


                                Genrator.createService(DriversApi.class).ConfirmOrder(id2).enqueue(new Callback<ResConfirmOrder>() {
                                    @Override
                                    public void onResponse(Call<ResConfirmOrder> call, Response<ResConfirmOrder> response) {
                                        if(response.isSuccess())
                                        Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<ResConfirmOrder> call, Throwable t) {

                                        Toast.makeText(context, "Order Confirmation Error " + t.getMessage() + "", Toast.LENGTH_SHORT).show();

                                    }
                                });


                                holder.confirm.setVisibility(View.INVISIBLE);
                                holder.confirmed.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();

                            }
                        }).setIcon(android.R.drawable.alert_light_frame);
                AlertDialog alert = builder.create();
                alert.show();


            }
        });


        //When confirmed Button is Clicked and go to order details

        holder.confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, Orders_info.class)
                        .putExtra("phone", orders.get(position).getMobileNo() + " ")
                        .putExtra("totalPrice", orders.get(position).getTotalAmount())
                        .putExtra("statusID", orders.get(position).getStatusID())
                        .putExtra("custname", orders.get(position).getCustomer() + " ")
                        .putExtra("orderId", orders.get(position).getOrderID().toString())
                        .putExtra("ordertime",orders.get(position).getTimeToRecieve())
                        .putExtra("lat", orders.get(position).getLatitude())
                        .putExtra("lng", orders.get(position).getLongtitude())
                        .putExtra("comment", orders.get(position).getDeliveryAddress()+" "));

            }
        });





        //Set Time to Order in order List item

        if(orders.get(position).getTimeToRecieve()==null){

            holder.duration.setText("Not Set");

        }else{
            /*
            String ackwardDate=orders.get(position).getTimeToRecieve();
            Calendar calendar = Calendar.getInstance();
            String ackwardRipOff = ackwardDate.replace("/Date(", "").replace(")/", "");
            Long timeInMillis = Long.valueOf(ackwardRipOff);
            calendar.setTimeInMillis(timeInMillis);
            */
            holder.duration.setText(orders.get(position).getTimeToRecieve()+"");

        }








    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


   //View holder class

    public static class vh extends RecyclerView.ViewHolder {


        TextView orderId,custName,duration,totalPrice, confirm, confirmed;
        ImageView call,sms,map;
        RelativeLayout layout;



        public vh(View itemView) {
            super(itemView);

            orderId=(TextView)itemView.findViewById(R.id.orderid);
            custName=(TextView)itemView.findViewById(R.id.tv_customername);
            duration=(TextView)itemView.findViewById(R.id.tv_duration);
            totalPrice=(TextView)itemView.findViewById(R.id.tv_price);
            confirm =(TextView)itemView.findViewById(R.id.statusbtn);
            confirmed =(TextView)itemView.findViewById(R.id.statusbtnok);

            call=(ImageView)itemView.findViewById(R.id.callbtn);
            sms=(ImageView)itemView.findViewById(R.id.smsbtn);
            map=(ImageView)itemView.findViewById(R.id.mapbtn);

            layout=(RelativeLayout)itemView.findViewById(R.id.layout);


        }

    }
}
