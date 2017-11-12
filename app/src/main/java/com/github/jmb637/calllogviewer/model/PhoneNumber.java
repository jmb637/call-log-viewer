package com.github.jmb637.calllogviewer.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Immutable model of a phone number with its calls.
 */
public class PhoneNumber implements Serializable {
    /**
     * @param ascending true to sort from least to greatest and false to sort from greatest to least
     * @return a comparator for comparing phone number by the date of their most recent call
     */
    public static Comparator<PhoneNumber> orderedByMostRecent(final boolean ascending) {
        return new Comparator<PhoneNumber>() {
            @Override
            public int compare(PhoneNumber o1, PhoneNumber o2) {
                int result = Long.compare(o1.getMostRecent().getTime(),
                        o2.getMostRecent().getTime());
                return ascending ? result : -result;
            }
        };
    }

    /**
     * @param ascending true to sort from least to greatest and false to sort from greatest to least
     * @return a comparator for comparing phone numbers the number of calls associated with the
     * number
     */
    public static Comparator<PhoneNumber> orderedByCallCount(final boolean ascending) {
        return new Comparator<PhoneNumber>() {
            @Override
            public int compare(PhoneNumber o1, PhoneNumber o2) {
                int result = Integer.compare(o1.getCalls().size(), o2.getCalls().size());
                return ascending ? result : -result;
            }
        };
    }

    private final String location;
    private final List<PhoneCall> calls;

    /**
     * @param location a possibly empty String describing the number's location based on its format
     * @param calls all calls associated with this phone number
     */
    public PhoneNumber(String location, @NonNull List<PhoneCall> calls) {
        if (calls.isEmpty()) {
            throw new IllegalArgumentException("calls must be non-empty");
        }

        this.location = location;
        Collections.sort(calls, PhoneCall.orderedByDate(false));
        this.calls = Collections.unmodifiableList(calls);
    }

    public String getNumber() {
        return calls.get(0).getNumber();
    }

    public Date getMostRecent() {
        return calls.get(0).getDate();
    }

    public String getLocation() {
        return location;
    }

    /**
     * @return an unmodifiable list of calls for this phone number
     */
    public List<PhoneCall> getCalls() {
        return calls;
    }
}
