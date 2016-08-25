package app.appsmatic.com.driversapp.Adabters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.appsmatic.com.driversapp.API.Models.OrderDetail;
import app.appsmatic.com.driversapp.R;

/**
 * Created by Mido PC on 8/24/2016.
 */
public class Items_Adb extends RecyclerView.Adapter<Items_Adb.vh3> {

    public Items_Adb(Context context, List<OrderDetail> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
    }

    private Context context;
    private List<OrderDetail>orderDetails;


    @Override
    public vh3 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new vh3(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_order_list,parent,false));
    }

    @Override
    public void onBindViewHolder(vh3 holder, int position) {

        holder.item.setText(orderDetails.get(position).getName()+"");
        holder.qty.setText(orderDetails.get(position).getQuantity()+"");
        holder.price.setText(orderDetails.get(position).getItemPrice()+"");
        holder.total.setText(orderDetails.get(position).getItemPrice()*orderDetails.get(position).getQuantity()+"");

    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }


    public static class vh3 extends RecyclerView.ViewHolder{

        TextView item,qty,price,total;


        public vh3(View itemView) {
            super(itemView);

            item=(TextView)itemView.findViewById(R.id.item);
            qty=(TextView)itemView.findViewById(R.id.qty);
            price=(TextView)itemView.findViewById(R.id.price);
            total=(TextView)itemView.findViewById(R.id.total);
        }
    }
}
