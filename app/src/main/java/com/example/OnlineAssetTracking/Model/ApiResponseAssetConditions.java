package com.example.OnlineAssetTracking.Model;

import com.example.OnlineAssetTracking.ApiResponse.Success;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseAssetConditions {

    @SerializedName("assetConditon")
    @Expose
    private List<AssetCondition> assetConditon = null;
    @SerializedName("success")
    @Expose
    private Success success;

    public List<AssetCondition> getAssetConditon() {
        return assetConditon;
    }

    public void setAssetConditon(List<AssetCondition> assetConditon) {
        this.assetConditon = assetConditon;
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }
}
