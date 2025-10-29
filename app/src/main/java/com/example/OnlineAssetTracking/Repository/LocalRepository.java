package com.example.OnlineAssetTracking.Repository;

import android.content.Context;

import com.example.OnlineAssetTracking.ApiResponse.Success;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.User;
import com.example.OnlineAssetTracking.DataBase.UserLocation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class LocalRepository {
    private AssetTrackingDataBase dataBase;

    public LocalRepository(Context context){
        dataBase = DataBase.getInstance(context);
    }
    public Single<User> getUserInformation(String username,String password){
        return dataBase.dao().getUserInformation(username);
    }

    public Single<List<UserLocation>> getAllUserLocation(int userId, int orderId){
        return dataBase.dao().getUserLocations(userId,orderId);
    }

    public Single<List<AssetCondition>> getAssetConditions(){
        return dataBase.dao().getAllAssetConditions();
    }
    public Single<Asset> getAssetData(String assetCode){
        return dataBase.dao().getAssetData(assetCode);
    }
    public Completable saveScannedAsset(Asset asset) {
        return dataBase.dao().updateLocation(asset);
    }
    public Observable<List<Asset>> getAssetListInFloor(int floorId) {
        return dataBase.dao().getAllAssetsInFloor(
                floorId
        );
    }
    public Single<List<Asset>> getAssetListInRoom(int roomId) {
        return dataBase.dao().getAllAssetsInRoom(
                roomId
        );
    }

    public Single<List<Asset>> getAllAssets(){
        return dataBase.dao().getAllAssetsData();
    }
}
