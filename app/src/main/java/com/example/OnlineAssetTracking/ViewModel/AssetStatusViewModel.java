package com.example.OnlineAssetTracking.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AssetStatusViewModel extends AndroidViewModel {
    private SingleLiveEvent<List<AssetCondition>> assetConditionsData;
    private SingleLiveEvent<Status> gettingAssetConditionStatus;
    private AssetTrackingDataBase dataBase;
    public AssetStatusViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        assetConditionsData = new SingleLiveEvent<>();
        gettingAssetConditionStatus     = new SingleLiveEvent<>();
    }
    public void getAssetConditionsFromDatabase(){
        dataBase.dao().getAllAssetConditions().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<List<AssetCondition>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        gettingAssetConditionStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(List<AssetCondition> assetConditions) {
                        assetConditionsData.postValue(assetConditions);
                        gettingAssetConditionStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        gettingAssetConditionStatus.postValue(Status.ERROR);
                    }
                });
    }

    public MutableLiveData<List<AssetCondition>> getAssetConditionsData() {
        return assetConditionsData;
    }

    public MutableLiveData<Status> getGettingAssetConditionStatus() {
        return gettingAssetConditionStatus;
    }
}