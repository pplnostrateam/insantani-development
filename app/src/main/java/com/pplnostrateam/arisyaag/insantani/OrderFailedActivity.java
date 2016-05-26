package com.pplnostrateam.arisyaag.insantani;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Suci Ayu on 5/26/2016.
 */
public class OrderFailedActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_not_found);
        Button backtosearch = (Button) findViewById(R.id.backtosearch);
        assert backtosearch != null;
        backtosearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrderFailedActivity.this, SearchingActivity.class);
                startActivity(intent);
            }
        });

    }
}
