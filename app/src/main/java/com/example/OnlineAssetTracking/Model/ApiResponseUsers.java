package com.example.OnlineAssetTracking.Model;

import com.example.OnlineAssetTracking.ApiResponse.Success;
import com.example.OnlineAssetTracking.DataBase.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseUsers {

    @SerializedName("users")
    @Expose
    private List<User> users = null;
    @SerializedName("success")
    @Expose
    private Success success;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }
}
