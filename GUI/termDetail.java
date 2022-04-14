package android.C868.Capstone.All.GUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.C868.Capstone.All.View.addCourseView;
import android.content.Intent;
import android.C868.Capstone.All.Database.repository;
import android.C868.Capstone.All.Model.Course;
import android.C868.Capstone.All.Model.Term;
import android.C868.Capstone.All.Utility.DateValidator;
import android.C868.Capstone.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class termDetail extends AppCompatActivity {

    private int current_termID;
    private EditText editTermTitle;
    private EditText editTermStart;
    private EditText editTermEnd;
    private List<Term> mTerms;
    private android.C868.Capstone.All.Database.repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        current_termID = getIntent().getIntExtra("id", -1);
        String termTitle = getIntent().getStringExtra("name");
        String termStart = getIntent().getStringExtra("start");
        String termEnd = getIntent().getStringExtra("end");
        String created_date = getIntent().getStringExtra("created");

        editTermTitle = findViewById(R.id.editText_termTitle);
        editTermStart = findViewById(R.id.editText_startDate);
        editTermEnd = findViewById(R.id.editText_endDate);

        editTermTitle.setText(termTitle);
        editTermStart.setText(termStart);
        editTermEnd.setText(termEnd);

        repository = new repository(getApplication());
        List<Course> allCourses = repository.getCoursesByTermId(current_termID);

        if (allCourses.isEmpty()) {
            Toast.makeText(termDetail.this, "No Courses Available.", Toast.LENGTH_LONG).show();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView_courseList);
        final courseAdapter courseAdapter = new courseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourse(allCourses);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.menu_refresh:
                repository = new repository(getApplication());

                List<Course> allCourses = repository.getCoursesByTermId(current_termID);
                RecyclerView recyclerView = findViewById(R.id.recyclerView_courseList);
                final courseAdapter courseAdapter = new courseAdapter(this);
                recyclerView.setAdapter(courseAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                courseAdapter.setCourse(allCourses);

                if (allCourses.isEmpty()) {
                    Toast.makeText(termDetail.this, "No Courses Available.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(termDetail.this, "Refreshed.", Toast.LENGTH_LONG).show();
                }
        }

        return super.onOptionsItemSelected(item);

    }

    //Add new Course.
    public void addCourse(View view) {

        Intent intent = new Intent(termDetail.this, addCourseView.class);
        intent.putExtra("id", current_termID);
        startActivity(intent);

    }

    //Update Term details in database.
    public void updateTerm(View view) {

        Term current_term;
        String name;
        String start;
        String end;

        Date currentDateTime = Calendar.getInstance().getTime();
        String created_date = currentDateTime.toString();

        DateValidator validator = new DateValidator();

        name = editTermTitle.getText().toString();
        start = editTermStart.getText().toString();
        end = editTermEnd.getText().toString();

        if (validator.isDateValid(start) && validator.isDateValid(end)) {

            if (validator.isDateSequenceValid(start, end)) {

                Toast.makeText(termDetail.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();

            } else {

                mTerms = repository.getAllTerms();
                for (int i = 0; i < mTerms.size(); i++) {

                    current_term = mTerms.get(i);

                    if (current_term.getTermID() == current_termID) {

                        current_term.setTermName(name);
                        current_term.setTermStart(start);
                        current_term.setTermEnd(end);
                        current_term.setCreated_date(created_date);

                        repository.update(current_term);
                        Toast.makeText(termDetail.this, "Term details Updated.", Toast.LENGTH_LONG).show();

                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(termDetail.this, termList.class);
                        startActivity(intent);

                    }
                }
            }

        } else if (name.isEmpty() || start.isEmpty() || end.isEmpty()){

            Toast.makeText(termDetail.this, "Check required fields.", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(termDetail.this, "Check date format.", Toast.LENGTH_LONG).show();

        }
    }

    //Delete selected Term from the database.
    public void deleteTerm(View view) {

        Term current_term;
        mTerms = repository.getAllTerms();
        for (int i = 0; i < mTerms.size(); i++) {

            current_term = mTerms.get(i);
            if (current_term.getTermID() == current_termID) {
                List<Course> allCourses = repository.getCoursesByTermId(current_termID);

                if (allCourses.isEmpty()) {
                    repository.delete(current_term);
                    Toast.makeText(termDetail.this, "Term deleted.", Toast.LENGTH_LONG).show();

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(termDetail.this, termList.class);
                    startActivity(intent);
                    break;

                }

                else {

                    Toast.makeText(termDetail.this, "Term contains associated Courses.", Toast.LENGTH_LONG).show();

                }
            }
        }
    }
}