package android.C868.Capstone.All.Model;

public class ObjectiveAssessment extends Assessment{

    private final int objectiveAssessmentReference;

    public ObjectiveAssessment(int assessment_id, String assessmentName, String assessmentStart, String assessmentEnd, int courseID, String type, int objectiveAssessmentReference) {
        super(assessment_id, assessmentName, assessmentStart, assessmentEnd, courseID, type);
        this.objectiveAssessmentReference = objectiveAssessmentReference;
    }

    public int getObjectiveAssessmentReference() {
        return objectiveAssessmentReference;
    }
}