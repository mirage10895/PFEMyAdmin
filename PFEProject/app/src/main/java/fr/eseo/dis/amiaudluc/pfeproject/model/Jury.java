package fr.eseo.dis.amiaudluc.pfeproject.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Samuel on 20/12/2017.
 */

public class Jury {

    private int idJury;
    private String description;
    private Date date;
    private ArrayList<Project> projects;


    public Jury(int idJury, String description, Date date){
        this.idJury = idJury;
        this.description = description;
        this.date = date;
    }

    public Jury(int idJury, String description, Date date, ArrayList<Project> projects){
        this.idJury = idJury;
        this.description = description;
        this.date = date;
        this.projects = projects;
    }


    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(int idJury) {
        this.idJury = idJury;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }
}
