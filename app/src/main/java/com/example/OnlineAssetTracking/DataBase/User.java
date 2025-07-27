package com.example.OnlineAssetTracking.DataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class User {
    @PrimaryKey
    @SerializedName("userID")
    @Expose
    private Integer userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("employeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("employeeName")
    @Expose
    private String employeeName;
    @SerializedName("email")
    @Expose
    private String email;
    private Integer roleId;
    private String roleName;
    @SerializedName("pass")
    @Expose
    private String password;

    public User(Integer userId, String userName, Integer employeeId, String employeeName, String email, Integer roleId, String roleName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.roleId = roleId;
        this.roleName = roleName;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userID) {
        this.userId = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
