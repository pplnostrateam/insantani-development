package com.insantani_nostra.arisyaag.insantani;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Suci Ayu on 4/14/2016.
 */
public class SearchResultActivity extends AppCompatActivity {
    ArrayList<Vegetable> arrayOfData = new ArrayList<Vegetable>();
    FancyAdapter aa = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //    textView = (TextView) findViewById(R.id.textView);
        showresult();
    }

    public void showresult(){

    }

    private class Vegetable {
        public String name;
        public Integer price;
        public Integer stock;
    }

    private class FancyAdapter extends ArrayAdapter<Vegetable> {
        FancyAdapter(){
            super(SearchResultActivity.this, android.R.layout.simple_list_item_1, arrayOfData);
        }

        public FancyAdapter(Context context, int resource) {
            super(context, resource);
        }
    }
}
