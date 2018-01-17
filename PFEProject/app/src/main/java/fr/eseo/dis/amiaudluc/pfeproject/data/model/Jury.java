package fr.eseo.dis.amiaudluc.pfeproject.data.model;


import java.util.ArrayList;

/**
 * Created by Samuel on 20/12/2017.
 */

public class Jury {

    private int idJury;

    private String description;

    private String date;

    private ArrayList<Project> projects;

    private ArrayList<User> juryMembers;



    public Jury(int idJury, String description,String date){
        this.idJury = idJury;
        this.description = description;
        this.date = date;
    }


   public Jury(int idJury, String date, ArrayList<User> juryMembers,ArrayList<Project> projects){
        this.idJury = idJury;
        this.date = date;
        this.juryMembers = juryMembers;
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

    public ArrayList<Project> getProject() {
        return projects;
    }

    public void setProject(ArrayList<Project> title) {
        this.projects = title;
    }

    public ArrayList<User> getMembers() {
        return this.juryMembers;
    }

    public void setMembers(ArrayList<User> juryMembers){this.juryMembers = juryMembers;}

    public ArrayList<Integer> getListeProjectId(){
        ArrayList<Integer> liste = new ArrayList<>();
        for (Project projs:this.getProject()) {
            liste.add(projs.getIdProject());
        }
        return liste;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
