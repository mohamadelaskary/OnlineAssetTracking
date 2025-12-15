package com.example.OnlineAssetTracking.Model;

import java.util.ArrayList;
import java.util.List;

public class UpdateAssetsConditionBody {
    private String assetCode ;
    private String newAssetConditionName;
    private Integer userId;
    private String deviseSerialNo;

    private Integer trackingOrderId;

    public UpdateAssetsConditionBody(String assetCode, String newAssetConditionName, Integer userId, String deviseSerialNo, Integer trackingOrderId) {
        this.assetCode = assetCode;
        this.newAssetConditionName = newAssetConditionName;
        this.userId = userId;
        this.deviseSerialNo = deviseSerialNo;
        this.trackingOrderId = trackingOrderId;
    }
}
