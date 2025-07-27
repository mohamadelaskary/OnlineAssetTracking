package com.example.OnlineAssetTracking.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditRandomAssetStatusViewModel extends AndroidViewModel {
    private AssetTrackingDataBase dataBase;
    private SingleLiveEvent<UserLocation> locationInfo;
    private SingleLiveEvent<Asset> assetInfo;
    private SingleLiveEvent<Status> locationInfoStatus;
    private SingleLiveEvent<Status> assetInfoStatus;
    private SingleLiveEvent<List<AssetCondition>> assetCoditionsMutableLiveData;
    private SingleLiveEvent<Status> assetConditionsStatus;
    private SingleLiveEvent<Status> saveAssetStatus;

    public EditRandomAssetStatusViewModel(@NonNull Application application) {
        super(application);
         dataBase = DataBase.getInstance(application.getApplicationContext());
         locationInfo = new SingleLiveEvent<>();
         assetInfo = new SingleLiveEvent<>();
         locationInfoStatus = new SingleLiveEvent<>();
         assetInfoStatus = new SingleLiveEvent<>();
         assetConditionsStatus = new SingleLiveEvent<>();
         assetCoditionsMutableLiveData = new SingleLiveEvent<>();
         saveAssetStatus = new SingleLiveEvent<>();
    }

    public void getLocationInfo(String roomCode){
        dataBase.dao().getRoomData(
                roomCode
        ).subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<UserLocation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        locationInfoStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(UserLocation userLocation) {
                        locationInfoStatus.postValue(Status.SUCCESS);
                        locationInfo.postValue(userLocation);
                    }

                    @Override
                    public void onError(Throwable e) {
                        locationInfoStatus.postValue(Status.ERROR);
                    }
                });
    }

    public MutableLiveData<UserLocation> getLocationInfo() {
        return locationInfo;
    }

    public MutableLiveData<Status> getLocationInfoStatus() {
        return locationInfoStatus;
    }

    public void getAssetInfo(String assetCode) {
        dataBase.dao().getAssetData(assetCode).subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<Asset>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        assetInfoStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(Asset asset) {
                        assetInfoStatus.postValue(Status.SUCCESS);
                        assetInfo.postValue(asset);
                    }

                    @Override
                    public void onError(Throwable e) {
                        assetInfoStatus.postValue(Status.ERROR);
                    }
                });
    }

    public MutableLiveData<Asset> getAssetInfo() {
        return assetInfo;
    }

    public MutableLiveData<Status> getAssetInfoStatus() {
        return assetInfoStatus;
    }

    public void getAssetConditions() {
        dataBase.dao().getAllAssetConditions().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<List<AssetCondition>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        assetConditionsStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(List<AssetCondition> assetConditions) {
                        assetConditionsStatus.postValue(Status.SUCCESS);
                        assetCoditionsMutableLiveData.postValue(assetConditions);
                    }

                    @Override
                    public void onError(Throwable e) {
                        assetConditionsStatus.postValue(Status.ERROR);
                    }
                });
    }

    public MutableLiveData<List<AssetCondition>> getAssetCoditionsMutableLiveData() {
        return assetCoditionsMutableLiveData;
    }

    public MutableLiveData<Status> getAssetConditionsStatus() {
        return assetConditionsStatus;
    }

    public void saveScannedAsset(Asset asset){
        dataBase.dao().updateLocation(asset).subscribeOn(Schedulers.io())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        saveAssetStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onComplete() {
                        saveAssetStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("saveAsset",e.getMessage());
                        saveAssetStatus.postValue(Status.ERROR);
                    }
                });
    }

    public MutableLiveData<Status> getSaveAssetStatus() {
        return saveAssetStatus;
    }
}