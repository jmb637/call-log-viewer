package com.github.jmb637.calllogviewer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.*;

public class PhoneNumberUnitTests {
    private ArrayList<PhoneCall> calls;

    @Before
    public void setUp() {
        PhoneCall a = new PhoneCall("123", new Date(2), 0, CallType.INCOMING_TYPE);
        PhoneCall b = new PhoneCall("123", new Date(3), 0, CallType.INCOMING_TYPE);
        PhoneCall c = new PhoneCall("123", new Date(1), 0, CallType.INCOMING_TYPE);

        calls = new ArrayList<>();
        calls.add(a);
        calls.add(b);
        calls.add(c);
    }

    @Test
    public void testConstructor() {
        PhoneNumber phoneNumber = new PhoneNumber("some location", calls);
        assertEquals("some location", phoneNumber.getLocation());
        assertEquals(3, phoneNumber.getMostRecent().getTime());
        assertEquals("123", phoneNumber.getNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithEmptyCalls() {
        new PhoneNumber("", new ArrayList<PhoneCall>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMutatingCalls() {
        PhoneNumber phoneNumber = new PhoneNumber("", calls);
        phoneNumber.getCalls().add(null);
    }

    @Test
    public void testComparators() {
        ArrayList<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber("1", calls));

        ArrayList<PhoneCall> calls2 = new ArrayList<>();
        calls2.add(new PhoneCall("456", new Date(5), 0, CallType.INCOMING_TYPE));
        calls2.add(new PhoneCall("456", new Date(2), 0, CallType.INCOMING_TYPE));
        phoneNumbers.add(new PhoneNumber("2", calls2));

        ArrayList<PhoneCall> calls3 = new ArrayList<>();
        calls3.add(new PhoneCall("789", new Date(4), 0, CallType.INCOMING_TYPE));
        phoneNumbers.add(new PhoneNumber("3", calls3));

        Collections.sort(phoneNumbers, PhoneNumber.orderedByMostRecent(false));
        assertEquals("2", phoneNumbers.get(0).getLocation());
        assertEquals("3", phoneNumbers.get(1).getLocation());
        assertEquals("1", phoneNumbers.get(2).getLocation());

        Collections.sort(phoneNumbers, PhoneNumber.orderedByCallCount(false));
        assertEquals("1", phoneNumbers.get(0).getLocation());
        assertEquals("2", phoneNumbers.get(1).getLocation());
        assertEquals("3", phoneNumbers.get(2).getLocation());
    }
}
