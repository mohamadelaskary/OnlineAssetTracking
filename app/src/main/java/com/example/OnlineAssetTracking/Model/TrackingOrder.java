package com.example.OnlineAssetTracking.Model;

import androidx.annotation.NonNull;

public class TrackingOrder {
    private int trackingOrderId;
    private String orderNumber;

    public TrackingOrder(int trackingOrderId, String orderNumber) {
        this.trackingOrderId = trackingOrderId;
        this.orderNumber = orderNumber;
    }

    public int getTrackingOrderId() {
        return trackingOrderId;
    }

    public void setTrackingOrderId(int trackingOrderId) {
        this.trackingOrderId = trackingOrderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(orderNumber);
    }
}
