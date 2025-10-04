package com.example.OnlineAssetTracking.Ui.ExportDataByLocationScreen;

import static com.example.OnlineAssetTracking.DataBase.Status.ERROR;
import static com.example.OnlineAssetTracking.DataBase.Status.LOADING;
import static com.example.OnlineAssetTracking.DataBase.Status.SUCCESS;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.todayDate;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.StatusWithMessage;
import com.example.OnlineAssetTracking.MyMethods.ReadWriteExcelSheet;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;
import com.example.OnlineAssetTracking.R;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExportDataByLocationViewModel extends AndroidViewModel {
    private AssetTrackingDataBase dataBase;


    public SingleLiveEvent<List<UserLocation>> getAllUserLocation() {
        return allUserLocation;
    }

    public SingleLiveEvent<Status> getAllLocationStatus() {
        return allLocationStatus;
    }
    private final SingleLiveEvent<List<UserLocation>> allUserLocation = new SingleLiveEvent<>();
    private final SingleLiveEvent<Status> allLocationStatus = new SingleLiveEvent<>();


    public ExportDataByLocationViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
    }
    public void getAllLocations(){
        dataBase.dao().getUserLocations().subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<UserLocation>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        allLocationStatus.postValue(LOADING);
                    }

                    @Override
                    public void onSuccess(List<UserLocation> userLocations) {
                        allLocationStatus.postValue(Status.SUCCESS);
                        allUserLocation.postValue(userLocations);
                    }

                    @Override
                    public void onError(Throwable e) {
                        allLocationStatus.postValue(Status.ERROR);
                        Log.e("ExportDataByLocationViewModel", "onError: ", e);
                    }
                });
    }
    private final SingleLiveEvent<List<Asset>> getAssets = new SingleLiveEvent<>();
    private final SingleLiveEvent<Status> getAssetsStatus = new SingleLiveEvent<>();
    public void getAssetsByLocation(String locationId){
        dataBase.dao().getAssetsByLocation(locationId)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<Asset>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getAssetsStatus.postValue(LOADING);
                    }

                    @Override
                    public void onSuccess(List<Asset> assets) {
                        getAssetsStatus.postValue(Status.SUCCESS);
                        getAssets.postValue(assets);
                        Log.d("ExportDataByLocationViewModel", "onSuccess: "+assets);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAssetsStatus.postValue(LOADING);
                    }
                });
    }

    public SingleLiveEvent<List<Asset>> getGetAssets() {
        return getAssets;
    }

    public SingleLiveEvent<Status> getGetAssetsStatus() {
        return getAssetsStatus;
    }
    private final SingleLiveEvent<StatusWithMessage> createFileStatus = new SingleLiveEvent<>();
    public String filePath = "";
    public void createExcelSheet(String[][] fileData,String fileName){
        Single.fromCallable(()-> ReadWriteExcelSheet.createEncryptedExcel(getApplication(),fileName,fileData,"222",todayDate())).subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Status>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        createFileStatus.postValue(new StatusWithMessage(LOADING));
                    }

                    @Override
                    public void onSuccess(Status t) {
                        if (t.equals(SUCCESS)) {
                            filePath = "/storage/emulated/0/Download/Asset tracking/"+fileName;
                            Log.d("ExportDataByLocationViewModel", "onSuccess: "+filePath);
                            createFileStatus.postValue(new StatusWithMessage(t, getApplication().getString(R.string.file_created_successfully)));
                        } else
                            createFileStatus.postValue(new StatusWithMessage(t,getApplication().getString(R.string.error_while_saving_file)));
                    }

                    @Override
                    public void onError(Throwable e) {
                        createFileStatus.postValue(new StatusWithMessage(ERROR,getApplication().getString(R.string.error_while_saving_file)));
                        Log.e("ExportDataByLocationViewModel", "onError: ", e);

                    }
                });
    }

    public SingleLiveEvent<StatusWithMessage> getCreateFileStatus() {
        return createFileStatus;
    }

    public void setAssetsAsExported(String roomId) {
        dataBase.dao().updateAllStatus(roomId).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}