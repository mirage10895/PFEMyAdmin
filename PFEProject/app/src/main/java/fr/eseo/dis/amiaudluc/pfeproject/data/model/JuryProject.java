package fr.eseo.dis.amiaudluc.pfeproject.data.model;


/**
 * Created by Samuel on 29/12/2017.
 */

public class JuryProject {

    private int idProject;

    private int idJury;

    public JuryProject(int idProject, int idUser) {
        this.idProject = idProject;
        this.idJury = idUser;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(int idJury) {
        this.idJury = idJury;
    }
}
