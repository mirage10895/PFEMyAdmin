package fr.eseo.dis.amiaudluc.pfeproject.data.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.data.model.SubJuryMark;

/**
 * Created by Samuel on 10/01/2018.
 */

@Dao
public interface SubJuryMarksDAO {

    @Insert
    public void insertNote(SubJuryMark note);

    @Query("SELECT * FROM marks_subJury")
    List<SubJuryMark> getAll();

    @Query("SELECT * FROM marks_subJury WHERE idProject LIKE :idProjet")
    List<SubJuryMark> notesForProject(int idProjet);

    @Insert
    void insertAll(SubJuryMark... notes);

    @Delete
    void delete(SubJuryMark note);

}
