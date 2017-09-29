package com.github.jmb637.calllogviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * A simple activity that uses a fragment to display a phone number and a recycler view holding the
 * list of calls associated with that phone number.
 */
public class PhoneCallActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);
    }
}
