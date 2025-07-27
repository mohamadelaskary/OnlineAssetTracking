package com.example.OnlineAssetTracking.Ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.OnlineAssetTracking.Model.CarInfo;
import com.example.OnlineAssetTracking.databinding.CarInfoDialogBinding;

public class CarInfoDialog extends Dialog {

    private CarInfo carInfo;
    public CarInfoDialog(@NonNull Context context,CarInfo carInfo) {
        super(context);
        this.carInfo = carInfo;
    }

    private CarInfoDialogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CarInfoDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        binding.bodyNo.setText(carInfo.getBodyNo());
        binding.carModel.setText(carInfo.getModelOfYear());
        binding.fuelType.setText(carInfo.getFuelType());
        binding.motorNo.setText(carInfo.getMotorNo());
        binding.carNo.setText(carInfo.getCarNo());
        binding.oracleSerialNo.setText(carInfo.getOracleSerialNo());
    }
}
