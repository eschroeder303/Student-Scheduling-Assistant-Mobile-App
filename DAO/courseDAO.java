package android.C868.Capstone.All.DAO;

import android.C868.Capstone.All.Model.Course;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface courseDAO {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM course_table ORDER BY courseID ASC")
    List<Course> getAllCourses();

    @Query("SELECT * FROM course_table WHERE termID = :termId")
    List<Course> getCoursesByTermID(final int termId);

}
