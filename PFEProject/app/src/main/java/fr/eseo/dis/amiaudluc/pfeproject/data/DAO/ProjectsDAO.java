package fr.eseo.dis.amiaudluc.pfeproject.data.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;


/**
 * Created by lucasamiaud on 29/12/2017.
 */

@Dao
public interface ProjectsDAO {

    @Insert
    public void insertUser(Project project);

    @Query("SELECT * FROM projects")
    List<Project> getAll();

    @Query("SELECT * FROM projects where title LIKE  :firstName")
    Project findByTitle(String firstName);

    @Query("SELECT COUNT(*) from projects")
    int countUsers();

    @Insert
    void insertAll(Project... projects);

    @Delete
    void delete(Project project);
}
