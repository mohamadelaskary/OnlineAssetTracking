package com.example.OnlineAssetTracking.Model;

import com.example.OnlineAssetTracking.DataBase.Status;

public class StatusWithMessage {
    private Status status;
    private String statusMessage = "";

    public StatusWithMessage(Status status, String statusMessage) {
        this.status = status;
        this.statusMessage = statusMessage;
    }

    public StatusWithMessage(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
