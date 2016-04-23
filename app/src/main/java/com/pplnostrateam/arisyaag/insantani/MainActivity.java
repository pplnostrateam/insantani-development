package com.pplnostrateam.arisyaag.insantani;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button tombol = (Button) findViewById(R.id.continue_order);



    }
    public void click(View v) {

        Intent i=new Intent(MainActivity.this,MapsActivity.class);
        startActivity(i);


    }

}
