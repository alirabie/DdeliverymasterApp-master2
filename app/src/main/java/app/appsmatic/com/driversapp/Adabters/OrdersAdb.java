package app.appsmatic.com.driversapp.Adabters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.appsmatic.com.driversapp.API.Models.Order;
import app.appsmatic.com.driversapp.MapsActivity;
import app.appsmatic.com.driversapp.R;

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

        holder.statusok.setVisibility(View.INVISIBLE);
        holder.status.setVisibility(View.INVISIBLE);


        //Check order Status To Set button Status
        switch (orders.get(position).getStatusID()){
            case 0 :
                holder.status.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.statusok.setVisibility(View.VISIBLE);
                break;

        }







        holder.orderId.setText(orders.get(position).getOrderID()+"");
        holder.custName.setText(orders.get(position).getCustomer()+"");
        holder.totalPrice.setText("Total Price : "+orders.get(position).getTotalAmount()+" SR");
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

        holder.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                        + orders.get(position).getMobileNo().toString())));
            }
        });
        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MapsActivity.class).putExtra("lat", orders.get(position).getLatitude()).putExtra("lng", orders.get(position).getLongtitude()));
            }
        });

        
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.status.setVisibility(View.INVISIBLE);
                holder.statusok.setVisibility(View.VISIBLE);



            }
        });

        holder.statusok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.statusok.setVisibility(View.VISIBLE);
                holder.status.setVisibility(View.INVISIBLE);

            }
        });




    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class vh extends RecyclerView.ViewHolder {


        TextView orderId,custName,duration,totalPrice,status,statusok;
        ImageView call,sms,map;



        public vh(View itemView) {
            super(itemView);

            orderId=(TextView)itemView.findViewById(R.id.orderid);
            custName=(TextView)itemView.findViewById(R.id.tv_customername);
            duration=(TextView)itemView.findViewById(R.id.tv_duration);
            totalPrice=(TextView)itemView.findViewById(R.id.tv_price);
            status=(TextView)itemView.findViewById(R.id.statusbtn);
            statusok=(TextView)itemView.findViewById(R.id.statusbtnok);


            call=(ImageView)itemView.findViewById(R.id.callbtn);
            sms=(ImageView)itemView.findViewById(R.id.smsbtn);
            map=(ImageView)itemView.findViewById(R.id.mapbtn);


        }

    }
}
