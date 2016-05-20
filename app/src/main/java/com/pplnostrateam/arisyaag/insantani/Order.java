package com.pplnostrateam.arisyaag.insantani;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.w3c.dom.Text;

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

    private TextView tvPlaceAPI;

    private View mProgressView;
    private View mOrderFormView;

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OrderTask mOrderTask = null;

    SessionManager session;

    Place place;

    private double longitude = 0;
    private double latitude = 0;
    private String address = null;

    private EditText mLocation1;
    private EditText mLocation2;

    private TextView userName;
    private TextView userEmail;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext());

        tvPlaceAPI = (TextView) findViewById(R.id.tv_place_id);
        // ImageButton btPlacesAPI = (ImageButton) findViewById(R.id.bt_ppicker);

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
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        mLocation1 = (EditText) findViewById(R.id.address_detail1);
        mLocation2 = (EditText) findViewById(R.id.address_detail2);

        Button orderButton = (Button) findViewById(R.id.order_button);
        assert orderButton != null;
        orderButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mLocation1.getText().toString().equals("") || !mLocation2.getText().toString().equals(""))
                    attemptCreateOrder();
                else
                    emptyDetailNotAllowed();
            }
        });

        mOrderFormView = findViewById(R.id.order_form);
        mProgressView = findViewById(R.id.order_progress);


        userName = (TextView) findViewById(R.id.user_name_nav);
        if (userName == null)
            Log.d("Debug:", "user_name_nave not found");
        else
            userName.setText(session.getUserDetails().get("name"));

        userEmail = (TextView) findViewById(R.id.userEmail);
        if (userEmail == null)
            Log.d("Debug:", "userEmail not found");
        else
            userEmail.setText(session.getUserDetails().get("email"));

        /*
        Button order_button = (Button) findViewById(R.id.order_button);
        order_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Ordermade.class);
                startActivityForResult(intent, 0);
            }
        });
        */
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void attemptCreateOrder() {
        if (mOrderTask != null) {
            return;
        }

        long userId = Long.parseLong(session.getUserDetails().get("userId"));
        int vegetableId = session.getVegetableDetails().get("vegetableId");

        if (place == null) {
            longitude = 0;
            latitude = 0;
            address = null;
        } else {
            longitude = place.getLatLng().longitude;
            latitude = place.getLatLng().latitude;
            address = place.getAddress().toString();
        }
        Editable location1 = mLocation1.getText();
        Editable location2 = mLocation2.getText();
        int weight = session.getVegetableDetails().get("quantity");
        int price = session.getVegetableDetails().get("price");

        //showProgress(true);
        mOrderTask = new OrderTask(userId, vegetableId,latitude, longitude, address, location1 + " " + location2, weight, price);
        mOrderTask.execute((Void) null);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Order Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.pplnostrateam.arisyaag.insantani/http/host/path")
        );
        AppIndex.AppIndexApi.start(client2, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Order Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.pplnostrateam.arisyaag.insantani/http/host/path")
        );
        AppIndex.AppIndexApi.end(client2, viewAction);
        client2.disconnect();
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
            showProgress(false);

            startActivity(new Intent(Order.this, Ordermade.class));

            if (success) {
                //Toast.makeText(getApplicationContext(), "Order has been successfully added.", Toast.LENGTH_LONG).show();
                finish();
            }
            /*
            else {
                // Toast.makeText(getApplicationContext(), "Register order failed...", Toast.LENGTH_LONG).show();
            }
            */
        }

        @Override
        protected void onCancelled() {
            mOrderTask = null;
            showProgress(false);
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

    public String post(String url, String json) throws IOException {
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
        assert drawer != null;
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
    public void viewMap(View view) {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            //menjalankan place picker
            startActivityForResult(builder.build(Order.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
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

    /*
    public void moveToOrdermade(View view) {
        Intent intent = new Intent(Order.this, Ordermade.class);
        startActivity(intent);
    }
    */

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mOrderFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mOrderFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mOrderFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mOrderFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void emptyDetailNotAllowed(){
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(this);
        alertDialogue.setMessage("please fill order detail");
        alertDialogue.setCancelable(false);

        alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = alertDialogue.create();
        dialog.show();
    }
}
