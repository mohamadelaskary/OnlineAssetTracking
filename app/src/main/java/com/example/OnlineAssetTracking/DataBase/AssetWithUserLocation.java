package com.example.OnlineAssetTracking.DataBase;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetWithUserLocation {

    @SerializedName("assetId")
    @Expose
    private Integer assetId;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("assetNumber")
    @Expose
    private String assetNumber;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("roomId")
    @Expose
    private String roomId;
    @SerializedName("roomName")
    @Expose
    private String roomName;
    @SerializedName("floorId")
    @Expose
    private String floorId;
    @SerializedName("floorName")
    @Expose
    private String floorName;
    @SerializedName("buildingId")
    @Expose
    private String buildingId;
    @SerializedName("buildingName")
    @Expose
    private String buildingName;
    @SerializedName("siteId")
    @Expose
    private String siteId;
    @SerializedName("siteName")
    @Expose
    private String siteName;
    @SerializedName("companyId")
    @Expose
    private String companyId;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("mainCategoryID")
    @Expose
    private String mainCategoryId;
    @SerializedName("mainCategoryName")
    @Expose
    private String mainCategoryName;
    @SerializedName("subCategory2ID")
    @Expose
    private String subCategory2Id;
    @SerializedName("subCategory2Name")
    @Expose
    private String subCategory2Name;
    @SerializedName("subCategory3ID")
    @Expose
    private String subCategory3Id;
    @SerializedName("subCategory3Name")
    @Expose
    private String subCategory3Name;
    @SerializedName("subCategory4ID")
    @Expose
    private String subCategory4Id;
    @SerializedName("subCategory4Name")
    @Expose
    private String subCategory4Name;
    @SerializedName("employeeID")
    @Expose
    private String employeeID;
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("sectorID")
    @Expose
    private String sectorID;
    @SerializedName("sector")
    @Expose
    private String sectorName;
    @SerializedName("centralDepartmentID")
    @Expose
    private String centralDepartmentID;
    @SerializedName("centralDepartment")
    @Expose
    private String centralDepartmentName;
    @SerializedName("genralDepartmentID")
    @Expose
    private String generalDepartmentID;
    @SerializedName("genralDepartment")
    @Expose
    private String generalDepartmentName;

    @SerializedName("departmentID")
    @Expose
    private String departmentID;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("assetConditionName")
    @Expose
    private String assetConditionName;
    @SerializedName("assetConditionId")
    @Expose
    private String assetConditionId;
    @SerializedName("sectionID")
    @Expose
    private String sectionID;
    @SerializedName("employee")
    @Expose
    private String employee;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @SerializedName("employee")
    @Expose
    private String employeeName;
    @SerializedName("fileBasse")
    @Expose
    private String fileBasse;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("carNo")
    @Expose
    private String CarNo;
    @SerializedName("modelOfYear")
    @Expose
    private String ModelOfYear;
    @SerializedName("bodyNo")
    @Expose
    private String BodyNo;
    @SerializedName("motorNo")
    @Expose
    private String MotorNo;
    @SerializedName("fuelType")
    @Expose
    private String FuelType;
    @SerializedName("orcalSerialNo")
    @Expose
    private String OrcalSerialNo;
    private boolean isScanned = false;
    private Boolean userApproved = null;
    private int newAssetConditionId=-1;
    private String isSameCondition;
    private String newRoomId="";
    private String newRoomName = "";
    private String newCompanyId = "";
    private String newCompanyName = "";
    private String isInSamePlace="";
    private int newFloorId=-1;
    private int newBuildingId=-1;
    private int newSectorId=-1;
    private int newCentralDepartmentId=-1;
    private int newGeneralDepartmentId=-1;
    private int newDepartmentId=-1;
    private int newSiteId = -1;
    private String inSameSite ="";
    private String isSameSector = "" ;
    private String isSameCentralDepartment="";
    private String isSameGeneralDepartment="";
    private String isSameDepartment = "";
    private String isSameBuilding="";
    private String isSameFloor ="";
    private String isSameRoom="";
    private String isSameCompany="";
    private String trackingOrderId;
    private String userId;
    private String date;

    private String serialNo;

    private boolean isExported = false;

    private String scanStatus;

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

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(String mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getSubCategory2Id() {
        return subCategory2Id;
    }

    public void setSubCategory2Id(String subCategory2Id) {
        this.subCategory2Id = subCategory2Id;
    }

    public String getSubCategory2Name() {
        return subCategory2Name;
    }

    public void setSubCategory2Name(String subCategory2Name) {
        this.subCategory2Name = subCategory2Name;
    }

    public String getSubCategory3Id() {
        return subCategory3Id;
    }

    public void setSubCategory3Id(String subCategory3Id) {
        this.subCategory3Id = subCategory3Id;
    }

    public String getSubCategory3Name() {
        return subCategory3Name;
    }

    public void setSubCategory3Name(String subCategory3Name) {
        this.subCategory3Name = subCategory3Name;
    }

    public String getSubCategory4Id() {
        return subCategory4Id;
    }

    public void setSubCategory4Id(String subCategory4Id) {
        this.subCategory4Id = subCategory4Id;
    }

    public String getSubCategory4Name() {
        return subCategory4Name;
    }

    public void setSubCategory4Name(String subCategory4Name) {
        this.subCategory4Name = subCategory4Name;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSectorID() {
        return sectorID;
    }

    public void setSectorID(String sectorID) {
        this.sectorID = sectorID;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getCentralDepartmentID() {
        return centralDepartmentID;
    }

    public void setCentralDepartmentID(String centralDepartmentID) {
        this.centralDepartmentID = centralDepartmentID;
    }

    public String getCentralDepartmentName() {
        return centralDepartmentName;
    }

    public void setCentralDepartmentName(String centralDepartmentName) {
        this.centralDepartmentName = centralDepartmentName;
    }

    public String getGeneralDepartmentID() {
        return generalDepartmentID;
    }

    public void setGeneralDepartmentID(String generalDepartmentID) {
        this.generalDepartmentID = generalDepartmentID;
    }

    public String getGeneralDepartmentName() {
        return generalDepartmentName;
    }

    public void setGeneralDepartmentName(String generalDepartmentName) {
        this.generalDepartmentName = generalDepartmentName;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAssetConditionName() {
        return assetConditionName;
    }

    public void setAssetConditionName(String assetConditionName) {
        this.assetConditionName = assetConditionName;
    }

    public String getAssetConditionId() {
        return assetConditionId;
    }

    public void setAssetConditionId(String assetConditionId) {
        this.assetConditionId = assetConditionId;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getFileBasse() {
        return fileBasse;
    }

    public void setFileBasse(String fileBasse) {
        this.fileBasse = fileBasse;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCarNo() {
        return CarNo;
    }

    public void setCarNo(String carNo) {
        CarNo = carNo;
    }

    public String getModelOfYear() {
        return ModelOfYear;
    }

    public void setModelOfYear(String modelOfYear) {
        ModelOfYear = modelOfYear;
    }

    public String getBodyNo() {
        return BodyNo;
    }

    public void setBodyNo(String bodyNo) {
        BodyNo = bodyNo;
    }

    public String getMotorNo() {
        return MotorNo;
    }

    public void setMotorNo(String motorNo) {
        MotorNo = motorNo;
    }

    public String getFuelType() {
        return FuelType;
    }

    public void setFuelType(String fuelType) {
        FuelType = fuelType;
    }

    public String getOrcalSerialNo() {
        return OrcalSerialNo;
    }

    public void setOrcalSerialNo(String orcalSerialNo) {
        OrcalSerialNo = orcalSerialNo;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }

    public Boolean getUserApproved() {
        return userApproved;
    }

    public void setUserApproved(Boolean userApproved) {
        this.userApproved = userApproved;
    }

    public int getNewAssetConditionId() {
        return newAssetConditionId;
    }

    public void setNewAssetConditionId(int newAssetConditionId) {
        this.newAssetConditionId = newAssetConditionId;
    }

    public String getIsSameCondition() {
        return isSameCondition;
    }

    public void setIsSameCondition(String isSameCondition) {
        this.isSameCondition = isSameCondition;
    }

    public String getNewRoomId() {
        return newRoomId;
    }

    public void setNewRoomId(String newRoomId) {
        this.newRoomId = newRoomId;
    }

    public String getNewRoomName() {
        return newRoomName;
    }

    public void setNewRoomName(String newRoomName) {
        this.newRoomName = newRoomName;
    }

    public String getNewCompanyId() {
        return newCompanyId;
    }

    public void setNewCompanyId(String newCompanyId) {
        this.newCompanyId = newCompanyId;
    }

    public String getNewCompanyName() {
        return newCompanyName;
    }

    public void setNewCompanyName(String newCompanyName) {
        this.newCompanyName = newCompanyName;
    }

    public String getIsInSamePlace() {
        return isInSamePlace;
    }

    public void setIsInSamePlace(String isInSamePlace) {
        this.isInSamePlace = isInSamePlace;
    }

    public int getNewFloorId() {
        return newFloorId;
    }

    public void setNewFloorId(int newFloorId) {
        this.newFloorId = newFloorId;
    }

    public int getNewBuildingId() {
        return newBuildingId;
    }

    public void setNewBuildingId(int newBuildingId) {
        this.newBuildingId = newBuildingId;
    }

    public int getNewSectorId() {
        return newSectorId;
    }

    public void setNewSectorId(int newSectorId) {
        this.newSectorId = newSectorId;
    }

    public int getNewCentralDepartmentId() {
        return newCentralDepartmentId;
    }

    public void setNewCentralDepartmentId(int newCentralDepartmentId) {
        this.newCentralDepartmentId = newCentralDepartmentId;
    }

    public int getNewGeneralDepartmentId() {
        return newGeneralDepartmentId;
    }

    public void setNewGeneralDepartmentId(int newGeneralDepartmentId) {
        this.newGeneralDepartmentId = newGeneralDepartmentId;
    }

    public int getNewDepartmentId() {
        return newDepartmentId;
    }

    public void setNewDepartmentId(int newDepartmentId) {
        this.newDepartmentId = newDepartmentId;
    }

    public int getNewSiteId() {
        return newSiteId;
    }

    public void setNewSiteId(int newSiteId) {
        this.newSiteId = newSiteId;
    }

    public String getInSameSite() {
        return inSameSite;
    }

    public void setInSameSite(String inSameSite) {
        this.inSameSite = inSameSite;
    }

    public String getIsSameSector() {
        return isSameSector;
    }

    public void setIsSameSector(String isSameSector) {
        this.isSameSector = isSameSector;
    }

    public String getIsSameCentralDepartment() {
        return isSameCentralDepartment;
    }

    public void setIsSameCentralDepartment(String isSameCentralDepartment) {
        this.isSameCentralDepartment = isSameCentralDepartment;
    }

    public String getIsSameGeneralDepartment() {
        return isSameGeneralDepartment;
    }

    public void setIsSameGeneralDepartment(String isSameGeneralDepartment) {
        this.isSameGeneralDepartment = isSameGeneralDepartment;
    }

    public String getIsSameDepartment() {
        return isSameDepartment;
    }

    public void setIsSameDepartment(String isSameDepartment) {
        this.isSameDepartment = isSameDepartment;
    }

    public String getIsSameBuilding() {
        return isSameBuilding;
    }

    public void setIsSameBuilding(String isSameBuilding) {
        this.isSameBuilding = isSameBuilding;
    }

    public String getIsSameFloor() {
        return isSameFloor;
    }

    public void setIsSameFloor(String isSameFloor) {
        this.isSameFloor = isSameFloor;
    }

    public String getIsSameRoom() {
        return isSameRoom;
    }

    public void setIsSameRoom(String isSameRoom) {
        this.isSameRoom = isSameRoom;
    }

    public String getIsSameCompany() {
        return isSameCompany;
    }

    public void setIsSameCompany(String isSameCompany) {
        this.isSameCompany = isSameCompany;
    }

    public String getTrackingOrderId() {
        return trackingOrderId;
    }

    public void setTrackingOrderId(String trackingOrderId) {
        this.trackingOrderId = trackingOrderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public boolean isExported() {
        return isExported;
    }

    public void setExported(boolean exported) {
        isExported = exported;
    }

    public String getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return description;
    }
}
