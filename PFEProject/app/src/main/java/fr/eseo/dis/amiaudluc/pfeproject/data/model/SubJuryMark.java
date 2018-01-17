package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Samuel on 17/01/2018.
 */

@Entity(tableName = "subJuryMarks")
public class SubJuryMark {

    @PrimaryKey
    @NonNull
    private int idProject;

    private int note;

    public SubJuryMark(@NonNull int idProject, int note) {
        this.idProject = idProject;
        this.note = note;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @NonNull
    public int getIdProject() {
            return idProject;
        }

    public void setIdProject(@NonNull int idProject) {
            this.idProject = idProject;
        }

}
