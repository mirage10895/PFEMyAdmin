package fr.eseo.dis.amiaudluc.pfeproject.data.model;

/**
 * Created by Samuel on 10/01/2018.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName="marks_subJury",
        foreignKeys = {@ForeignKey(entity = Project.class, parentColumns = "idProject", childColumns = "idProject")})
public class SubJuryMark {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int idNote;

    @NonNull
    private int idProject;

    @NonNull
    private int note; // Pas de "float", on peut mettre entre 0 et 10 par exemple

    public SubJuryMark(@NonNull int idNote, @NonNull int idProject, @NonNull int note) {
        this.idNote = idNote;
        this.idProject = idProject;
        this.note = note;
    }

    public SubJuryMark(@NonNull int idProject, @NonNull int note) {
        this.idNote = idNote;
        this.idProject = idProject;
        this.note = note;
    }

    @NonNull
    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(@NonNull int idNote) {
        this.idNote = idNote;
    }

    @NonNull
    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(@NonNull int idProject) {
        this.idProject = idProject;
    }

    @NonNull
    public int getNote() {
        return note;
    }

    public void setNote(@NonNull int note) {
        this.note = note;
    }
}
