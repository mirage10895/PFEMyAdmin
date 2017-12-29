package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

/**
 * Created by Samuel on 29/12/2017.
 */

@Entity(tableName="marks_students",
        primaryKeys = {"idStudent","idJury", "idMember"},
        foreignKeys = {@ForeignKey(entity=User.class, parentColumns = "idUser",childColumns = "idStudent"),
                @ForeignKey(entity=Jury.class, parentColumns = "idJury",childColumns = "idJury"),
                @ForeignKey(entity=JuryMember.class, parentColumns = "idMember",childColumns = "idMember")})
public class StudentMark {

    @NonNull
    private int idStudent;

    @NonNull
    private int idJury;

    @NonNull
    private int idMember;

    @NonNull
    private int mark; // Peut-etre en float ?

    public StudentMark(@NonNull int idStudent, @NonNull int idJury, @NonNull int idMember, @NonNull int mark) {
        this.idStudent = idStudent;
        this.idJury = idJury;
        this.idMember = idMember;
        this.mark = mark;
    }

    @NonNull
    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(@NonNull int idStudent) {
        this.idStudent = idStudent;
    }

    @NonNull
    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(@NonNull int idJury) {
        this.idJury = idJury;
    }

    @NonNull
    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(@NonNull int idMember) {
        this.idMember = idMember;
    }

    @NonNull
    public int getMark() {
        return mark;
    }

    public void setMark(@NonNull int mark) {
        this.mark = mark;
    }
}
