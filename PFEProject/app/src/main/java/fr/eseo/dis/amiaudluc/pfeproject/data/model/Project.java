package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
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
    private Poster poster;
    private User supervisor;
    private int confidentiality;
    private ArrayList<User> team;

    public Project(@NonNull int idProject, @NonNull String title, @NonNull String description, int confidentiality){
        this.idProject = idProject;
        this.title = title;
        this.description = description;
        this.confidentiality = confidentiality;
    }

    public Project(@NonNull int idProject, @NonNull String title, @NonNull String description, Poster poster, User supervisor, int confidentiality, ArrayList<User> team){
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

    public Poster getPoster() {
        return poster;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
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

    public boolean isPoster() {return this.poster == null;};
}
