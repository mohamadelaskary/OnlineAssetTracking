package com.example.OnlineAssetTracking.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("roomId")
    @Expose
    private Integer roomId;
    @SerializedName("lastRoomID")
    @Expose
    private Integer lastRoomID;
    @SerializedName("assetConditionId")
    @Expose
    private Integer assetConditionId;
    @SerializedName("curFloorID")
    @Expose
    private Integer curFloorID;
    @SerializedName("lastFloorID")
    @Expose
    private Integer lastFloorID;
    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("buildingID")
    @Expose
    private Integer buildingID;
    @SerializedName("newBuildingID")
    @Expose
    private Integer newBuildingID;
    @SerializedName("sectorID")
    @Expose
    private Integer sectorID;
    @SerializedName("newSectorID")
    @Expose
    private Integer newSectorID;
    @SerializedName("centralDepartmentID")
    @Expose
    private Integer centralDepartmentID;
    @SerializedName("newCentralDepartmentID")
    @Expose
    private Integer newCentralDepartmentID;
    @SerializedName("generalDepartmentID")
    @Expose
    private Integer generalDepartmentID;
    @SerializedName("newGeneralDepartmentID")
    @Expose
    private Integer newGeneralDepartmentID;
    @SerializedName("departmentID")
    @Expose
    private Integer departmentID;
    @SerializedName("newDepartmentID")
    @Expose
    private Integer newDepartmentID;
    @SerializedName("isSameLocation")
    @Expose
    private Integer isSameLocation;
    @SerializedName("isSameAssetCondition")
    @Expose
    private Integer isSameAssetCondition;
    @SerializedName("locationStatus")
    @Expose
    private String locationStatus;
    @SerializedName("assetConditionStatus")
    @Expose
    private String assetConditionStatus;
    @SerializedName("isSameSector")
    @Expose
    private Integer isSameSector;
    @SerializedName("isSameCentralDepartment")
    @Expose
    private Integer isSameCentralDepartment;
    @SerializedName("isSameGeneralDepartment")
    @Expose
    private Integer isSameGeneralDepartment;
    @SerializedName("isSameDepartment")
    @Expose
    private Integer isSameDepartment;
    @SerializedName("sectoStatus")
    @Expose
    private String sectoStatus;
    @SerializedName("centralDepartmentStatus")
    @Expose
    private String centralDepartmentStatus;
    @SerializedName("generalDepartmentStatus")
    @Expose
    private String generalDepartmentStatus;
    @SerializedName("departmentStatus")
    @Expose
    private String departmentStatus;
    @SerializedName("newAssetConditionId")
    @Expose
    private int newAssetConditionId;
    @SerializedName("userID")
    @Expose
    private int userId;

    public Data(String barcode, String notes, Integer roomId, Integer lastRoomID, Integer assetConditionId, Integer curFloorID, Integer lastFloorID, String dt, Integer buildingID, Integer newBuildingID, Integer sectorID, Integer newSectorID, Integer centralDepartmentID, Integer newCentralDepartmentID, Integer generalDepartmentID, Integer newGeneralDepartmentID, Integer departmentID, Integer newDepartmentID, Integer isSameLocation, Integer isSameAssetCondition, String locationStatus, String assetConditionStatus, Integer isSameSector, Integer isSameCentralDepartment, Integer isSameGeneralDepartment, Integer isSameDepartment, String sectoStatus, String centralDepartmentStatus, String generalDepartmentStatus, String departmentStatus,int newAssetConditionId) {
        this.barcode = barcode;
        this.notes = notes;
        this.roomId = roomId;
        this.lastRoomID = lastRoomID;
        this.assetConditionId = assetConditionId;
        this.curFloorID = curFloorID;
        this.lastFloorID = lastFloorID;
        this.dt = dt;
        this.buildingID = buildingID;
        this.newBuildingID = newBuildingID;
        this.sectorID = sectorID;
        this.newSectorID = newSectorID;
        this.centralDepartmentID = centralDepartmentID;
        this.newCentralDepartmentID = newCentralDepartmentID;
        this.generalDepartmentID = generalDepartmentID;
        this.newGeneralDepartmentID = newGeneralDepartmentID;
        this.departmentID = departmentID;
        this.newDepartmentID = newDepartmentID;
        this.isSameLocation = isSameLocation;
        this.isSameAssetCondition = isSameAssetCondition;
        this.locationStatus = locationStatus;
        this.assetConditionStatus = assetConditionStatus;
        this.isSameSector = isSameSector;
        this.isSameCentralDepartment = isSameCentralDepartment;
        this.isSameGeneralDepartment = isSameGeneralDepartment;
        this.isSameDepartment = isSameDepartment;
        this.sectoStatus = sectoStatus;
        this.centralDepartmentStatus = centralDepartmentStatus;
        this.generalDepartmentStatus = generalDepartmentStatus;
        this.departmentStatus = departmentStatus;
        this.newAssetConditionId = newAssetConditionId;
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

    public Integer getSectorID() {
        return sectorID;
    }

    public void setSectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }

    public Integer getNewSectorID() {
        return newSectorID;
    }

    public void setNewSectorID(Integer newSectorID) {
        this.newSectorID = newSectorID;
    }

    public Integer getCentralDepartmentID() {
        return centralDepartmentID;
    }

    public void setCentralDepartmentID(Integer centralDepartmentID) {
        this.centralDepartmentID = centralDepartmentID;
    }

    public Integer getNewCentralDepartmentID() {
        return newCentralDepartmentID;
    }

    public void setNewCentralDepartmentID(Integer newCentralDepartmentID) {
        this.newCentralDepartmentID = newCentralDepartmentID;
    }

    public Integer getGeneralDepartmentID() {
        return generalDepartmentID;
    }

    public void setGeneralDepartmentID(Integer generalDepartmentID) {
        this.generalDepartmentID = generalDepartmentID;
    }

    public Integer getNewGeneralDepartmentID() {
        return newGeneralDepartmentID;
    }

    public void setNewGeneralDepartmentID(Integer newGeneralDepartmentID) {
        this.newGeneralDepartmentID = newGeneralDepartmentID;
    }

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
    }

    public Integer getNewDepartmentID() {
        return newDepartmentID;
    }

    public void setNewDepartmentID(Integer newDepartmentID) {
        this.newDepartmentID = newDepartmentID;
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

    public Integer getIsSameSector() {
        return isSameSector;
    }

    public void setIsSameSector(Integer isSameSector) {
        this.isSameSector = isSameSector;
    }

    public Integer getIsSameCentralDepartment() {
        return isSameCentralDepartment;
    }

    public void setIsSameCentralDepartment(Integer isSameCentralDepartment) {
        this.isSameCentralDepartment = isSameCentralDepartment;
    }

    public Integer getIsSameGeneralDepartment() {
        return isSameGeneralDepartment;
    }

    public void setIsSameGeneralDepartment(Integer isSameGeneralDepartment) {
        this.isSameGeneralDepartment = isSameGeneralDepartment;
    }

    public Integer getIsSameDepartment() {
        return isSameDepartment;
    }

    public void setIsSameDepartment(Integer isSameDepartment) {
        this.isSameDepartment = isSameDepartment;
    }

    public String getSectoStatus() {
        return sectoStatus;
    }

    public void setSectoStatus(String sectoStatus) {
        this.sectoStatus = sectoStatus;
    }

    public String getCentralDepartmentStatus() {
        return centralDepartmentStatus;
    }

    public void setCentralDepartmentStatus(String centralDepartmentStatus) {
        this.centralDepartmentStatus = centralDepartmentStatus;
    }

    public String getGeneralDepartmentStatus() {
        return generalDepartmentStatus;
    }

    public void setGeneralDepartmentStatus(String generalDepartmentStatus) {
        this.generalDepartmentStatus = generalDepartmentStatus;
    }

    public String getDepartmentStatus() {
        return departmentStatus;
    }

    public void setDepartmentStatus(String departmentStatus) {
        this.departmentStatus = departmentStatus;
    }

    public void setNewAssetConditionId(int newAssetConditionId) {
        this.newAssetConditionId = newAssetConditionId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
