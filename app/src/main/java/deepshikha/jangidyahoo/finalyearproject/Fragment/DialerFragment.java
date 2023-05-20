package deepshikha.jangidyahoo.finalyearproject.Fragment;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.OnBackPressedCallback;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.Activity.MainActivity;
import deepshikha.jangidyahoo.finalyearproject.R;

public class DialerFragment extends Fragment implements TextToSpeech.OnInitListener {
    TextView phoneNumberEditText;
    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnFour;
    Button btnFive;
    Button btnSix;
    Button btnSeven;
    Button btnEight;
    Button btnNine;
    Button btnZero;
    Button btnStar;
    Button btnHash;
    TextToSpeech textToSpeech;
    AudioManager audioManager;
    private int clickCount = 0;
    private static final int SINGLE_CLICK_THRESHOLD = 1;
    private static final int DOUBLE_CLICK_THRESHOLD = 2;
    private static final int TRIPLE_CLICK_THRESHOLD = 3;
    CardView DialerOptionsCard;
    LinearLayout dialerLinearLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialer, container, false);
        dialerLinearLayout = view.findViewById(R.id.DialerLinearLayout);
        DialerOptionsCard = view.findViewById(R.id.DialerOptionCard);
        phoneNumberEditText = view.findViewById(R.id.dialPhoneNumber);
         //Inflate the layout for this fragment
        btnOne = view.findViewById(R.id.ButtonOne);
        btnTwo = view.findViewById(R.id.ButtonTwo);
        btnThree = view.findViewById(R.id.ButtonThree);
        btnFour = view.findViewById(R.id.ButtonFour);
        btnFive = view.findViewById(R.id.ButtonFive);
        btnSix = view.findViewById(R.id.ButtonSix);
        btnSeven = view.findViewById(R.id.ButtonSeven);
        btnEight = view.findViewById(R.id.ButtonEight);
        btnNine = view.findViewById(R.id.ButtonNine);
        btnZero = view.findViewById(R.id.ButtonZero);
        btnStar = view.findViewById(R.id.ButtonStar);
        btnHash = view.findViewById(R.id.ButtonHash);

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

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut("1");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "1");
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut("2");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "2");
            }
        });
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut("3");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "3");
            }
        });
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut("4");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "4");
            }
        });
        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut("5");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "5");
            }
        });
        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut("6");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "6");
            }
        });
        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut("7");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "7");
            }
        });
        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut("8");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "8");
            }
        });
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut("9");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "9");
            }
        });
        btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut("0");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "0");
            }
        });
        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut("star");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "*");
            }
        });
        btnHash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                speakOut("hash");
                phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + "#");
            }
        });
        phoneNumberEditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialerLinearLayout.setVisibility(View.GONE);
                DialerOptionsCard.setVisibility(View.VISIBLE);
                speakOut("CLick once to read number. Click Twice to make a call ");
                return true;
            }
        });

        DialerOptionsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickCount++;
                switch (clickCount){
                    case SINGLE_CLICK_THRESHOLD:
                        speakOut(phoneNumberEditText.getText().toString());
                        break;
                    case DOUBLE_CLICK_THRESHOLD:
                        makePhoneCall();
                        clickCount = 0;


                }
                // Reset the click count if more than 3 clicks
                if (clickCount > TRIPLE_CLICK_THRESHOLD) {
                    clickCount = 0;
                }

            }


        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                speakOut("Dialer number is erased");
                phoneNumberEditText.setText("");
                DialerOptionsCard.setVisibility(View.GONE);
                dialerLinearLayout.setVisibility(View.VISIBLE);
            }
        });

        return view;
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
    private void makePhoneCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
        startActivity(callIntent);
        phoneNumberEditText.setText("");
        DialerOptionsCard.setVisibility(View.GONE);
        dialerLinearLayout.setVisibility(View.VISIBLE);
    }


}
