package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Samuel on 20/12/2017.
 */

@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @NonNull
    private int userId;
    @NonNull
    private String login;
    private String forename;
    private String surname;
    private String token;


    public User(String forename, @NonNull String login,String surname,String token){
        this.forename = forename;
        this.login = login;
        this.surname = surname;
        this.token = token;
    }

    @Ignore
    public User(@NonNull int userId, String forename, @NonNull String login, String surname, String token){
        this.userId = userId;
        this.forename = forename;
        this.login = login;
        this.surname = surname;
        this.token = token;
    }

    @Ignore
    public User(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
        this.login = "";
        this.userId =0;
        this.token ="";
    }

    @NonNull
    public int getUserId() {
        return userId;
    }

    public void setUserId(@NonNull int userId) {
        this.userId = userId;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    @NonNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
