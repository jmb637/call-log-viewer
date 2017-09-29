package com.github.jmb637.calllogviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * A simple activity that uses a fragment to display a recycler view containing every phone number
 * found in the call log.
 */
public class PhoneNumberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
    }
}
