package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Samuel on 20/12/2017.
 */

@Entity(tableName = "posters")
public class Poster {

    @PrimaryKey
    @NonNull
    private int idPoster;
    @ForeignKey(entity=Project.class, parentColumns = "idProject",childColumns = "idProject")
    @NonNull
    private int idProject;
    @NonNull
    private String path; // ou private "Image" pngPoster;


    public Poster(@NonNull int idPoster, @NonNull String path) {
        this.idPoster = idPoster;
        this.path = path;
    }


    @NonNull
    public int getIdPoster() {
        return idPoster;
    }

    public void setIdPoster(@NonNull int idPoster) {
        this.idPoster = idPoster;
    }

    @NonNull
    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(@NonNull int idProject) {
        this.idProject = idProject;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public void setPath(@NonNull String path) {
        this.path = path;
    }

}
