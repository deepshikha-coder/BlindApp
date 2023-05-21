package deepshikha.jangidyahoo.finalyearproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.CallLog;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.Adapter.PhoneViewPagerAdapter;
import deepshikha.jangidyahoo.finalyearproject.R;
import deepshikha.jangidyahoo.finalyearproject.model.CallLogItem;
import deepshikha.jangidyahoo.finalyearproject.model.messageModel;

public class Phone extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextToSpeech textToSpeech;
    AudioManager audioManager;
    TabLayout PhoneTabLayout;
    ViewPager PhoneViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        PhoneTabLayout = findViewById(R.id.PhoneTabLayout);
        PhoneViewPager= findViewById(R.id.PhoneViewPager);

        PhoneTabLayout.addTab(PhoneTabLayout.newTab().setText("Phone"));
        PhoneTabLayout.addTab(PhoneTabLayout.newTab().setText("Call Log"));
        PhoneTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        PhoneTabLayout.setBackgroundColor(Color.GRAY);
        PhoneTabLayout.setTabTextColors(Color.BLACK, Color.RED);

        final PhoneViewPagerAdapter adapter = new PhoneViewPagerAdapter(this,getSupportFragmentManager(), PhoneTabLayout.getTabCount());
        PhoneViewPager.setAdapter(adapter);
        PhoneViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(PhoneTabLayout));

        textToSpeech = new TextToSpeech(Phone.this, this);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build();
        textToSpeech.setAudioAttributes(audioAttributes);
        audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
        PhoneTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                // Perform actions based on the selected tab position
                switch (position) {
                    case 0:
                        speakOut("Dialer is open. Single click will add the numbers or long press for details. Or swipe Left for call Logs");
                        break;
                    case 1:
                        textToSpeech.stop();
                        speakOut("Call Log is open . Click on any number to speak the details.");
                        break;
                    // Add more cases for other tabs as needed
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
    public void speakOut(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}