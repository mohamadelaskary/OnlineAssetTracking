package com.example.OnlineAssetTracking.Model;

import androidx.annotation.NonNull;

public class TrackingOrder {
    private int trackingOrderId;
    private int orderNumber;

    public TrackingOrder(int trackingOrderId, int orderNumber) {
        this.trackingOrderId = trackingOrderId;
        this.orderNumber = orderNumber;
    }

    public int getTrackingOrderId() {
        return trackingOrderId;
    }

    public void setTrackingOrderId(int trackingOrderId) {
        this.trackingOrderId = trackingOrderId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(orderNumber);
    }
}
