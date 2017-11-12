package com.github.jmb637.calllogviewer;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github.jmb637.calllogviewer.model.PhoneNumber;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class PhoneCallActivityTest {
    @Rule
    public ActivityTestRule<PhoneCallActivity> activityRule =
            new ActivityTestRule<>(PhoneCallActivity.class, true, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        MockPhoneNumberCursorParser cursorParser = new MockPhoneNumberCursorParser();
        PhoneNumber phoneNumber = cursorParser.parsePhoneNumbers(null).get(0);
        intent.putExtra("phoneNumber", phoneNumber);

        activityRule.launchActivity(intent);
    }

    @Test
    public void testPhoneNumberViews() {
        PhoneCallActivity activity = activityRule.getActivity();

        TextView phoneNumberView = (TextView) activity.findViewById(R.id.phoneNumberView);
        TextView locationView = (TextView) activity.findViewById(R.id.locationView);

        assertEquals("(555) 123-4567", phoneNumberView.getText().toString());
        assertEquals("some location1", locationView.getText().toString());

    }

    @Test
    public void testPhoneCallRecyclerView() {
        PhoneCallActivity activity = activityRule.getActivity();

        RecyclerView phoneCallRV =
                (RecyclerView) activity.findViewById(R.id.phoneCallRecyclerView);
        assertEquals(3, phoneCallRV.getAdapter().getItemCount());

        PhoneCallAdapter.PhoneCallViewHolder phoneCallVH =
                (PhoneCallAdapter.PhoneCallViewHolder) phoneCallRV.findViewHolderForAdapterPosition(0);
        assertFalse(phoneCallVH.dateView.getText().toString().isEmpty());
        assertEquals("0h:00m:02s", phoneCallVH.durationView.getText().toString());
        assertEquals("Incoming call", phoneCallVH.callTypeView.getText().toString());

        phoneCallVH =
                (PhoneCallAdapter.PhoneCallViewHolder) phoneCallRV.findViewHolderForAdapterPosition(1);
        assertFalse(phoneCallVH.dateView.getText().toString().isEmpty());
        assertEquals("0h:00m:01s", phoneCallVH.durationView.getText().toString());
        assertEquals("Outgoing call", phoneCallVH.callTypeView.getText().toString());

        phoneCallVH =
                (PhoneCallAdapter.PhoneCallViewHolder) phoneCallRV.findViewHolderForAdapterPosition(2);
        assertFalse(phoneCallVH.dateView.getText().toString().isEmpty());
        assertEquals("0h:00m:00s", phoneCallVH.durationView.getText().toString());
        assertEquals("Missed call", phoneCallVH.callTypeView.getText().toString());
    }
}
