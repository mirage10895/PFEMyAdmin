package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/**
 * Created by Samuel on 29/12/2017.
 */

@Entity(tableName="teams",
        primaryKeys = {"idProject","idMember"},
        foreignKeys = {@ForeignKey(entity=Project.class, parentColumns = "idProject",childColumns = "idProject"),
                @ForeignKey(entity=User.class, parentColumns = "idUser",childColumns = "idMember")})
public class Team {

    @NonNull
    private int idProject;

    @NonNull
    private int idMember;

    public Team(@NonNull int idProject, @NonNull int idMember) {
        this.idProject = idProject;
        this.idMember = idMember;
    }

    @NonNull
    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(@NonNull int idProject) {
        this.idProject = idProject;
    }

    @NonNull
    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(@NonNull int idMember) {
        this.idMember = idMember;
    }
}
