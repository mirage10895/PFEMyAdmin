package fr.eseo.dis.amiaudluc.pfeproject.data.model;


/**
 * Created by Samuel on 29/12/2017.
 */

public class Supervisor {

    private int idSupervisor;

    private int idProject;

    private int idUser;

    public Supervisor(int idSupervisor, int idProject, int idMember) {
        this.idSupervisor = idSupervisor;
        this.idProject = idProject;
        this.idUser = idMember;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}