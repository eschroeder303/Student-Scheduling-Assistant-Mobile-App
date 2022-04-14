package android.C868.Capstone.All.View;

import android.C868.Capstone.All.Database.repository;
import android.C868.Capstone.All.Model.Course;
import android.C868.Capstone.All.Utility.DateValidator;
import android.C868.Capstone.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class addCourseView extends AppCompatActivity {

    private EditText editCourseTitle;
    private EditText editStart;
    private EditText editEnd;
    private EditText editStatus;
    private EditText editInstructor;
    private EditText editPhone;
    private EditText editEmail;
    private EditText editNotes;
    private android.C868.Capstone.All.Database.repository repository;
    private int currentTermID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_view);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentTermID = getIntent().getIntExtra("id", -1);

        editCourseTitle = findViewById(R.id.editText_courseTitle);
        editStart = findViewById(R.id.editText_courseStart);
        editEnd = findViewById(R.id.editText_courseEnd);
        editStatus = findViewById(R.id.editText_courseStatus);
        editInstructor = findViewById(R.id.editText_instructorName);
        editPhone = findViewById(R.id.editText_phone);
        editEmail = findViewById(R.id.editText_email);
        editNotes = findViewById(R.id.editText_note);

        repository = new repository(getApplication());

    }

    // Return to previous view.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            this.finish();
            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    //Add new course to database.
    public void saveCourse(View view) {

        String courseTitle = editCourseTitle.getText().toString();
        String courseStart = editStart.getText().toString();
        String courseEnd = editEnd.getText().toString();
        String courseStatus = editStatus.getText().toString();
        String courseInstructor = editInstructor.getText().toString();
        String phone = editPhone.getText().toString();
        String email = editEmail.getText().toString();
        String notes = editNotes.getText().toString();

        DateValidator validator = new DateValidator();

        //Check for empty fields.
        if (    courseTitle.isEmpty() || courseStart.isEmpty() || courseEnd.isEmpty()
                || courseStatus.isEmpty() || courseInstructor.isEmpty() ||
                phone.isEmpty() || email.isEmpty()) {

            Toast.makeText(addCourseView.this, "Check required fields.", Toast.LENGTH_LONG).show();

        }
        else {

            //Validate correct format for dates.
            if (validator.isDateValid(courseStart) && validator.isDateValid(courseEnd)) {

                if (validator.isDateSequenceValid(courseStart, courseEnd)) {

                    Toast.makeText(addCourseView.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();

                } else {

                    Course newCourse = new Course(0, courseTitle, courseStart, courseEnd, courseStatus, courseInstructor, phone, email, notes, currentTermID);
                    repository.insert(newCourse);
                    Toast.makeText(addCourseView.this, "New course added.", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(addCourseView.this, "Check date format.", Toast.LENGTH_LONG).show();

            }
        }
    }
}