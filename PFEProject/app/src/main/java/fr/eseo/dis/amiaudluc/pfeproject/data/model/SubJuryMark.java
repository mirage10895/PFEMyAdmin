package fr.eseo.dis.amiaudluc.pfeproject.data.model;

/**
 * Created by Samuel on 10/01/2018.
 */

public class SubJuryMark {

    private int idNote;

    private int idProject;

    private int note; // Pas de "float", on peut mettre entre 0 et 10 par exemple

    public SubJuryMark(int idNote, int idProject, int note) {
        this.idNote = idNote;
        this.idProject = idProject;
        this.note = note;
    }

    public SubJuryMark(int idProject, int note) {
        this.idNote = idNote;
        this.idProject = idProject;
        this.note = note;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
