package com.pplnostrateam.arisyaag.insantani;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hartico on 05/20/2016.
 */

   public class OrderAdapter extends ArrayAdapter {
    List list = new ArrayList();
    int post;

    public OrderAdapter(Context context, int resource) {
        super(context, resource);
        post = 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_layout_order, parent, false);

        return row;
    }

}