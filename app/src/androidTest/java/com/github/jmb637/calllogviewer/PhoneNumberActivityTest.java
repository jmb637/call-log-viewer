package com.github.jmb637.calllogviewer;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PhoneNumberActivityTest {
    @Rule
    public ActivityTestRule<PhoneNumberActivity> activityRule =
            new ActivityTestRule<>(PhoneNumberActivity.class, true, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("phoneNumberCursorParser", new MockPhoneNumberCursorParser());
        activityRule.launchActivity(intent);
    }

    @Test
    public void testPhoneNumberRecyclerView() {
        PhoneNumberActivity activity = activityRule.getActivity();

        RecyclerView phoneNumberRV =
                (RecyclerView) activity.findViewById(R.id.phoneNumberRecyclerView);
        assertEquals(2, phoneNumberRV.getAdapter().getItemCount());

        PhoneNumberAdapter.PhoneNumberViewHolder phoneNumberVH =
                (PhoneNumberAdapter.PhoneNumberViewHolder) phoneNumberRV.findViewHolderForAdapterPosition(0);
        assertEquals("(555) 123-4567", phoneNumberVH.phoneNumberView.getText().toString());
        assertEquals("some location1", phoneNumberVH.locationView.getText().toString());
        assertEquals(
                activity.getResources().getString(R.string.call_count_label),
                phoneNumberVH.callCountLabelView.getText().toString()
        );
        assertEquals("3", phoneNumberVH.callCountView.getText().toString());
        assertEquals(
                activity.getResources().getString(R.string.most_recent_label),
                phoneNumberVH.mostRecentLabelView.getText().toString()
        );
        assertFalse(phoneNumberVH.mostRecentView.getText().toString().isEmpty());

        phoneNumberVH =
                (PhoneNumberAdapter.PhoneNumberViewHolder) phoneNumberRV.findViewHolderForAdapterPosition(1);
        assertEquals("(555) 765-4321", phoneNumberVH.phoneNumberView.getText().toString());
        assertEquals("some location2", phoneNumberVH.locationView.getText().toString());
        assertEquals(
                activity.getResources().getString(R.string.call_count_label),
                phoneNumberVH.callCountLabelView.getText().toString()
        );
        assertEquals("1", phoneNumberVH.callCountView.getText().toString());
        assertEquals(
                activity.getResources().getString(R.string.most_recent_label),
                phoneNumberVH.mostRecentLabelView.getText().toString()
        );
        assertFalse(phoneNumberVH.mostRecentView.getText().toString().isEmpty());
    }
}
