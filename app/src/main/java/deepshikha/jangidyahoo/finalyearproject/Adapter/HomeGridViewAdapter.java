package deepshikha.jangidyahoo.finalyearproject.Adapter;

import static android.content.ContentValues.TAG;


import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.MainActivity;
import deepshikha.jangidyahoo.finalyearproject.R;
import deepshikha.jangidyahoo.finalyearproject.model.HomeGridViewModel;

public class HomeGridViewAdapter extends ArrayAdapter<HomeGridViewModel> implements TextToSpeech.OnInitListener {
    TextToSpeech textToSpeech;
    AudioManager audioManager;
    private String previousCLick = "";


    public HomeGridViewAdapter(@NonNull Context context, ArrayList<HomeGridViewModel> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
        textToSpeech = new TextToSpeech(context, this);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build();
        textToSpeech.setAudioAttributes(audioAttributes);
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        HomeGridViewModel courseModel = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.CardTitle);
        ImageView courseIV = listitemView.findViewById(R.id.icon);

        CardView cardView = listitemView.findViewById(R.id.cardView);

        ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
        layoutParams.height = (parent.getHeight() -15)/2 ;
        cardView.setLayoutParams(layoutParams);
        cardView.setContentDescription(courseModel.getCourse_name());
        courseTV.setText(courseModel.getCourse_name());
        courseIV.setImageResource(courseModel.getImgid());

        // Set an OnClickListener on the CardView
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the action when the CardView is clicked
                String contentDescription = cardView.getContentDescription().toString();
                if (!previousCLick.equals(contentDescription)){
                    String textToSpeak = "You Clicked  " + contentDescription + "  Click again to confirm ";
                    speakOut(textToSpeak);
                    previousCLick = contentDescription;

                }else{
                    if(contentDescription == "Battery"){
                        getBatteryInformation();
                    }else {
                        Intent intent = new Intent().setClassName(getContext(), "deepshikha.jangidyahoo.finalyearproject." + contentDescription );
                        getContext().startActivity(intent);
                    }
                }




            }
        });

        return listitemView;
    }

    @Override
    public void onInit(int i) {
        int result = textToSpeech.setLanguage(Locale.getDefault());
        String introText = "Welcome to voice assistant app. Click on the different sides of screen to know details" ;
        speakOut(introText);
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("MyAdapter", "Language not supported");
        } else {
            Log.e("MyAdapter", "Initialization failed");
        }
    }
    private void speakOut(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    private void getBatteryInformation() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getContext().registerReceiver(null, ifilter);

        // Battery level
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPercentage = (level / (float) scale) * 100;

        // Battery status
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL;

        // Display the battery information
        String message = "Battery Level: " + batteryPercentage + "%";
        message += "\nCharging: " + (isCharging ? "Yes" : "No");
        speakOut(message);
    }
}
