package com.example.OnlineAssetTracking.ApiResponse;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.Model.ResponseStatus;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetAssetsByDescriptionResponse {
    @SerializedName("responseStatus")
    private ResponseStatus responseStatus;
    @SerializedName("assetsDataParam")
    private List<Asset> assetsDataParam = new ArrayList<>();

    public GetAssetsByDescriptionResponse() {
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Asset> getAssetsDataParam() {
        return assetsDataParam;
    }

    public void setAssetsDataParam(List<Asset> assetsDataParam) {
        this.assetsDataParam = assetsDataParam;
    }
}
