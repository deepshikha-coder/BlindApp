package deepshikha.jangidyahoo.finalyearproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Window;

import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.R;

public class ActivitySplash extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextToSpeech textToSpeech;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                speakOut("Welcome to vocal Eyes. 'Empowering Vision Through Voice.");  //speak after 1000ms
            }
        }, 1000);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent= new Intent(ActivitySplash.this, MainActivity.class);
                startActivity(intent);
                textToSpeech.stop();
            }
        }, 5500);

        textToSpeech = new TextToSpeech(ActivitySplash.this, this);
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

    @Override
    public void onInit(int status) {
        int result = textToSpeech.setLanguage(Locale.getDefault());
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