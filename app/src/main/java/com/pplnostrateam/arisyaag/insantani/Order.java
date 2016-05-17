package com.pplnostrateam.arisyaag.insantani;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Order extends AppCompatActivity
        implements GlobalConfig, NavigationView.OnNavigationItemSelectedListener {

    // konstanta untuk mendeteksi hasil balikan dari place picker
    private int PLACE_PICKER_REQUEST = 1;

    private ImageButton btPlacesAPI;
    private TextView tvPlaceAPI;

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OrderTask mOrderTask = null;

    SessionManager session;

    Place place;

    private EditText mLocation1;
    private EditText mLocation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext());

        tvPlaceAPI = (TextView) findViewById(R.id.tv_place_id);
        btPlacesAPI = (ImageButton)findViewById(R.id.bt_ppicker);

        /*
        btPlacesAPI.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // membuat Intent untuk Place Picker
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    //menjalankan place picker
                    startActivityForResult(builder.build(Order.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        */

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mLocation1 = (EditText) findViewById(R.id.address_detail1);
        mLocation2 = (EditText) findViewById(R.id.address_detail2);

        Button order_button = (Button) findViewById(R.id.order_button);
        assert  order_button != null;
        order_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptCreateOrder();
            }
        });

        /*
        Button order_button = (Button) findViewById(R.id.order_button);
        order_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Ordermade.class);
                startActivityForResult(intent, 0);
            }
        });
        */
    }

    private void attemptCreateOrder() {
        if (mOrderTask != null) {
            return;
        }

        //showProgress(true);
        mOrderTask = new OrderTask(session.getUserId(), 1, -10.1, 34.4, "error: place.getAddress().toString()",
                mLocation1.getText() + " " + mLocation2.getText(), session.getVegetableWeight(), 4000);
        mOrderTask.execute((Void) null);
    }


    public class OrderTask extends AsyncTask<Void, Void, Boolean> {

        private final long user;
        private final int vegetable;
        private final double longitude;
        private final double latitude;
        private final String location;
        private final String note;
        private final int quantity;
        private final int price;


        OrderTask(long user, int vegetable, double longitude, double latitude,
                  String location, String note, int quantity, int price) {
            this.user = user;
            this.vegetable = vegetable;
            this.longitude = longitude;
            this.latitude = latitude;
            this.location = location;
            this.note = note;
            this.quantity = quantity;
            this.price = price;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }


            try {
                callServerResponse(user, vegetable, longitude, latitude, location, note, quantity, price);
            } catch (Exception e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mOrderTask = null;
            //showProgress(false);

            startActivity(new Intent(Order.this, Ordermade.class));

            if (success) {
                //Toast.makeText(getApplicationContext(), "Order has been successfully added.", Toast.LENGTH_LONG).show();
                finish();
            } else {
                // Toast.makeText(getApplicationContext(), "Register order failed...", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mOrderTask = null;
            //showProgress(false);
        }
    }

    public void callServerResponse(long user, int vegetable, double longitude, double latitude,
                                   String location, String note, int quantity, int price) throws JSONException {

        String json =
                String.format("{\"user\":%d,\"vegetable\":%d,\"longitude\":%f,\"latitude\":%f,\"location\":\"%s\",\"note\":\"%s\",\"quantity\":%d,\"price\":%s}",
                user, vegetable, longitude, latitude, location, note, quantity, price);

        String response = null;

        Log.d("JSON", json);

        try {
            response = post(APP_SERVER_IP + "api/order/", json);
            Log.d("Inside CallResponse:", "success");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Inside CallResponse:", "failed");
        }
        System.out.println(response);
    }

    public String post (String url, String json)throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        Log.d("TAG", response.body().string());
        return response.body().string();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order, menu);
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    // membuat Intent untuk Place Picker jika ingin mengubah lokasi yang telah dipilih menggunakan tombol atau editText
    public void viewMap (View view) {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            //menjalankan place picker
            startActivityForResult(builder.build(Order.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format(
                        "%s \n", place.getName());
                tvPlaceAPI.setText(toastMsg);
            }
        }
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlacePicker.getPlace(data, this);
//                String toastMsg = String.format(
//                        "Place: %s \n" +
//                                "Alamat: %s \n" +
//                                "Latlng %s \n", place.getName(), place.getAddress(), place.getLatLng().latitude+" "+place.getLatLng().longitude);
//                tvPlaceAPI.setText(toastMsg);
//            }
//        }
//    }

    public void moveToOrdermade(View view) {
        Intent intent = new Intent(Order.this, Ordermade.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_history) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FirstFragment()).commit();

        } else if (id == R.id.nav_order) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SecondFragment()).commit();

        } else if (id == R.id.nav_wishlist) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ThirdFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
