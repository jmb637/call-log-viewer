package com.github.jmb637.calllogviewer.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * Immutable model of a phone call.
 */
public class PhoneCall implements Serializable {
    /**
     * @param ascending true to sort from least to greatest and false to sort from greatest to least
     * @return a comparator for comparing phone calls by their date
     */
    public static Comparator<PhoneCall> orderedByDate(final boolean ascending) {
        return new Comparator<PhoneCall>() {
            @Override
            public int compare(PhoneCall o1, PhoneCall o2) {
                int result = Long.compare(o1.epochMilliseconds, o2.epochMilliseconds);
                return ascending ? result : -result;
            }
        };
    }

    private final String number;
    private final long epochMilliseconds;
    private final long duration;
    private final CallType type;

    /**
     * @param number phone number of the other device
     * @param date the datetime when the phone call began
     * @param duration the length of the phone call in seconds
     * @param type the type of the call (incoming, outgoing, missed, etc.)
     */
    public PhoneCall(String number, Date date, long duration, CallType type) {
        this.number = number;
        this.epochMilliseconds = date.getTime();
        this.duration = duration;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public Date getDate() {
        return new Date(epochMilliseconds);
    }

    public long getDuration() {
        return duration;
    }

    public CallType getType() {
        return type;
    }
}
