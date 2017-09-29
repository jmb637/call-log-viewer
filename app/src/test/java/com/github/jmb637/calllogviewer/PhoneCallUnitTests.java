package com.github.jmb637.calllogviewer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.*;

public class PhoneCallUnitTests {
    @Test
    public void testConstructor() {
        String number = "1";
        Date date = new Date(1);
        long duration = 1;
        CallType type = CallType.INCOMING_TYPE;
        PhoneCall phoneCall = new PhoneCall(number, date, duration, type);

        assertEquals(number, phoneCall.getNumber());
        assertEquals(date, phoneCall.getDate());
        assertEquals(duration, phoneCall.getDuration());
        assertEquals(type, phoneCall.getType());
    }

    @Test
    public void testDateImmutability() {
        String number = "1";
        Date date = new Date(1);
        long duration = 1;
        CallType type = CallType.INCOMING_TYPE;
        PhoneCall phoneCall = new PhoneCall(number, date, duration, type);

        assertEquals(1, phoneCall.getDate().getTime());
        date.setTime(2);
        assertEquals(1, phoneCall.getDate().getTime());
    }

    @Test
    public void testComparator() {
        ArrayList<PhoneCall> calls = new ArrayList<>();

        String number = "1";
        Date date = new Date(1);
        long duration = 1;
        CallType type = CallType.INCOMING_TYPE;
        calls.add(new PhoneCall(number, date, duration, type));

        date.setTime(2);
        calls.add(new PhoneCall(number, date, duration, type));

        date.setTime(3);
        calls.add(new PhoneCall(number, date, duration, type));

        Collections.sort(calls, PhoneCall.orderedByDate(false));
        assertEquals(3, calls.get(0).getDate().getTime());
        assertEquals(2, calls.get(1).getDate().getTime());
        assertEquals(1, calls.get(2).getDate().getTime());
    }
}
