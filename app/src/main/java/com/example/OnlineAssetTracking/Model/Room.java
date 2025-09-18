package com.example.OnlineAssetTracking.Model;

import androidx.annotation.NonNull;

public class Room {
    private String roomId;
    private String roomName;

    public Room(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
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

    @NonNull
    @Override
    public String toString() {
        return roomName;
    }
}
