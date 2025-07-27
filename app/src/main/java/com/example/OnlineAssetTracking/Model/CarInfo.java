package com.example.OnlineAssetTracking.Model;

public class CarInfo {
    private String carNo;
    private String modelOfYear;
    private String motorNo;
    private String bodyNo;
    private String fuelType;
    private String oracleSerialNo;

    public CarInfo(String carNo, String modelOfYear, String motorNo, String bodyNo, String fuelType, String oracleSerialNo) {
        this.carNo = carNo;
        this.modelOfYear = modelOfYear;
        this.motorNo = motorNo;
        this.bodyNo = bodyNo;
        this.fuelType = fuelType;
        this.oracleSerialNo = oracleSerialNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public String getModelOfYear() {
        return modelOfYear;
    }

    public String getMotorNo() {
        return motorNo;
    }

    public String getBodyNo() {
        return bodyNo;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getOracleSerialNo() {
        return oracleSerialNo;
    }
}
