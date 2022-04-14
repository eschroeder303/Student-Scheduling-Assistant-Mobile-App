package android.C868.Capstone.All.GUI;

import static android.C868.Capstone.All.GUI.MyReceiver.REQUEST_CODE;

import android.C868.Capstone.All.View.addAssessmentView;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.C868.Capstone.All.Database.repository;
import android.C868.Capstone.All.Model.Assessment;
import android.C868.Capstone.All.Model.Course;
import android.C868.Capstone.All.Utility.DateValidator;
import android.C868.Capstone.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class courseDetail extends AppCompatActivity {

    private int courseID;
    private int currentTermID;
    private String courseTitle;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private String courseInstructor;
    private String phone;
    private String email;
    private String notes;
    private EditText editCourseTitle;
    private EditText editStart;
    private EditText editEnd;
    private EditText editStatus;
    private EditText editInstructor;
    private EditText editPhone;
    private EditText editEmail;
    private EditText editNotes;
    private List<Course> mCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentTermID = getIntent().getIntExtra("term", -1);

        courseID = getIntent().getIntExtra("id", -1);
        courseTitle = getIntent().getStringExtra("name");
        courseStart = getIntent().getStringExtra("start");
        courseEnd = getIntent().getStringExtra("end");
        courseStatus = getIntent().getStringExtra("status");
        courseInstructor = getIntent().getStringExtra("instructor");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
        notes = getIntent().getStringExtra("note");

        editCourseTitle = findViewById(R.id.editText_courseTitle);
        editStart = findViewById(R.id.editText_courseStart);
        editEnd = findViewById(R.id.editText_courseEnd);
        editStatus = findViewById(R.id.editText_courseStatus);
        editInstructor = findViewById(R.id.editText_instructorName);
        editPhone = findViewById(R.id.editText_phone);
        editEmail = findViewById(R.id.editText_email);
        editNotes = findViewById(R.id.editText_note);

        editCourseTitle.setText(courseTitle);
        editStart.setText(courseStart);
        editEnd.setText(courseEnd);
        editStatus.setText(courseStatus);
        editInstructor.setText(courseInstructor);
        editPhone.setText(phone);
        editEmail.setText(email);
        editNotes.setText(notes);

        repository repository_assessmentList = new repository(getApplication());
        List<Assessment> allAssessments = repository_assessmentList.getAssessmentsByCourseID(courseID);

        RecyclerView recyclerView_assessmentList = findViewById(R.id.recyclerView_assessmentList);
        final android.C868.Capstone.All.GUI.assessmentAdapter assessmentAdapter = new android.C868.Capstone.All.GUI.assessmentAdapter(this);
        recyclerView_assessmentList.setAdapter(assessmentAdapter);
        recyclerView_assessmentList.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessment(allAssessments);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.menu_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, notes);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            case R.id.menu_notify:
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

                Intent intent = new Intent(courseDetail.this, MyReceiver.class);
                intent.putExtra("key", "Course has started.");
                PendingIntent sender = PendingIntent.getBroadcast(courseDetail.this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;

            case R.id.menu_notifyEnd:
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
                Intent secondIntent = new Intent(courseDetail.this, MyReceiver.class);
                secondIntent.putExtra("key", "Course has ended.");
                PendingIntent sender2 = PendingIntent.getBroadcast(courseDetail.this, REQUEST_CODE, secondIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager1 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                return true;

            case R.id.menu_optionRefresh:
                repository repository = new repository(getApplication());
                List<Assessment> allAssessments = repository.getAssessmentsByCourseID(courseID);
                RecyclerView recyclerView = findViewById(R.id.recyclerView_assessmentList);
                final android.C868.Capstone.All.GUI.assessmentAdapter assessmentAdapter = new android.C868.Capstone.All.GUI.assessmentAdapter(this);
                recyclerView.setAdapter(assessmentAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                assessmentAdapter.setAssessment(allAssessments);

                if (allAssessments.isEmpty()) {
                    Toast.makeText(this, "No Assessments for this Course.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "Refreshed.", Toast.LENGTH_LONG).show();
                }

        }

        return super.onOptionsItemSelected(item);

    }

    //Add new Assessment.
    public void addAssessment(View view) {

        Intent intent = new Intent(courseDetail.this, addAssessmentView.class);
        intent.putExtra("id", courseID);
        startActivity(intent);

    }

    //Update Course details in database.
    public void updateCourse(View view) {

        Course currentCourse;
        courseTitle = editCourseTitle.getText().toString();
        courseStart = editStart.getText().toString();
        courseEnd = editEnd.getText().toString();
        courseStatus = editStatus.getText().toString();
        courseInstructor = editInstructor.getText().toString();
        phone = editPhone.getText().toString();
        email = editEmail.getText().toString();
        notes = editNotes.getText().toString();

        DateValidator validator = new DateValidator();

        if (validator.isDateValid(courseStart) && validator.isDateValid(courseEnd)) {

            if (validator.isDateSequenceValid(courseStart, courseEnd)) {

                Toast.makeText(courseDetail.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();

            } else {

                repository repository = new repository(getApplication());
                mCourses = repository.getCoursesByTermId(currentTermID);
                for (int i = 0; i < mCourses.size(); i++) {

                    currentCourse = mCourses.get(i);

                    if (currentCourse.getCourseID() == courseID) {

                        currentCourse.setCourseName(courseTitle);
                        currentCourse.setCourseStart(courseStart);
                        currentCourse.setCourseEnd(courseEnd);
                        currentCourse.setStatus(courseStatus);
                        currentCourse.setCourseInstructorName(courseInstructor);
                        currentCourse.setInstructorPhone(phone);
                        currentCourse.setInstructorEmail(email);
                        currentCourse.setCourseNote(notes);
                        repository.update(currentCourse);
                        Toast.makeText(courseDetail.this, "Course details updated.", Toast.LENGTH_LONG).show();

                    }
                }
            }

        } else if ( courseTitle.isEmpty() || courseStart.isEmpty() || courseEnd.isEmpty()
            || courseStatus.isEmpty() || courseInstructor.isEmpty() ||
            phone.isEmpty() || email.isEmpty() ){

            Toast.makeText(courseDetail.this, "Check required fields.", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(courseDetail.this, "Check date format.", Toast.LENGTH_LONG).show();

        }

    }

    //Delete selected Course from the database.
    public void deleteCourse(View view) {

        Course current_course;
        repository repository = new repository(getApplication());
        mCourses = repository.getCoursesByTermId(currentTermID);         
        for(int i = 0; i < mCourses.size(); i++) {

            current_course = mCourses.get(i);

            if (current_course.getCourseID() == courseID) {

                android.C868.Capstone.All.Database.repository repository_assessments = new repository(getApplication());
                List<Assessment> assessments = repository_assessments.getAssessmentsByCourseID(courseID);

                if (assessments.isEmpty()) {

                    repository_assessments.delete(current_course);
                    Toast.makeText(courseDetail.this, "Course deleted.", Toast.LENGTH_LONG).show();

                }
                else {

                    Toast.makeText(courseDetail.this, "Course contains associated Assessments.", Toast.LENGTH_LONG).show();

                }
            }
        }
    }
}
