package fr.eseo.dis.amiaudluc.pfeproject.data.model;

import java.util.ArrayList;

/**
 * Created by Samuel on 20/12/2017.
 */

public class User {

    private int userId;

    private String login;

    private String forename;

    private String surname;

    private String token;

    private ArrayList<Jury> jurys;

    private ArrayList<Project> projects;

    public User(int userId,String forename,String login,String surname,String token){
        this.userId = userId;
        this.forename = forename;
        this.login = login;
        this.surname = surname;
        this.token = token;
    }

    public User(int userId, String forename, String login, String surname){
        this.userId = userId;
        this.forename = forename;
        this.login = login;
        this.surname = surname;
        this.token = token;
    }

    public User(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
        this.login = "";
        this.token ="";
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
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
        this.projects = projects;
    }

    public ArrayList<Integer> getListeIdJurys(){
        ArrayList<Integer> liste = new ArrayList<>();
        for (Jury jurs:getJurys()) {
            liste.add(jurs.getIdJury());
        }
        return liste;
    }
}
