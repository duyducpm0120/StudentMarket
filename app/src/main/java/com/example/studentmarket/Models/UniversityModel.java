package com.example.studentmarket.Models;

public class UniversityModel {
    public int universityId;
    public String universityName;
    public String universityAbbName;
    public Object universityDescription;
    public String universityEmailSuffix;

    public UniversityModel(int universityId, String universityName, String universityAbbName, Object universityDescription, String universityEmailSuffix) {
        this.universityId = universityId;
        this.universityName = universityName;
        this.universityAbbName = universityAbbName;
        this.universityDescription = universityDescription;
        this.universityEmailSuffix = universityEmailSuffix;
    }

    public UniversityModel() {
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityAbbName() {
        return universityAbbName;
    }

    public void setUniversityAbbName(String universityAbbName) {
        this.universityAbbName = universityAbbName;
    }

    public Object getUniversityDescription() {
        return universityDescription;
    }

    public void setUniversityDescription(Object universityDescription) {
        this.universityDescription = universityDescription;
    }

    public String getUniversityEmailSuffix() {
        return universityEmailSuffix;
    }

    public void setUniversityEmailSuffix(String universityEmailSuffix) {
        this.universityEmailSuffix = universityEmailSuffix;
    }
}
