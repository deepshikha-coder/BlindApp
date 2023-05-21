package deepshikha.jangidyahoo.finalyearproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import deepshikha.jangidyahoo.finalyearproject.R;
import deepshikha.jangidyahoo.finalyearproject.model.CallLogItem;
import deepshikha.jangidyahoo.finalyearproject.model.messageModel;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.CallLogViewHolder> {

    private List<CallLogItem> callLogItems;
    private OnClickListener onClickListener;
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
        holder.CallerName.setText(callLogItem.getName());
        holder.CallerNumber.setText(callLogItem.getPhoneNumber());
        holder.Date.setText(callLogItem.getDate());
        holder.Time.setText(callLogItem.getTime());
        switch (callLogItem.getType()){
            case "Incoming":
                holder.CallTypeSign.setImageResource(R.drawable.baseline_call_received_24);
                break;
            case "Outgoing":
                holder.CallTypeSign.setImageResource(R.drawable.baseline_call_made_24);
                break;
            case "Missed":
                holder.CallTypeSign.setImageResource(R.drawable.baseline_call_missed_24);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(holder.getAdapterPosition(), callLogItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }



    public interface OnClickListener {
        void onClick(int position, CallLogItem item);
    }
    public static class CallLogViewHolder extends RecyclerView.ViewHolder {
        public TextView CallerName;
        public TextView CallerNumber;
        public TextView Date;
        public TextView Time;
        public ImageView CallTypeSign;


        public CallLogViewHolder(View itemView) {
            super(itemView);
            CallerName = itemView.findViewById(R.id.CallerName);
            CallerNumber = itemView.findViewById(R.id.CallerNumber);
            Date = itemView.findViewById(R.id.CallDate);
            Time = itemView.findViewById(R.id.CallTime);
            CallTypeSign = itemView.findViewById(R.id.callLogo);
        }
    }
}
