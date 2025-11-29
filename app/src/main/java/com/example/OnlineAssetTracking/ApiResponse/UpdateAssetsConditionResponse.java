package com.example.OnlineAssetTracking.ApiResponse;

import com.example.OnlineAssetTracking.Model.ResponseStatus;

public class UpdateAssetsConditionResponse {
    private ResponseStatus responseStatus;
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
