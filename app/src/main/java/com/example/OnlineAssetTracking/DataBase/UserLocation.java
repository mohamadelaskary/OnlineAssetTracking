package com.example.OnlineAssetTracking.DataBase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserLocation implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @SerializedName("userLocationsOrganizationID")
    @Expose
    private Integer userLocationsOrganizationID;
    @SerializedName("userID")
    @Expose
    private String userID;
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

    public String getFloorCode() {
        return floorCode;
    }

    @SerializedName("floorCode")
    @Expose
    @Ignore
    private String floorCode;
    @SerializedName("roomID")
    @Expose
    private int roomId;
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
    private String orderNumber;
    @SerializedName("companyID")
    @Expose
    private Integer companyId;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @Ignore
    @SerializedName("assetGroupsId")
    @Expose
    private List<Integer> assetGroupsId = new ArrayList();

    public UserLocation(Integer userLocationsOrganizationID, String userID, String userName, Integer companyId, String companyName, Integer siteId, String siteName, Integer buildingId, String buildingName, int floorId, String floorName, int roomId, String roomName, String roomCode, Integer sectorId, String sectorName, Integer centralDepartmentId, String centralDepartmentName, Integer generalDepartmentId, String generalDepartmentName, Integer departmentId, String departmentName, Integer trackingOrderId, String orderNumber) {
        this.userLocationsOrganizationID = userLocationsOrganizationID;
        this.userID = userID;
        this.userName = userName;
        this.companyId = companyId;
        this.companyName = companyName;
        this.siteId = siteId;
        this.siteName = siteName;
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.floorId = floorId;
        this.floorName = floorName;
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomCode = roomCode;
        this.sectorId = sectorId;
        this.sectorName = sectorName;
        this.centralDepartmentId = centralDepartmentId;
        this.centralDepartmentName = centralDepartmentName;
        this.generalDepartmentId = generalDepartmentId;
        this.generalDepartmentName = generalDepartmentName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.trackingOrderId = trackingOrderId;
        this.orderNumber = orderNumber;
    }
    @Ignore
    protected UserLocation(Parcel in) {
        if (in.readByte() == 0) {
            userLocationsOrganizationID = null;
        } else {
            userLocationsOrganizationID = in.readInt();
        }
        if (in.readByte() == 0) {
            userID = null;
        } else {
            userID = in.readString();
        }
        userName = in.readString();
        if (in.readByte() == 0) {
            siteId = null;
        } else {
            siteId = in.readInt();
        }
        siteName = in.readString();
        if (in.readByte() == 0) {
            buildingId = null;
        } else {
            buildingId = in.readInt();
        }
        buildingName = in.readString();
        floorId = in.readInt();
        floorName = in.readString();
        roomId = in.readInt();
        roomName = in.readString();
        roomCode = in.readString();
        if (in.readByte() == 0) {
            sectorId = null;
        } else {
            sectorId = in.readInt();
        }
        sectorName = in.readString();
        if (in.readByte() == 0) {
            centralDepartmentId = null;
        } else {
            centralDepartmentId = in.readInt();
        }
        centralDepartmentName = in.readString();
        if (in.readByte() == 0) {
            generalDepartmentId = null;
        } else {
            generalDepartmentId = in.readInt();
        }
        generalDepartmentName = in.readString();
        if (in.readByte() == 0) {
            departmentId = null;
        } else {
            departmentId = in.readInt();
        }
        departmentName = in.readString();
        if (in.readByte() == 0) {
            trackingOrderId = null;
        } else {
            trackingOrderId = in.readInt();
        }
        if (in.readByte() == 0) {
            orderNumber = null;
        } else {
            orderNumber = in.readString();
        }
        if (in.readByte() == 0) {
            companyId = null;
        } else {
            companyId = in.readInt();
        }
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
        if (userID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(userID);
        }
        dest.writeString(userName);
        if (siteId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(siteId);
        }
        dest.writeString(siteName);
        if (buildingId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(buildingId);
        }
        dest.writeString(buildingName);
        dest.writeInt(floorId);
        dest.writeString(floorName);
        dest.writeInt(roomId);
        dest.writeString(roomName);
        dest.writeString(roomCode);
        if (sectorId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sectorId);
        }
        dest.writeString(sectorName);
        if (centralDepartmentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(centralDepartmentId);
        }
        dest.writeString(centralDepartmentName);
        if (generalDepartmentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(generalDepartmentId);
        }
        dest.writeString(generalDepartmentName);
        if (departmentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(departmentId);
        }
        dest.writeString(departmentName);
        if (trackingOrderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(trackingOrderId);
        }
        if (orderNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(orderNumber);
        }
        if (companyId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(companyId);
        }
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

    public Integer getUserLocationsOrganizationID() {
        return userLocationsOrganizationID;
    }

    public void setUserLocationsOrganizationID(Integer userLocationsOrganizationID) {
        this.userLocationsOrganizationID = userLocationsOrganizationID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
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


    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }



    public String getRoomName() {
        return roomName;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @NonNull
    @Override
    public String toString() {
        return trackingOrderId.toString();
    }

    public List<Integer> getAssetGroupsId() {
        return assetGroupsId;
    }
}
