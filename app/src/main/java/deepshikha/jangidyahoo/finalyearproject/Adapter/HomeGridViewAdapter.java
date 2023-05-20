package deepshikha.jangidyahoo.finalyearproject.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

import deepshikha.jangidyahoo.finalyearproject.R;
import deepshikha.jangidyahoo.finalyearproject.model.HomeGridViewModel;

public class HomeGridViewAdapter extends ArrayAdapter<HomeGridViewModel>{

    private OnClickListener onClickListener;
    Context context;



    public HomeGridViewAdapter(@NonNull Context context, ArrayList<HomeGridViewModel> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
        this.context = context;


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
                    if (onClickListener != null) {
                        onClickListener.onClick(position, cardView.getContentDescription().toString());
                    }

            }
        });

                // Perform the action when the CardView is clicked
//






        return listitemView;
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public interface OnClickListener {
        void onClick(int position,  String contentDesccription);
    }



}
