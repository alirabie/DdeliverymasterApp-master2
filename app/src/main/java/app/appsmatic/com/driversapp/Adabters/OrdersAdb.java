package app.appsmatic.com.driversapp.Adabters;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import java.util.List;

import app.appsmatic.com.driversapp.API.DriversApi;
import app.appsmatic.com.driversapp.API.Genrator;
import app.appsmatic.com.driversapp.API.Models.ChangeStautMsg;
import app.appsmatic.com.driversapp.API.Models.Order;
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
     int statusId=0;

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


        //Check order Status To Set button Status
        switch (orders.get(position).getStatusID()){
            case 0 :
                holder.confirm.setVisibility(View.VISIBLE);
                break;
            case 6:
                holder.confirmed.setVisibility(View.VISIBLE);
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
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
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
                context.startActivity(new Intent(context, MapsActivity.class)
                        .putExtra("custname",orders.get(position).getCustomer())
                        .putExtra("lat", orders.get(position).getLatitude())
                        .putExtra("lng", orders.get(position).getLongtitude()));
            }
        });



        //When confirm Button is clicked
        
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do You Want to Confirm This Order ??")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {



                                //Send 6 Status





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
                        .putExtra("phone", orders.get(position).getMobileNo() + "")
                        .putExtra("totalPrice", orders.get(position).getTotalAmount())
                        .putExtra("statusID", orders.get(position).getStatusID())
                        .putExtra("custname", orders.get(position).getCustomer() + "")
                        .putExtra("orderId", orders.get(position).getOrderID().toString())
                        .putExtra("lat", orders.get(position).getLatitude())
                        .putExtra("lng", orders.get(position).getLongtitude()));
            }
        });







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
