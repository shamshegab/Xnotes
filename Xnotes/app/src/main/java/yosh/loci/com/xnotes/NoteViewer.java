package yosh.loci.com.xnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Shams Sherif on 1/18/2018.
 */

public class NoteViewer extends Activity {
    TextView title;
    String date;
    TextView datetext;
    EditText notetext;
    DatabaseManager databaseManager;
    LinearLayout toolbar;
    ImageView edit;
    EditText titleedit;
    ImageView save;

    Note note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_item);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        title= (TextView) findViewById(R.id.title);
        datetext=(TextView) findViewById(R.id.date);
        notetext=(EditText) findViewById(R.id.notetext);

        notetext.setEnabled(false);
        titleedit=(EditText)findViewById(R.id.titleedit);
        toolbar=(LinearLayout)findViewById(R.id.toolbar);
        edit=(ImageView) findViewById(R.id.edit);
        save=(ImageView) findViewById(R.id.save);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        date = df.format(Calendar.getInstance().getTime());
        datetext.setText(date);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        databaseManager = DatabaseManager.getInstance();
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.tools);
        titleedit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
              delete(view);
              return false;
            }
        });

        titleedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(view);
            }
        });
       if(databaseManager.isPassed()){
           note= databaseManager.getCurrent();
           title.setText(note.getNoteName());
           notetext.setText(note.getText());

       }else{
           note=new Note(databaseManager.newnote(),"","");
       }
    }

    public void closeNote(View view) {
        if(titleedit.getVisibility()==View.VISIBLE){
            areYouSure();
        }else{
            finish();
        }

    }

    public void areYouSure(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        save();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        finish();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to save these changes?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void save(){

        titleedit.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        title.setText(titleedit.getText());
        note.setTitle(title.getText().toString());
        note.setLastModified(date);
        note.setContent(notetext.getText().toString());
        databaseManager.add(note,this);
        notetext.setEnabled(false);
        save.setVisibility(View.GONE);
        edit.setVisibility(View.VISIBLE);



    }



    public void edit(View view) {

        edit.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        titleedit.setVisibility(View.VISIBLE);
        titleedit.setText(title.getText());
        notetext.setEnabled(true);
        //edit.setVisibility(View.GONE);
        save.setVisibility(View.VISIBLE);


    }

    public void delete(View view) {
        Log.e("title","clicked");
        if(titleedit.getText().toString().equals("Title")){
            titleedit.setText("");
            Log.e("title","true");
        }
    }
}
