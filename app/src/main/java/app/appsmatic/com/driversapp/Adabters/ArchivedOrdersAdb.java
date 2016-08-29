package app.appsmatic.com.driversapp.Adabters;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import app.appsmatic.com.driversapp.API.Models.ArchivedOrder;
import app.appsmatic.com.driversapp.R;

/**
 * Created by Mido PC on 8/17/2016.
 */


public class ArchivedOrdersAdb extends RecyclerView.Adapter<ArchivedOrdersAdb.vh2> {


    private List<ArchivedOrder> archivedOrders;
    private Context context;
    private float totalmount;

    public ArchivedOrdersAdb(List<ArchivedOrder> archivedOrders, Context context) {
        this.archivedOrders = archivedOrders;
        this.context = context;
    }




    @Override
    public vh2 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new vh2(LayoutInflater.from(parent.getContext()).inflate(R.layout.archived_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(vh2 holder, int position) {

        holder.orderid.setText(archivedOrders.get(position).getOrderID()+"");
        holder.price.setText(archivedOrders.get(position).getTotalAmount()+"");
        holder.duration.setText("20");



    }

    @Override
    public int getItemCount() {

        return archivedOrders.size();
    }










    public static class vh2 extends RecyclerView.ViewHolder{

        TextView orderid,price,duration;


        public vh2(View itemView) {
            super(itemView);
            orderid=(TextView)itemView.findViewById(R.id.ar_order);
            price=(TextView)itemView.findViewById(R.id.ar_price);
            duration=(TextView)itemView.findViewById(R.id.ar_time);


        }
    }
}
