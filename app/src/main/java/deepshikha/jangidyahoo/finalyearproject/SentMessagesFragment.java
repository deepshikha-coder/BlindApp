package deepshikha.jangidyahoo.finalyearproject;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.Adapter.RV_InboxAdapter;
import deepshikha.jangidyahoo.finalyearproject.model.messageModel;


public class SentMessagesFragment extends Fragment {
    private RV_InboxAdapter adapter;
    private List<messageModel> sentItemList;
    private static final int REQUEST_CODE_SMS_PERMISSION = 323;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sent_messages, container, false);

        // Initialize the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.RV_sentItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create and set the adapter
        sentItemList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.READ_SMS}, REQUEST_CODE_SMS_PERMISSION);
        } else {
            getSentMessages();

        }
        // Retrieve phone messages
        adapter = new RV_InboxAdapter(sentItemList);
        recyclerView.setAdapter(adapter);

        return view;

    }
    private void getSentMessages() {
        // Query the sent messages using the SMS content provider
        Uri uri = Uri.parse("content://sms/sent");
        ContentResolver cr = requireActivity().getContentResolver();

        Cursor cursor = cr.query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int senderIndex = cursor.getColumnIndex("address");
            int contentIndex = cursor.getColumnIndex("body");
            int dateIndex = cursor.getColumnIndex("date");
            int timeIndex = cursor.getColumnIndex("date_sent");
            do {
                // Retrieve sender and content from the cursor
                String sender = cursor.getString(senderIndex);
                String content = cursor.getString(contentIndex);
                long timestamp = cursor.getLong(dateIndex);
                long sentTime = cursor.getLong(timeIndex);

                String contactName = getContactNameFromNumber(sender);
                if (contactName != null) {
                    sender = contactName;
                }

                // Format the date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(new Date(timestamp));

                // Format the time
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String formattedTime = timeFormat.format(new Date(sentTime));

                // Create a Message object and add it to the messageList
                messageModel message = new messageModel(sender, content, formattedDate, formattedTime);
                sentItemList.add(message);
            } while (cursor.moveToNext());

            cursor.close();
            try {
                adapter.notifyDataSetChanged();
            }catch (Exception e){
                Log.e("Adapter Issue : ", e.toString());
            }

        }
    }

    private String getContactNameFromNumber(String phoneNumber) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            cursor.close();
            return contactName;
        }

        return null;
    }
}