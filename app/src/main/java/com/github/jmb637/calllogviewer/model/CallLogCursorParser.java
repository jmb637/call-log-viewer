package com.github.jmb637.calllogviewer.model;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Contains methods for accessing the call log through a cursor.
 */
public class CallLogCursorParser implements PhoneNumberCursorParser {
    private static class PhoneNumberArgs {
        public String location;
        public ArrayList<PhoneCall> calls = new ArrayList<>();
    }

    /**
     * @param context the context of the app
     * @return a cursor loader that will load a cursor compatible with the parser
     */
    @Override
    public CursorLoader getCursorLoader(Context context) {
        String projection[] = {CallLog.Calls.NUMBER, CallLog.Calls.DATE,
                CallLog.Calls.DURATION, CallLog.Calls.TYPE, CallLog.Calls.GEOCODED_LOCATION};
        String sortOrder = CallLog.Calls.DATE + " DESC";
        return new CursorLoader(context, CallLog.Calls.CONTENT_URI, projection, null, null, sortOrder);
    }

    /**
     * @param cursor a cursor specified by the getCursorLoader method to be parsed
     * @return a list of all phone numbers found in the call log
     */
    @Override
    public List<PhoneNumber> parsePhoneNumbers(@NonNull Cursor cursor) {
        HashMap<String, PhoneNumberArgs> argsByNumber = groupArgsByNumber(cursor);

        ArrayList<PhoneNumber> phoneNumbers = new ArrayList<>();
        for (PhoneNumberArgs args : argsByNumber.values()) {
            phoneNumbers.add(new PhoneNumber(args.location, args.calls));
        }

        Collections.sort(phoneNumbers, PhoneNumber.orderedByMostRecent(false));
        return Collections.unmodifiableList(phoneNumbers);
    }

    private HashMap<String, PhoneNumberArgs> groupArgsByNumber(@NonNull Cursor cursor) {
        HashMap<String, PhoneNumberArgs> argsByNumber = new HashMap<>();

        int initialPosition = cursor.getPosition();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            long epochMilliseconds = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            long duration = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));
            int typeValue = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            CallType type = CallType.fromValue(typeValue);

            PhoneCall call = new PhoneCall(number, new Date(epochMilliseconds), duration, type);

            PhoneNumberArgs args = argsByNumber.get(number);
            if (args == null) {
                args = new PhoneNumberArgs();
                args.location = cursor.getString(cursor.getColumnIndex(CallLog.Calls.GEOCODED_LOCATION));
                argsByNumber.put(number, args);
            }
            args.calls.add(call);
        }

        cursor.moveToPosition(initialPosition);
        return argsByNumber;
    }
}
