package com.github.jmb637.calllogviewer;

import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * An adapter for displaying phone numbers in a recycler view.
 */
public class PhoneNumberAdapter
        extends RecyclerView.Adapter<PhoneNumberAdapter.PhoneNumberViewHolder> {
    /**
     * A simple view holder required by the recycler view.
     */
    public static class PhoneNumberViewHolder extends RecyclerView.ViewHolder {
        public PhoneNumberViewHolder(View itemView) {
            super(itemView);
        }
    }

    private final DateFormat dateFormat = DateFormat.getDateTimeInstance();
    private final View.OnClickListener clickListener;
    private List<PhoneNumber> phoneNumbers;

    /**
     * @param clickListener listener to be bound to every view holder in the list
     */
    public PhoneNumberAdapter(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * Standard interface implementation to create new view holders.
     */
    @Override
    public PhoneNumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.phone_number_list_item, parent, false);
        view.setOnClickListener(clickListener);
        return new PhoneNumberViewHolder(view);
    }

    /**
     * @param holder   the view holder being bound to
     * @param position the position inside of the list of phone numbers to be bound
     */
    @Override
    public void onBindViewHolder(PhoneNumberViewHolder holder, int position) {
        if (phoneNumbers == null) {
            return;
        }

        PhoneNumber phoneNumber = phoneNumbers.get(position);
        holder.itemView.setTag(phoneNumber);

        String formattedNumber = PhoneNumberUtils.formatNumber(phoneNumber.getNumber(), "US");
        TextView phoneNumberView = (TextView) holder.itemView.findViewById(R.id.phoneNumberView);
        phoneNumberView.setText(formattedNumber);

        if (phoneNumber.getLocation() != null && !phoneNumber.getLocation().isEmpty()) {
            TextView locationView = (TextView) holder.itemView.findViewById(R.id.locationView);
            locationView.setText(phoneNumber.getLocation());
            locationView.setVisibility(View.VISIBLE);
        }

        String formattedCallCount = String.format(Locale.getDefault(),
                "%d", phoneNumber.getCalls().size());
        TextView callCountView = (TextView) holder.itemView.findViewById(R.id.callCountView);
        callCountView.setText(formattedCallCount);

        String formattedDate = dateFormat.format(phoneNumber.getMostRecent());
        TextView mostRecentView = (TextView) holder.itemView.findViewById(R.id.mostRecentView);
        mostRecentView.setText(formattedDate);
    }

    /**
     * @return the total number of phone numbers
     */
    @Override
    public int getItemCount() {
        return phoneNumbers == null ? 0 : phoneNumbers.size();
    }

    public void swapPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        if (phoneNumbers == null) {
            this.phoneNumbers = null;
        } else {
            List<PhoneNumber> copy = new ArrayList<>(phoneNumbers);
            Collections.sort(copy, PhoneNumber.orderedByMostRecent(false));
            this.phoneNumbers = copy;
        }

        notifyDataSetChanged();
    }

    /**
     * @param comparator used to change the sorting of phone numbers
     */
    public void sortPhoneNumbers(Comparator<PhoneNumber> comparator) {
        if (phoneNumbers == null) {
            return;
        }

        Collections.sort(phoneNumbers, comparator);
        notifyDataSetChanged();
    }
}
