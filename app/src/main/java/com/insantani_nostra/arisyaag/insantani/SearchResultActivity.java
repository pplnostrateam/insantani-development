package com.insantani_nostra.arisyaag.insantani;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Suci Ayu on 4/14/2016.
 */


public class SearchResultActivity extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    VegetableAdapter vegetableAdapter;


    //    ArrayList<Vegetable> arrayOfData = new ArrayList<Vegetable>();
//    FancyAdapter aa = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //TextView textView3 = (TextView) findViewById(R.id.textView3);
        ListView searchResult = (ListView)findViewById(R.id.searchResult);
        vegetableAdapter = new VegetableAdapter(this, R.layout.row_layout);
        searchResult.setAdapter(vegetableAdapter);

        json_string = getIntent().getExtras().getString("json_data");
        try {
            // jsonObject = new JSONObject(json_string);
            jsonArray = new JSONArray(json_string);
            int count = 0;
            String name, price, stock;
            while(count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("name");
                price = JO.getString("price");
                stock = JO.getString("stock");
                Vegetable vegetable = new Vegetable(name, price, stock);
                vegetableAdapter.add(vegetable);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
