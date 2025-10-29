package com.example.OnlineAssetTracking.ViewModel;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.ApiResponse.ApiResponse;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.Model.Data;
import com.example.OnlineAssetTracking.Model.SaveAssetTrackingBody;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;
import com.example.OnlineAssetTracking.Repository.ApiFactory;
import com.example.OnlineAssetTracking.Repository.ApiInterface;
import com.example.OnlineAssetTracking.Repository.LocalRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragmentViewModel extends AndroidViewModel {
    private AssetTrackingDataBase dataBase;
    private SingleLiveEvent<Status> getScannedAssetsStatus;
    private SingleLiveEvent<ApiResponse> uploadDataResponse;
    private ApiInterface apiInterface;
    private SingleLiveEvent<List<Asset>> getScannedAssets;

    private LocalRepository repository;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        repository = new LocalRepository(application.getApplicationContext());
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        getScannedAssetsStatus = new SingleLiveEvent<>();
        uploadDataResponse = new SingleLiveEvent<>();
        getScannedAssets = new SingleLiveEvent<>();
    }

    public void getScannedAssets(boolean databaseChecked){
        dataBase.dao().getAllScannedAssets().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<List<Asset>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getScannedAssetsStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(List<Asset> assets) {
                        List<Asset> scannedAssets = new ArrayList<>();
                        if (!assets.isEmpty()) {
                            for (Asset asset : assets) {
                                if (!asset.getIsInSamePlace().isEmpty()) {
                                    scannedAssets.add(asset);
                                }
                            }
                            if (!scannedAssets.isEmpty()) {
                                if (databaseChecked){
                                    getScannedAssets.postValue(scannedAssets);
                                    getScannedAssetsStatus.postValue(Status.SUCCESS);
                                } else {
                                    uploadData(scannedAssets);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getScannedAssetsStatus.postValue(Status.ERROR);
                    }
                });
    }


    private String notes = "";
    int roomId=0,newRoomId=0;
    public void uploadData(List<Asset> scannedAssets){
        SaveAssetTrackingBody body = new SaveAssetTrackingBody();
        body.setTrackingOrderId(Integer.parseInt(scannedAssets.get(0).getTrackingOrderId()));
        List<Data> data = new ArrayList<>();
        for (Asset asset:scannedAssets) {
            Log.d(TAG, "uploadData: "+asset.getNewBuildingId()+"");
            Data data1 = new Data(
                    asset.getBarcode(),
                    notes,
                    asset.getNewRoomId(),
                    asset.getRoomId(),
                    asset.getAssetConditionId(),
                    asset.getNewAssetConditionId(),
                    asset.getFloorId(),
                    asset.getNewFloorId(),
                    asset.getDate(),
                    asset.getUserId(),
                    asset.getBuildingId(),
                    asset.getNewBuildingId(),
                    Integer.parseInt(asset.getIsInSamePlace()),
                    Integer.parseInt(asset.getIsSameCondition()),
                    "",
                    ""
                    );
            data.add(data1);
        }
        body.setData(data);
        apiInterface.saveAssetTracking(body).subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getScannedAssetsStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        getScannedAssetsStatus.postValue(Status.SUCCESS);
                        uploadDataResponse.postValue(apiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getScannedAssetsStatus.postValue(Status.ERROR);
                    }
                });
    }

    public MutableLiveData<Status> getGetScannedAssetsStatus() {
        return getScannedAssetsStatus;
    }

    public MutableLiveData<ApiResponse> getuploadDataResponse() {
        return uploadDataResponse;
    }

    public SingleLiveEvent<List<Asset>> getGetScannedAssets() {
        return getScannedAssets;
    }
}
