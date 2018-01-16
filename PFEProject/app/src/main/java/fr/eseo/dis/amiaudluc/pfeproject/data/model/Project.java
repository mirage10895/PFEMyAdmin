package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    @Ignore
    @NonNull
    private int idPoster;
    @Ignore
    @NonNull
    private int idSupervisor;
    @Ignore
    @NonNull
    private int confidentiality;
    //Out of database
    @Ignore
    private ArrayList<User> team;
    @Ignore
    private User supervisor;
    @Ignore
    private boolean poster;

    private byte[] posterString;

    private int seed;

    @Ignore
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

    @Ignore
    public Project(@NonNull int idProject, @NonNull String title, @NonNull String description){
        this.idProject = idProject;
        this.title = title;
        this.description = description;
    }


    public Project(@NonNull int idProject, int seed,String title, String description, byte[] posterString){
        this.idProject = idProject;
        this.seed = seed;
        this.title = title;
        this.description = description;
        if(posterString != null) {
            this.posterString = posterString;
            this.poster = true;
        }else{
            this.posterString = new byte[0];
            this.poster = false;
        }
    }

    @Ignore
    public Project(){
        this.idProject = 0;
        this.title = "error";
        this.description = "error";
        this.idPoster = -1;
        this.idSupervisor = -1;
        this.confidentiality = -1;
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

    public byte[] getPosterString() {
        return posterString;
    }

    public void setPosterString(byte[] posterString) {
        this.posterString = posterString;
    }

    public boolean isPoster() {return this.poster;};

    public void setPoster(boolean poster) {
        this.poster = poster;
    }

    public Bitmap getBmpPoster(){;
        return BitmapFactory.decodeByteArray(posterString,0,posterString.length);
    }

    public int getSeed(){return this.seed;}

    public void setSeed(int seed) {
        this.seed = seed;
    }

}
