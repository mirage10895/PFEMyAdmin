package fr.eseo.dis.amiaudluc.pfeproject.data.model;


/**
 * Created by Samuel on 29/12/2017.
 */

public class JuryMember {

    private int idMember;

    private int idJury;

    public JuryMember(int idProject, int idUser) {
        this.idMember = idProject;
        this.idJury = idUser;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(int idJury) {
        this.idJury = idJury;
    }
}
