package com.example.OnlineAssetTracking.DataBase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AssetCondition implements Parcelable {
    @PrimaryKey
    private int assetConditionId;
    private String assetConditionName;

    public AssetCondition(int assetConditionId, String assetConditionName) {
        this.assetConditionId = assetConditionId;
        this.assetConditionName = assetConditionName;
    }


    protected AssetCondition(Parcel in) {
        assetConditionId = in.readInt();
        assetConditionName = in.readString();
    }

    public static final Creator<AssetCondition> CREATOR = new Creator<AssetCondition>() {
        @Override
        public AssetCondition createFromParcel(Parcel in) {
            return new AssetCondition(in);
        }

        @Override
        public AssetCondition[] newArray(int size) {
            return new AssetCondition[size];
        }
    };

    public int getAssetConditionId() {
        return assetConditionId;
    }

    public void setAssetConditionId(int assetConditionId) {
        this.assetConditionId = assetConditionId;
    }

    public String getAssetConditionName() {
        return assetConditionName;
    }

    public void setAssetConditionName(String assetConditionName) {
        this.assetConditionName = assetConditionName;
    }

    @NonNull
    @Override
    public String toString() {
        return assetConditionName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}


