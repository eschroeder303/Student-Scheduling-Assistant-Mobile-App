package android.C868.Capstone.All.Database;

import android.content.Context;
import android.C868.Capstone.All.DAO.assessmentDAO;
import android.C868.Capstone.All.DAO.courseDAO;
import android.C868.Capstone.All.DAO.termDAO;
import android.C868.Capstone.All.DAO.userDAO;
import android.C868.Capstone.All.Model.Assessment;
import android.C868.Capstone.All.Model.Course;
import android.C868.Capstone.All.Model.Term;
import android.C868.Capstone.All.Model.User;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Term.class, Course.class, Assessment.class, User.class}, version = 18, exportSchema = false)
public abstract class dbBuilder extends RoomDatabase {

    public abstract termDAO termDAO();
    public abstract courseDAO courseDAO();
    public abstract assessmentDAO assessmentDAO();
    public abstract userDAO userDAO();

    private static volatile dbBuilder INSTANCE;

    static dbBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (dbBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), dbBuilder.class, "MyDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;

    }
}
