package fr.eseo.dis.amiaudluc.pfeproject.model;

/**
 * Created by Samuel on 20/12/2017.
 */

class Person {

    private int userId;
    private String forename;
    private String surname;


    public Person(String forename, String surname){
        this.forename = forename;
        this.surname = surname;
    }

    public Person(int userId, String forename, String surname){
        this.userId = userId;
        this.forename = forename;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
