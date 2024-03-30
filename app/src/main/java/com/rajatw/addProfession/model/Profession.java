package com.rajatw.addProfession.model;

public class Profession {
    private int professionId;
    private String Profession;

    public Profession(int professionId, String profession) {
        this.professionId = professionId;
        Profession = profession;
    }

    public int getProfessionId() {
        return professionId;
    }

    public void setProfessionId(int professionId) {
        this.professionId = professionId;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }
}
