package com.insantani_nostra.arisyaag.insantani;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suci Ayu on 4/17/2016.
 */
public class VegetableAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public VegetableAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Vegetable object) {
        super.add(object);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        VegetableHolder vegetableHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            vegetableHolder = new VegetableHolder();
            vegetableHolder.vName = (TextView) row.findViewById(R.id.vName);
            vegetableHolder.vPrice = (TextView) row.findViewById(R.id.vPrice);
            vegetableHolder.vStock = (TextView) row.findViewById(R.id.vStock);
            row.setTag(vegetableHolder);

        }
        else{
            vegetableHolder = (VegetableHolder) row.getTag();
        }
        Vegetable vegetable = (Vegetable) this.getItem(position);
        vegetableHolder.vName.setText(vegetable.getName());
        vegetableHolder.vPrice.setText(vegetable.getPrice());
        vegetableHolder.vStock.setText(vegetable.getStock());

        return row;
    }
    static class VegetableHolder{
        TextView vName, vPrice, vStock;
    }
}