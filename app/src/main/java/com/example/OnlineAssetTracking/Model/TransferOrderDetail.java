package com.example.OnlineAssetTracking.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferOrderDetail {
    @SerializedName("transferToWarehouseDetailId")
    @Expose
    private Integer transferToWarehouseDetailId;
    @SerializedName("to_WarehouseId")
    @Expose
    private Integer toWarehouseId;
    @SerializedName("to_WarehouseCode")
    @Expose
    private String toWarehouseCode;
    @SerializedName("to_WarehouseDesc")
    @Expose
    private String toWarehouseDesc;
    @SerializedName("assetId")
    @Expose
    private Integer assetId;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("buildingId")
    @Expose
    private Integer buildingId;
    @SerializedName("from_RoomId")
    @Expose
    private Integer fromRoomId;
    @SerializedName("from_RoomCode")
    @Expose
    private String fromRoomCode;
    @SerializedName("from_RoomName")
    @Expose
    private String fromRoomName;
    @SerializedName("isReceived")
    @Expose
    private Object isReceived;

    public Integer getTransferToWarehouseDetailId() {
        return transferToWarehouseDetailId;
    }

    public void setTransferToWarehouseDetailId(Integer transferToWarehouseDetailId) {
        this.transferToWarehouseDetailId = transferToWarehouseDetailId;
    }

    public Integer getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(Integer toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public String getToWarehouseCode() {
        return toWarehouseCode;
    }

    public void setToWarehouseCode(String toWarehouseCode) {
        this.toWarehouseCode = toWarehouseCode;
    }

    public String getToWarehouseDesc() {
        return toWarehouseDesc;
    }

    public void setToWarehouseDesc(String toWarehouseDesc) {
        this.toWarehouseDesc = toWarehouseDesc;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getFromRoomId() {
        return fromRoomId;
    }

    public void setFromRoomId(Integer fromRoomId) {
        this.fromRoomId = fromRoomId;
    }

    public String getFromRoomCode() {
        return fromRoomCode;
    }

    public void setFromRoomCode(String fromRoomCode) {
        this.fromRoomCode = fromRoomCode;
    }

    public String getFromRoomName() {
        return fromRoomName;
    }

    public void setFromRoomName(String fromRoomName) {
        this.fromRoomName = fromRoomName;
    }

    public Object getIsReceived() {
        return isReceived;
    }

    public void setIsReceived(Object isReceived) {
        this.isReceived = isReceived;
    }

}
