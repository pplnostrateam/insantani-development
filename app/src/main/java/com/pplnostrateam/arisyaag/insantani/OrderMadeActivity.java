package com.pplnostrateam.arisyaag.insantani;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;

public class OrderMadeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager session;

    private TextView userName;
    private TextView userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermade);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        session = new SessionManager(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        userName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name_nav);
        userName.setText(session.getUserDetails().get("name"));

        userEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userEmail);
        userEmail.setText(session.getUserDetails().get("email"));

        //on createorder button
        Button createorder = (Button) findViewById(R.id.createorder);
        createorder.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(OrderMadeActivity.this, OrderActivity.class);
                OrderMadeActivity.this.startActivity(intent);
            }
        });

        //on statusorder button
        //intentnya masih dummy (ke halaman status) harusnya ke list of order
        Button view_all_order = (Button) findViewById(R.id.view_all_order);
        view_all_order.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), OrderStatusAcceptedActivity.class);
                startActivityForResult(intent, 0);
            }
        });

/*        Intent myIntent = new Intent(OrderMadeActivity.this, OrderActivity.class);
        OrderMadeActivity.this.startActivity(myIntent);*/
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_history) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MyHistoryFragment()).commit();

        } else if (id == R.id.nav_order) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MyOrderFragment()).commit();

        } else if (id == R.id.nav_wishlist) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MyWishlistFragment()).commit();

        } else if (id == R.id.nav_profile) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MyProfileFragment()).commit();

        } else if (id == R.id.nav_logout) {
            logoutNotification();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logoutNotification(){
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(this);
        alertDialogue.setMessage("Your login session will be deleted. Are you sure?");
        alertDialogue.setCancelable(true);

        alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                session.logoutUser();
            }
        });
        AlertDialog dialog = alertDialogue.create();
        dialog.show();
    }
}

