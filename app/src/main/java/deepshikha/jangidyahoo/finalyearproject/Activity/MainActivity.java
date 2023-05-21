package deepshikha.jangidyahoo.finalyearproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.Adapter.HomeGridViewAdapter;
import deepshikha.jangidyahoo.finalyearproject.R;
import deepshikha.jangidyahoo.finalyearproject.model.HomeGridViewModel;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    TextToSpeech homeTextToSpeech;
    AudioManager audioManager;
    GridView homeGV;
    final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static final int PERMISSION_REQUEST_CODE = 323;
    private String previousCLick = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.INTERNET","android.permission.RECORD_AUDIO","android.permission.CALL_PHONE" ,"android.permission.READ_SMS","android.permission.READ_CALL_LOG" , "android.permission.BATTERY_STATS", "android.permission.READ_CONTACTS"}, REQUEST_CODE_ASK_PERMISSIONS);
        // Adding Grid Elements
        homeGV = findViewById(R.id.homeGridView);
        ArrayList<HomeGridViewModel> ModelArrayList = new ArrayList<HomeGridViewModel>();
        ModelArrayList.add(new HomeGridViewModel("Messages", R.drawable.ic_baseline_message_24));
        ModelArrayList.add(new HomeGridViewModel("Phone", R.drawable.ic_baseline_add_ic_call_24));
        ModelArrayList.add(new HomeGridViewModel("Notes", R.drawable.ic_baseline_event_note_24));
        ModelArrayList.add(new HomeGridViewModel("Battery", R.drawable.ic_baseline_battery_charging_full_24));
        HomeGridViewAdapter adapter = new HomeGridViewAdapter(this, ModelArrayList);
        homeGV.setAdapter(adapter);
        homeTextToSpeech = new TextToSpeech(MainActivity.this, this);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build();
        homeTextToSpeech.setAudioAttributes(audioAttributes);
        audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                speakOut("You are at home. Click on the different sides of screen to know details");  //speak after 1000ms
            }
        }, 1000);
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                homeTextToSpeech.stop();
            }
        }, 5700);

        adapter.setOnClickListener(new HomeGridViewAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String contentDesccription) {
                String contentDescription = contentDesccription;

                if (!previousCLick.equals(contentDescription)) {
                    homeTextToSpeech.stop();
                    String textToSpeak = "You Clicked  " + contentDescription + "  Click again to confirm ";
                    speakOut(textToSpeak);
                    previousCLick = contentDescription;

                } else {

                    if (contentDescription == "Battery") {
                        getBatteryInformation();
                    } else {

                        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                            // Permission has not been granted, so request it
                            speakOut("First Grant All Permission");
                            (MainActivity.this).requestPermissions(new String[]{Manifest.permission.READ_SMS}, PERMISSION_REQUEST_CODE);
                        } else {
                            Intent intent = new Intent().setClassName(MainActivity.this, "deepshikha.jangidyahoo.finalyearproject.Activity." + contentDescription);
                            startActivity(intent);
                            previousCLick = "";
                        }

                    }
                }
            }
        });






    }

    @Override
    public void onInit(int i) {
        int result = homeTextToSpeech.setLanguage(Locale.getDefault());

        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("MyAdapter", "Language not supported");
        } else {
            Log.e("MyAdapter", "Initialization failed");
        }
    }
    public void speakOut(String text) {
        homeTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void getBatteryInformation() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        // Battery level
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPercentage = (level / (float) scale) * 100;

        // Battery status
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL;

        // speak the battery information
        String message = "Battery Level: " + batteryPercentage + "%";
        message += "\nCharging: " + (isCharging ? "Yes" : "No");
        speakOut(message);
    }

}