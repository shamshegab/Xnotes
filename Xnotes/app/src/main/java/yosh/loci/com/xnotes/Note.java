package yosh.loci.com.xnotes;

import android.graphics.Color;
import android.support.annotation.NonNull;

/**
 * Created by Shams Sherif on 12/3/2017.
 */

public class Note implements Comparable {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private String content;

    public int getId() {
        return id;
    }

    private int id;
    private String lastModified;


    public Note (int id,String noteName, String date){
        this.id= id;
        title = noteName;
        lastModified = date;
    }



    public String getNoteName(){

        return title;
    }

    public String getText(){
        return content;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        o=(Note) o;
        return lastModified.compareTo(((Note) o).getLastModified());
    }
}
