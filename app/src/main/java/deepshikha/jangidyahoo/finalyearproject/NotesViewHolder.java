package deepshikha.jangidyahoo.finalyearproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    public TextView title, body;
    public NotesViewHolder(@NonNull View notesView) {
        super(notesView);
        title = notesView.findViewById(R.id.notesTitle);
        body = notesView.findViewById(R.id.notesBody);
    }
}
