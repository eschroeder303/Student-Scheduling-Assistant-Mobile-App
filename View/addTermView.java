package android.C868.Capstone.All.View;

import android.C868.Capstone.All.GUI.termList;
import android.content.Intent;
import android.C868.Capstone.All.Database.repository;
import android.C868.Capstone.All.Utility.DateValidator;
import android.C868.Capstone.All.Model.Term;
import android.C868.Capstone.R;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class addTermView extends AppCompatActivity {

    private EditText editTermTitle;
    private EditText editTermStart;
    private EditText editTermEnd;
    private android.C868.Capstone.All.Database.repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term_view);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String termTitle = getIntent().getStringExtra("name");
        String termStart = getIntent().getStringExtra("start");
        String termEnd = getIntent().getStringExtra("end");

        editTermTitle = findViewById(R.id.editText_termTitle);
        editTermStart = findViewById(R.id.editText_startDate);
        editTermEnd = findViewById(R.id.editText_endDate);

        editTermTitle.setText(termTitle);;
        editTermStart.setText(termStart);
        editTermEnd.setText(termEnd);

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

    //Add new term to database.
    public void saveTerm(View view) {

        String currentTermTitle = editTermTitle.getText().toString();
        String currentStart = editTermStart.getText().toString();
        String currentEnd = editTermEnd.getText().toString();

        Date currentDateTime = Calendar.getInstance().getTime();
        String created_date = currentDateTime.toString();

        DateValidator validator = new DateValidator();

        //Check for empty fields.
        if (    currentTermTitle.isEmpty() ||
                currentStart.isEmpty()     ||
                currentEnd.isEmpty()    ) {

            Toast.makeText(addTermView.this, "Check required fields.", Toast.LENGTH_LONG).show();

        } else {

            //Validate correct format for dates.
            if (validator.isDateValid(currentStart) && validator.isDateValid(currentEnd)) {

                if (validator.isDateSequenceValid(currentStart, currentEnd)) {

                    Toast.makeText(addTermView.this, "Start date must be before end date.", Toast.LENGTH_LONG).show();

                } else {

                    Term newTerm = new Term(0, currentTermTitle, currentStart, currentEnd, created_date);
                    repository.insert(newTerm);
                    Toast.makeText(addTermView.this, "New term added.", Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(addTermView.this, termList.class);
                    startActivity(intent);

                }

            } else {

                Toast.makeText(addTermView.this, "Check date format.", Toast.LENGTH_LONG).show();

            }
        }
    }
}