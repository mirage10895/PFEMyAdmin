package fr.eseo.dis.amiaudluc.pfeproject.Content;

import java.util.ArrayList;

import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.User;

/**
 * Created by lucasamiaud on 27/12/2017.
 */

public class Content {

    // The user object.
    public static User user;

    // The list of my projects
    public static ArrayList<Project> myProjects = new ArrayList<>();

    // The list of all projects
    public static ArrayList<Project> allProjects = new ArrayList<>();

    // The list of the projects selected
    public static ArrayList<Project> projects = new ArrayList<>();

    //The selected project
    public static Project project;

}
