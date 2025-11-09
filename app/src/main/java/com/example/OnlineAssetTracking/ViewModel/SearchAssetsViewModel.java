package com.example.OnlineAssetTracking.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.ApiResponse.GetAssetsByDescriptionResponse;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;
import com.example.OnlineAssetTracking.Repository.NetworkRepository;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchAssetsViewModel extends AndroidViewModel {
    private AssetTrackingDataBase dataBase;
    private SingleLiveEvent<List<Asset>> getAllAssetsDataLiveData;
    private SingleLiveEvent<Status> getAllAssetsDataStatus;

    private NetworkRepository repository;

    public SearchAssetsViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        getAllAssetsDataLiveData = new SingleLiveEvent<>();
        getAllAssetsDataStatus  = new SingleLiveEvent<>();
        repository = new NetworkRepository();
    }
    public void getAllAssetsData(String assetDescription){
        repository.getAssetsByDescription(assetDescription).subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<GetAssetsByDescriptionResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getAllAssetsDataStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(GetAssetsByDescriptionResponse response) {
                        getAllAssetsDataLiveData.postValue(response.getAssetsDataParam());
                        getAllAssetsDataStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAllAssetsDataStatus.postValue(Status.ERROR);
                    }
                });
    }

    public MutableLiveData<List<Asset>> getGetAllAssetsDataLiveData() {
        return getAllAssetsDataLiveData;
    }

    public MutableLiveData<Status> getGetAllAssetsDataStatus() {
        return getAllAssetsDataStatus;
    }
}