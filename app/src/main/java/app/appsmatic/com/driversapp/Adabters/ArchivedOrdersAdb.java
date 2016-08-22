package app.appsmatic.com.driversapp.Adabters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import app.appsmatic.com.driversapp.API.Models.ArchivedOrder;

/**
 * Created by Mido PC on 8/17/2016.
 */


public class ArchivedOrdersAdb extends RecyclerView.Adapter<ArchivedOrdersAdb.vh2> {


    private List<ArchivedOrder> archivedOrders;
    private Context context;

    public ArchivedOrdersAdb(List<ArchivedOrder> archivedOrders, Context context) {
        this.archivedOrders = archivedOrders;
        this.context = context;
    }


    @Override
    public vh2 onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(vh2 holder, int position) {

    }

    @Override
    public int getItemCount() {

        return archivedOrders.size();
    }

    public static class vh2 extends RecyclerView.ViewHolder{


        public vh2(View itemView) {
            super(itemView);
        }
    }
}
