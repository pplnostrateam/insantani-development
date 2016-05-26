package com.pplnostrateam.arisyaag.insantani;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 05/25/2016.
 */

   public class OrderAdapter extends ArrayAdapter {
    List list = new ArrayList();
    int post;
    TextView vegetableName, farmName, orderDate;
    public OrderAdapter(Context context, int resource) {
        super(context, resource);
        post = 0;
    }

    public void add(Order object) {
        super.add(object); //1
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_layout_order, parent, false);
        final Order order = (Order) this.getItem(position);
        vegetableName = (TextView) row.findViewById(R.id.vegetable_name);
        farmName = (TextView) row.findViewById(R.id.farm_name);
        orderDate = (TextView) row.findViewById(R.id.order_date);

        vegetableName.setText(order.getVegetableName());
        farmName.setText(order.getFarmerName());
        orderDate.setText(order.getCreated());

        return row;
    }


}