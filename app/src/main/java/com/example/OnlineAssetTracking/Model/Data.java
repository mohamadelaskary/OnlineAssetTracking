package com.example.OnlineAssetTracking.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    private String barcode;
    private Integer curFloorID;
    private Integer lastFloorID;
    private Integer roomId;
    private Integer lastRoomID;
    private Integer assetConditionId;
    private Integer newAssetConditionId;
    private String notes;
    private Integer userID;
    private String dt;
    private Integer status;
    private Integer buildingID;
    private Integer newBuildingID;
    private Integer isSameLocation;
    private Integer isSameAssetCondition;

    public Data(String barcode, Integer curFloorID, Integer lastFloorID, Integer roomId, Integer lastRoomID, Integer assetConditionId, Integer newAssetConditionId, String notes, Integer userID, String dt, Integer status, Integer buildingID, Integer newBuildingID, Integer isSameLocation, Integer isSameAssetCondition) {
        this.barcode = barcode;
        this.curFloorID = curFloorID;
        this.lastFloorID = lastFloorID;
        this.roomId = roomId;
        this.lastRoomID = lastRoomID;
        this.assetConditionId = assetConditionId;
        this.newAssetConditionId = newAssetConditionId;
        this.notes = notes;
        this.userID = userID;
        this.dt = dt;
        this.status = status;
        this.buildingID = buildingID;
        this.newBuildingID = newBuildingID;
        this.isSameLocation = isSameLocation;
        this.isSameAssetCondition = isSameAssetCondition;
    }

    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public Integer getCurFloorID() {
        return curFloorID;
    }
    public void setCurFloorID(Integer curFloorID) {
        this.curFloorID = curFloorID;
    }
    public Integer getLastFloorID() {
        return lastFloorID;
    }
    public void setLastFloorID(Integer lastFloorID) {
        this.lastFloorID = lastFloorID;
    }
    public Integer getRoomId() {
        return roomId;
    }
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
    public Integer getLastRoomID() {
        return lastRoomID;
    }
    public void setLastRoomID(Integer lastRoomID) {
        this.lastRoomID = lastRoomID;
    }
    public Integer getAssetConditionId() {
        return assetConditionId;
    }
    public void setAssetConditionId(Integer assetConditionId) {
        this.assetConditionId = assetConditionId;
    }
    public Integer getNewAssetConditionId() {
        return newAssetConditionId;
    }
    public void setNewAssetConditionId(Integer newAssetConditionId) {
        this.newAssetConditionId = newAssetConditionId;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public Integer getUserID() {
        return userID;
    }
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    public String getDt() {
        return dt;
    }
    public void setDt(String dt) {
        this.dt = dt;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getBuildingID() {
        return buildingID;
    }
    public void setBuildingID(Integer buildingID) {
        this.buildingID = buildingID;
    }
    public Integer getNewBuildingID() {
        return newBuildingID;
    }
    public void setNewBuildingID(Integer newBuildingID) {
        this.newBuildingID = newBuildingID;
    }
    public Integer getIsSameLocation() {
        return isSameLocation;
    }
    public void setIsSameLocation(Integer isSameLocation) {
        this.isSameLocation = isSameLocation;
    }
    public Integer getIsSameAssetCondition() {
        return isSameAssetCondition;
    }
    public void setIsSameAssetCondition(Integer isSameAssetCondition) {
        this.isSameAssetCondition = isSameAssetCondition;
    }
}

