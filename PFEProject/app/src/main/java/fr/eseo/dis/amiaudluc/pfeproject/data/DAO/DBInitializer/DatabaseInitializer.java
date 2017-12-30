package fr.eseo.dis.amiaudluc.pfeproject.data.DAO.DBInitializer;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.amiaudluc.pfeproject.Content.Content;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.User;
import fr.eseo.dis.amiaudluc.pfeproject.decoder.WebServerExtractor;

/**
 * Created by lucasamiaud on 30/12/2017.
 */

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void userAsync(@NonNull final AppDatabase db, String projectAry) {
        UserDbAsync task = new UserDbAsync(db, projectAry);
        task.execute();
    }

    public static Project addProject(final AppDatabase db, Project project) {
        db.projectsDao().insertAll(project);
        return project;
    }

    public static User addUser(final AppDatabase db, User user) {
        db.usersDao().insertUser(user);
        return user;
    }

    private static void userProjects(AppDatabase db, String projectAry) {
        ArrayList<Project> projects = WebServerExtractor.extractProjects(projectAry);
        for(Project project: projects){
            Content.currentUser.setForename(project.getSupervisor().getForename());
            Content.currentUser.setSurname(project.getSupervisor().getSurname());
            Project projectToAdd = new Project(project.getIdProject(),
                    project.getTitle(),project.getDescription(),
                    -1,0,project.getConfidentiality());
            addProject(db,projectToAdd);
        }
        User userToAdd = new User(0,
                Content.currentUser.getForename(),
                Content.currentUser.getLogin(),
                Content.currentUser.getSurname());
        addUser(db,userToAdd);

        List<Project> projectList = db.projectsDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count:" + projectList.size());
    }

    private static class UserDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        private String projectAry;

        UserDbAsync(AppDatabase db, String projectAry) {
            mDb = db;
            this.projectAry = projectAry;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            userProjects(mDb, projectAry);
            return null;
        }
    }
}
