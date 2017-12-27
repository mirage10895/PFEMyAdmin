package fr.eseo.dis.amiaudluc.pfeproject.model;

import java.util.ArrayList;

/**
 * Created by Samuel on 20/12/2017.
 */

@Entity(tableName = "projects")
public class Project {

    @PrimaryKey
    @NonNull
    private int idProject;
    @NonNull
    private String title;
    private String description;
    private Poster poster;
    private Person supervisor;
    private int confidentiality;
    private ArrayList<Person> team;

    public Project(int idProject, String title, String description, int confidentiality){
        this.idProject = idProject;
        this.title = title;
        this.description = description;
        this.confidentiality = confidentiality;
    }

    public Project(int idProject, String title, String description, Poster poster, Person supervisor, int confidentiality, ArrayList<User> team){
        this.idProject = idProject;
        this.title = title;
        this.description = description;
        this.poster = poster;
        this.supervisor = supervisor;
        this.confidentiality = confidentiality;
        this.team = team;
    }


    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Poster getPoster() {
        return poster;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }

    public Person getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Person supervisor) {
        this.supervisor = supervisor;
    }

    public int getConfidentiality() {
        return confidentiality;
    }

    public void setConfidentiality(int confidentiality) {
        this.confidentiality = confidentiality;
    }

    public ArrayList<Person> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<Person> team) {
        this.team = team;
    }
}
