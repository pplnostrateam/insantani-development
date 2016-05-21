package com.pplnostrateam.arisyaag.insantani;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class OrderStatusOnDeliveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderstatus_ondelivery);

        Button confirm_delivered = (Button) findViewById(R.id.confirm_delivered);
        confirm_delivered.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), OrderStatusDeliveredActivity.class);
                startActivityForResult(intent, 0);
            }
        });


    }
}
