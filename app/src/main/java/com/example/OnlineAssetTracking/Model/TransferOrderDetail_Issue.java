package com.example.OnlineAssetTracking.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferOrderDetail_Issue {
    @SerializedName("transferFromWarehouseDetailId")
    @Expose
    private Integer transferFromWarehouseDetailId;
    @SerializedName("from_WarehouseId")
    @Expose
    private Integer fromWarehouseId;
    @SerializedName("from_WarehouseCode")
    @Expose
    private String fromWarehouseCode;
    @SerializedName("from_WarehouseDesc")
    @Expose
    private String fromWarehouseDesc;
    @SerializedName("assetId")
    @Expose
    private Integer assetId;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("to_BuildingId")
    @Expose
    private Object toBuildingId;
    @SerializedName("to_BuildingCode")
    @Expose
    private Object toBuildingCode;
    @SerializedName("to_BuildingName")
    @Expose
    private Object toBuildingName;
    @SerializedName("isIssued")
    @Expose
    private Object isIssued;

    public Integer getTransferFromWarehouseDetailId() {
        return transferFromWarehouseDetailId;
    }

    public void setTransferFromWarehouseDetailId(Integer transferFromWarehouseDetailId) {
        this.transferFromWarehouseDetailId = transferFromWarehouseDetailId;
    }

    public Integer getFromWarehouseId() {
        return fromWarehouseId;
    }

    public void setFromWarehouseId(Integer fromWarehouseId) {
        this.fromWarehouseId = fromWarehouseId;
    }

    public String getFromWarehouseCode() {
        return fromWarehouseCode;
    }

    public void setFromWarehouseCode(String fromWarehouseCode) {
        this.fromWarehouseCode = fromWarehouseCode;
    }

    public String getFromWarehouseDesc() {
        return fromWarehouseDesc;
    }

    public void setFromWarehouseDesc(String fromWarehouseDesc) {
        this.fromWarehouseDesc = fromWarehouseDesc;
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

    public Object getToBuildingId() {
        return toBuildingId;
    }

    public void setToBuildingId(Object toBuildingId) {
        this.toBuildingId = toBuildingId;
    }

    public Object getToBuildingCode() {
        return toBuildingCode;
    }

    public void setToBuildingCode(Object toBuildingCode) {
        this.toBuildingCode = toBuildingCode;
    }

    public Object getToBuildingName() {
        return toBuildingName;
    }

    public void setToBuildingName(Object toBuildingName) {
        this.toBuildingName = toBuildingName;
    }

    public Object getIsIssued() {
        return isIssued;
    }

    public void setIsIssued(Object isIssued) {
        this.isIssued = isIssued;
    }

}
