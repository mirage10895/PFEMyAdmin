package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
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
    private double mark; // Peut-etre en float ?

    @NonNull
    private double avgMark;

    @Ignore
    private User student;

    public StudentMark(@NonNull int idStudent, @NonNull int idJury, @NonNull int idMember, @NonNull int mark) {
        this.idStudent = idStudent;
        this.idJury = idJury;
        this.idMember = idMember;
        this.mark = mark;
    }

    @Ignore
    public StudentMark(User user,double note,double avgNote){
        this.student = user;
        this.mark = note;
        this.avgMark = avgNote;
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
    public double getMark() {
        return mark;
    }

    @NonNull
    public double getAvgMark() {
        return avgMark;
    }

    public void setMark(@NonNull double mark) {
        this.mark = mark;
    }

    public User getStudent(){return student;}

    public void setStudent(User user){this.student = user;}
}
