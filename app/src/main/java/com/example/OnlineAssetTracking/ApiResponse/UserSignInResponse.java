package com.example.OnlineAssetTracking.ApiResponse;

import com.example.OnlineAssetTracking.DataBase.User;

public class UserSignInResponse {
    private Success success;
    private User userinfo;
    public Success getSuccess() {
        return success;
    }
    public void setSuccess(Success success) {
        this.success = success;
    }
    public User getUserinfo() {
        return userinfo;
    }
    public void setUserinfo(User userinfo) {
        this.userinfo = userinfo;
    }
}
