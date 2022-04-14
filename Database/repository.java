package android.C868.Capstone.All.Database;

import android.C868.Capstone.All.DAO.userDAO;
import android.app.Application;
import android.C868.Capstone.All.DAO.assessmentDAO;
import android.C868.Capstone.All.DAO.courseDAO;
import android.C868.Capstone.All.DAO.termDAO;
import android.C868.Capstone.All.Model.Assessment;
import android.C868.Capstone.All.Model.Course;
import android.C868.Capstone.All.Model.Term;
import android.C868.Capstone.All.Model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class repository {

    private final termDAO mTermDAO;
    private final courseDAO mCourseDAO;
    private final assessmentDAO mAssessmentDAO;
    private final userDAO mUserDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private List<User> mAllUsers;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public repository(Application application) {
        dbBuilder db = dbBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
        mUserDAO = db.userDAO();
    }

    //Get All
    public List<User> getAllUsers() {
        databaseWriteExecutor.execute(() -> {
            mAllUsers = mUserDAO.getAllUsers();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllUsers;
    }

    public List<Term> getAllTerms() {
        databaseWriteExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<Course> getAllCourses() {
        databaseWriteExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<Assessment> getAllAssessments() {
        databaseWriteExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    //Get By
    public List<Course> getCoursesByTermId(final int termId) {
        databaseWriteExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getCoursesByTermID(termId);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<Assessment> getAssessmentsByCourseID(final int courseId) {
        databaseWriteExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAssessmentsByCourseID(courseId);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    //Insert Term, Course, Assessment, User
    public void insert(Term term) {
        databaseWriteExecutor.execute(() -> {
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Course course) {
        databaseWriteExecutor.execute(() -> {
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Assessment assessment) {
        databaseWriteExecutor.execute(() -> {
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(User user) {
        databaseWriteExecutor.execute(() -> {
            mUserDAO.insert(user);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Update Term, Course, Assessment, User
    public void update(Term term) {
        databaseWriteExecutor.execute(() -> {
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        databaseWriteExecutor.execute(() -> {
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        databaseWriteExecutor.execute(() -> {
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        databaseWriteExecutor.execute(() -> {
            mUserDAO.update(user);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Delete Term, Course, Assessment, User
    public void delete(Term term) {
        databaseWriteExecutor.execute(() -> {
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        databaseWriteExecutor.execute(() -> {
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        databaseWriteExecutor.execute(() -> {
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(User user) {
        databaseWriteExecutor.execute(() -> {
            mUserDAO.delete(user);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
