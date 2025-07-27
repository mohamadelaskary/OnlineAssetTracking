package com.example.OnlineAssetTracking.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransferOrderHeader {
    @SerializedName("transferToWarehouseHeaderId")
    @Expose
    private Integer transferToWarehouseHeaderId;
    @SerializedName("orderNo")
    @Expose
    private Integer orderNo;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("transferOrderDetails")
    @Expose
    private List<TransferOrderDetail> transferOrderDetails;

    public Integer getTransferToWarehouseHeaderId() {
        return transferToWarehouseHeaderId;
    }

    public void setTransferToWarehouseHeaderId(Integer transferToWarehouseHeaderId) {
        this.transferToWarehouseHeaderId = transferToWarehouseHeaderId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<TransferOrderDetail> getTransferOrderDetails() {
        return transferOrderDetails;
    }

    public void setTransferOrderDetails(List<TransferOrderDetail> transferOrderDetails) {
        this.transferOrderDetails = transferOrderDetails;
    }

    @NonNull
    @Override
    public String toString() {
        return orderNo + " ( " + orderDate.substring(0,10) + " )";
    }
}
