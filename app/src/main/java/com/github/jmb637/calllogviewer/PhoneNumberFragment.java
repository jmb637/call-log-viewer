package com.github.jmb637.calllogviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.jmb637.calllogviewer.model.PhoneNumber;

import java.util.List;

/**
 * A fragment that displays a recycler view containing every phone number found in the call log.
 */
public class PhoneNumberFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private PhoneNumberAdapter adapter;

    /**
     * Standard fragment override to return this fragment's view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_number, container, false);

        adapter = new PhoneNumberAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PhoneCallActivity.class);
                PhoneNumber phoneNumber = (PhoneNumber) v.getTag();
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.phoneNumberRecyclerView);
        recyclerView.setAdapter(adapter);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        configureActionBar(actionBar, inflater);


        return view;
    }

    /**
     * Changes the sorting of phone numbers based on the spinner's new position.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                adapter.sortPhoneNumbers(PhoneNumber.orderedByMostRecent(false));
                break;
            case 1:
                adapter.sortPhoneNumbers(PhoneNumber.orderedByCallCount(false));
                break;
            default:
                throw new RuntimeException();
        }
    }

    /**
     * No change in the sorting is made if no new option is selected.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        adapter.swapPhoneNumbers(phoneNumbers);
    }

    private void configureActionBar(ActionBar actionBar, LayoutInflater inflater) {
        View actionBarLayout = inflater.inflate(R.layout.action_bar_phone_number, null);

        Spinner spinner = (Spinner) actionBarLayout.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_choices_array, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        actionBar.setCustomView(actionBarLayout);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }
}
