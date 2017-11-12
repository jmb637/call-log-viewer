package com.github.jmb637.calllogviewer;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import com.github.jmb637.calllogviewer.model.CallType;
import com.github.jmb637.calllogviewer.model.PhoneCall;
import com.github.jmb637.calllogviewer.model.PhoneNumber;
import com.github.jmb637.calllogviewer.model.PhoneNumberCursorParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A mock implementation of the PhoneNumberCursorParser interface for testing.
 */
public class MockPhoneNumberCursorParser implements PhoneNumberCursorParser {
    /**
     * @param context the context of the app
     * @return an empty cursor loader
     */
    @Override
    public CursorLoader getCursorLoader(Context context) {
        return new CursorLoader(context, Uri.EMPTY, null, null, null, null);
    }

    /**
     * @param cursor an unused cursor
     * @return mock phone numbers
     */
    @Override
    public List<PhoneNumber> parsePhoneNumbers(Cursor cursor) {
        PhoneCall phoneCall1 = new PhoneCall("5551234567", new Date(3), 2, CallType.INCOMING_TYPE);
        PhoneCall phoneCall2 = new PhoneCall("5551234567", new Date(2), 1, CallType.OUTGOING_TYPE);
        PhoneCall phoneCall3 = new PhoneCall("5551234567", new Date(1), 0, CallType.MISSED_TYPE);

        ArrayList<PhoneCall> phoneCalls1 = new ArrayList<>();
        phoneCalls1.add(phoneCall1);
        phoneCalls1.add(phoneCall2);
        phoneCalls1.add(phoneCall3);
        PhoneNumber phoneNumber1 = new PhoneNumber("some location1", phoneCalls1);

        PhoneCall phoneCall4 = new PhoneCall("5557654321", new Date(0), 1, CallType.INCOMING_TYPE);

        ArrayList<PhoneCall> phoneCalls2 = new ArrayList<>();
        phoneCalls2.add(phoneCall4);
        PhoneNumber phoneNumber2 = new PhoneNumber("some location2", phoneCalls2);

        ArrayList<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phoneNumber1);
        phoneNumbers.add(phoneNumber2);

        return phoneNumbers;
    }
}
