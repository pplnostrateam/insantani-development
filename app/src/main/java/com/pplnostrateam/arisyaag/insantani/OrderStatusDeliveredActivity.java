package com.pplnostrateam.arisyaag.insantani;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class OrderStatusDeliveredActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderstatus_delivered);

        Button return_search = (Button) findViewById(R.id.return_search);
        return_search.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), SearchingActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //button "see my order" masih pergi ke dummy. Harusnya akan pergi ke list of order
        Button view_all_order = (Button) findViewById(R.id.view_all_order);
        view_all_order.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), OrderActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}
