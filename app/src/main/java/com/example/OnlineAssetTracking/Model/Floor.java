package com.example.OnlineAssetTracking.Model;

public class Floor {
    private int floorId;
    private String floorCode;
    private String floorName;

    public Floor(int floorId, String floorCode, String floorName) {
        this.floorId = floorId;
        this.floorName = floorName;
        this.floorCode = floorCode;
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

    @Override
    public String toString() {
        return  floorName + " | "+floorCode ;
    }
}
