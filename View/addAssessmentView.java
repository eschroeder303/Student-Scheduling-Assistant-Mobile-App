package android.C868.Capstone.All.View;

import androidx.appcompat.app.AppCompatActivity;

import android.C868.Capstone.All.Database.repository;
import android.C868.Capstone.All.Model.Assessment;
import android.C868.Capstone.All.Model.ObjectiveAssessment;
import android.C868.Capstone.All.Model.PerformanceAssessment;
import android.C868.Capstone.All.Utility.DateValidator;
import android.C868.Capstone.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class addAssessmentView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int currentCourseID;
    private String selectedString;
    private boolean assessment_type;
    private EditText editName;
    private EditText editStart;
    private EditText editEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment_view);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentCourseID = getIntent().getIntExtra("id", -1);

        editName = findViewById(R.id.assessmentEditText_name);
        editStart = findViewById(R.id.assessmentEditText_start);
        editEnd = findViewById(R.id.assessmentEditText_end);

        Spinner spinner_assessmentType = findViewById(R.id.addassessmentSpinner_type);
        spinner_assessmentType.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessmentType_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_assessmentType.setAdapter(adapter);

    }

    //Return to previous view.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            this.finish();
            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    //Add new assessment to database.
    public void saveAssessment(View view) {

        String assessmentTitle = editName.getText().toString();
        String assessmentStart = editStart.getText().toString();
        String assessmentEnd = editEnd.getText().toString();

        DateValidator validator = new DateValidator();

        //Check for empty fields.
        if (assessmentTitle.isEmpty() || assessmentStart.isEmpty() || assessmentEnd.isEmpty()) {

            Toast.makeText(addAssessmentView.this, "Check required fields.", Toast.LENGTH_LONG).show();

        }
        else {

            //Validate correct format for dates.
            if (validator.isDateValid(assessmentStart) && validator.isDateValid(assessmentEnd)) {

                if (validator.isDateSequenceValid(assessmentStart, assessmentEnd)) {

                    Toast.makeText(addAssessmentView.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();

                } else {

                    if (assessment_type) {
                        Assessment performanceAssessment = (Assessment) new PerformanceAssessment(0, assessmentTitle, assessmentStart, assessmentEnd, currentCourseID, selectedString, 0);
                        repository addToAssessment = new repository(getApplication());
                        addToAssessment.insert(performanceAssessment);

                    }

                    else {
                        Assessment objectiveAssessment = new ObjectiveAssessment(0, assessmentTitle, assessmentStart, assessmentEnd, currentCourseID, selectedString, 0);
                        repository addToAssessment = new repository(getApplication());
                        addToAssessment.insert(objectiveAssessment);
                    }

                    Toast.makeText(addAssessmentView.this, "New assessment added.", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(addAssessmentView.this, "Check date format.", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

        selectedString = String.valueOf(adapterView.getItemAtPosition(pos));
        assessment_type = selectedString.equals("Performance Assessment");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}