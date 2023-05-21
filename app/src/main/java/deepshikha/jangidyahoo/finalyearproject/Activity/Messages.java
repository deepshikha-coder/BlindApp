package deepshikha.jangidyahoo.finalyearproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.Adapter.MessageViewPagerAdapter;
import deepshikha.jangidyahoo.finalyearproject.R;

public class Messages extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TabLayout MessageTabLayout;
    ViewPager MessageViewPager;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);


        MessageTabLayout = findViewById(R.id.MessageTabLayout);
        MessageViewPager= findViewById(R.id.MessagesPager);

        MessageTabLayout.addTab(MessageTabLayout.newTab().setText("Inbox"));
        MessageTabLayout.addTab(MessageTabLayout.newTab().setText("Sent"));
        MessageTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        MessageTabLayout.setTabTextColors(Color.BLACK, Color.RED);

        final MessageViewPagerAdapter adapter = new MessageViewPagerAdapter(this,getSupportFragmentManager(), MessageTabLayout.getTabCount());
        MessageViewPager.setAdapter(adapter);
        MessageViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(MessageTabLayout));
        textToSpeech = new TextToSpeech(Messages.this, this);
        MessageTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                // Perform actions based on the selected tab position
                switch (position) {
                    case 0:
                        textToSpeech.speak("Inbox is open", TextToSpeech.QUEUE_FLUSH, null);
                        break;
                    case 1:
                        textToSpeech.stop();
                        textToSpeech.speak("Sent message is open", TextToSpeech.QUEUE_FLUSH, null);
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
    public void onBackPressed() {
        Intent intent = new Intent(Messages.this, MainActivity.class);
        startActivity(intent);
        String text = "You are at home. press on the different sides of the screen to know details";
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
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
}