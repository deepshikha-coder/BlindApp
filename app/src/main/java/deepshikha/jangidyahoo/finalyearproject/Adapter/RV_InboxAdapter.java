package deepshikha.jangidyahoo.finalyearproject.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.util.List;

import deepshikha.jangidyahoo.finalyearproject.R;
import deepshikha.jangidyahoo.finalyearproject.model.messageModel;

public class RV_InboxAdapter extends RecyclerView.Adapter<RV_InboxAdapter.ViewHolder> {
    private List<messageModel> messageList;
    private OnClickListener onClickListener;
    public RV_InboxAdapter(List<messageModel> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout XML and create a ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_carditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the ViewHolder
        messageModel message = messageList.get(position);
        holder.senderTextView.setText(message.getSender());
        holder.contentTextView.setText(message.getContent());
        holder.DateTextView.setText(message.getDate());
        holder.TimeTextView.setText(message.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(holder.getAdapterPosition(), message);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, messageModel message);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView senderTextView;
        public TextView contentTextView;
        public TextView DateTextView;
        public TextView TimeTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.senders_name);
            contentTextView = itemView.findViewById(R.id.Sender_Message);
            DateTextView = itemView.findViewById(R.id.Message_date);
            TimeTextView = itemView.findViewById(R.id.Message_time);

        }
    }

}