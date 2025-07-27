package com.example.OnlineAssetTracking.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveAssetTrackingBody {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("trackingOrderId")
    @Expose
    private int trackingOrderId;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTrackingOrderId() {
        return trackingOrderId;
    }

    public void setTrackingOrderId(int trackingOrderId) {
        this.trackingOrderId = trackingOrderId;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }


}
