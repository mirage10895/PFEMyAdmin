package fr.eseo.dis.amiaudluc.pfeproject.data.DAO.DBInitializer;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import fr.eseo.dis.amiaudluc.pfeproject.data.DAO.ProjectCommentDAO;
import fr.eseo.dis.amiaudluc.pfeproject.data.DAO.ProjectsDAO;
import fr.eseo.dis.amiaudluc.pfeproject.data.DAO.SubJuryMarkDAO;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.Project;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.ProjectComment;
import fr.eseo.dis.amiaudluc.pfeproject.data.model.SubJuryMark;

/**
 * Created by lucasamiaud on 29/12/2017.
 */

@Database(entities = {Project.class, ProjectComment.class, SubJuryMark.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ProjectsDAO projectsDao();

    public abstract ProjectCommentDAO posterCommentDao();

    public abstract SubJuryMarkDAO subJuryMarkDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "pfeproject-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
