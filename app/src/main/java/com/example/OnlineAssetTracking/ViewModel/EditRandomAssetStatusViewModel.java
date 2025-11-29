package com.example.OnlineAssetTracking.ViewModel;

import static com.example.OnlineAssetTracking.Ui.MainActivity.DEVICE_SERIAL_NO;
import static com.example.OnlineAssetTracking.Ui.MainActivity.ORDER_ID;
import static com.example.OnlineAssetTracking.Ui.MainActivity.USER_ID;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.ApiResponse.ApiResponse;
import com.example.OnlineAssetTracking.ApiResponse.GetAssetDataByAssetCodeResponse;
import com.example.OnlineAssetTracking.ApiResponse.GetRoomDataByCodeResponse;
import com.example.OnlineAssetTracking.ApiResponse.UpdateAssetsConditionResponse;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.ApiResponseAssetConditions;
import com.example.OnlineAssetTracking.Model.Data;
import com.example.OnlineAssetTracking.Model.RoomData;
import com.example.OnlineAssetTracking.Model.SaveAssetTrackingBody;
import com.example.OnlineAssetTracking.Model.StatusWithMessage;
import com.example.OnlineAssetTracking.Model.UpdateAssetsConditionBody;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.Repository.NetworkRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditRandomAssetStatusViewModel extends AndroidViewModel {
    private SingleLiveEvent<RoomData> locationInfo;
    private SingleLiveEvent<Asset> assetInfo;
    private SingleLiveEvent<Status> locationInfoStatus;
    private SingleLiveEvent<Status> assetInfoStatus;
    private SingleLiveEvent<List<AssetCondition>> assetCoditionsMutableLiveData;
    private SingleLiveEvent<Status> assetConditionsStatus;
    private SingleLiveEvent<StatusWithMessage> saveAssetStatus;
    private NetworkRepository repository;

    public EditRandomAssetStatusViewModel(@NonNull Application application) {
        super(application);
         repository = new NetworkRepository();
         locationInfo = new SingleLiveEvent<>();
         assetInfo = new SingleLiveEvent<>();
         locationInfoStatus = new SingleLiveEvent<>();
         assetInfoStatus = new SingleLiveEvent<>();
         assetConditionsStatus = new SingleLiveEvent<>();
         assetCoditionsMutableLiveData = new SingleLiveEvent<>();
         saveAssetStatus = new SingleLiveEvent<>();
    }

    public void getLocationInfo(String roomCode){
        repository.getRoomDataByCode(
                roomCode
        ).subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<GetRoomDataByCodeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        locationInfoStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(GetRoomDataByCodeResponse response) {
                        locationInfoStatus.postValue(Status.SUCCESS);
                        locationInfo.postValue(response.getRoomsDataParam().get(0));
                    }

                    @Override
                    public void onError(Throwable e) {
                        locationInfoStatus.postValue(Status.ERROR);
                    }
                });
    }

    public MutableLiveData<RoomData> getLocationInfo() {
        return locationInfo;
    }

    public MutableLiveData<Status> getLocationInfoStatus() {
        return locationInfoStatus;
    }

    public void getAssetInfo(String assetCode) {
        repository.getAssetData(assetCode).subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<GetAssetDataByAssetCodeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        assetInfoStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(GetAssetDataByAssetCodeResponse response) {
                        assetInfoStatus.postValue(Status.SUCCESS);
                        assetInfo.postValue(response.getAssetsDataParam().get(0));
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
        repository.getAssetConditions().subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseAssetConditions>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        assetConditionsStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseAssetConditions response) {
                        assetConditionsStatus.postValue(Status.SUCCESS);
                        assetCoditionsMutableLiveData.postValue(response.getAssetConditon());
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
    public void updateAssetCondition(String assetCode,String newAssetConditionName){
        repository.updateAssetsCondition(
                        new UpdateAssetsConditionBody(Collections.singletonList(assetCode),newAssetConditionName,USER_ID,DEVICE_SERIAL_NO)
                ).subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<UpdateAssetsConditionResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        saveAssetStatus.postValue(new StatusWithMessage(Status.LOADING));
                    }

                    @Override
                    public void onSuccess(UpdateAssetsConditionResponse apiResponse) {
                        if (apiResponse.getResponseStatus().getIsSuccess())
                            saveAssetStatus.postValue(new StatusWithMessage(Status.SUCCESS,apiResponse.getResponseStatus().getStatusMessage()));
                        else
                            saveAssetStatus.postValue(new StatusWithMessage(Status.ERROR,apiResponse.getResponseStatus().getStatusMessage()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        saveAssetStatus.postValue(new StatusWithMessage(Status.ERROR,getApplication().getString(R.string.error_in_getting_data)));
                    }
                });
    }
//    public void saveScannedAsset(Asset asset){
//        dataBase.dao().updateLocation(asset).subscribeOn(Schedulers.io())
//                .subscribeWith(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        saveAssetStatus.postValue(Status.LOADING);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        saveAssetStatus.postValue(Status.SUCCESS);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("saveAsset",e.getMessage());
//                        saveAssetStatus.postValue(Status.ERROR);
//                    }
//                });
//    }

    public MutableLiveData<StatusWithMessage> getSaveAssetStatus() {
        return saveAssetStatus;
    }
}