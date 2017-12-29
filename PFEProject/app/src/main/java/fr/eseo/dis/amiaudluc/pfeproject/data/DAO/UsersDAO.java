package fr.eseo.dis.amiaudluc.pfeproject.data.DAO;

/**
 * Created by lucasamiaud on 29/12/2017.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.data.model.User;

@Dao
public interface UsersDAO {

    @Insert
    public void insertUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users where forename LIKE  :firstName AND surname LIKE :lastName")
    User findByName(String firstName, String lastName);

    @Query("SELECT COUNT(*) from users")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
