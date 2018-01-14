package fr.eseo.dis.amiaudluc.pfeproject.decoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import fr.eseo.dis.amiaudluc.pfeproject.data.model.Jury;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.StudentMark;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.User;

/**
 * Created by lucasamiaud on 27/12/2017.
 */

public class WebServerExtractor {

    private static final String TAG = WebServerExtractor.class.getSimpleName();
    private static final String JSON_PROJECTS = "projects";
    private static final String JSON_JURYS = "juries";
    private static final String JSON_NOTES = "notes";

    /**
     * Extract the user from the JSON as String.
     *
     * @param data : The JSON String.
     * @return User the instance of the user.
     */
    public static int extractResult(String data) {
        int result = 0;

        try {
            JSONObject object = new JSONObject(data);

            if("OK".equals(object.getString("result"))){
                result = 1;
            }

        } catch (JSONException e) {
            Log.e(TAG,"Json parsing error: " + e.getMessage());
            result = 0;
        }
        return result;
    }

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
            Log.e("OccurenceExProj",data);
            JSONObject object = new JSONObject(data);

            if(object.getString("result").equals("KO")){
                return new ArrayList<>();
            } else {
                JSONArray projects = object.getJSONArray(JSON_PROJECTS);

                for (int i = 0; i < projects.length(); i++) {
                    JSONObject c = projects.getJSONObject(i);

                    idProject = c.getInt("projectId");
                    title = c.getString("title");
                    description = c.getString("descrip");
                    poster = c.getBoolean("poster");
                    confidentiality = c.getInt("confid");

                    JSONObject jsonSupervisor = c.getJSONObject("supervisor");
                    if (jsonSupervisor.has("forename") && jsonSupervisor.has("surname")) {
                        String forename = jsonSupervisor.getString("forename");
                        String surname = jsonSupervisor.getString("surname");
                        supervisor = new User(forename, surname);
                    }

                    JSONArray jsonMates = c.getJSONArray("students");
                    for (int j = 0; j < jsonMates.length(); j++) {
                        User mate;
                        JSONObject jsonMate = jsonMates.getJSONObject(j);

                        String forename = jsonMate.getString("forename");
                        String surname = jsonMate.getString("surname");
                        mate = new User(forename, surname);
                        mate.setUserId(jsonMate.getInt("userId"));
                        team.add(mate);
                    }


                    // tmp hash map for single contact
                    Project project = new Project(idProject, title, description, poster, supervisor, confidentiality, team);

                    // adding contact to contact list
                    projectList.add(project);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG,"Json parsing error: " + e.getMessage());
            projectList = null;
        }
        return projectList;
    }

    public static ArrayList<Jury> extractJurys(String data) {
        ArrayList<Jury> juryList = new ArrayList<>();
        int idJury;
        String date;
        Project project;
        Jury jury;

        try {
            Log.e("OccurenceExJur",data);
            JSONObject object = new JSONObject(data);

            if(object.getString("result").equals("KO")){
                return new ArrayList<>();
            } else {
                JSONArray jurys = object.getJSONArray(JSON_JURYS);

                for (int i = 0; i < jurys.length(); i++) {
                    JSONObject c = jurys.getJSONObject(i);

                    ArrayList<Project> listProjects = new ArrayList<>();
                    ArrayList<User> juryMembers = new ArrayList<>();

                    idJury = c.getInt("idJury");
                    date = c.getString("date");

                    JSONObject jsonInfo = c.getJSONObject("info");
                    if(jsonInfo.has("members")){
                        JSONArray members = jsonInfo.getJSONArray("members");
                        for (int j = 0; j < members.length(); j++) {
                            JSONObject m = members.getJSONObject(j);
                            String forename;
                            String surname;
                            forename = m.getString("forename");
                            surname = m.getString("surname");
                            User user = new User(forename,surname);
                            juryMembers.add(user);
                        }
                    }
                    if (jsonInfo.has("projects")) {
                        JSONArray projects = jsonInfo.getJSONArray("projects");
                        for (int j = 0; j < projects.length(); j++) {
                            JSONObject p = projects.getJSONObject(j);
                            int id;
                            String title;
                            String description = "";
                            Boolean poster;
                            int confid;
                            User supervisor = null;
                            ArrayList<User> team = new ArrayList<>();

                            id = p.getInt("projectId");
                            title = p.getString("title");
                            confid = p.getInt("confid");
                            poster =p.getBoolean("poster");

                            JSONObject jsonSupervisor = p.getJSONObject("supervisor");
                            if (jsonSupervisor.has("forename")) {
                                String forename = jsonSupervisor.getString("forename");
                                String surname = jsonSupervisor.getString("surname");
                                supervisor = new User(forename, surname);
                            }

                            project = new Project(id,title,description,poster,supervisor,confid,team);
                            listProjects.add(project);
                        }
                    }
                    jury = new Jury(idJury,date,juryMembers,listProjects);
                    // adding contact to contact list
                    juryList.add(jury);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG,"Json parsing error: " + e.getMessage());
            juryList = null;
        }
        return juryList;
    }

    public static Bitmap extractPoster(InputStream data){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        return BitmapFactory.decodeStream(data);
    }

    public static ArrayList<StudentMark> extractMarks(String data){
        ArrayList<StudentMark> listeNotes= new ArrayList<>();
        int idUser;
        String forename;
        String surname;
        String verify;
        int note;
        int avgNote;

        try {
            Log.e("OccurenceExNotes",data);
            JSONObject object = new JSONObject(data);

            if(object.getString("result").equals("KO")){
                return new ArrayList<>();
            } else {
                JSONArray notes = object.getJSONArray(JSON_NOTES);

                for (int i = 0; i < notes.length(); i++) {
                    JSONObject c = notes.getJSONObject(i);

                    StudentMark studentMark;
                    User student;

                    idUser = c.getInt("userId");
                    forename = c.getString("forename");
                    surname = c.getString("surname");
                    note = c.getInt("mynote");
                    verify = c.getString("avgNote");

                    student = new User(idUser,forename,"",surname);

                    if("null".equals(verify)){
                        avgNote = -1;
                    }else{
                        avgNote = Integer.parseInt(verify);
                    }

                    studentMark = new StudentMark(student,note,avgNote);
                    listeNotes.add(studentMark);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG,"Json parsing error: " + e.getMessage());
            listeNotes = null;
        }

        return listeNotes;
    }

    public static Project extractPorte(String data) {
        byte[] poster;
        int projectId;
        String title;
        int seed;
        String description;
        Project porte = new Project();

        try {
            Log.e("OccurenceExPorte", data);
            JSONObject object = new JSONObject(data);

            if (object.getString("result").equals("KO")) {
                return new Project();
            } else {
                seed = object.getInt("seed");
                JSONArray projects = object.getJSONArray(JSON_PROJECTS);

                for (int i = 0; i < projects.length(); i++) {
                    JSONObject c = projects.getJSONObject(i);

                    projectId = c.getInt("idProject");
                    title = c.getString("title");
                    description = c.getString("description");
                    poster = c.getString("poster").getBytes();

                    porte = new Project(projectId,seed,title,description,poster);
                }
            }
        }catch (JSONException e) {
            Log.e(TAG,"Json parsing error: " + e.getMessage());
        }
        return porte;
    }
}
