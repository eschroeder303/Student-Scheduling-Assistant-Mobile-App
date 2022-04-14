package android.C868.Capstone.All.GUI;

import static android.C868.Capstone.All.GUI.MyReceiver.REQUEST_CODE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.C868.Capstone.All.Database.repository;
import android.C868.Capstone.All.Model.Assessment;
import android.C868.Capstone.All.Model.ObjectiveAssessment;
import android.C868.Capstone.All.Model.PerformanceAssessment;
import android.C868.Capstone.All.Utility.DateValidator;
import android.C868.Capstone.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class assessmentDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int currentCourseID;
    private int assessmentID;
    private boolean assessment_type;
    private String assessmentTitle;
    private String assessmentStart;
    private String assessmentEnd;
    private EditText editName;
    private EditText editStart;
    private EditText editEnd;
    private List<Assessment> assessmentList;
    private android.C868.Capstone.All.Database.repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentCourseID = getIntent().getIntExtra("course", -1);

        assessmentID = getIntent().getIntExtra("assessment_id", -1);
        assessmentTitle = getIntent().getStringExtra("name");
        assessmentStart = getIntent().getStringExtra("start");
        assessmentEnd = getIntent().getStringExtra("end");
        String current_assessmentType = getIntent().getStringExtra("type");
        System.out.println(current_assessmentType);

        editName = findViewById(R.id.assessmentEditText_name);
        editStart = findViewById(R.id.assessmentEditText_start);
        editEnd = findViewById(R.id.assessmentEditText_end);

        Spinner spinner_assessmentType = findViewById(R.id.assessmentSpinner_type);
        spinner_assessmentType.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessmentType_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_assessmentType.setAdapter(adapter);

        if (current_assessmentType.equals("Performance Assessment")) {
            spinner_assessmentType.setSelection(adapter.getPosition("Performance Assessment"));
        }
        else {
            spinner_assessmentType.setSelection(adapter.getPosition("Objective Assessment"));
        }

        editName.setText(assessmentTitle);
        editStart.setText(assessmentStart);
        editEnd.setText(assessmentEnd);

        repository = new repository(getApplication());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.assessment_notifyStart:
                String startDate = editStart.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myStartDate = null;

                try {
                    if (!startDate.isEmpty())
                        myStartDate = sdf.parse(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myStartDate.getTime();

                Intent intent = new Intent(assessmentDetail.this, MyReceiver.class);
                intent.putExtra("key", "Assessment has started.");
                PendingIntent sender = PendingIntent.getBroadcast(assessmentDetail.this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;

            case R.id.assessment_notifyEnd:
                String endDate = editEnd.getText().toString();
                String myFormat2 = "MM/dd/yy";
                SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
                Date myEndDate = null;

                try {
                    myEndDate = sdf2.parse(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long trigger2 = myEndDate.getTime();
                Intent secondIntent = new Intent(assessmentDetail.this, MyReceiver.class);
                secondIntent.putExtra("key", "Assessment has ended.");
                PendingIntent sender2 = PendingIntent.getBroadcast(assessmentDetail.this, REQUEST_CODE, secondIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager1 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    //Update Assessment details in database.
    public void updateAssessment(View view) {

        assessmentTitle = editName.getText().toString();
        assessmentStart = editStart.getText().toString();
        assessmentEnd = editEnd.getText().toString();

        DateValidator validator = new DateValidator();

        if ( assessmentTitle.isEmpty() || assessmentStart.isEmpty() || assessmentEnd.isEmpty() ){

            Toast.makeText(assessmentDetail.this, "Check required fields.", Toast.LENGTH_LONG).show();

        }

        else if (validator.isDateValid(assessmentStart) && validator.isDateValid(assessmentEnd)) {

            if (validator.isDateSequenceValid(assessmentStart, assessmentEnd)) {

                Toast.makeText(assessmentDetail.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();

            } else {

                assessmentList = repository.getAssessmentsByCourseID(currentCourseID);
                for (int i = 0; i < assessmentList.size(); i++) {

                    Assessment assessment = assessmentList.get(i);

                    if (assessment.getAssessment_id() == assessmentID) {

                        repository.delete(assessment);
                        break;

                    }

                }

                    if (assessment_type) {

                        String changeType = "Performance Assessment";
                        Assessment performanceAssessment = (Assessment) new PerformanceAssessment(0, assessmentTitle, assessmentStart, assessmentEnd, currentCourseID, changeType, 0);
                        android.C868.Capstone.All.Database.repository addToAssessment = new repository(getApplication());
                        addToAssessment.insert(performanceAssessment);
                        Toast.makeText(assessmentDetail.this, "Assessment updated.", Toast.LENGTH_LONG).show();

                    } else {

                        String changeType = "Objective Assessment";
                        Assessment objectiveAssessment = (Assessment) new ObjectiveAssessment(0, assessmentTitle, assessmentStart, assessmentEnd, currentCourseID, changeType, 0);
                        android.C868.Capstone.All.Database.repository addToAssessment = new repository(getApplication());
                        addToAssessment.insert(objectiveAssessment);
                        Toast.makeText(assessmentDetail.this, "Assessment updated.", Toast.LENGTH_LONG).show();

                    }
                }

            } else {

                    Toast.makeText(assessmentDetail.this, "Check date format.", Toast.LENGTH_LONG).show();

                }
    }

    //Delete selected Assessment from the database.
    public void deleteAssessment(View view) {

        assessmentList = repository.getAssessmentsByCourseID(currentCourseID);
        for (int i = 0; i < assessmentList.size(); i++) {

            Assessment assessment = assessmentList.get(i);

            if (assessment.getAssessment_id() == assessmentID) {

                repository.delete(assessment);
                Toast.makeText(assessmentDetail.this, "Assessment deleted.", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

        String selectedString = String.valueOf(adapterView.getItemAtPosition(pos));
        assessment_type = selectedString.equals("Performance Assessment");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}