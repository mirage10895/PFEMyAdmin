package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/**
 * Created by Samuel on 29/12/2017.
 */

@Entity(tableName="jury_members",
        primaryKeys = {"idMember","idJury"},
        foreignKeys = {@ForeignKey(entity=User.class, parentColumns = "idMember",childColumns = "idUser"),
                @ForeignKey(entity=Jury.class, parentColumns = "idJury",childColumns = "idJury")})
public class JuryMember {

    @NonNull
    private int idMember;

    @NonNull
    private int idJury;

    public JuryMember(@NonNull int idProject, @NonNull int idUser) {
        this.idMember = idProject;
        this.idJury = idUser;
    }

    @NonNull
    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(@NonNull int idMember) {
        this.idMember = idMember;
    }

    @NonNull
    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(@NonNull int idJury) {
        this.idJury = idJury;
    }
}
