package com.pplnostrateam.arisyaag.insantani;

/**
 * Created by desiratnamukti on 4/27/16.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.content.Intent;

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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

    
public class MyOrderFragment extends Fragment implements GlobalConfig {

    View myView;
    OrderAdapter orderAdapter;

    SessionManager session;
    String json_string;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout_order, container, false);

        session = new SessionManager(getContext());
        
        ListView orderList = (ListView)myView.findViewById(R.id.orderList);
        orderAdapter = new OrderAdapter(this.getContext(), R.layout.row_layout_order);
        orderList.setAdapter(orderAdapter);

       /*Manggil dari api trus masukin ke adapter*/

        return myView;
    }



    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String JSON_STRING;
        String data_url;

        private Activity activity;

        @Override
        protected void onPreExecute() {
            data_url = APP_SERVER_IP + "api/order?userid=" + session.getUserId();
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
//            json_string = result;
//            move();
        }
    }


}

