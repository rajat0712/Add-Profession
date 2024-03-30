package com.rajatw.addProfession.model;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static DataManager instance;
    private List<String> selectedSkillsList;

    DataManager() {
        selectedSkillsList = new ArrayList<>();
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public List<String> getSelectedStandards() {
        return selectedSkillsList;
    }

    public void addSelectedStandard(String standard) {
        selectedSkillsList.add(standard);
    }
}

