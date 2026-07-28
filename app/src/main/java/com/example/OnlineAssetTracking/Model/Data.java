package com.example.OnlineAssetTracking.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    private String barcode;
    private String notes;
    private Integer roomId;
    private Integer lastRoomID;
    private Integer assetConditionId;
    private Integer newAssetConditionId;
    private Integer curFloorID;
    private Integer lastFloorID;
    private String dt;
    private String userID;
    private Integer buildingID;
    private Integer newBuildingID;
    private Integer isSameLocation;
    private Integer isSameAssetCondition;
    private String locationStatus;
    private String assetConditionStatus;

    public Data(String barcode, String notes, Integer roomId, Integer lastRoomID, Integer assetConditionId, Integer newAssetConditionId, Integer curFloorID, Integer lastFloorID, String dt, String userID, Integer buildingID, Integer newBuildingID, Integer isSameLocation, Integer isSameAssetCondition, String locationStatus, String assetConditionStatus) {
        this.barcode = barcode;
        this.notes = notes;
        this.roomId = roomId;
        this.lastRoomID = lastRoomID;
        this.assetConditionId = assetConditionId;
        this.newAssetConditionId = newAssetConditionId;
        this.curFloorID = curFloorID;
        this.lastFloorID = lastFloorID;
        this.dt = dt;
        this.userID = userID;
        this.buildingID = buildingID;
        this.newBuildingID = newBuildingID;
        this.isSameLocation = isSameLocation;
        this.isSameAssetCondition = isSameAssetCondition;
        this.locationStatus = locationStatus;
        this.assetConditionStatus = assetConditionStatus;
    }

    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
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
    public String getDt() {
        return dt;
    }
    public void setDt(String dt) {
        this.dt = dt;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
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
    public String getLocationStatus() {
        return locationStatus;
    }
    public void setLocationStatus(String locationStatus) {
        this.locationStatus = locationStatus;
    }
    public String getAssetConditionStatus() {
        return assetConditionStatus;
    }
    public void setAssetConditionStatus(String assetConditionStatus) {
        this.assetConditionStatus = assetConditionStatus;
    }
}

