package com.rajatw.addProfession;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rajatw.addProfession.adapter.DisplayAdapter;
import com.rajatw.addProfession.model.ProfessionExpItem;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DisplayAdapter adapter;
    private Button addButton, btnNext;
    private TextView Back, Skip, TeamNumber;
    private List<ProfessionExpItem> professionExpItemList;
    private RelativeLayout SinglePerson;
    private ConstraintLayout AddTeam;
    private ImageButton btnAddTeam, btnMinus, btnPlus;
    private int currentValue = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        SinglePerson = findViewById(R.id.layoutSingle);
        AddTeam = findViewById(R.id.layoutTeam);
        btnAddTeam = findViewById(R.id.btnAddTeam);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        TeamNumber = findViewById(R.id.txtTeamNumber);
        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.btnAdd);
        btnNext = findViewById(R.id.btnNext);
        Back = findViewById(R.id.txtBack);
        Skip = findViewById(R.id.txtSkip);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> selectedProfession = getIntent().getStringArrayListExtra("selectedProfession");
        List<Integer> experienceList = getIntent().getIntegerArrayListExtra("experienceList");
        professionExpItemList = convertToProfessionExpItemList(selectedProfession, experienceList);
        adapter = new DisplayAdapter(professionExpItemList, this);
        recyclerView.setAdapter(adapter);

        Back.setOnClickListener(v -> finish());
        Skip.setOnClickListener(v -> finishAffinity());
        updateButtonStates();
        addButton.setOnClickListener(v -> addNewProfession());
        btnNext.setOnClickListener(v -> submitProfessions());
        btnAddTeam.setOnClickListener(v -> toggleVisibility());

        btnMinus.setOnClickListener(v -> decrementNumber());

        btnPlus.setOnClickListener(v -> incrementNumber());
    }

    private void toggleVisibility() {
        if (SinglePerson.getVisibility() == View.VISIBLE) {
            SinglePerson.setVisibility(View.INVISIBLE);
            AddTeam.setVisibility(View.VISIBLE);
        } else {
            SinglePerson.setVisibility(View.VISIBLE);
            AddTeam.setVisibility(View.INVISIBLE);
        }
    }

    private void incrementNumber() {
        currentValue++;
        TeamNumber.setText(String.valueOf(currentValue));
    }

    private void decrementNumber() {
        if (currentValue > 1) {
            currentValue--;
            TeamNumber.setText(String.valueOf(currentValue));
        }
    }

    @NonNull
    private List<ProfessionExpItem> convertToProfessionExpItemList(List<String> selectedProfession, List<Integer> experienceList) {
        List<ProfessionExpItem> professionExpItems = new ArrayList<>();
        // Ensure that the sizes match
        int minSize = Math.min(selectedProfession.size(), experienceList.size());

        for (int i = 0; i < minSize; i++) {
            String profession = selectedProfession.get(i);
            int experience = experienceList.get(i);
            professionExpItems.add(new ProfessionExpItem(profession, experience, true));
        }
        return professionExpItems;
    }

    private void addNewProfession() {
        // Check if the number of profession is less than the limit.
        if (adapter.getItemCount() < 5) {
            // Add a new dummy profession with initial experience set to "0".
            adapter.addNewProfession(new ProfessionExpItem("New Profession", 0, true));
            // Finish the current activity (DetailActivity) to go back to MainActivity
            finish();
        } else {
            showToast("You've reached the limit of 5 Professions");
        }
    }

    private void submitProfessions() {
        // Retrieve the selected profession from the adapter
        List<ProfessionExpItem> professionExpItemList = adapter.getUpdatedProfession();

        // Start the professionListActivity and pass the selected professions and experience
        Intent intent = new Intent(this, ProfessionListActivity.class);
        intent.putParcelableArrayListExtra("selectedProfessions", new ArrayList<>(professionExpItemList));

        // Assuming all profession have the same experience, so taking the experience from the first profession
        int experience = professionExpItemList.isEmpty() ? 0 : professionExpItemList.get(0).getExperience();
        intent.putExtra("experience", experience);

        // Pass the current value as well
        intent.putExtra("currentValue", currentValue);

        startActivity(intent);
    }


    private void updateButtonStates() {
        addButton.setVisibility(adapter.getItemCount() < 5 ? View.VISIBLE : View.GONE);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
