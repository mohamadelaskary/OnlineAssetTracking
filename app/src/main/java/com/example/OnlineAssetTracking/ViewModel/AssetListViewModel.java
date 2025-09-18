package com.example.OnlineAssetTracking.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AssetListViewModel extends AndroidViewModel {
    private AssetTrackingDataBase dataBase;
    private SingleLiveEvent<List<Asset>> gettingAssetListLiveData;
    private SingleLiveEvent<Status> status;
    private SingleLiveEvent<List<Asset>> gettingScannedList;
    public AssetListViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        gettingAssetListLiveData = new SingleLiveEvent<>();
        status = new SingleLiveEvent<>();
    }

    public void getAssetListInFloor(int floorId){
        dataBase.dao().getAllAssetsInFloor(
                floorId
                ).subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<List<Asset>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        status.postValue(Status.LOADING);
                    }

                    @Override
                    public void onNext(List<Asset> assets) {
                        gettingAssetListLiveData.postValue(assets);
                        status.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        status.postValue(Status.ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getAssetListInRoom(String roomId){
        dataBase.dao().getAllAssetsInRoom(
                        roomId
                ).subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<List<Asset>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        status.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(List<Asset> assets) {
                        gettingAssetListLiveData.postValue(assets);
                        status.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        status.postValue(Status.ERROR);
                    }
                });
    }

    public void getScannedList(){
//       dataBase.dao().getAllScannedAssets().subscribeOn(Schedulers.io())
//               .subscribeWith(new SingleObserver<List<Asset>>() {
//                   @Override
//                   public void onSubscribe(Disposable d) {
//                       status.postValue(Status.LOADING);
//                   }
//
//                   @Override
//                   public void onSuccess(List<Asset> scannedAssets) {
//                        gettingScannedList.postValue(scannedAssets);
//                        status.postValue(Status.SUCCESS);
//                   }
//
//                   @Override
//                   public void onError(Throwable e) {
//                        status.postValue(Status.ERROR);
//                   }
//               });
    }
    public MutableLiveData<List<Asset>> getGettingAssetListLiveData() {
        return gettingAssetListLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}