package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/**
 * Created by Samuel on 29/12/2017.
 */

@Entity(tableName="jury_projects",
        primaryKeys = {"idProject","idJury"},
        foreignKeys = {@ForeignKey(entity=Project.class, parentColumns = "idProject",childColumns = "idProject"),
                @ForeignKey(entity=Jury.class, parentColumns = "idJury",childColumns = "idJury")})
public class JuryProject {

    @NonNull
    private int idProject;

    @NonNull
    private int idJury;

    public JuryProject(@NonNull int idProject, @NonNull int idUser) {
        this.idProject = idProject;
        this.idJury = idUser;
    }

    @NonNull
    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(@NonNull int idProject) {
        this.idProject = idProject;
    }

    @NonNull
    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(@NonNull int idJury) {
        this.idJury = idJury;
    }
}
