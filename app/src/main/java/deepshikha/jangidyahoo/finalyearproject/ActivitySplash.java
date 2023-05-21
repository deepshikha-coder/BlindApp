package deepshikha.jangidyahoo.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class ActivitySplash extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextToSpeech textToSpeech;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent= new Intent(ActivitySplash.this, MainActivity.class);
                startActivity(intent);
            }
        }, 2000);

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
        String introText = "Welcome to vocal Eyes. 'Empowering Vision Through Voice.'" ;
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