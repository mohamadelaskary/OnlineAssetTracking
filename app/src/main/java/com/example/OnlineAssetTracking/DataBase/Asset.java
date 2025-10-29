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
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("roomId")
    @Expose
    private Integer roomId;
    @SerializedName("roomCode")
    @Expose
    private String roomCode;
    @SerializedName("newRoomCode")
    @Expose
    private String newRoomCode;
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
    @SerializedName("ac1name")
    @Expose
    private String mainCategoryName;
    @SerializedName("ac2name")
    @Expose
    private String subCategory2Name;
    @SerializedName("assetConditionName")
    @Expose
    private String assetConditionName;
    @SerializedName("assetConditionId")
    @Expose
    private int assetConditionId;

    private int newAssetConditionId=-1;
    private String isSameCondition;
    private int newRoomId=-1;
    private String isInSamePlace="";
    private int newFloorId=-1;
    private int newBuildingId=-1;
    private String isSameBuilding="";
    private String isSameFloor ="";
    private String isSameRoom="";
    private String trackingOrderId;
    private int userId;
    private String date;
    private int orderId;

    public Asset() {
    }

    public Asset(Integer assetId, String barcode, String description, Integer roomId, String roomName, int floorId, String floorName, Integer buildingId, String buildingName, String mainCategoryName, String subCategory2Name, int assetConditionId, String assetConditionName) {
        this.assetId = assetId;
        this.barcode = barcode;
        this.description = description;
        this.roomId = roomId;
        this.roomName = roomName;
        this.floorId = floorId;
        this.floorName = floorName;
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.mainCategoryName = mainCategoryName;
        this.subCategory2Name = subCategory2Name;
        this.assetConditionName = assetConditionName;
        this.assetConditionId = assetConditionId;
    }

    protected Asset(Parcel in) {
        if (in.readByte() == 0) {
            assetId = null;
        } else {
            assetId = in.readInt();
        }
        barcode = in.readString();
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
        mainCategoryName = in.readString();
        subCategory2Name = in.readString();
        assetConditionName = in.readString();
        assetConditionId = in.readInt();
        newAssetConditionId = in.readInt();
        isSameCondition = in.readString();
        newRoomId = in.readInt();
        isInSamePlace = in.readString();
        newFloorId = in.readInt();
        newBuildingId = in.readInt();
        isSameBuilding = in.readString();
        isSameFloor = in.readString();
        isSameRoom = in.readString();
        trackingOrderId = in.readString();
        userId = in.readInt();
        date = in.readString();
        orderId = in.readInt();
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

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getSubCategory2Name() {
        return subCategory2Name;
    }

    public void setSubCategory2Name(String subCategory2Name) {
        this.subCategory2Name = subCategory2Name;
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

    public int getNewRoomId() {
        return newRoomId;
    }

    public void setNewRoomId(int newRoomId) {
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
                getRoomId()+","+
                getNewRoomId()+","+
                getIsSameRoom()+","+
                getFloorId()+","+
                getNewFloorId()+","+
                getIsSameFloor()+","+
                getBuildingId()+","+
                getNewBuildingId()+","+
                getIsSameBuilding()+","+
                getAssetConditionId()+","+
                getNewAssetConditionId()+","+
                getIsSameCondition()+","+
                getDate()+","+
                getUserId()+","+
                getOrderId()+",";
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
        parcel.writeString(description);
        if (roomId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(roomId);
        }
        parcel.writeString(roomName);
        parcel.writeInt(floorId);
        parcel.writeString(floorName);
        if (buildingId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(buildingId);
        }
        parcel.writeString(buildingName);
        parcel.writeString(mainCategoryName);
        parcel.writeString(subCategory2Name);
        parcel.writeString(assetConditionName);
        parcel.writeInt(assetConditionId);
        parcel.writeInt(newAssetConditionId);
        parcel.writeString(isSameCondition);
        parcel.writeInt(newRoomId);
        parcel.writeString(isInSamePlace);
        parcel.writeInt(newFloorId);
        parcel.writeInt(newBuildingId);
        parcel.writeString(isSameBuilding);
        parcel.writeString(isSameFloor);
        parcel.writeString(isSameRoom);
        parcel.writeString(trackingOrderId);
        parcel.writeInt(userId);
        parcel.writeString(date);
        parcel.writeInt(orderId);
    }

    public void setNewRoomCode(String newRoomCode) {
        this.newRoomCode = newRoomCode;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getNewRoomCode() {
        return newRoomCode;
    }
}
