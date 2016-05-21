package com.pplnostrateam.arisyaag.insantani;

/**
 *
 * Created by desiratnamukti on 4/27/16.
 */
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;


public class MyProfileFragment extends Fragment {

    View myView;

    TextView userName;
    TextView userEmail;
    TextView userPhone;

    SessionManager session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fourth_layout_profile, container, false);

        session = new SessionManager(getActivity());

        Log.d("ProfileFragment:", session.getUserDetails().get("name"));
        Log.d("ProfileFragment:", session.getUserDetails().get("email"));
        //Log.d("ProfileFragment:", session.getUserDetails().get("phone"));

        userName = (TextView) myView.findViewById(R.id.my_profile_name);
        userName.setText(session.getUserDetails().get("name"));
        userEmail = (TextView) myView.findViewById(R.id.my_profile_email);
        userEmail.setText(session.getUserDetails().get("email"));

        ImageButton backButton = (ImageButton)myView.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        startActivity(intent);
            }
        });

        return myView;
    }


}

