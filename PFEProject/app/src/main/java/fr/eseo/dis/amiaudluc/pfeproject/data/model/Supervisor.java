package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/**
 * Created by Samuel on 29/12/2017.
 */

@Entity(tableName="supervisors",
        primaryKeys = {"idProject","idUser"},
        foreignKeys = {@ForeignKey(entity=Project.class, parentColumns = "idProject",childColumns = "idProject"),
                @ForeignKey(entity=User.class, parentColumns = "idUser",childColumns = "idUser")})
public class Supervisor {

    @NonNull
    private int idProject;

    @NonNull
    private int idUser;

    public Supervisor(@NonNull int idProject, @NonNull int idMember) {
        this.idProject = idProject;
        this.idUser = idMember;
    }

    @NonNull
    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(@NonNull int idProject) {
        this.idProject = idProject;
    }

    @NonNull
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(@NonNull int idUser) {
        this.idUser = idUser;
    }
}