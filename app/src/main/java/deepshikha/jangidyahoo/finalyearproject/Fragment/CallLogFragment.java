package deepshikha.jangidyahoo.finalyearproject.Fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CallLog;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.Activity.MainActivity;
import deepshikha.jangidyahoo.finalyearproject.Adapter.CallLogAdapter;
import deepshikha.jangidyahoo.finalyearproject.Adapter.RV_InboxAdapter;
import deepshikha.jangidyahoo.finalyearproject.R;
import deepshikha.jangidyahoo.finalyearproject.model.CallLogItem;
import deepshikha.jangidyahoo.finalyearproject.model.messageModel;

public class CallLogFragment extends Fragment implements TextToSpeech.OnInitListener {
    private List<CallLogItem> CallList;
    private RecyclerView recyclerView;
    private CallLogAdapter adapter;
    TextToSpeech textToSpeech;
    AudioManager audioManager;
    private int previousClickPosition = -1;

    public void CallLogsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_log, container, false);

        CallList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.RV_callLog);

        // Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Set adapter
        adapter = new CallLogAdapter();
        adapter.setCallLogItems(CallList);
        recyclerView.setAdapter(adapter);
        textToSpeech = new TextToSpeech(getContext(), this);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build();
        textToSpeech.setAudioAttributes(audioAttributes);
        audioManager = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
        adapter.setOnClickListener(new CallLogAdapter.OnClickListener() {
            @Override
            public void onClick(int position, CallLogItem item) {
                String dialerName;
                if(item.getName() == "Unknown"){
                    dialerName = item.getPhoneNumber();
                }else{
                    dialerName = item.getName();
                }
                if (previousClickPosition == position){
                    String phoneNumber = item.getPhoneNumber();
                    makePhoneCall(phoneNumber);
                }else{
                    textToSpeech.stop();
                    String confirmationSpeech  = "Do you want to make call to " + dialerName + ". Click again to confirm" ;
                    speakOut(confirmationSpeech);
                    previousClickPosition = position;
                }
            }

        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    speakOut("You are at home. press on the different sides of the screen to know details");


            }
        });

        retrieveCallLog();
        return view;
    }
    private void retrieveCallLog() {
        // Query the call log

        Cursor cursor = getContext().getContentResolver().query(
                CallLog.Calls.CONTENT_URI, null, null, null, null);
        // Check if the cursor is not null and has data

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve call log details
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                @SuppressLint("Range") long dateMillis = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String formattedDate = dateFormat.format(new Date(dateMillis));
                String formattedTime = timeFormat.format(new Date(dateMillis));
                String callerName = getCallerName(number);
                String callType = getCallTypeString(Integer.parseInt(type));
                CallLogItem item = new CallLogItem(callerName, callType,number, formattedDate, formattedTime);

                CallList.add(item);

                // displayCallDetails(callDetails);

            } while (cursor.moveToNext());
            cursor.close();
        }
    }
    private String getCallTypeString(int callType) {
        String typeString = "";
        switch (callType) {
            case CallLog.Calls.INCOMING_TYPE:
                typeString = "Incoming";
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                typeString = "Outgoing";
                break;
            case CallLog.Calls.MISSED_TYPE:
                typeString = "Missed";
                break;
            case CallLog.Calls.REJECTED_TYPE:
                typeString = "Rejected";
                break;

        }
        return typeString;
    }
    private String getCallerName(String phoneNumber) {

        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            cursor.close();
            return contactName;
        }

        return "Unknown";

    }
    private void makePhoneCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);

    }

    @Override
    public void onInit(int i) {
        int result = textToSpeech.setLanguage(Locale.getDefault());
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("MyAdapter", "Language not supported");
        } else {
            Log.e("MyAdapter", "Initialization failed");
        }
    }
    private void speakOut(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}