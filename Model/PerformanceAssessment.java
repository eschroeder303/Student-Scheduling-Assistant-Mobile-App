package android.C868.Capstone.All.Model;

public class PerformanceAssessment extends Assessment{

    private final int performanceAssessmentReference;

    public PerformanceAssessment(int assessment_id, String assessmentName, String assessmentStart, String assessmentEnd, int courseID, String type, int performanceAssessmentReference) {
        super(assessment_id, assessmentName, assessmentStart, assessmentEnd, courseID, type);
        this.performanceAssessmentReference = performanceAssessmentReference;
    }

    public int getPerformanceAssessmentReference() {
        return performanceAssessmentReference;
    }
}
