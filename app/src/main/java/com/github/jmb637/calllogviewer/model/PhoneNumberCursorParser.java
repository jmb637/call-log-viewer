package com.github.jmb637.calllogviewer.model;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import java.io.Serializable;
import java.util.List;

/**
 * Contains methods for parsing phone numbers from a cursor.
 */
public interface PhoneNumberCursorParser extends Serializable {
    /**
     * @param context the context of the app
     * @return a cursor loader that will load a cursor compatible with the parser
     */
    CursorLoader getCursorLoader(Context context);

    /**
     * @param cursor a cursor specified by the getCursorLoader method to be parsed
     * @return a list of all phone numbers found in cursor
     */
    List<PhoneNumber> parsePhoneNumbers(Cursor cursor);
}
