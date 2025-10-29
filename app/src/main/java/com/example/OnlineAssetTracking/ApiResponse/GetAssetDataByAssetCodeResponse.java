package com.example.OnlineAssetTracking.ApiResponse;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.Model.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

public class GetAssetDataByAssetCodeResponse {
    private ResponseStatus responseStatus;
    private List<Asset> assetsDataParam = new ArrayList<Asset>();
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
