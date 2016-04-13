package com.insantani_nostra.arisyaag.insantani;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchingActivity extends AppCompatActivity {
    //private EditText editTextName;
    private EditText search_vegetable;
    private Button button;
    private TextView textView;
    // private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        //    textView = (TextView) findViewById(R.id.textView);
        init();
    }

    public void init(){
        //editTextName = (EditText) findViewById(R.id.editTextName);
        search_vegetable = (EditText) findViewById(R.id.search_vegetable);
        //   editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getData() throws IOException {
        //String vName = editTextName.getText().toString();
        String vName = search_vegetable.getText().toString();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://104.155.215.144:8080/api/vegetable/sugesstion?name="+vName)
                .build();

//        Response response = client.newCall(request).execute();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, final Response response) throws IOException {

                String data = response.body().string();
                String temp = "";
                if (response.isSuccessful()) {
                    try {

                        JSONArray vegetables = new JSONArray(data);
                        for (int i = 0; i < vegetables.length(); i++){
                            JSONObject jsonobject = vegetables.getJSONObject(i);

                            final String name = jsonobject.optString("name").toString();
                            final double stock = Double.parseDouble(jsonobject.optString("stock").toString());

                            final double price = Double.parseDouble(jsonobject.optString("price").toString());
                            temp += "Name= "+ name +" : \n Stock= "+ stock +" \n Price= "+ price +" \n ";
                            System.out.println(temp);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        })

        ;}
}
