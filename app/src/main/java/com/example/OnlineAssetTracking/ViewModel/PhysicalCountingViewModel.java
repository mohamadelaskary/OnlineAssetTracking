package com.example.OnlineAssetTracking.ViewModel;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.AssetWithUserLocation;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.User;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PhysicalCountingViewModel extends AndroidViewModel {
    private AssetTrackingDataBase dataBase;
    private SingleLiveEvent<AssetWithUserLocation> assetDataLiveData;
    private SingleLiveEvent<Status> gettingAssetDataStatus;
    private SingleLiveEvent<Status> saveAssetStatus;
    private SingleLiveEvent<List<AssetCondition>> gettingAssetConditions;
    private SingleLiveEvent<Status> gettingAssetConditionStatus;
    private SingleLiveEvent<List<User>> gettingUsersList = new SingleLiveEvent<>();
    private SingleLiveEvent<Status> gettingUsersListStatus = new SingleLiveEvent<>();

    public PhysicalCountingViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        assetDataLiveData = new SingleLiveEvent<>();
        gettingAssetDataStatus = new SingleLiveEvent<>();
        gettingAssetConditions = new SingleLiveEvent<>();
        saveAssetStatus = new SingleLiveEvent<>();
        gettingAssetConditionStatus = new SingleLiveEvent<>();
    }

    public void getUsersList(){
        dataBase.dao().getUsersList()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        gettingUsersListStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(List<User> users) {
                        gettingUsersListStatus.postValue(Status.SUCCESS);
                        gettingUsersList.postValue(users);
                    }

                    @Override
                    public void onError(Throwable e) {
                        gettingUsersListStatus.postValue(Status.ERROR);
                    }
                });
    }

    public void getAssetData(String assetCode){
        Log.d(TAG, "getAssetData:"+assetCode+"Code");
        dataBase.dao().getAssetWithLocationNames(assetCode)
                .subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<AssetWithUserLocation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        gettingAssetDataStatus.postValue(Status.LOADING);
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onSuccess(AssetWithUserLocation asset) {
                        assetDataLiveData.postValue(asset);
                        gettingAssetDataStatus.postValue(Status.SUCCESS);
                        Log.d(TAG, "onSuccess: "+asset.getDescription());
                    }

                    @Override
                    public void onError(Throwable e) {
                        gettingAssetDataStatus.postValue(Status.ERROR);
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    public void getAssetConditions(){
        dataBase.dao().getAllAssetConditions().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<List<AssetCondition>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        gettingAssetConditionStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(List<AssetCondition> assetConditions) {
                        gettingAssetConditions.postValue(assetConditions);
                        gettingAssetConditionStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        gettingAssetConditionStatus.postValue(Status.ERROR);
                    }
                });
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

    public MutableLiveData<AssetWithUserLocation> getAssetDataLiveData() {
        return assetDataLiveData;
    }

    public MutableLiveData<Status> getGettingAssetDataStatus() {
        return gettingAssetDataStatus;
    }

    public MutableLiveData<List<AssetCondition>> getGettingAssetConditions() {
        return gettingAssetConditions;
    }

    public MutableLiveData<Status> getSaveAssetStatus() {
        return saveAssetStatus;
    }

    public SingleLiveEvent<List<User>> getGettingUsersList() {
        return gettingUsersList;
    }

    public SingleLiveEvent<Status> getGettingUsersListStatus() {
        return gettingUsersListStatus;
    }
}