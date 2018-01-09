package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

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
    private Date date;
    @NonNull
    private int projectId;
    @NonNull
    private String projectTitle;
    @Ignore
    private Project project;



    public Jury(@NonNull int idJury, String description,@NonNull Date date){
        this.idJury = idJury;
        this.description = description;
        this.date = date;
    }

/*    public Jury(@NonNull int idJury, String description, Date date, ArrayList<Project> projects){
        this.idJury = idJury;
        this.description = description;
        this.date = date;
        this.projects = projects;
    }*/

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project title) {
        this.project = title;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

}
