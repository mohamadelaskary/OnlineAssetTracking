package com.example.OnlineAssetTracking.Model;

import androidx.annotation.NonNull;

public class Room {
    private int roomId;
    private String roomCode;
    private String roomName;

    public Room(int roomId, String roomCode,String roomName) {
        this.roomName = roomName;
        this.roomCode = roomCode;
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
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
        return roomCode;
    }
}
