package deepshikha.jangidyahoo.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.Adapter.HomeGridViewAdapter;
import deepshikha.jangidyahoo.finalyearproject.model.HomeGridViewModel;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener  {
    TextToSpeech textToSpeech;
    AudioManager audioManager;
    GridView homeGV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        // Adding Grid Elements
        homeGV = findViewById(R.id.homeGridView);
        ArrayList<HomeGridViewModel> ModelArrayList = new ArrayList<HomeGridViewModel>();
        ModelArrayList.add(new HomeGridViewModel("Messages", R.drawable.ic_baseline_message_24));
        ModelArrayList.add(new HomeGridViewModel("Phone", R.drawable.ic_baseline_add_ic_call_24));
        ModelArrayList.add(new HomeGridViewModel("Notes", R.drawable.ic_baseline_event_note_24));
        ModelArrayList.add(new HomeGridViewModel("Battery", R.drawable.ic_baseline_battery_charging_full_24));
        HomeGridViewAdapter adapter = new HomeGridViewAdapter(this, ModelArrayList);
        homeGV.setAdapter(adapter);

        // Introductory Text
        textToSpeech = new TextToSpeech(this,this);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build();
        textToSpeech.setAudioAttributes(audioAttributes);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
            // Text-to-speech is ready to use
            String introText = "Welcome to voice assistant app. Click on the different sides of screen to know details" ;
            textToSpeech.speak(introText, TextToSpeech.QUEUE_FLUSH, null);

        }
    }
}