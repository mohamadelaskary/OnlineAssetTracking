package com.example.OnlineAssetTracking.Model;

public class Floor {
    private int floorId;
    private String floorName;

    public Floor(int floorId, String floorName) {
        this.floorId = floorId;
        this.floorName = floorName;
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
        return  floorName ;
    }
}
