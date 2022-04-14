package android.C868.Capstone.All.DAO;

import android.C868.Capstone.All.Model.Assessment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface assessmentDAO {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("DELETE from assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY assessment_id ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM assessment_table WHERE courseID = :courseId")
    List<Assessment> getAssessmentsByCourseID(final int courseId);

}
