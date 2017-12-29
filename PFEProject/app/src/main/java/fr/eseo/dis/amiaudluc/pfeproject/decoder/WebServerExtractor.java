package fr.eseo.dis.amiaudluc.pfeproject.decoder;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.eseo.dis.amiaudluc.pfeproject.data.model.Jury;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.User;

/**
 * Created by lucasamiaud on 27/12/2017.
 */

public class WebServerExtractor {

    private static final String TAG = WebServerExtractor.class.getSimpleName();
    private static final String JSON_PROJECTS = "projects";

    /**
     * Extract the user from the JSON as String.
     *
     * @param data : The JSON String.
     * @return User the instance of the user.
     */
    public static User extractUser(String data) {
        User result;

        int id = 0;
        String name = "", login = "",forename ="", token;
        try {
            JSONObject object = new JSONObject(data);

            token = object.getString("token");

            result = new User(id, name, login, forename, token);
        } catch (JSONException e) {
            Log.e(TAG,"Json parsing error: " + e.getMessage());
            result = null;
        }
        return result;
    }

    /**
     * Extract the project from the JSON as String.
     *
     * @param data : The JSON String.
     * @return List<Project> a list of projects.
     */
    public static ArrayList<Project> extractProjects(String data) {
        ArrayList<Project> projectList = new ArrayList<>();

        int idProject;
        String title;
        String description;
        boolean poster;
        User supervisor = null;
        int confidentiality;
        ArrayList<User> team = new ArrayList<>();
        try {
            Log.e("Occurence",data);
            JSONObject object = new JSONObject(data);
            JSONArray projects = object.getJSONArray(JSON_PROJECTS);

            for (int i = 0; i < projects.length(); i++) {
                JSONObject c = projects.getJSONObject(i);

                idProject = c.getInt("projectId");
                title = c.getString("title");
                description = c.getString("descrip");
                poster = c.getBoolean("poster");
                confidentiality = c.getInt("confid");

                JSONObject jsonSupervisor = c.getJSONObject("supervisor");
                if(!jsonSupervisor.isNull("forename")) {
                    String forename = jsonSupervisor.getString("forename");
                    String surname = jsonSupervisor.getString("surname");
                    supervisor = new User(forename,surname);
                }

                JSONArray jsonMates = c.getJSONArray("students");
                for (int j = 0; j < jsonMates.length(); j++) {
                    User mate;
                    JSONObject jsonMate = jsonMates.getJSONObject(j);

                    String forename = jsonMate.getString("forename");
                    String surname = jsonMate.getString("surname");
                    mate = new User(forename,surname);
                    mate.setUserId(jsonMate.getInt("userId"));
                    team.add(mate);
                }


                // tmp hash map for single contact
                Project project = new Project(idProject,title,description,null,supervisor,confidentiality,team);

                // adding contact to contact list
                projectList.add(project);
            }
        } catch (JSONException e) {
            Log.e(TAG,"Json parsing error: " + e.getMessage());
            projectList = null;
        }
        return projectList;
    }

    public static Jury extractJury(String data) {
        Jury jury = null;
        return jury;
    }
}
