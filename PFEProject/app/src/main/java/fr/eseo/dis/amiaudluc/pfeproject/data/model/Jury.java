package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by Samuel on 20/12/2017.
 */

@Entity(tableName = "juries")
public class Jury {

    @PrimaryKey
    @NonNull
    private int idJury;
    private String description;
    @NonNull
    private String date;
    @NonNull
    @Ignore
    private ArrayList<Project> projects;



    public Jury(@NonNull int idJury, String description,@NonNull String date){
        this.idJury = idJury;
        this.description = description;
        this.date = date;
    }

   public Jury(@NonNull int idJury, String date, ArrayList<Project> projects){
        this.idJury = idJury;
        this.date = date;
        this.projects = projects;
    }

    @NonNull
    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(@NonNull int idJury) {
        this.idJury = idJury;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Project> getProject() {
        return projects;
    }

    public void setProject(ArrayList<Project> title) {
        this.projects = title;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

}
