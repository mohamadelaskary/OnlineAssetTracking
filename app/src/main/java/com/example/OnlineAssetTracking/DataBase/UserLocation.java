package com.example.OnlineAssetTracking.DataBase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class UserLocation implements Parcelable {
    @PrimaryKey
    @SerializedName("userLocationsOrganizationID")
    @Expose
    private Integer userLocationsOrganizationID;
    @SerializedName("userID")
    @Expose
    private Integer userID;
    @SerializedName("userName")
    @Expose
    private String userName;
//    private Integer branchID;
//    private String branchName;
    @SerializedName("siteID")
    @Expose
    private Integer siteId;
    @SerializedName("siteName")
    @Expose
    private String siteName;
    @SerializedName("buildingID")
    @Expose
    private Integer buildingId;
    @SerializedName("buildingName")
    @Expose
    private String buildingName;
    @SerializedName("floorID")
    @Expose
    private int floorId;

    @SerializedName("floorName")
    @Expose
    private String floorName;
    @SerializedName("roomID")
    @Expose
    private String roomId;
    @SerializedName("roomName")
    @Expose
    private String roomName;
    @SerializedName("roomCode")
    @Expose
    private String roomCode;
    @SerializedName("sectorId")
    @Expose
    private Integer sectorId;
    @SerializedName("sector")
    @Expose
    private String sectorName;
    @SerializedName("centraDepId")
    @Expose
    private Integer centralDepartmentId;
    @SerializedName("centralDep")
    @Expose
    private String centralDepartmentName;
    @SerializedName("genDepartmenID")
    @Expose
    private Integer generalDepartmentId;
    @SerializedName("genDepartmenName")
    @Expose
    private String generalDepartmentName;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("debartment")
    @Expose
    private String departmentName;
    @SerializedName("trackingOrderId")
    @Expose
    private Integer trackingOrderId;
    @SerializedName("orderNumber")
    @Expose
    private Integer orderNumber;
    @SerializedName("companyID")
    @Expose
    private String companyId;
    @SerializedName("companyName")
    @Expose
    private String companyName;

    public UserLocation(String companyId,String roomId, String roomName,  String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public Integer getUserLocationsOrganizationID() {
        return userLocationsOrganizationID;
    }

    public void setUserLocationsOrganizationID(Integer userLocationsOrganizationID) {
        this.userLocationsOrganizationID = userLocationsOrganizationID;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    protected UserLocation(Parcel in) {
        if (in.readByte() == 0) {
            userLocationsOrganizationID = null;
        } else {
            userLocationsOrganizationID = in.readInt();
        }
        roomId = in.readString();
        roomName = in.readString();
        companyId = in.readString();
        companyName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (userLocationsOrganizationID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userLocationsOrganizationID);
        }
        dest.writeString(roomId);
        dest.writeString(roomName);
        dest.writeString(companyId);
        dest.writeString(companyName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserLocation> CREATOR = new Creator<UserLocation>() {
        @Override
        public UserLocation createFromParcel(Parcel in) {
            return new UserLocation(in);
        }

        @Override
        public UserLocation[] newArray(int size) {
            return new UserLocation[size];
        }
    };

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public Integer getSectorId() {
        return sectorId;
    }

    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public Integer getCentralDepartmentId() {
        return centralDepartmentId;
    }

    public void setCentralDepartmentId(Integer centralDepartmentId) {
        this.centralDepartmentId = centralDepartmentId;
    }

    public String getCentralDepartmentName() {
        return centralDepartmentName;
    }

    public void setCentralDepartmentName(String centralDepartmentName) {
        this.centralDepartmentName = centralDepartmentName;
    }

    public Integer getGeneralDepartmentId() {
        return generalDepartmentId;
    }

    public void setGeneralDepartmentId(Integer generalDepartmentId) {
        this.generalDepartmentId = generalDepartmentId;
    }

    public String getGeneralDepartmentName() {
        return generalDepartmentName;
    }

    public void setGeneralDepartmentName(String generalDepartmentName) {
        this.generalDepartmentName = generalDepartmentName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getTrackingOrderId() {
        return trackingOrderId;
    }

    public void setTrackingOrderId(Integer trackingOrderId) {
        this.trackingOrderId = trackingOrderId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
