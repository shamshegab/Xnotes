package yosh.loci.com.xnotes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Shams Sherif on 12/3/2017.
 */

public class NoteItemAdapter extends ArrayAdapter<Note> {

    public NoteItemAdapter(Context context, int resource, ArrayList<Note> noteObject){
        super(context, 0, noteObject);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        // Get the data item for this position
      final  Note note = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_row, parent, false);
        }
        TextView nameView = (TextView) convertView.findViewById(R.id.Title);
        TextView textSampleView = (TextView) convertView.findViewById(R.id.desc);
        TextView date =(TextView) convertView.findViewById(R.id.date);
        ImageView delete=(ImageView) convertView.findViewById(R.id.delete);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                remove(note);
                DatabaseManager.getInstance().delete(note);
//                Menu.getInstance().refresh();

            }
        });
        nameView.setText(note.getNoteName());
        if(note.getText().length()<120){
            textSampleView.setText(note.getText());
        }else {
            textSampleView.setText(note.getText().substring(0, 120)+"..................");
        }

        date.setText(note.getLastModified());



        return convertView;
    }
}
