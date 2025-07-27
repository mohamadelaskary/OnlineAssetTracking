package com.example.OnlineAssetTracking.Model;

import com.example.OnlineAssetTracking.ApiResponse.Success;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseAssets {

    @SerializedName("assets")
    @Expose
    private List<Asset> assets = null;
    @SerializedName("success")
    @Expose
    private Success success;

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }
}
