package deepshikha.jangidyahoo.finalyearproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import deepshikha.jangidyahoo.finalyearproject.R;
import deepshikha.jangidyahoo.finalyearproject.model.NotesRecyclerViewModel;


public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesViewHolder>{

    Context context;
    List<NotesRecyclerViewModel> notes;
    private static final long LONG_PRESS_TIME_THRESHOLD = 10;

    public NotesRecyclerViewAdapter(Context context, List<NotesRecyclerViewModel> notes) {
        this.context = context;
        this.notes = notes;
    }
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.notes_card, parent, false);
//        itemView.setOnTouchListener(this);
        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.title.setText(notes.get(position).getNotesTitle());
        holder.body.setText(notes.get(position).getNotesBody());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        switch (motionEvent.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                // Handle the touch down event if needed
//                break;
//            case MotionEvent.ACTION_UP:
//                // Handle the touch up event if needed
//                long pressDuration = motionEvent.getEventTime() - motionEvent.getDownTime();
//                if (pressDuration >= LONG_PRESS_TIME_THRESHOLD) {
//                    // Long press detected
//                    // Perform your desired action here
//                    int position = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
//                    NotesRecyclerViewModel note = notes.get(position);
//                    // Perform action with the note item
//                    Toast.makeText(context, "Long Pressed", Toast.LENGTH_LONG).show();
//
//                }
//                break;
//        }
//        return true;
//    }
}
