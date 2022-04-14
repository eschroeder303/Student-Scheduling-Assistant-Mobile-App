package android.C868.Capstone.All.GUI;

import android.C868.Capstone.All.View.addTermView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.C868.Capstone.All.Database.repository;
import android.C868.Capstone.All.Model.Term;
import android.C868.Capstone.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class termList extends AppCompatActivity {

    private android.C868.Capstone.All.GUI.termAdapter termAdapter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        android.C868.Capstone.All.Database.repository repository = new repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();

        if (allTerms.isEmpty()) {

            Toast.makeText(termList.this, "No Terms Available.", Toast.LENGTH_LONG).show();

        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView_term);
        termAdapter = new termAdapter(this, allTerms);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        };

        menu.findItem(R.id.search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search Terms");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                termAdapter.getFilter().filter(newText.toLowerCase());
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Add new Term.
    public void addTerm(View view) {
        Intent intent = new Intent(termList.this, addTermView.class);
        startActivity(intent);
    }

    // Term Report view.
    public void getTermReport(View view) {
        Intent intent = new Intent(termList.this, report.class);
        startActivity(intent);
    }
}
