package app.appsmatic.com.driversapp.Adabters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.appsmatic.com.driversapp.API.Models.Order;
import app.appsmatic.com.driversapp.R;

/**
 * Created by Mido PC on 8/17/2016.
 */
public class OrdersAdb extends RecyclerView.Adapter<OrdersAdb.vh> {

    private List<Order>orders;
    private Context context;

    public OrdersAdb(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }


    @Override
    public vh onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(vh holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class vh extends RecyclerView.ViewHolder {


        public vh(View itemView) {
            super(itemView);


        }

    }
}
