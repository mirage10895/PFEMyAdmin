package fr.eseo.dis.amiaudluc.pfeproject.data.model;


/**
 * Created by Samuel on 20/12/2017.
 */


public class Poster {

    private int idPoster;

    private int idProject;

    private String path; // ou private "Image" pngPoster;

    public Poster(int idPoster, String path) {
        this.idPoster = idPoster;
        this.path = path;
    }

    public int getIdPoster() {
        return idPoster;
    }

    public void setIdPoster(int idPoster) {
        this.idPoster = idPoster;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
