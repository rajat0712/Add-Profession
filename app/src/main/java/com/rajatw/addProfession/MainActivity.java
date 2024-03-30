package com.rajatw.addProfession;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.rajatw.addProfession.adapter.ProfessionAdapter;
import com.rajatw.addProfession.model.Profession;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProfessionAdapter adapter;
    private List<Profession> professionList;
    private SearchView searchView;
    private Button Next;
    private List<String> selectedProfessions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.listStandards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        professionList = generateDummyStandards();
        adapter = new ProfessionAdapter(professionList, this);
        recyclerView.setAdapter(adapter);
        searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search...");
        Next = findViewById(R.id.btnNext);
        setupSearchView();
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchView.getQuery().toString();
                if (isValidProfession(searchText)) {
                    if (!selectedProfessions.contains(searchText)) {
                        String experience = ((EditText) findViewById(R.id.edtExperience)).getText().toString();

                        if (!experience.isEmpty()) {
                            selectedProfessions.add(searchText);
                        } else {
                            Toast.makeText(MainActivity.this, "Please enter experience for the selected profession",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    openProfessionDetailsActivity(searchText);
                } else {
                    Toast.makeText(MainActivity.this, "Entered Profession is not found in the profession List : " + searchText,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        selectedProfessions = new ArrayList<>();
    }

    private List<Profession> generateDummyStandards() {
        List<Profession> professionList = new ArrayList<>();
        String[] professionData = {"Software Developer", "Network Engineer", "System Administrator",
                                "Database Administrator", "Cybersecurity Analyst", "Web Developer",
                                "UI/UX Designer", "Cloud Engineer", "DevOps Engineer", "Data Scientist",
                                "AI Engineer", "Machine Learning Engineer", "IT Support Specialist", "QA Tester",
                                "Technical Writer", "Project Manager", "IT Consultant", "Digital Marketing Specialist",
                                "Business Analyst", "IT Trainer", "Technical Architect", "Enterprise Architect", "Product Manager",
                                "Scrum Master", "IT Auditor", "Ethical Hacker", "Penetration Tester", "Blockchain Developer",
                                "Frontend Developer", "Backend Developer", "Full Stack Developer", "Mobile App Developer",
                                "IT Sales Representative", "IT Recruiter", "IT Procurement Specialist", "IT Operations Manager",
                                "IT Compliance Analyst", "IT Risk Manager", "Data Engineer", "Computer Programmer", "Network Administrator",
                                "Systems Analyst", "IT Business Analyst", "UX Researcher", "UI Designer"};

        int index = 1;
        for (String profession : professionData) {
            professionList.add(new Profession(index++, profession));
        }
        return professionList;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterProfession(newText);
                return true;
            }
        });
    }

    private void filterProfession(String query) {
        List<Profession> filteredProfessions = new ArrayList<>();

        for (Profession profession : professionList) {
            if (profession.getProfession().toLowerCase().contains(query.toLowerCase())) {
                filteredProfessions.add(profession);
            }
        }

        adapter = new ProfessionAdapter(filteredProfessions, this);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            // Access the clicked item from the filtered list
            Profession clickedProfession = filteredProfessions.get(position);
            if (!selectedProfessions.contains(clickedProfession.getProfession())) {
                selectedProfessions.add(clickedProfession.getProfession());
                SearchView searchView = findViewById(R.id.searchView);
                searchView.setQuery(clickedProfession.getProfession(), false);

                String experience = ((EditText) findViewById(R.id.edtExperience)).getText().toString();
                if (experience.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter experience for " + clickedProfession.getProfession(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                openProfessionDetailsActivity(clickedProfession.getProfession());
            } else {
                Toast.makeText(MainActivity.this, "Profession already selected: " + clickedProfession.getProfession(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void openProfessionDetailsActivity(String profession) {
        Intent intent = new Intent(this, DisplayActivity.class);

        // Ensure experienceList is not null before passing it
        List<Integer> experienceList = new ArrayList<>();

        for (String selectedProfession : selectedProfessions) {
            String experienceStr = ((EditText) findViewById(R.id.edtExperience)).getText().toString();

            try {
                // Attempt to parse the experience string to an integer
                int experience = TextUtils.isEmpty(experienceStr) ? 0 : Integer.parseInt(experienceStr);
                experienceList.add(experience);
            } catch (NumberFormatException e) {
                // Handle the case where the entered experience is not a valid integer
                Toast.makeText(MainActivity.this, "Invalid experience value entered for " + selectedProfession
                        , Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Pass selectedSkills and the corresponding experience values
        intent.putStringArrayListExtra("selectedProfession", new ArrayList<>(selectedProfessions));
        intent.putIntegerArrayListExtra("experienceList", new ArrayList<>(experienceList));

        // Update the SearchView query with the selected skill
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQuery(profession, false);

        startActivity(intent);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        EditText edtExperience = findViewById(R.id.edtExperience);
//        edtExperience.setText("");
//    }

    private boolean isValidProfession(String professions) {
        for (Profession profession : professionList) {
            if (profession.getProfession().equalsIgnoreCase(professions)) {
                return true;
            }
        }
        return false;
    }
}
