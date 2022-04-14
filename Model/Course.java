package android.C868.Capstone.All.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String status;
    private String courseInstructorName;
    private String instructorPhone;
    private String instructorEmail;
    private String courseNote;
    private int termID;

    public Course(int courseID, String courseName, String courseStart, String courseEnd, String status, String courseInstructorName,
                  String instructorPhone, String instructorEmail, String courseNote, int termID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.status = status;
        this.courseInstructorName = courseInstructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.courseNote = courseNote;
        this.termID = termID;
    }

    @NonNull
    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseName='" + courseName + '\'' +
                ", courseStart='" + courseStart + '\'' +
                ", courseEnd='" + courseEnd + '\'' +
                ", status='" + status + '\'' +
                ", courseInstructorName='" + courseInstructorName + '\'' +
                ", instructorPhone='" + instructorPhone + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", courseNote='" + courseNote + '\'' +
                ", termID=" + termID +
                '}';
    }

    public int getCourseID() {
        return courseID;
    }
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getCourseStart() {
        return courseStart;
    }
    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }
    public String getCourseEnd() {
        return courseEnd;
    }
    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCourseInstructorName() {
        return courseInstructorName;
    }
    public void setCourseInstructorName(String courseInstructorName) {
        this.courseInstructorName = courseInstructorName;
    }
    public String getInstructorPhone() {
        return instructorPhone;
    }
    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }
    public String getInstructorEmail() {
        return instructorEmail;
    }
    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }
    public String getCourseNote() {
        return courseNote;
    }
    public void setCourseNote(String courseNote) {
        this.courseNote = courseNote;
    }
    public int getTermID() {
        return termID;
    }
    public void setTermID(int termID) {
        this.termID = termID;
    }
}
