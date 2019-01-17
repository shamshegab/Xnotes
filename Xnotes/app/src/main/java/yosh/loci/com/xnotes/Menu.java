package yosh.loci.com.xnotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Comparator;

public class Menu extends AppCompatActivity {
   static NoteItemAdapter noteArrayAdapter;
    DatabaseManager databaseManager;
    int sorting=0;
    private static Menu instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = DatabaseManager.getInstance();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_menu);
         noteArrayAdapter =  new NoteItemAdapter(Menu.this, 0, databaseManager.getNotesList(this));

        final ListView listView = (ListView) findViewById(R.id.noteList);
        listView.setAdapter(noteArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note= (Note) listView.getAdapter().getItem(i);
                open((Note) note);
            }

        });


    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        refresh();
    }

    public static Menu getInstance() {
        if(instance == null) {
            instance = new Menu();
        }

        return instance;
    }

    @Override
    protected void onResume() {
        super.onResume();
       refresh();

    }
    public void refresh(){
        NoteItemAdapter noteArrayAdapter =  new NoteItemAdapter(Menu.this, 0, databaseManager.getNotesList(this));
        noteArrayAdapter.sort(new Comparator<Note>() {
            @Override
            public int compare(Note note, Note t1) {
                if(sorting==1){
                    return 1-(note.getLastModified().compareTo(t1.getLastModified()));
                }else{
                    return (note.getLastModified().compareTo(t1.getLastModified()));
                }

            }


        });
        ListView listView = (ListView) findViewById(R.id.noteList);
        listView.setAdapter(noteArrayAdapter);
    }





public void open(Note note){
    Log.e("Open Note",note.getNoteName());
    Intent intent = new Intent(this, NoteViewer.class);
    intent.putExtra("open",true);
    databaseManager.setCurrent(note);
    startActivity(intent);
}
    public void newNote(View view) {

        Intent intent = new Intent(this, NoteViewer.class);
        startActivity(intent);
    }

    public void sort(View view) {
        if(sorting==0){
            sorting=1;

        }else{
            sorting=0;
        }

        refresh();

    }
}
