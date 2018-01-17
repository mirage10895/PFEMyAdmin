package fr.eseo.dis.amiaudluc.pfeproject.data.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.data.model.ProjectComment;

/**
 * Created by Samuel on 16/01/2018.
 */

@Dao
public interface ProjectCommentDAO {

    @Insert
    public void insertComment(ProjectComment comment);

    @Query("UPDATE projectComments SET posterComments = :comment WHERE idProject = :id")
    void updatePosterComments(String comment, int id);

    @Query("SELECT COUNT(*) FROM projectComments WHERE idProject = :idProject")
    Integer numberOfCommentByID(int idProject);

    @Query("SELECT * FROM projectComments WHERE idProject = :idProject")
    ProjectComment getCommentByID(int idProject);

    @Query("SELECT * FROM projectComments")
    List<ProjectComment> getAll();

    @Delete
    void deleteComment(ProjectComment comment);

    @Query("DELETE FROM projectComments")
    void deleteAllComments();

}
