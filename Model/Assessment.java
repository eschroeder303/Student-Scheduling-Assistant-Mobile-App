package android.C868.Capstone.All.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment_table")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessment_id")
    private final int assessment_id;
    private String assessmentName;
    private String assessmentStart;
    private String assessmentEnd;
    private int courseID;
    private String type;

    public Assessment(int assessment_id, String assessmentName, String assessmentStart, String assessmentEnd, int courseID, String type) {
        this.assessment_id = assessment_id;
        this.assessmentName = assessmentName;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
        this.courseID = courseID;
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return "Assessment{" +
                "assessment_id=" + assessment_id +
                ", assessmentName='" + assessmentName + '\'' +
                ", assessmentStart='" + assessmentStart + '\'' +
                ", assessmentEnd='" + assessmentEnd + '\'' +
                ", courseID=" + courseID +
                ", type='" + type + '\'' +
                '}';
    }

    public int getAssessment_id() {
        return assessment_id;
    }
    public String getAssessmentName() {
        return assessmentName;
    }
    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }
    public String getAssessmentStart() {
        return assessmentStart;
    }
    public void setAssessmentStart(String assessmentStart) {
        this.assessmentStart = assessmentStart;
    }
    public String getAssessmentEnd() {
        return assessmentEnd;
    }
    public void setAssessmentEnd(String assessmentDate) {
        this.assessmentEnd = assessmentDate;
    }
    public int getCourseID() {
        return courseID;
    }
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
