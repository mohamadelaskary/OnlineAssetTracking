package com.example.OnlineAssetTracking.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.AssetWithUserLocation;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchAssetsViewModel extends AndroidViewModel {
    private AssetTrackingDataBase dataBase;
    private SingleLiveEvent<List<AssetWithUserLocation>> getAllAssetsDataLiveData;
    private SingleLiveEvent<Status> getAllAssetsDataStatus;

    public SearchAssetsViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        getAllAssetsDataLiveData = new SingleLiveEvent<>();
        getAllAssetsDataStatus  = new SingleLiveEvent<>();
    }
    public void getAllAssetsData(){
        dataBase.dao().getAssetWithLocationNames().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<List<AssetWithUserLocation>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getAllAssetsDataStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(List<AssetWithUserLocation> assets) {
                        getAllAssetsDataLiveData.postValue(assets);
                        getAllAssetsDataStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAllAssetsDataStatus.postValue(Status.ERROR);
                    }
                });
    }

    public MutableLiveData<List<AssetWithUserLocation>> getGetAllAssetsDataLiveData() {
        return getAllAssetsDataLiveData;
    }

    public MutableLiveData<Status> getGetAllAssetsDataStatus() {
        return getAllAssetsDataStatus;
    }
}