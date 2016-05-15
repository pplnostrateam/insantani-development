package com.pplnostrateam.arisyaag.insantani;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchingActivity extends AppCompatActivity {
    //private EditText editTextName;
    private EditText search_vegetable;
    private Button button;
    private TextView textView2;
    private Activity activity;
    String json_string;
    // private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        //    textView = (TextView) findViewById(R.id.textView);
        search_vegetable = (EditText) findViewById(R.id.search_vegetable);

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
            move();
        }
    }
    public void move(){
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("json_data", json_string);
        startActivity(intent);
    }


}
