package com.pplnostrateam.arisyaag.insantani;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suci Ayu on 4/17/2016.
 */
public class VegetableAdapter extends ArrayAdapter {
    List list = new ArrayList();
    RadioButton lastChecked;
    int post;

    SessionManager session;

    public VegetableAdapter(Context context, int resource) {
        super(context, resource);
        //lastChecked = new RadioButton(context);
        post = 0;
        session = new SessionManager(context);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;
        //View view = convertView;
        row = convertView;
//        VegetableHolder vegetableHolder;
        // LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //   row = layoutInflater.inflate(R.layout.row_layout,parent,false);

//
        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
        }
//            //RadioButton radioButton = (RadioButton) row.findViewById(R.id.radioButton);
//            vegetableHolder = new VegetableHolder();
//            //vegetableHolder.vName = (TextView) row.findViewById(R.id.vName);
//            //vegetableHolder.vPrice = (TextView) row.findViewById(R.id.vPrice);
//            //vegetableHolder.vStock = (TextView) row.findViewById(R.id.vStock);
//            //vegetableHolder.vName = (RadioButton) row.findViewById(R.id.radioButton);
//            row.setTag(vegetableHolder);
//
//        }
//        else{
//            vegetableHolder = (VegetableHolder) row.getTag();
//        }
        RadioButton radioButton = (RadioButton) row.findViewById(R.id.radiobutton);
        TextView textView = (TextView) row.findViewById(R.id.vPrice);
        final Vegetable vegetable = (Vegetable) this.getItem(position);
        // vegetableHolder.vName.setText(vegetable.getName());
        radioButton.setText(vegetable.getName());
        textView.setText("Price: " + vegetable.getPrice());
        //vegetableHolder.vPrice.setText(vegetable.getPrice());
        //vegetableHolder.vStock.setText(vegetable.getStock());
        radioButton.setChecked(post == position);
            if(radioButton.isChecked()){
                session.createVegetableDetails(vegetable.getId(), vegetable.getPrice());
                Log.d("WEWEWE", String.valueOf(vegetable.getId()));
                Log.d("WEWEWE", String.valueOf(vegetable.getName()));
            }
    //        session.createVegetableDetails(vegetable.getId(), vegetable.getPrice());
//        Log.d("WWWWW:", session.getVegetableDetails().get("vegetableId").toString());
//        Log.d("KKKKK:", session.getVegetableDetails().get("price").toString());

        radioButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //lastChecked.setChecked(false);
                //lastChecked = (RadioButton) v;
                post = position;
                notifyDataSetChanged();

//                session.createVegetableDetails(vegetable.getId(), vegetable.getPrice());
//                Log.d("VegetableAdapter:", session.getVegetableDetails().get("vegetableId").toString());
//                Log.d("VegetableAdapter:", session.getVegetableDetails().get("price").toString());
            }
        });

        //Log.d("WEWEWE", String.valueOf(vegetable.getId()));

        return row;
    }
    static class VegetableHolder{
        RadioButton vName, vPrice, vStock;
    }
}
