package com.pplnostrateam.arisyaag.insantani;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CompleteProfileActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "InsantaniAppPreference";

    private TextView mEmailView;
    private TextView mNameView;
    private EditText mPhoneView;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext());

        // user email id
        mEmailView = (TextView) findViewById(R.id.textView14);
        assert mEmailView != null;
        Log.d("CompleteProfile", mEmailView.getText().toString());
        mEmailView.setText(session.getUserDetails().get("email"));
        //Log.d("mEmailView:", session.getUserDetails().get("email"));

        // user name
        mNameView = (TextView) findViewById(R.id.textView12);
        assert mNameView != null;
        Log.d("CompleteProfile", mNameView.getText().toString());
        mNameView.setText(session.getUserDetails().get("name"));
        //Log.d("mNameView:", session.getUserDetails().get("name"));

        mPhoneView = (EditText) findViewById(R.id.editText);

        Button mCompleteProfileButton = (Button) findViewById(R.id.button);
        assert mCompleteProfileButton != null;
        mCompleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptSubmitProfile();
                if (mPhoneView.getText().equals(""))
                    emptyPhone();
                else
                    startActivity(new Intent(CompleteProfileActivity.this, OrderActivity.class));
            }
        });
    }

    public void emptyPhone (){
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(this);
        alertDialogue.setMessage("Phone number cannot be empty");
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
