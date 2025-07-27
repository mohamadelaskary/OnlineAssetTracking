package com.example.OnlineAssetTracking.Model;

public class CentralDepartment {
    private int centralDepartmentId;
    private String centralDepartmentName;

    public CentralDepartment(int centralDepartmentId, String centralDepartmentName) {
        this.centralDepartmentId = centralDepartmentId;
        this.centralDepartmentName = centralDepartmentName;
    }

    public int getCentralDepartmentId() {
        return centralDepartmentId;
    }

    public void setCentralDepartmentId(int centralDepartmentId) {
        this.centralDepartmentId = centralDepartmentId;
    }

    public String getCentralDepartmentName() {
        return centralDepartmentName;
    }

    public void setCentralDepartmentName(String centralDepartmentName) {
        this.centralDepartmentName = centralDepartmentName;
    }

    @Override
    public String toString() {
        return centralDepartmentName ;
    }
}
