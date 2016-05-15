package com.pplnostrateam.arisyaag.insantani;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;



public class Orderstatus_accepted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderstatus_accepted);

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

                Intent intent = new Intent(v.getContext(), Order.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}
