package com.example.OnlineAssetTracking.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransferOrderHeader_Issue {
    @SerializedName("transferFromWarehouseHeaderId")
    @Expose
    private Integer transferFromWarehouseHeaderId;
    @SerializedName("orderNo")
    @Expose
    private Integer orderNo;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("transferOrderDetails")
    @Expose
    private List<TransferOrderDetail_Issue> transferOrderDetails;

    public Integer getTransferFromWarehouseHeaderId() {
        return transferFromWarehouseHeaderId;
    }

    public void setTransferFromWarehouseHeaderId(Integer transferFromWarehouseHeaderId) {
        this.transferFromWarehouseHeaderId = transferFromWarehouseHeaderId;
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

    public List<TransferOrderDetail_Issue> getTransferOrderDetails() {
        return transferOrderDetails;
    }

    public void setTransferOrderDetails(List<TransferOrderDetail_Issue> transferOrderDetails) {
        this.transferOrderDetails = transferOrderDetails;
    }
}
