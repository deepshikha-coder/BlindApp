package deepshikha.jangidyahoo.finalyearproject.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.R;

public class AddNotesActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    TextView titleText, bodyText, body;
    private int activeTextViewId = -1;
    TextToSpeech textToSpeech;
    AudioManager audioManager;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_notes);

        titleText = findViewById(R.id.titleEditText);
        bodyText = findViewById(R.id.bodyEditText);
        body = findViewById(R.id.body);

        titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                speakOut("Title is focused.");
                // Set the active TextView ID to indicate which TextView triggered the speech recognition
                activeTextViewId = titleText.getId();
                // Speak title if focused
                speakOut("Title is focused. You can now speak to add title.");
                // Start speech recognition
                startSpeechRecognition();
            }
        });

        bodyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the active TextView ID to indicate which TextView triggered the speech recognition
                activeTextViewId = bodyText.getId();
                // Speak body if focused
                speakOut("Body is focused. You can now speak to add body.");
                // Start speech recognition
                startSpeechRecognition();
            }
        });

        body.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(@NonNull MotionEvent e) {
                    speakOut("Note is saved, going back to the list of notes.");
                    Intent intent = new Intent(AddNotesActivity.this, Notes.class);
                    intent.putExtra("Title", titleText.getText().toString());
                    intent.putExtra("Body",bodyText.getText().toString());
                    startActivity(intent);
                    super.onLongPress(e);
                }
            });
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });

        textToSpeech = new TextToSpeech(AddNotesActivity.this, this);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build();
        textToSpeech.setAudioAttributes(audioAttributes);
        audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
    }
    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");

        try{
            // if no error show dialog
            startActivityForResult(intent, 1000);
        }catch (Exception e){
            Toast.makeText(AddNotesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1000:{
                if(resultCode == RESULT_OK && null != data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    // Determine which TextView triggered the speech recognition based on the activeTextViewId
                    if (activeTextViewId == titleText.getId()) {
                        titleText.setText(result.get(0));
                    } else if (activeTextViewId == bodyText.getId()) {
                        bodyText.setText(result.get(0));
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onInit(int i) {
        int result = textToSpeech.setLanguage(Locale.getDefault());
        String introText = "You can add new note here. Tap on the top of the screen to add title and tap below the title to add body. Long press on the screen to save the note." ;
        speakOut(introText);
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("MyAdapter", "Language not supported");
        } else {
            Log.e("MyAdapter", "Initialization failed");
        }
    }
    public void speakOut(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
