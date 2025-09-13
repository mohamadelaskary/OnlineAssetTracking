package com.example.OnlineAssetTracking.ViewModel;

import static android.content.ContentValues.TAG;

import static com.example.OnlineAssetTracking.DataBase.Status.ERROR;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.ApiResponse.ApiResponse;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.Model.Data;
import com.example.OnlineAssetTracking.Model.SaveAssetTrackingBody;
import com.example.OnlineAssetTracking.Model.StatusWithMessage;
import com.example.OnlineAssetTracking.MyMethods.NetworkScanner;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;
import com.example.OnlineAssetTracking.MyMethods.Tools;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.Repository.ApiFactory;
import com.example.OnlineAssetTracking.Repository.ApiInterface;
import com.example.OnlineAssetTracking.Repository.NetworkRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragmentViewModel extends AndroidViewModel {
    private AssetTrackingDataBase dataBase;
    private SingleLiveEvent<StatusWithMessage> getScannedAssetsStatus;
    private SingleLiveEvent<ApiResponse> uploadDataResponse;
    private ApiInterface apiInterface;
    private SingleLiveEvent<List<Asset>> getScannedAssets;

    private NetworkRepository networkRepository;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        apiInterface = ApiFactory.createService(ApiInterface.class);
        networkRepository = new NetworkRepository(apiInterface);
        getScannedAssetsStatus = new SingleLiveEvent<>();
        uploadDataResponse = new SingleLiveEvent<>();
        getScannedAssets = new SingleLiveEvent<>();
    }

    @SuppressLint("CheckResult")
    public void getScannedAssets(boolean databaseChecked){
        dataBase.dao().getAllScannedAssets().subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<Asset>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getScannedAssetsStatus.postValue(new StatusWithMessage(Status.LOADING));
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
//                                if (databaseChecked){
//                                    getScannedAssets.postValue(scannedAssets);
//                                    getScannedAssetsStatus.postValue(Status.SUCCESS);
//                                } else {
                                    uploadData(scannedAssets);
//                                }
                            }
                        } else {
                            getScannedAssetsStatus.postValue(new StatusWithMessage(ERROR,"No Scanned assets found"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, " get scanned assets onError: "+e.getMessage());
                        getScannedAssetsStatus.postValue(new StatusWithMessage(ERROR,e.getMessage()));
                    }
                });
    }


    @SuppressLint("CheckResult")
    public void uploadData(List<Asset> scannedAssets){
        SaveAssetTrackingBody body = new SaveAssetTrackingBody();
        body.setTrackingOrderId(Integer.parseInt(scannedAssets.get(0).getTrackingOrderId()));
        List<Data> data = new ArrayList<>();
        for (Asset asset:scannedAssets) {
            Data data1 = new Data(
                    asset.getBarcode(),
                    asset.getNewFloorId(),
                    asset.getFloorId(),
                    asset.getNewRoomId(),
                    asset.getRoomId(),
                    asset.getAssetConditionId(),
                    asset.getNewAssetConditionId(),
                    asset.getNotes(),
                    asset.getUserId(),
                    asset.getDate(),
                    asset.getStatus(),
                    asset.getBuildingId(),
                    asset.getNewBuildingId(),
                    Integer.parseInt(asset.getIsInSamePlace()),
                    Integer.parseInt(asset.getIsSameCondition())
                    );
            data.add(data1);
        }
        body.setData(data);
        networkRepository.uploadData(body).subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getScannedAssetsStatus.postValue(new StatusWithMessage(Status.LOADING));
                    }

                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        getScannedAssetsStatus.postValue(new StatusWithMessage(Status.SUCCESS));
                        uploadDataResponse.postValue(apiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getScannedAssetsStatus.postValue(new StatusWithMessage(ERROR));
                    }
                });
    }

    public MutableLiveData<StatusWithMessage> getGetScannedAssetsStatus() {
        return getScannedAssetsStatus;
    }

    public MutableLiveData<ApiResponse> getuploadDataResponse() {
        return uploadDataResponse;
    }

    public SingleLiveEvent<List<Asset>> getGetScannedAssets() {
        return getScannedAssets;
    }

    private final MutableLiveData<Boolean> isConnected = new MutableLiveData<>();
    private final MutableLiveData<StatusWithMessage> checkConnectivityStatus = new MutableLiveData<>();

    public void checkConnectivity(){
        networkRepository.checkConnectivity().subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        checkConnectivityStatus.postValue(new StatusWithMessage(Status.LOADING));
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        checkConnectivityStatus.postValue(aBoolean?
                                new StatusWithMessage(Status.SUCCESS):
                                new StatusWithMessage(ERROR,getApplication().getString(R.string.device_is_not_connected_or_usb_tethering_is_not_enabled)));
                        isConnected.postValue(aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        checkConnectivityStatus.postValue(new StatusWithMessage(ERROR,getApplication().getString(R.string.device_is_not_connected_or_usb_tethering_is_not_enabled)));
                    }
                });
    }

    public void changeBaseUrl(String newUrl) {
        networkRepository.updateApiService(newUrl);
    }

    public MutableLiveData<Boolean> getIsConnected() {
        return isConnected;
    }

    public MutableLiveData<StatusWithMessage> getCheckConnectivityStatus() {
        return checkConnectivityStatus;
    }

    private final NetworkScanner scanner = new NetworkScanner();

    public LiveData<String> getBaseUrlLiveData() {
        return scanner.getFoundBaseUrlLiveData();
    }

    public void startScan(String port) {
        // 1) Try default fast check first (optional) - omitted here for brevity
        // 2) If not, start scan:
        String healthPath = "api/AssetTracking/CheckConnection";
        int concurrency = 20; // number of parallel requests
        int timeoutMs = 1000; // 1 second per request

        scanner.scanAndSetBaseUrl(port, healthPath, concurrency, timeoutMs);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        scanner.clear();
    }


}
