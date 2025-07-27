package com.example.OnlineAssetTracking.Model;

import com.example.OnlineAssetTracking.ApiResponse.Success;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseUserLocations {
    @SerializedName("userLocation")
    @Expose
    private List<UserLocation> userLocation = null;
    @SerializedName("success")
    @Expose
    private Success success;

    public List<UserLocation> getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(List<UserLocation> userLocation) {
        this.userLocation = userLocation;
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }
}
