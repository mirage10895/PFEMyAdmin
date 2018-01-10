package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by Samuel on 20/12/2017.
 */

@Entity(tableName = "projects")
public class Project {

    @PrimaryKey
    @NonNull
    private int idProject;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private int idPoster;
    @NonNull
    private int idSupervisor;
    @NonNull
    private int confidentiality;
    //Out of database
    @Ignore
    private ArrayList<User> team;
    @Ignore
    private User supervisor;
    @Ignore
    private boolean poster;

    public Project(@NonNull int idProject, @NonNull String title, @NonNull String description,int idPoster,int idSupervisor, int confidentiality){
        this.idProject = idProject;
        this.title = title;
        this.description = description;
        this.idPoster = idPoster;
        this.idSupervisor = idSupervisor;
        this.confidentiality = confidentiality;
    }

    @Ignore
    public Project(@NonNull int idProject, @NonNull String title, @NonNull String description, boolean poster, User supervisor, int confidentiality, ArrayList<User> team){
        this.idProject = idProject;
        this.title = title;
        this.description = description;
        this.poster = poster;
        this.supervisor = supervisor;
        this.confidentiality = confidentiality;
        this.team = team;
    }

    @NonNull
    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(@NonNull int idProject) {
        this.idProject = idProject;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public int getIdPoster() {
        return idPoster;
    }

    public void setPoster(int idPoster) {
        this.idPoster = idPoster;
    }

    public User getSupervisor() {
        return this.supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public int getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(int idSupervisor) {
        this.idSupervisor = idSupervisor;
    }

    public int getConfidentiality() {
        return confidentiality;
    }

    public void setConfidentiality(int confidentiality) {
        this.confidentiality = confidentiality;
    }

   public ArrayList<User> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<User> team) {
        this.team = team;
    }

    public boolean isPoster() {return this.poster;};
}
