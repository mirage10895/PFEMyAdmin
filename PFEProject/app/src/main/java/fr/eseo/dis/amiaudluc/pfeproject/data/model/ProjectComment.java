package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Samuel on 16/01/2018.
 */

@Entity(tableName = "projectComments")
public class ProjectComment {

    @PrimaryKey
    @NonNull
    private int idProject;

    private String posterComments;

    public ProjectComment(@NonNull int idProject, String posterComments) {
        this.idProject = idProject;
        this.posterComments = posterComments;
    }

    public String getPosterComments() {
        return posterComments;
    }

    public void setPosterComments(String posterComments) {
        this.posterComments = posterComments;
    }

    @NonNull
    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(@NonNull int idProject) {
        this.idProject = idProject;
    }
}
