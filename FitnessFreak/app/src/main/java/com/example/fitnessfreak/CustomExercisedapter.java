package com.example.fitnessfreak;

import pl.droidsonroids.gif.GifImageView;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class CustomExercisedapter extends ArrayAdapter<Exercise> {

    private final Context context;
    private final ArrayList<Exercise> values;


    public CustomExercisedapter(@NonNull Context context, @NonNull ArrayList<Exercise> objects) {
        super(context, -1, objects);
        this.context = context;
        this.values = objects;
        System.out.println(objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_lists, parent, false);

        TextView eName = rowView.findViewById(R.id.Name);
        TextView eTime = rowView.findViewById(R.id.time);
        TextView eDet = rowView.findViewById(R.id.details);
        GifImageView gifImageView = rowView.findViewById(R.id.gif);



        String g = "android.resource://" + "com.example.fitnessfreak" + "/drawable/" + values.get(position).img;
        Uri uri = Uri.parse(g);
        gifImageView.setImageURI(uri);

        eName.setText(values.get(position).name);
        eTime.setText(values.get(position).time);
        eDet.setText(values.get(position).details);

        return rowView;
    }
}