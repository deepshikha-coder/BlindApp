package deepshikha.jangidyahoo.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.ObjectAnimator;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.Adapter.HomeGridViewAdapter;
import deepshikha.jangidyahoo.finalyearproject.model.HomeGridViewModel;

public class MainActivity extends AppCompatActivity {

    GridView homeGV;
    final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        // Adding Grid Elements
        homeGV = findViewById(R.id.homeGridView);
        ArrayList<HomeGridViewModel> ModelArrayList = new ArrayList<HomeGridViewModel>();
        ModelArrayList.add(new HomeGridViewModel("Messages", R.drawable.ic_baseline_message_24));
        ModelArrayList.add(new HomeGridViewModel("Phone", R.drawable.ic_baseline_add_ic_call_24));
        ModelArrayList.add(new HomeGridViewModel("Notes", R.drawable.ic_baseline_event_note_24));
        ModelArrayList.add(new HomeGridViewModel("Battery", R.drawable.ic_baseline_battery_charging_full_24));
        HomeGridViewAdapter adapter = new HomeGridViewAdapter(this, ModelArrayList);
        homeGV.setAdapter(adapter);
    }

}