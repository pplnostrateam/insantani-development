package com.pplnostrateam.arisyaag.insantani;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CompleteProfile extends AppCompatActivity {

    public static final String PREFS_NAME = "InsantaniAppPreference";

    private TextView mEmailView;
    private TextView mNameView;
    private EditText mPhoneView;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(getApplicationContext());

        // user email id
        mEmailView = (TextView) findViewById(R.id.textView14);
        assert mEmailView != null;
        mEmailView.setText(sessionManager.getUserDetails().get("email"));

        // user name
        mNameView = (TextView) findViewById(R.id.textView12);
        assert mNameView != null;
        mNameView.setText(sessionManager.getUserDetails().get("name"));

        mPhoneView = (EditText) findViewById(R.id.password);
        mPhoneView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    //attemptSubmitProfile();
                    return true;
                }

                return false;
            }
        });

        Button mCompleteProfileButton = (Button) findViewById(R.id.button);
        assert mCompleteProfileButton != null;
        mCompleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptSubmitProfile();
                startActivity(new Intent(CompleteProfile.this, Order.class));
            }
        });
    }


}
