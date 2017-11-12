package com.github.jmb637.calllogviewer;


import android.support.v4.app.Fragment;;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jmb637.calllogviewer.model.PhoneNumber;

/**
 * A fragment that displays a phone number and a recycler view holding the list of calls associated
 * with that phone number.
 */
public class PhoneCallFragment extends Fragment {
    private View view;

    /**
     * Standard fragment override to return this fragment's view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_phone_call, container, false);
        return view;
    }

    public void setPhoneNumber(final PhoneNumber phoneNumber) {
        String formattedNumber = PhoneNumberUtils.formatNumber(phoneNumber.getNumber(), "US");
        TextView phoneNumberView = (TextView) view.findViewById(R.id.phoneNumberView);
        phoneNumberView.setText(formattedNumber);

        phoneNumberView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                String query = phoneNumber.getNumber() + " phone number";
                intent.putExtra(SearchManager.QUERY, query);
                startActivity(intent);
            }
        });

        if (!phoneNumber.getLocation().isEmpty()) {
            TextView locationView = (TextView) view.findViewById(R.id.locationView);
            locationView.setText(phoneNumber.getLocation());
            locationView.setVisibility(View.VISIBLE);
        }

        PhoneCallAdapter adapter = new PhoneCallAdapter(phoneNumber.getCalls());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.phoneCallRecyclerView);
        recyclerView.setAdapter(adapter);
    }
}
