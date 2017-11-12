package com.github.jmb637.calllogviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.jmb637.calllogviewer.model.PhoneNumber;

/**
 * A simple activity that uses a fragment to display a phone number and a recycler view holding the
 * list of calls associated with that phone number.
 */
public class PhoneCallActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);

        PhoneCallFragment phoneCallFragment = (PhoneCallFragment)
                getSupportFragmentManager().findFragmentById(R.id.phoneCallFragment);
        PhoneNumber phoneNumber = (PhoneNumber) getIntent().getSerializableExtra("phoneNumber");
        phoneCallFragment.setPhoneNumber(phoneNumber);
    }
}
