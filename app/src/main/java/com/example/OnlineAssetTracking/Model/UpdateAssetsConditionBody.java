package com.example.OnlineAssetTracking.Model;

import java.util.ArrayList;
import java.util.List;

public class UpdateAssetsConditionBody {
    private List<String> assetCodes = new ArrayList<String>();
    private String newAssetConditionName;
    private Integer userId;
    private String deviseSerialNo;

    public UpdateAssetsConditionBody(List<String> assetCodes, String newAssetConditionName, Integer userId, String deviseSerialNo) {
        this.assetCodes = assetCodes;
        this.newAssetConditionName = newAssetConditionName;
        this.userId = userId;
        this.deviseSerialNo = deviseSerialNo;
    }

    public List<String> getAssetCodes() {
        return assetCodes;
    }
    public void setAssetCodes(List<String> assetCodes) {
        this.assetCodes = assetCodes;
    }
    public String getNewAssetConditionName() {
        return newAssetConditionName;
    }
    public void setNewAssetConditionName(String newAssetConditionName) {
        this.newAssetConditionName = newAssetConditionName;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getDeviseSerialNo() {
        return deviseSerialNo;
    }
    public void setDeviseSerialNo(String deviseSerialNo) {
        this.deviseSerialNo = deviseSerialNo;
    }
}
