package com.pplnostrateam.arisyaag.insantani;

/**
 * Created by desiratnamukti on 4/27/16.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;

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
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.google.android.gms.internal.zzip.runOnUiThread;


public class MyOrderFragment extends Fragment implements GlobalConfig {

    View myView;
    OrderAdapter orderAdapter;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    VegetableAdapter vegetableAdapter;
    SessionManager session;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout_order, container, false);

        session = new SessionManager(getActivity());
        session.getUserDetails().put("userid","3");
        ListView orderList = (ListView)myView.findViewById(R.id.orderList);
        orderAdapter = new OrderAdapter(this.getActivity(), R.layout.row_layout_order);
        getOrder();
        orderList.setAdapter(orderAdapter);

        ImageButton backButton = (ImageButton)myView.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
            }
        });
        Order name = new Order("2fecd","2015/15/10","Cabai","Tangerang Selatan",session.getUserDetails().get("userid"));
        Order name2 = new Order("2fecd","2015/15/10","Cabai","Tangerang Utara",session.getUserDetails().get("userid"));

        orderAdapter.add(name);
        orderAdapter.add(name2);

        return myView;
    }

    private void getOrder() {
        String apiURL = "http://l04.196.35.119:8080/api/order?userid="+session.getUserDetails().get("userid");
        if(isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiURL)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("GET ORDER CALLBACK", "FAILEDDD");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsondata = response.body().string();
                        if (response.isSuccessful()) {
                            getCurrentOrders(jsondata);
                        } else {
                            Toast.makeText(getActivity(), "Network is unavailable!", Toast.LENGTH_LONG);
                        }
                    } catch (IOException e) {
                        Log.e("GET ORDER IO", "Exception caught : ", e);
                    } catch (JSONException e) {
                        Log.e("GET ORDER JSON", "Exception caught : ", e);
                    }
                }
            });
        } else {
            Toast.makeText(this.getActivity(), "Network is unavailable!", Toast.LENGTH_LONG);
        }
    }




    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void getCurrentOrders(String payload) throws JSONException {
        JSONArray jsondata =  new JSONArray(payload);
        for(int i = 0; i < jsondata.length();i++) {
            JSONObject x = jsondata.getJSONObject(i);
            String vegetableName = x.getJSONObject("vegetable").getString("name");
            String farmName = x.getJSONObject("farmer").getString("farmer");
            String orderDate = x.getString("created");
            Order newOrder = new Order();
            newOrder.setCreated(orderDate);
            newOrder.setFarmerName(farmName);
            newOrder.setVegetableName(vegetableName);
            orderAdapter.add(newOrder);
        }


    }

}

