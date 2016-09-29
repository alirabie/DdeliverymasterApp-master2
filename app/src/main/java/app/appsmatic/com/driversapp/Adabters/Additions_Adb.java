package app.appsmatic.com.driversapp.Adabters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.appsmatic.com.driversapp.API.Models.Addition;
import app.appsmatic.com.driversapp.R;

/**
 * Created by Mido PC on 9/21/2016.
 */
public class Additions_Adb extends RecyclerView.Adapter<Additions_Adb.vhd> {

    private Context context;
    private List<Addition> additionList;

    public Additions_Adb(Context context, List<Addition> additionList) {
        this.context = context;
        this.additionList = additionList;
    }





    @Override
    public vhd onCreateViewHolder(ViewGroup parent, int viewType) {
        return new vhd(LayoutInflater.from(parent.getContext()).inflate(R.layout.addition_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(vhd holder, int position) {

        holder.addname.setText(additionList.get(position).getAdditionName()+"");
        holder.addprice.setText("ITEM PRICE : "+additionList.get(position).getAdditionPrice()+" SR");
        holder.addqty.setText("QTY : "+additionList.get(position).getAdditionQuantity());

    }

    @Override
    public int getItemCount() {
        return additionList.size();
    }


    public static class vhd extends RecyclerView.ViewHolder{

        TextView addname,addqty,addprice;


        public vhd(View itemView) {
            super(itemView);

            addname=(TextView)itemView.findViewById(R.id.Tv_add_Item);
            addqty=(TextView)itemView.findViewById(R.id.Tv_add_Qty);
            addprice=(TextView)itemView.findViewById(R.id.Tv_add_Price);

        }
    }
}
