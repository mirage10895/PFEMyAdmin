package fr.eseo.dis.amiaudluc.pfeproject.Content;

import android.graphics.Bitmap;

import java.util.ArrayList;

import fr.eseo.dis.amiaudluc.pfeproject.data.model.Jury;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.StudentMark;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.User;

/**
 * Created by lucasamiaud on 27/12/2017.
 */

public class Content {

    // The user object.
    public static User currentUser;

    // The list of my projects
    public static ArrayList<Project> myProjects = new ArrayList<>();

    // The list of all projects
    public static ArrayList<Project> allProjects = new ArrayList<>();

    // The list of the projects selected
    public static ArrayList<Project> projects = new ArrayList<>();

    //The selected project
    public static Project project;

    // The list of the projects selected for a SubJury
    public static ArrayList<Project> subJuryProjects = new ArrayList<>();

    // The List of my jurys
    public static ArrayList<Jury> myJurys = new ArrayList<>();

    // The List of all jurys
    public static ArrayList<Jury> allJurys = new ArrayList<>();

    // The List of jurys selected
    public static ArrayList<Jury> jurys = new ArrayList<>();

    //The selected jury
    public static Jury jury;

    //The students marks
    public static ArrayList<StudentMark> marks = new ArrayList<>();

    //The poster
    public static Bitmap poster;

    //
    public static ArrayList<Project> porteProjects = new ArrayList<>();

    //Error
    public static String error = "";

}
