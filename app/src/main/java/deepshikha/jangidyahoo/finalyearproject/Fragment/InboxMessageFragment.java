package deepshikha.jangidyahoo.finalyearproject.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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

import deepshikha.jangidyahoo.finalyearproject.Adapter.RV_InboxAdapter;
import deepshikha.jangidyahoo.finalyearproject.R;
import deepshikha.jangidyahoo.finalyearproject.model.messageModel;


public class InboxMessageFragment extends Fragment implements TextToSpeech.OnInitListener {

    private RecyclerView recyclerView;
    private RV_InboxAdapter adapter;
    private List<messageModel> messageList;
    TextToSpeech textToSpeech;
    AudioManager audioManager;
    private int previousClickPosition;
    private static final int REQUEST_CODE_SMS_PERMISSION = 323;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_inbox, container, false);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.RV_Inbox);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create and set the adapter
        messageList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_SMS}, REQUEST_CODE_SMS_PERMISSION);
        } else {
            // Permission is already granted, proceed with retrieving the sent messages
            getPhoneMessages();
        }

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



        adapter = new RV_InboxAdapter(getContext(),"InboxMessageFragment",messageList);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new RV_InboxAdapter.OnClickListener() {
            @Override
            public void onClick(int position, messageModel model) {
                if (previousClickPosition == position){
                    String textToSpeak = model.getContent();
                    speakOut(textToSpeak);


                }else{
                    String confirmationSpeech  = "You clicked message from " + model.getSender() +" at " + model.getTime() + "on" + model.getDate();
                    speakOut(confirmationSpeech + ".   click again on message to read");
                    previousClickPosition = position;

                    }
                }

        });

        return view;


    }

    private void getPhoneMessages() {


        // Query the phone messages using the SMS content provider
        Uri uri = Uri.parse("content://sms/");
        ContentResolver cr = getActivity().getContentResolver();

        Cursor cursor = cr.query(uri, null, null, null, null);
        getActivity().startManagingCursor(cursor);
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

                // Format the date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(new Date(timestamp));

                // Format the time
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String formattedTime = timeFormat.format(new Date(sentTime));

                // Create a Message object and add it to the messageList
                messageModel message = new messageModel(sender, content, formattedDate, formattedTime);
                messageList.add(message);
            } while (cursor.moveToNext());

            cursor.close();
            try {
                adapter.notifyDataSetChanged();
            }catch (Exception e){
                Log.e("Adapter Issue : ", e.toString());
            }

        }

    }
    @Override
    public void onInit(int i) {
        int result = textToSpeech.setLanguage(Locale.getDefault());
        String SentIntroText = "You are at Inbox messages. swipe Left for sent messages";
        speakOut(SentIntroText);
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