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
    private Integer roomId;
    @SerializedName("roomName")
    @Expose
    private String roomName;
    @SerializedName("floorId")
    @Expose
    private int floorId;
    @SerializedName("floorName")
    @Expose
    private String floorName;
    @SerializedName("buildingId")
    @Expose
    private Integer buildingId;
    @SerializedName("buildingName")
    @Expose
    private String buildingName;
    @SerializedName("siteId")
    @Expose
    private Integer siteId;
    @SerializedName("siteName")
    @Expose
    private String siteName;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("mainCategoryID")
    @Expose
    private Integer mainCategoryId;
    @SerializedName("mainCategoryName")
    @Expose
    private String mainCategoryName;
    @SerializedName("subCategory2ID")
    @Expose
    private Integer subCategory2Id;
    @SerializedName("subCategory2Name")
    @Expose
    private String subCategory2Name;
    @SerializedName("subCategory3ID")
    @Expose
    private Integer subCategory3Id;
    @SerializedName("subCategory3Name")
    @Expose
    private String subCategory3Name;
    @SerializedName("subCategory4ID")
    @Expose
    private Integer subCategory4Id;
    @SerializedName("subCategory4Name")
    @Expose
    private String subCategory4Name;
    @SerializedName("employeeID")
    @Expose
    private Integer employeeID;
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("sectorID")
    @Expose
    private Integer sectorID;
    @SerializedName("sector")
    @Expose
    private String sectorName;
    @SerializedName("centralDepartmentID")
    @Expose
    private Integer centralDepartmentID;
    @SerializedName("centralDepartment")
    @Expose
    private String centralDepartmentName;
    @SerializedName("genralDepartmentID")
    @Expose
    private Integer generalDepartmentID;
    @SerializedName("genralDepartment")
    @Expose
    private String generalDepartmentName;

    @SerializedName("departmentID")
    @Expose
    private Integer departmentID;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("assetConditionName")
    @Expose
    private String assetConditionName;
    @SerializedName("assetConditionId")
    @Expose
    private int assetConditionId;
    @SerializedName("sectionID")
    @Expose
    private Integer sectionID;
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
    private int newRoomId=-1;
    private String isInSamePlace="";
    private int newFloorId=-1;
    private int newBuildingId=-1;
    private int newSectorId=-1;
    private int newCentralDepartmentId=-1;
    private int newGeneralDepartmentId=-1;
    private int newDepartmentId=-1;
    private int newSiteId = -1;
    private int newCompanyId = -1;
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
    private int userId;
    private String date;
    private int orderId;

    public Asset() {
    }

    public Asset(Integer assetId, String barcode, String assetNumber, String description, Integer roomId, String roomName, int floorId, String floorName, Integer buildingId, String buildingName, Integer siteId, String siteName, Integer companyId, String companyName, Integer mainCategoryId, String mainCategoryName, Integer subCategory2Id, String subCategory2Name, Integer subCategory3Id, String subCategory3Name, Integer subCategory4Id, String subCategory4Name, Integer employeeID, String serialNumber, Integer sectorID, String sectorName, Integer centralDepartmentID, String centralDepartmentName, Integer generalDepartmentID, String generalDepartmentName, Integer departmentID, String department, String carNo, String modelOfYear, String bodyNo, String motorNo, String fuelType, String orcalSerialNo,  int assetConditionId,String assetConditionName) {
        this.assetId = assetId;
        this.barcode = barcode;
        this.assetNumber = assetNumber;
        this.description = description;
        this.roomId = roomId;
        this.roomName = roomName;
        this.floorId = floorId;
        this.floorName = floorName;
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.siteId = siteId;
        this.siteName = siteName;
        this.companyId = companyId;
        this.companyName = companyName;
        this.mainCategoryId = mainCategoryId;
        this.mainCategoryName = mainCategoryName;
        this.subCategory2Id = subCategory2Id;
        this.subCategory2Name = subCategory2Name;
        this.subCategory3Id = subCategory3Id;
        this.subCategory3Name = subCategory3Name;
        this.subCategory4Id = subCategory4Id;
        this.subCategory4Name = subCategory4Name;
        this.employeeID = employeeID;
        this.serialNumber = serialNumber;
        this.sectorID = sectorID;
        this.sectorName = sectorName;
        this.centralDepartmentID = centralDepartmentID;
        this.centralDepartmentName = centralDepartmentName;
        this.generalDepartmentID = generalDepartmentID;
        this.generalDepartmentName = generalDepartmentName;
        this.departmentID = departmentID;
        this.department = department;
        this.assetConditionName = assetConditionName;
        this.assetConditionId = assetConditionId;
        this.section = section;
        CarNo = carNo;
        ModelOfYear = modelOfYear;
        BodyNo = bodyNo;
        MotorNo = motorNo;
        FuelType = fuelType;
        OrcalSerialNo = orcalSerialNo;
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
        if (in.readByte() == 0) {
            roomId = null;
        } else {
            roomId = in.readInt();
        }
        roomName = in.readString();
        floorId = in.readInt();
        floorName = in.readString();
        if (in.readByte() == 0) {
            buildingId = null;
        } else {
            buildingId = in.readInt();
        }
        buildingName = in.readString();
        if (in.readByte() == 0) {
            siteId = null;
        } else {
            siteId = in.readInt();
        }
        siteName = in.readString();
        if (in.readByte() == 0) {
            companyId = null;
        } else {
            companyId = in.readInt();
        }
        companyName = in.readString();
        if (in.readByte() == 0) {
            mainCategoryId = null;
        } else {
            mainCategoryId = in.readInt();
        }
        mainCategoryName = in.readString();
        if (in.readByte() == 0) {
            subCategory2Id = null;
        } else {
            subCategory2Id = in.readInt();
        }
        subCategory2Name = in.readString();
        if (in.readByte() == 0) {
            subCategory3Id = null;
        } else {
            subCategory3Id = in.readInt();
        }
        subCategory3Name = in.readString();
        if (in.readByte() == 0) {
            subCategory4Id = null;
        } else {
            subCategory4Id = in.readInt();
        }
        subCategory4Name = in.readString();
        if (in.readByte() == 0) {
            employeeID = null;
        } else {
            employeeID = in.readInt();
        }
        serialNumber = in.readString();
        if (in.readByte() == 0) {
            sectorID = null;
        } else {
            sectorID = in.readInt();
        }
        sectorName = in.readString();
        if (in.readByte() == 0) {
            centralDepartmentID = null;
        } else {
            centralDepartmentID = in.readInt();
        }
        centralDepartmentName = in.readString();
        if (in.readByte() == 0) {
            generalDepartmentID = null;
        } else {
            generalDepartmentID = in.readInt();
        }
        generalDepartmentName = in.readString();
        if (in.readByte() == 0) {
            departmentID = null;
        } else {
            departmentID = in.readInt();
        }
        department = in.readString();
        assetConditionName = in.readString();
        assetConditionId = in.readInt();
        if (in.readByte() == 0) {
            sectionID = null;
        } else {
            sectionID = in.readInt();
        }
        employee = in.readString();
        fileBasse = in.readString();
        section = in.readString();
        CarNo = in.readString();
        ModelOfYear = in.readString();
        BodyNo = in.readString();
        MotorNo = in.readString();
        FuelType = in.readString();
        OrcalSerialNo = in.readString();
        newAssetConditionId = in.readInt();
        isSameCondition = in.readString();
        newRoomId = in.readInt();
        isInSamePlace = in.readString();
        newFloorId = in.readInt();
        newBuildingId = in.readInt();
        newSectorId = in.readInt();
        newCentralDepartmentId = in.readInt();
        newGeneralDepartmentId = in.readInt();
        newDepartmentId = in.readInt();
        newSiteId = in.readInt();
        newCompanyId = in.readInt();
        inSameSite = in.readString();
        isSameSector = in.readString();
        isSameCentralDepartment = in.readString();
        isSameGeneralDepartment = in.readString();
        isSameDepartment = in.readString();
        isSameBuilding = in.readString();
        isSameFloor = in.readString();
        isSameRoom = in.readString();
        isSameCompany = in.readString();
        trackingOrderId = in.readString();
        userId = in.readInt();
        date = in.readString();
        orderId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (assetId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(assetId);
        }
        dest.writeString(barcode);
        dest.writeString(assetNumber);
        dest.writeString(description);
        if (roomId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(roomId);
        }
        dest.writeString(roomName);
        dest.writeInt(floorId);
        dest.writeString(floorName);
        if (buildingId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(buildingId);
        }
        dest.writeString(buildingName);
        if (siteId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(siteId);
        }
        dest.writeString(siteName);
        if (companyId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(companyId);
        }
        dest.writeString(companyName);
        if (mainCategoryId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mainCategoryId);
        }
        dest.writeString(mainCategoryName);
        if (subCategory2Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(subCategory2Id);
        }
        dest.writeString(subCategory2Name);
        if (subCategory3Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(subCategory3Id);
        }
        dest.writeString(subCategory3Name);
        if (subCategory4Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(subCategory4Id);
        }
        dest.writeString(subCategory4Name);
        if (employeeID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(employeeID);
        }
        dest.writeString(serialNumber);
        if (sectorID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sectorID);
        }
        dest.writeString(sectorName);
        if (centralDepartmentID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(centralDepartmentID);
        }
        dest.writeString(centralDepartmentName);
        if (generalDepartmentID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(generalDepartmentID);
        }
        dest.writeString(generalDepartmentName);
        if (departmentID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(departmentID);
        }
        dest.writeString(department);
        dest.writeString(assetConditionName);
        dest.writeInt(assetConditionId);
        if (sectionID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sectionID);
        }
        dest.writeString(employee);
        dest.writeString(fileBasse);
        dest.writeString(section);
        dest.writeString(CarNo);
        dest.writeString(ModelOfYear);
        dest.writeString(BodyNo);
        dest.writeString(MotorNo);
        dest.writeString(FuelType);
        dest.writeString(OrcalSerialNo);
        dest.writeInt(newAssetConditionId);
        dest.writeString(isSameCondition);
        dest.writeInt(newRoomId);
        dest.writeString(isInSamePlace);
        dest.writeInt(newFloorId);
        dest.writeInt(newBuildingId);
        dest.writeInt(newSectorId);
        dest.writeInt(newCentralDepartmentId);
        dest.writeInt(newGeneralDepartmentId);
        dest.writeInt(newDepartmentId);
        dest.writeInt(newSiteId);
        dest.writeInt(newCompanyId);
        dest.writeString(inSameSite);
        dest.writeString(isSameSector);
        dest.writeString(isSameCentralDepartment);
        dest.writeString(isSameGeneralDepartment);
        dest.writeString(isSameDepartment);
        dest.writeString(isSameBuilding);
        dest.writeString(isSameFloor);
        dest.writeString(isSameRoom);
        dest.writeString(isSameCompany);
        dest.writeString(trackingOrderId);
        dest.writeInt(userId);
        dest.writeString(date);
        dest.writeInt(orderId);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getNewCompanyId() {
        return newCompanyId;
    }

    public void setNewCompanyId(int newCompanyId) {
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

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(Integer mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public Integer getSubCategory2Id() {
        return subCategory2Id;
    }

    public void setSubCategory2Id(Integer subCategory2Id) {
        this.subCategory2Id = subCategory2Id;
    }

    public String getSubCategory2Name() {
        return subCategory2Name;
    }

    public void setSubCategory2Name(String subCategory2Name) {
        this.subCategory2Name = subCategory2Name;
    }

    public Integer getSubCategory3Id() {
        return subCategory3Id;
    }

    public void setSubCategory3Id(Integer subCategory3Id) {
        this.subCategory3Id = subCategory3Id;
    }

    public String getSubCategory3Name() {
        return subCategory3Name;
    }

    public void setSubCategory3Name(String subCategory3Name) {
        this.subCategory3Name = subCategory3Name;
    }

    public Integer getSubCategory4Id() {
        return subCategory4Id;
    }

    public void setSubCategory4Id(Integer subCategory4Id) {
        this.subCategory4Id = subCategory4Id;
    }

    public String getSubCategory4Name() {
        return subCategory4Name;
    }

    public void setSubCategory4Name(String subCategory4Name) {
        this.subCategory4Name = subCategory4Name;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getSectorID() {
        return sectorID;
    }

    public void setSectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public Integer getCentralDepartmentID() {
        return centralDepartmentID;
    }

    public void setCentralDepartmentID(Integer centralDepartmentID) {
        this.centralDepartmentID = centralDepartmentID;
    }

    public String getCentralDepartmentName() {
        return centralDepartmentName;
    }

    public void setCentralDepartmentName(String centralDepartmentName) {
        this.centralDepartmentName = centralDepartmentName;
    }

    public Integer getGeneralDepartmentID() {
        return generalDepartmentID;
    }

    public void setGeneralDepartmentID(Integer generalDepartmentID) {
        this.generalDepartmentID = generalDepartmentID;
    }

    public String getGeneralDepartmentName() {
        return generalDepartmentName;
    }

    public void setGeneralDepartmentName(String generalDepartmentName) {
        this.generalDepartmentName = generalDepartmentName;
    }

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Integer departmentID) {
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

    public int getAssetConditionId() {
        return assetConditionId;
    }

    public void setAssetConditionId(int assetConditionId) {
        this.assetConditionId = assetConditionId;
    }

    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
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


    public String getIsSameCondition() {
        return isSameCondition;
    }

    public void setIsSameCondition(String isSameCondition) {
        this.isSameCondition = isSameCondition;
    }


    public String getIsInSamePlace() {
        return isInSamePlace;
    }

    public void setIsInSamePlace(String isInSamePlace) {
        this.isInSamePlace = isInSamePlace;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String toFileRow() {
        return  getAssetId()+","+
                getBarcode()+","+
                getAssetNumber()+","+
                getRoomId()+","+
                getNewRoomId()+","+
                getIsSameRoom()+","+
                getFloorId()+","+
                getNewFloorId()+","+
                getIsSameFloor()+","+
                getBuildingId()+","+
                getNewBuildingId()+","+
                getIsSameBuilding()+","+
                getSiteId()+","+
                getNewSiteId()+","+
                getInSameSite()+","+
                getSectorID()+","+
                getNewSectorId()+","+
                getIsSameSector()+","+
                getCentralDepartmentID()+","+
                getNewCentralDepartmentId()+","+
                getIsSameCentralDepartment()+","+
                getGeneralDepartmentID()+","+
                getNewGeneralDepartmentId()+","+
                getIsSameGeneralDepartment()+","+
                getDepartmentID()+","+
                getNewDepartmentId()+","+
                getIsSameDepartment()+","+
                getCompanyId()+","+
                getNewCompanyId()+","+
                getIsSameCompany()+","+
                getAssetConditionId()+","+
                getNewAssetConditionId()+","+
                getIsSameCondition()+","+
                getDate()+","+
                getUserId()+","+
                getOrderId()+",";
    }

    public int getNewAssetConditionId() {
        return newAssetConditionId;
    }

    public void setNewAssetConditionId(int newAssetConditionId) {
        this.newAssetConditionId = newAssetConditionId;
    }

    public int getNewRoomId() {
        return newRoomId;
    }

    public void setNewRoomId(int newRoomId) {
        this.newRoomId = newRoomId;
    }

    public int getNewFloorId() {
        return newFloorId;
    }

    public void setNewFloorId(int newFloorId) {
        this.newFloorId = newFloorId;
    }

    @Override
    public String toString() {
        return description;
    }


}
