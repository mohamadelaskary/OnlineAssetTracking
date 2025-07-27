package com.example.OnlineAssetTracking.Model;

public class GeneralDepartment {
    private int generalDepartmentId;
    private String generalDepartmentName;

    public GeneralDepartment(int centralDepartmentId, String generalDepartmentName) {
        this.generalDepartmentId = centralDepartmentId;
        this.generalDepartmentName = generalDepartmentName;
    }

    public int getGeneralDepartmentId() {
        return generalDepartmentId;
    }

    public void setGeneralDepartmentId(int generalDepartmentId) {
        this.generalDepartmentId = generalDepartmentId;
    }

    public String getGeneralDepartmentName() {
        return generalDepartmentName;
    }

    public void setGeneralDepartmentName(String generalDepartmentName) {
        this.generalDepartmentName = generalDepartmentName;
    }

    @Override
    public String toString() {
        return generalDepartmentName ;
    }
}
