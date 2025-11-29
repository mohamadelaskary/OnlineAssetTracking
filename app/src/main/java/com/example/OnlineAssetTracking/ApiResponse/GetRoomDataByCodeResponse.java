package com.example.OnlineAssetTracking.ApiResponse;

import com.example.OnlineAssetTracking.Model.ResponseStatus;
import com.example.OnlineAssetTracking.Model.RoomData;

import java.util.List;

public class GetRoomDataByCodeResponse {
    private ResponseStatus responseStatus;
    private List<RoomData> roomsDataParam;

    public GetRoomDataByCodeResponse() {
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<RoomData> getRoomsDataParam() {
        return roomsDataParam;
    }

    public void setRoomsDataParam(List<RoomData> roomsDataParam) {
        this.roomsDataParam = roomsDataParam;
    }
}


