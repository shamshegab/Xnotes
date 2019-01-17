package yosh.loci.com.xnotes;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Shams Sherif on 1/21/2018.
 */

public class DatabaseManager {
    DataBase db ;
    private static DatabaseManager instance = null;
    ArrayList<Note> notesList = new ArrayList<Note>();
    Note current;
    int lastID=0;

    public boolean isPassed() {
        return passed;
    }

    boolean passed;
    public Note getCurrent() {
        this.passed=false;
        return current;

    }

    public int newnote(){

lastID=0;
        while (chkNote(lastID)){
            lastID++;
        }
        return lastID;
    }


    public void setCurrent(Note current) {
        this.current = current;
        this.passed=true;
    }


    protected DatabaseManager() {
        // Exists only to defeat instantiation.
    }
    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    public ArrayList<Note> getNotesList(Context c) {

        db=new DataBase(c);
        refresh();
        return notesList;
    }
    public boolean chkNote(int n){
        for(int i=0;i<notesList.size();i++){
            if(notesList.get(i).getId()==n){
                return true;
            }
        }
        return false;
    }
    public int getID(Note n){
        for(int i=0;i<notesList.size();i++){
            if(notesList.get(i).getId()==n.getId()){
                return i;
            }
        }
        return -1;
    }

    public void add(Note note, Context c){
        db=new DataBase(c);
       if(!chkNote(note.getId())){
           notesList.add(note);
           db.addNote(note);
           db.close();
          refresh();
       }else{
           notesList.set(getID(note),note);
           db.updateContact(note);
           db.close();
           refresh();
       }

    }


    public void refresh(){
        if(notesList.size()==0) {
            if (db.getAllContacts() != null) {
                notesList.clear();
                notesList = db.getAllContacts();
                db.close();
            }
        }


    }

    public void delete(Note n){
        notesList.remove(n);
        db.deleteContact(n);
        refresh();
       // noteArrayAdapter =  new NoteItemAdapter(Menu.this, 0, databaseManager.getNotesList(this));
    }




}
