package com.rajatw.addProfession;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rajatw.addProfession.model.ProfessionExpItem;

import java.util.List;

public class ProfessionListActivity extends AppCompatActivity {

    TextView Title,ProfessionWithExperience,Team;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_list);

        Title = findViewById(R.id.txtHeading);
        ProfessionWithExperience = findViewById(R.id.txtProfessionFetch);
        Team = findViewById(R.id.txtTeam);
        Intent intent = getIntent();
        List<ProfessionExpItem> professionExpItemList = intent.getParcelableArrayListExtra("selectedProfessions");
        int experience = intent.getIntExtra("experience", 0);
        int currentValue = intent.getIntExtra("currentValue", 1);

        Team.setText("Team Size : "+currentValue+".");

        StringBuilder professions = new StringBuilder();
        for (int i = 0; i < professionExpItemList.size(); i++) {
            ProfessionExpItem expItem = professionExpItemList.get(i);
            professions.append(expItem.getProfession())
                    .append(" (")
                    .append(experience)
                    .append(" Yr)");
            if (i < professionExpItemList.size() - 1) {
                professions.append(", ");
            } else {
                professions.append(".");
            }
        }
        ProfessionWithExperience.setText(professions.toString());
        Title.setText("Selected Professions");
    }
}
