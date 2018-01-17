package fr.eseo.dis.amiaudluc.pfeproject.data.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.data.model.SubJuryMark;

/**
 * Created by Samuel on 17/01/2018.
 */

@Dao
public interface SubJuryMarkDAO {

    @Insert
    public void insertNote(SubJuryMark note);

    @Query("UPDATE subJuryMarks SET note = :note WHERE idProject = :id")
    void updateNote(int note, int id);

    @Query("SELECT COUNT(*) FROM subJuryMarks WHERE idProject = :idProject")
    Integer numberOfNotesByID(int idProject);

    @Query("SELECT * FROM subJuryMarks WHERE idProject = :idProject")
    SubJuryMark getNoteByID(int idProject);

    @Query("SELECT * FROM subJuryMarks")
    List<SubJuryMark> getAll();

    @Delete
    void deleteNote(SubJuryMark note);

    @Query("DELETE FROM subJuryMarks")
    void deleteAllNotes();

}