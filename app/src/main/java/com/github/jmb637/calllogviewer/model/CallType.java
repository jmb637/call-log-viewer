package com.github.jmb637.calllogviewer.model;

/**
 * An enum that contains an entry for each call type used by the call log.
 */
public enum CallType {
    INCOMING_TYPE(1),
    OUTGOING_TYPE(2),
    MISSED_TYPE(3),
    VOICEMAIL_TYPE(4),
    REJECTED_TYPE(5),
    BLOCKED_TYPE(6),
    ANSWERED_EXTERNALLY_TYPE(7);

    private static final CallType[] allValues = values();

    /**
     * @param i an integer corresponding to the value of a call log type
     * @return the call type with the same integer value
     */
    public static CallType fromValue(int i) {
        return allValues[i - 1];
    }

    private final int value;

    private CallType(int value) {
        this.value = value;
    }
}
