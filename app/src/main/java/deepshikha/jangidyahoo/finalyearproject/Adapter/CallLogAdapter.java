package deepshikha.jangidyahoo.finalyearproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import deepshikha.jangidyahoo.finalyearproject.R;
import deepshikha.jangidyahoo.finalyearproject.model.CallLogItem;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.CallLogViewHolder> {

    private List<CallLogItem> callLogItems;

    public CallLogAdapter() {
        callLogItems = new ArrayList<>();
    }

    public void setCallLogItems(List<CallLogItem> callLogItems) {
        this.callLogItems = callLogItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CallLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_log_item, parent, false);
        return new CallLogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CallLogViewHolder holder, int position) {
        CallLogItem callLogItem = callLogItems.get(position);

        // Bind the data to the views within the ViewHolder
        holder.nameTextView.setText(callLogItem.getName());
        holder.phoneNumberTextView.setText(callLogItem.getPhoneNumber());
        holder.dateTimeTextView.setText(callLogItem.getDateTime());
    }

    @Override
    public int getItemCount() {
        return callLogItems.size();
    }

    public static class CallLogViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView phoneNumberTextView;
        public TextView dateTimeTextView;

        public CallLogViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_name);
            phoneNumberTextView = itemView.findViewById(R.id.text_phone_number);
            dateTimeTextView = itemView.findViewById(R.id.text_date_time);
        }
    }
}
