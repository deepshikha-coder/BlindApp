package deepshikha.jangidyahoo.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import deepshikha.jangidyahoo.finalyearproject.Adapter.NotesRecyclerViewAdapter;
import deepshikha.jangidyahoo.finalyearproject.model.NotesRecyclerViewModel;

public class Notes extends AppCompatActivity implements TextToSpeech.OnInitListener {

    ArrayList<NotesRecyclerViewModel> NotesArrayList = new ArrayList<NotesRecyclerViewModel>();
    private RecyclerView recyclerView;
    private ImageView imageView;
    TextToSpeech textToSpeech;
    AudioManager audioManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_layout);

        recyclerView = findViewById(R.id.notesRecyclerView);
        imageView = findViewById(R.id.addIcon);

        // Load the data from shared preferences
        loadDataFromSharedPreferences();

        // Check if new notes are added and call addItemToList method
        if (getIntent().hasExtra("Title") && getIntent().hasExtra("Body")) {
            String notesTitle = getIntent().getStringExtra("Title");
            String notesBody = getIntent().getStringExtra("Body");
            addItemToList(notesTitle, notesBody);
        }

        // Save the updated data to shared preferences
        saveDataToSharedPreferences();

        imageView.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(@NonNull MotionEvent e) {
                    Intent intent = new Intent(Notes.this, AddNotesActivity.class);
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

        // Long-press listener to add a new note
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(Notes.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public void onLongPress(MotionEvent e) {
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null) {
                        int position = recyclerView.getChildAdapterPosition(childView);
                        Intent intent = new Intent(Notes.this, AddNotesActivity.class);
                        intent.putExtra("key", "value");
                        startActivity(intent);
                    }
                }
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    speakOut("The selected note is deleted.");
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null) {
                        int position = recyclerView.getChildAdapterPosition(childView);
                        // Perform the action for double tap
                        // For example, you can delete the item at the position
                        removeItem(position);
                    }
                    return true;
                }

                @Override
                public boolean onSingleTapUp(@NonNull MotionEvent e) {
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    int position = recyclerView.getChildAdapterPosition(childView);
                    // Get the NotesRecyclerViewModel object for the tapped note
                    NotesRecyclerViewModel tappedNote = NotesArrayList.get(position);

                    // Retrieve the title and body of the tapped note
                    String title = tappedNote.getNotesTitle();
                    String body = tappedNote.getNotesBody();
                    speakOut("Title is " + title + "Body is " + body);
                    return super.onSingleTapUp(e);
                }
            });

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                gestureDetector.onTouchEvent(e);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        // Read the notes
        textToSpeech = new TextToSpeech(Notes.this, this);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build();
        textToSpeech.setAudioAttributes(audioAttributes);
        audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);

        if(!NotesArrayList.isEmpty()){
            imageView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NotesRecyclerViewAdapter(this, NotesArrayList));
    }


    @Override
    public void onBackPressed() {
        // Create an intent to navigate back to the Notes activity
        Intent intent = new Intent(Notes.this, MainActivity.class);
        startActivity(intent);

        // Finish the current activity
        super.onBackPressed();
    }

    private void addItemToList(String title, String body) {
        NotesArrayList.add(new NotesRecyclerViewModel(title, body));
    }

    private void removeItem(int position) {
        if (position >= 0 && position < NotesArrayList.size()) {
            NotesArrayList.remove(position);
            recyclerView.getAdapter().notifyItemRemoved(position);
            // Save the updated data to shared preferences
            saveDataToSharedPreferences();
        }
    }
    private void loadDataFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("notes_data", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("notes_list", null);
        Type type = new TypeToken<ArrayList<NotesRecyclerViewModel>>() {}.getType();
        NotesArrayList = gson.fromJson(json, type);

        if (NotesArrayList == null) {
            NotesArrayList = new ArrayList<>();
        }
    }

    private void saveDataToSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("notes_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(NotesArrayList);
        editor.putString("notes_list", json);
        editor.apply();
    }

    @Override
    public void onInit(int i) {
        int result = textToSpeech.setLanguage(Locale.getDefault());
        if(NotesArrayList.isEmpty()){
            speakOut("Notes are opened. There are no notes, long press on the screen to add new note.");
        }else{
            speakOut("Notes are opened. Tap on each note to read, double tap to delete and long press to add a new note.");
        }
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