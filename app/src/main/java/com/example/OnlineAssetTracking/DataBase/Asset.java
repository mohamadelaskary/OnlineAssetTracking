package com.example.OnlineAssetTracking.DataBase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Asset implements Parcelable {
    @PrimaryKey
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

    private int newAssetConditionId=-1;
    private String isSameCondition;
    private String newRoomId="";
    private String isInSamePlace="";
    private int newFloorId=-1;
    private int newBuildingId=-1;
    private int newSectorId=-1;
    private int newCentralDepartmentId=-1;
    private int newGeneralDepartmentId=-1;
    private int newDepartmentId=-1;
    private int newSiteId = -1;
    private String newCompanyId = "";
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


    public Asset() {
    }

    public Asset(String barcode, String companyId, String roomId, String description, String scanStatus, String serialNumber, String userId) {
        this.barcode = barcode;
        this.companyId = companyId;
        this.roomId = roomId;
        this.description = description;
        this.scanStatus = scanStatus;
        this.serialNumber = serialNumber;
        this.userId = userId;
    }

    protected Asset(Parcel in) {
        if (in.readByte() == 0) {
            assetId = null;
        } else {
            assetId = in.readInt();
        }
        barcode = in.readString();
        assetNumber = in.readString();
        description = in.readString();
        roomId = in.readString();
        roomName = in.readString();
        floorId = in.readString();
        floorName = in.readString();
        buildingId = in.readString();
        buildingName = in.readString();
        siteId = in.readString();
        siteName = in.readString();
        companyId = in.readString();
        companyName = in.readString();
        mainCategoryId = in.readString();
        mainCategoryName = in.readString();
        subCategory2Id = in.readString();
        subCategory2Name = in.readString();
        subCategory3Id = in.readString();
        subCategory3Name = in.readString();
        subCategory4Id = in.readString();
        subCategory4Name = in.readString();
        employeeID = in.readString();
        serialNumber = in.readString();
        sectorID = in.readString();
        sectorName = in.readString();
        centralDepartmentID = in.readString();
        centralDepartmentName = in.readString();
        generalDepartmentID = in.readString();
        generalDepartmentName = in.readString();
        departmentID = in.readString();
        department = in.readString();
        assetConditionName = in.readString();
        assetConditionId = in.readString();
        sectionID = in.readString();
        employee = in.readString();
        fileBasse = in.readString();
        scanStatus =  in.readString();
        userId = in.readString();
        date = in.readString();
        serialNo = in.readString();
    }

    public static final Creator<Asset> CREATOR = new Creator<Asset>() {
        @Override
        public Asset createFromParcel(Parcel in) {
            return new Asset(in);
        }

        @Override
        public Asset[] newArray(int size) {
            return new Asset[size];
        }
    };

    public String getCarNo() {
        return CarNo==null?"":CarNo;
    }

    public void setCarNo(String carNo) {
        CarNo = carNo;
    }

    public void setModelOfYear(String modelOfYear) {
        ModelOfYear = modelOfYear;
    }

    public void setBodyNo(String bodyNo) {
        BodyNo = bodyNo;
    }

    public void setMotorNo(String motorNo) {
        MotorNo = motorNo;
    }

    public void setFuelType(String fuelType) {
        FuelType = fuelType;
    }

    public void setOrcalSerialNo(String orcalSerialNo) {
        OrcalSerialNo = orcalSerialNo;
    }

    public String getModelOfYear() {
        return ModelOfYear;
    }

    public String getBodyNo() {
        return BodyNo;
    }

    public String getMotorNo() {
        return MotorNo;
    }

    public String getFuelType() {
        return FuelType;
    }

    public String getOrcalSerialNo() {
        return OrcalSerialNo;
    }

    public String getNewCompanyId() {
        return newCompanyId;
    }

    public void setNewCompanyId(String newCompanyId) {
        this.newCompanyId = newCompanyId;
    }

    public String getIsSameCompany() {
        return isSameCompany;
    }

    public void setIsSameCompany(String isSameCompany) {
        this.isSameCompany = isSameCompany;
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

    public Integer getAssetId() {
        return assetId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setMainCategoryId(String mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public void setSubCategory2Id(String subCategory2Id) {
        this.subCategory2Id = subCategory2Id;
    }

    public void setSubCategory2Name(String subCategory2Name) {
        this.subCategory2Name = subCategory2Name;
    }

    public void setSubCategory3Id(String subCategory3Id) {
        this.subCategory3Id = subCategory3Id;
    }

    public void setSubCategory3Name(String subCategory3Name) {
        this.subCategory3Name = subCategory3Name;
    }

    public void setSubCategory4Id(String subCategory4Id) {
        this.subCategory4Id = subCategory4Id;
    }

    public void setSubCategory4Name(String subCategory4Name) {
        this.subCategory4Name = subCategory4Name;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setSectorID(String sectorID) {
        this.sectorID = sectorID;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public void setCentralDepartmentID(String centralDepartmentID) {
        this.centralDepartmentID = centralDepartmentID;
    }

    public void setCentralDepartmentName(String centralDepartmentName) {
        this.centralDepartmentName = centralDepartmentName;
    }

    public void setGeneralDepartmentID(String generalDepartmentID) {
        this.generalDepartmentID = generalDepartmentID;
    }

    public void setGeneralDepartmentName(String generalDepartmentName) {
        this.generalDepartmentName = generalDepartmentName;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setAssetConditionName(String assetConditionName) {
        this.assetConditionName = assetConditionName;
    }

    public void setAssetConditionId(String assetConditionId) {
        this.assetConditionId = assetConditionId;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setFileBasse(String fileBasse) {
        this.fileBasse = fileBasse;
    }

    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setExported(boolean exported) {
        isExported = exported;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getFloorId() {
        return floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getMainCategoryId() {
        return mainCategoryId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public String getSubCategory2Id() {
        return subCategory2Id;
    }

    public String getSubCategory2Name() {
        return subCategory2Name;
    }

    public String getSubCategory3Id() {
        return subCategory3Id;
    }

    public String getSubCategory3Name() {
        return subCategory3Name;
    }

    public String getSubCategory4Id() {
        return subCategory4Id;
    }

    public String getSubCategory4Name() {
        return subCategory4Name;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getSectorID() {
        return sectorID;
    }

    public String getSectorName() {
        return sectorName;
    }

    public String getCentralDepartmentID() {
        return centralDepartmentID;
    }

    public String getCentralDepartmentName() {
        return centralDepartmentName;
    }

    public String getGeneralDepartmentID() {
        return generalDepartmentID;
    }

    public String getGeneralDepartmentName() {
        return generalDepartmentName;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public String getDepartment() {
        return department;
    }

    public String getAssetConditionName() {
        return assetConditionName;
    }

    public String getAssetConditionId() {
        return assetConditionId;
    }

    public String getSectionID() {
        return sectionID;
    }

    public String getEmployee() {
        return employee;
    }

    public String getFileBasse() {
        return fileBasse;
    }

    public String getScanStatus() {
        return scanStatus;
    }

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public boolean isExported() {
        return isExported;
    }

    @Override
    public String toString() {
        return description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (assetId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(assetId);
        }
        parcel.writeString(barcode);
        parcel.writeString(assetNumber);
        parcel.writeString(description);
        parcel.writeString(roomId);
        parcel.writeString(roomName);
        parcel.writeString(floorId);
        parcel.writeString(floorName);
        parcel.writeString(buildingId);
        parcel.writeString(buildingName);
        parcel.writeString(siteId);
        parcel.writeString(siteName);
        parcel.writeString(companyId);
        parcel.writeString(companyName);
        parcel.writeString(mainCategoryId);
        parcel.writeString(mainCategoryName);
        parcel.writeString(subCategory2Id);
        parcel.writeString(subCategory2Name);
        parcel.writeString(subCategory3Id);
        parcel.writeString(subCategory3Name);
        parcel.writeString(subCategory4Id);
        parcel.writeString(subCategory4Name);
        parcel.writeString(employeeID);
        parcel.writeString(serialNumber);
        parcel.writeString(sectorID);
        parcel.writeString(sectorName);
        parcel.writeString(centralDepartmentID);
        parcel.writeString(centralDepartmentName);
        parcel.writeString(generalDepartmentID);
        parcel.writeString(generalDepartmentName);
        parcel.writeString(departmentID);
        parcel.writeString(department);
        parcel.writeString(assetConditionName);
        parcel.writeString(assetConditionId);
        parcel.writeString(sectionID);
        parcel.writeString(employee);
        parcel.writeString(fileBasse);
        parcel.writeString(scanStatus);
        parcel.writeString(userId);
        parcel.writeString(date);
        parcel.writeString(serialNo);
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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

    public String getTrackingOrderId() {
        return trackingOrderId;
    }

    public void setTrackingOrderId(String trackingOrderId) {
        this.trackingOrderId = trackingOrderId;
    }
}
