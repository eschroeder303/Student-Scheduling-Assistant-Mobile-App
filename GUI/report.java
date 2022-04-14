package android.C868.Capstone.All.GUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.C868.Capstone.All.Database.repository;
import android.C868.Capstone.All.Model.Term;
import android.C868.Capstone.R;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.List;
import java.util.Objects;

public class report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        android.C868.Capstone.All.Database.repository repository = new repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();

        RecyclerView recyclerView = findViewById(R.id.recyclerView_Report);
        android.C868.Capstone.All.GUI.reportAdapter reportAdapter = new reportAdapter(this, allTerms);
        recyclerView.setAdapter(reportAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return true;
    }
}
