package com.pplnostrateam.arisyaag.insantani;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompleteProfileActivity extends AppCompatActivity implements GlobalConfig, LoaderManager.LoaderCallbacks<Cursor> {

    private UserRegisterTask mAuthTask = null;

    public static final String PREFS_NAME = "InsantaniAppPreference";
    public static final int NO_OPTIONS=0;

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
        Log.d("mEmailView:", session.getUserDetails().get("email"));

        // user name
        mNameView = (TextView) findViewById(R.id.textView12);
        assert mNameView != null;
        Log.d("CompleteProfile", mNameView.getText().toString());
        mNameView.setText(session.getUserDetails().get("name"));
        Log.d("mNameView:", session.getUserDetails().get("name"));

        mPhoneView = (EditText) findViewById(R.id.editText);

        Button mCompleteProfileButton = (Button) findViewById(R.id.button);
        assert mCompleteProfileButton != null;
        mCompleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptSubmitProfile();
                if (mPhoneView.getText().equals("")) {
                    mAuthTask = new UserRegisterTask(session.getUserDetails().get("email"),
                            session.getUserDetails().get("name"), "", mPhoneView.getText().toString());
                    mAuthTask.execute((Void) null);
                }
                   // emptyPhone();
                else
                    startActivity(new Intent(CompleteProfileActivity.this, OrderActivity.class));
            }
        });
    }

    public void emptyPhone () {
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        // int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mName;
        private final String mPhone;

        UserRegisterTask(String email, String name, String password, String phone) {
            mEmail = email;
            mPassword = password;
            mName = name;
            mPhone = phone;
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
                registerUserRestServer(mEmail, mName, mPassword, mPhone);
            } catch (Exception e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                // Toast.makeText(getApplicationContext(), "User has been successfully added.", Toast.LENGTH_LONG).show();

                finish();
            } else {
                // Toast.makeText(getApplicationContext(), "Sign up failed...", Toast.LENGTH_LONG).show();
                // mPasswordView.setError(getString(R.string.error_sign_up_failed));
                // mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            // showProgress(false);
        }
    }

    public void registerUserRestServer(String email, String name, String password, String phone) throws Exception {
        String url = APP_SERVER_IP + "api/user";
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {

            User theUser = null;

            User request = new User(email, name, computeSHAHash(password), phone);


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<User> entity = new HttpEntity<>(request, headers);

            ResponseEntity<User> loginResponse = rest.exchange(url, HttpMethod.POST, entity, User.class);

            Log.d("SignInActivity", "After post query");

            if (loginResponse.getStatusCode() == HttpStatus.OK) {
                String getterURL = url + "/find?email={email}";
                //theUser = rest.getForObject(getterURL, User.class, email);
                ResponseEntity<User> response = rest.getForEntity(getterURL, User.class, email);

                Log.d("HttpResponse", response.getStatusCode().toString());
                theUser = response.getBody();

                Log.d("SignInActivity", "After check query");

                Log.d("Return ID", Long.toString(theUser.getId()));
                Log.d("Return Name", theUser.getName());
                Log.d("Return Email", theUser.getEmail());

            } else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Log.d("statusCode", "HttpStatus.UNAUTHORIZED");
            }

            Log.d("Output#1", theUser.getName());

            Log.d("Output#2", theUser.getName());

        } catch (Exception e) {
            if(e instanceof ResourceAccessException){
                throw new Exception("Connection to server failed");
            } else {
                throw new Exception(e.getMessage());
            }
        }
    }

    private static String convertToHex(byte[] data) throws java.io.IOException {
        StringBuffer sb = new StringBuffer();
        String hex=null;

        hex= Base64.encodeToString(data, 0, data.length, NO_OPTIONS);

        sb.append(hex);

        return sb.toString();
    }


    public String computeSHAHash(String password) {
        MessageDigest mdSha1 = null;
        String SHAHash = null;

        try {
            mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            Log.e("myapp", "Error initializing SHA1 message digest");
        }

        try {
            mdSha1.update(password.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] data = mdSha1.digest();

        try {
            SHAHash=convertToHex(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SHAHash;
    }


    public String computeMD5Hash(String password) {
        StringBuffer MD5Hash = new StringBuffer();

        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return MD5Hash.toString();
    }
}
