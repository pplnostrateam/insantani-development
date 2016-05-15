package com.pplnostrateam.arisyaag.insantani;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    EditText search_vegetable;

    SessionManager session;


    //    ArrayList<Vegetable> arrayOfData = new ArrayList<Vegetable>();
//    FancyAdapter aa = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //TextView textView3 = (TextView) findViewById(R.id.textView3);
        // ListView searchResult = (ListView)findViewById(R.id.searchResult);
        ListView searchResult = (ListView)findViewById(R.id.searchResult);
        vegetableAdapter = new VegetableAdapter(this, R.layout.row_layout);
        searchResult.setAdapter(vegetableAdapter);
        search_vegetable = (EditText) findViewById(R.id.search_vegetable);

        json_string = getIntent().getExtras().getString("json_data");

        session = new SessionManager(getApplicationContext());

        Button mContinueButton = (Button) findViewById(R.id.button2);
        assert mContinueButton != null;
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!session.isLoggedIn())
                    getConfirmation(view);
                else
                    move2();
            }
        });

        try {
            // jsonObject = new JSONObject(json_string);
            jsonArray = new JSONArray(json_string);
            int count = 0;
            String name;
            int price, stock;
            while(count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("name");
                price = JO.getInt("price");
                stock = JO.getInt("stock");
                Vegetable vegetable = new Vegetable(name, price, stock);
                vegetableAdapter.add(vegetable);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getConfirmation(View view){
        //Intent intent = new Intent(this, SearchResultActivity.class);
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(this);
        alertDialogue.setTitle("You have to sign in first");
        alertDialogue.setMessage("Are you sure you want to continue this order?");
        alertDialogue.setCancelable(false);

        alertDialogue.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                move();
            }
        });
        alertDialogue.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = alertDialogue.create();
        dialog.show();
    }

    private void move() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
    public void getData(View view){
        new BackgroundTask().execute();

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String JSON_STRING;
        String data_url;
        String vName = search_vegetable.getText().toString();
        private Activity activity;

        @Override
        protected void onPreExecute() {
            data_url = "http://104.196.48.112:8080/api/vegetable/sugesstion?name=" + vName;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(data_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine()) != null){

                    stringBuilder.append(JSON_STRING+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            //textView2 = (TextView) findViewById(R.id.textView2);
            //textView2.setText(result);
            json_string = result;
            move2();
        }
    }

    public void move2(){
        Intent intent = new Intent(this, SearchingActivity.class);
        intent.putExtra("json_data", json_string);
        startActivity(intent);
    }

}
