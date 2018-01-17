package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

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
    @Ignore
    private String token;
    @Ignore
    private ArrayList<Jury> jurys;
    @Ignore
    private ArrayList<Project> projects;


    @Ignore
    public User(int userId,String forename, @NonNull String login,String surname,String token){
        this.userId = userId;
        this.forename = forename;
        this.login = login;
        this.surname = surname;
        this.token = token;
    }

    public User(@NonNull int userId, String forename, @NonNull String login, String surname){
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

    public String getFullName(){
        return this.forename + " " + this.surname;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Jury> getJurys(){return this.jurys;};

    public void setJurys(ArrayList<Jury> jurys){
        this.jurys = jurys;
    }

    public ArrayList<Project> getProjects(){return this.projects;};

    public void setProjects(ArrayList<Project> projects){
        Log.e("YO","MOUVCHOIR");
        this.projects = projects;
    }

    public ArrayList<Integer> getListeIdJurys(){
        ArrayList<Integer> liste = new ArrayList<>();
        for (Jury jurs:this.jurys) {
            liste.add(jurs.getIdJury());
        }
        return liste;
    }
}
