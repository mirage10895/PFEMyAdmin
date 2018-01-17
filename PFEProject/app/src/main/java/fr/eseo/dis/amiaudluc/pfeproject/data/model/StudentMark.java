package fr.eseo.dis.amiaudluc.pfeproject.data.model;

/**
 * Created by Samuel on 29/12/2017.
 */

public class StudentMark {

    private int idStudent;

    private int idJury;

    private int idMember;

    private double mark;

    private double avgMark;

    private User student;

    public StudentMark(int idStudent, int idJury, int idMember, int mark) {
        this.idStudent = idStudent;
        this.idJury = idJury;
        this.idMember = idMember;
        this.mark = mark;
    }

    public StudentMark(User user,double note,double avgNote){
        this.student = user;
        this.mark = note;
        this.avgMark = avgNote;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(int idJury) {
        this.idJury = idJury;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public double getMark() {
        return mark;
    }

    public double getAvgMark() {
        return avgMark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public User getStudent(){return student;}

    public void setStudent(User user){this.student = user;}
}
