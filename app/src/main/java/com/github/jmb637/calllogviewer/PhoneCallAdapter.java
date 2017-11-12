package com.github.jmb637.calllogviewer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jmb637.calllogviewer.model.CallType;
import com.github.jmb637.calllogviewer.model.PhoneCall;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * An adapter for displaying phone calls in a recycler view.
 */
public class PhoneCallAdapter extends RecyclerView.Adapter<PhoneCallAdapter.PhoneCallViewHolder> {
    /**
     * A simple view holder required by the recycler view.
     */
    public static class PhoneCallViewHolder extends RecyclerView.ViewHolder {
        public final TextView dateView;
        public final TextView durationView;
        public final TextView callTypeView;

        public PhoneCallViewHolder(View itemView) {
            super(itemView);

            dateView = (TextView) itemView.findViewById(R.id.dateView);
            durationView = (TextView) itemView.findViewById(R.id.durationView);
            callTypeView = (TextView) itemView.findViewById(R.id.callTypeView);
        }
    }

    private final DateFormat dateFormat = DateFormat.getDateTimeInstance();
    private final List<PhoneCall> phoneCalls;

    /**
     * @param phoneCalls the phone calls to be displayed
     */
    public PhoneCallAdapter(@NonNull List<PhoneCall> phoneCalls) {
        List<PhoneCall> copy = new ArrayList<>(phoneCalls);
        Collections.sort(copy, PhoneCall.orderedByDate(false));
        this.phoneCalls = copy;
    }

    /**
     * Standard interface implementation to create new view holders.
     */
    @Override
    public PhoneCallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.phone_call_list_item, parent, false);
        return new PhoneCallViewHolder(view);
    }

    /**
     * @param holder   the view holder being bound to
     * @param position the position inside of the list of phone calls to be bound
     */
    @Override
    public void onBindViewHolder(PhoneCallViewHolder holder, int position) {
        if (phoneCalls == null) {
            return;
        }

        PhoneCall phoneCall = phoneCalls.get(position);

        String formattedDate = dateFormat.format(phoneCall.getDate());
        TextView dateView = holder.dateView;
        dateView.setText(formattedDate);

        long duration = phoneCall.getDuration();
        long hours = duration / (60 * 60);
        long minutes = (duration / 60) % 60;
        long seconds = duration % 60;
        String formattedDuration = String.format(Locale.getDefault(),
                "%dh:%02dm:%02ds", hours, minutes, seconds);
        TextView durationView = holder.durationView;
        durationView.setText(formattedDuration);

        String formattedCallType = formatCallType(phoneCall.getType());
        TextView callTypeView = holder.callTypeView;
        callTypeView.setText(formattedCallType);
    }

    /**
     * @return the total number of phone calls
     */
    @Override
    public int getItemCount() {
        return phoneCalls.size();
    }

    private String formatCallType(CallType callType) {
        switch (callType) {
            case INCOMING_TYPE:
                return "Incoming call";
            case OUTGOING_TYPE:
                return "Outgoing call";
            case MISSED_TYPE:
                return "Missed call";
            case REJECTED_TYPE:
                return "Rejected call";
            case BLOCKED_TYPE:
                return "Blocked call";
            default:
                return "Other";
        }
    }
}
