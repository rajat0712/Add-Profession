package com.rajatw.addProfession.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProfessionExpItem implements Parcelable {
    private String Profession;
    private int experience;
    private boolean cancelVisible;

    public ProfessionExpItem(String profession, int experience, boolean cancelVisible) {
        Profession = profession;
        this.experience = experience;
        this.cancelVisible = cancelVisible;
    }

    protected ProfessionExpItem(Parcel in) {
        Profession = in.readString();
        experience = in.readInt();
        cancelVisible = in.readByte() != 0;
    }

    public static final Creator<ProfessionExpItem> CREATOR = new Creator<ProfessionExpItem>() {
        @Override
        public ProfessionExpItem createFromParcel(Parcel in) {
            return new ProfessionExpItem(in);
        }

        @Override
        public ProfessionExpItem[] newArray(int size) {
            return new ProfessionExpItem[size];
        }
    };

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public boolean isCancelVisible() {
        return cancelVisible;
    }

    public void setCancelVisible(boolean cancelVisible) {
        this.cancelVisible = cancelVisible;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(Profession);
        dest.writeInt(experience);
        dest.writeByte((byte) (cancelVisible ? 1 : 0));
    }
}
