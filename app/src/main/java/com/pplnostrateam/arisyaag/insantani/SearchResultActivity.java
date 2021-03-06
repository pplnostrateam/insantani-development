package com.pplnostrateam.arisyaag.insantani;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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


/**
 * Created by Suci Ayu on 4/14/2016.
 */


public class SearchResultActivity extends AppCompatActivity implements GlobalConfig {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    VegetableAdapter vegetableAdapter;
    EditText search_vegetable;
    EditText weight;
    SessionManager session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultActivity.this, SearchingActivity.class);
                SearchResultActivity.this.startActivity(intent);
            }
        });

        ListView searchResult = (ListView)findViewById(R.id.searchResult);
        vegetableAdapter = new VegetableAdapter(this, R.layout.row_layout);
        searchResult.setAdapter(vegetableAdapter);
        search_vegetable = (EditText) findViewById(R.id.search_vegetable);
        weight = (EditText) findViewById(R.id.weight);
        weight.setFilters(new InputFilter[]{new InputFilterMinMax("1", "100")});

        search_vegetable.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String vName = search_vegetable.getText().toString();

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(vName.equals("")){
                        searchFirst();
                    }
                    else {
                        new BackgroundTask().execute();
                        return true;
                    }
                }
                return false;
            }
        });

        json_string = getIntent().getExtras().getString("json_data");

        session = new SessionManager(getApplicationContext());

        Button mContinueButton = (Button) findViewById(R.id.button2);
        assert mContinueButton != null;

        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weight.getText().toString().equals(""))
                    weightNullNotAllowed();
                else if (Integer.parseInt(weight.getText().toString()) < 1)
                    weightLessThanOneNotAllowed();
                else if (!session.isLoggedIn())
                    getConfirmation(view);
                else
                    moveByPassLogin();
            }
        });

        try {
            // jsonObject = new JSONObject(json_string);
            jsonArray = new JSONArray(json_string);
            int count = 0;
            String name;
            int price, stock, id;
            while(count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("name");
                price = JO.getInt("price");
                stock = JO.getInt("stock");
                id = JO.getInt("id");
                Vegetable vegetable = new Vegetable(id, name, price, stock);
                vegetableAdapter.add(vegetable);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getConfirmation(View view){
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
        String vName = search_vegetable.getText().toString();
        if(vName.equals("")){
            searchFirst();
        }
        else{
            new BackgroundTask().execute();
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String JSON_STRING;
        String data_url;
        String vName = search_vegetable.getText().toString();
        private Activity activity;

        @Override
        protected void onPreExecute() {
            data_url = APP_SERVER_IP + "api/vegetable/sugesstion?name=" + vName;
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
            vegetableQueryResultChecking();
        }
    }
    public void searchFirst(){
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(this);
        alertDialogue.setMessage("insert vegetable's name first");
        alertDialogue.setCancelable(false);

        alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = alertDialogue.create();
        dialog.show();
    }

    public void weightNullNotAllowed(){
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(this);
        alertDialogue.setMessage("please fill weight to order");
        alertDialogue.setCancelable(false);

        alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = alertDialogue.create();
        dialog.show();
    }

    public void weightLessThanOneNotAllowed(){
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(this);
        alertDialogue.setMessage("weight must be integer greater than 0");
        alertDialogue.setCancelable(false);

        alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = alertDialogue.create();
        dialog.show();
    }

    public void vegetableQueryResultChecking() {
        if (json_string.equals("[]")) {
            AlertDialog.Builder alertDialogue = new AlertDialog.Builder(this);
            alertDialogue.setMessage("vegetable not found");
            alertDialogue.setCancelable(false);

            alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = alertDialogue.create();
            dialog.show();
        } else {
            Intent intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);
            finish();
        }
    }

    public void moveByPassLogin(){
        session.updateVegetableDetails(session.getVegetableDetails().get("vegetableId"),
                Integer.parseInt(weight.getText().toString()),
                session.getVegetableDetails().get("price"));

        Log.d("Debug Quantity:", session.getVegetableDetails().get("quantity").toString());

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("json_data", json_string);
        startActivity(intent);
        finish();
    }
}
