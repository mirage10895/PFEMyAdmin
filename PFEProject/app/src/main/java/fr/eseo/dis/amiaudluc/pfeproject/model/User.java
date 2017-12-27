package fr.eseo.dis.amiaudluc.pfeproject.model;

/**
 * Created by Samuel on 20/12/2017.
 */

public class User {

    private int userId;
    private String login;
    private String forename;
    private String surname;
    private String token;


    public User(String forename, String login,String surname,String token){
        this.forename = forename;
        this.login = login;
        this.surname = surname;
        this.token = token;
    }

    public User(int userId, String forename, String login, String surname, String token){
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
        this.userId =0;
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

    public void setToken(String token) {
        this.token = token;
    }
}
