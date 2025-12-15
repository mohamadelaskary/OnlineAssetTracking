package com.example.OnlineAssetTracking.ViewModel;

import static android.content.ContentValues.TAG;

import static com.example.OnlineAssetTracking.Ui.MainActivity.DEVICE_SERIAL_NO;
import static com.example.OnlineAssetTracking.Ui.MainActivity.USER_ID;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.ApiResponse.ApiResponse;
import com.example.OnlineAssetTracking.ApiResponse.GetAssetDataByAssetCodeResponse;
import com.example.OnlineAssetTracking.ApiResponse.UpdateAssetsConditionResponse;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.ApiResponseAssetConditions;
import com.example.OnlineAssetTracking.Model.Data;
import com.example.OnlineAssetTracking.Model.SaveAssetTrackingBody;
import com.example.OnlineAssetTracking.Model.StatusWithMessage;
import com.example.OnlineAssetTracking.Model.UpdateAssetsConditionBody;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.Repository.NetworkRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class PhysicalCountingViewModel extends AndroidViewModel {
    private AssetTrackingDataBase dataBase;
    private SingleLiveEvent<Asset> assetDataLiveData;
    private SingleLiveEvent<Status> gettingAssetDataStatus;
    private SingleLiveEvent<StatusWithMessage> saveAssetStatus;
    private SingleLiveEvent<List<AssetCondition>> gettingAssetConditions;
    private SingleLiveEvent<Status> gettingAssetConditionStatus;

    private NetworkRepository repository;

    public PhysicalCountingViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        assetDataLiveData = new SingleLiveEvent<>();
        gettingAssetDataStatus = new SingleLiveEvent<>();
        gettingAssetConditions = new SingleLiveEvent<>();
        saveAssetStatus = new SingleLiveEvent<>();
        gettingAssetConditionStatus = new SingleLiveEvent<>();
        repository = new NetworkRepository();
    }

    public void getAssetData(String assetCode){
        repository.getAssetData(assetCode)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<GetAssetDataByAssetCodeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        gettingAssetDataStatus.postValue(Status.LOADING);
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onSuccess(GetAssetDataByAssetCodeResponse assetResponse) {
                        if (assetResponse.getResponseStatus().getIsSuccess()) {
                            assetDataLiveData.postValue(assetResponse.getAssetsDataParam().get(0));
                            gettingAssetDataStatus.postValue(Status.SUCCESS);
                        } else {
                            gettingAssetDataStatus.postValue(Status.ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        gettingAssetDataStatus.postValue(Status.ERROR);
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    public void getAssetConditions(){
        repository.getAssetConditions().subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponseAssetConditions>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        gettingAssetConditionStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseAssetConditions assetConditions) {
                        gettingAssetConditions.postValue(assetConditions.getAssetConditon());
                        gettingAssetConditionStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        gettingAssetConditionStatus.postValue(Status.ERROR);
                    }
                });
    }

    public void saveScannedAsset(Asset asset, UserLocation userLocation){
        SaveAssetTrackingBody body = new SaveAssetTrackingBody();
        body.setTrackingOrderId(userLocation.getTrackingOrderId());
        List<Data> dataList = new ArrayList<>();
        dataList.add(new Data(
                        asset.getBarcode(),
                        "",
                        userLocation.getRoomId(),
                        asset.getRoomId(),
                        asset.getAssetConditionId(),
                        asset.getNewAssetConditionId(),
                        userLocation.getFloorId(),
                        asset.getFloorId(),
                        asset.getDate(),
                        asset.getUserId(),
                        asset.getBuildingId(),
                        userLocation.getBuildingId(),
                        Integer.parseInt(asset.getIsSameLocation()),
                        Integer.parseInt(asset.getIsSameCondition()),
                        "",
                        ""
                ));
        body.setData(
                dataList
        );
        repository.saveScannedAsset(body).subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        saveAssetStatus.postValue(new StatusWithMessage(Status.LOADING));
                    }

                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if (apiResponse.getSuccess())
                            saveAssetStatus.postValue(new StatusWithMessage(Status.SUCCESS,apiResponse.getMessage()));
                        else
                            saveAssetStatus.postValue(new StatusWithMessage(Status.ERROR,apiResponse.getMessage()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        saveAssetStatus.postValue(new StatusWithMessage(Status.ERROR,getApplication().getString(R.string.error_in_getting_data)));
                    }
                });
    }
    public void updateAssetCondition(String assetCode,String newAssetConditionName,int trackingOrderId){
        repository.updateAssetsCondition(
                new UpdateAssetsConditionBody(assetCode,newAssetConditionName,USER_ID,DEVICE_SERIAL_NO,trackingOrderId)
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

    public MutableLiveData<Asset> getAssetDataLiveData() {
        return assetDataLiveData;
    }

    public MutableLiveData<Status> getGettingAssetDataStatus() {
        return gettingAssetDataStatus;
    }

    public MutableLiveData<List<AssetCondition>> getGettingAssetConditions() {
        return gettingAssetConditions;
    }

    public MutableLiveData<StatusWithMessage> getSaveAssetStatus() {
        return saveAssetStatus;
    }

    public MutableLiveData<StatusWithMessage> getUploadStatus() {
        return uploadStatus;
    }

    private final MutableLiveData<StatusWithMessage> uploadStatus = new MutableLiveData<>();
    public void uploadImage(String assetCode, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody code = RequestBody.create(MediaType.parse("text/plain"), assetCode);

        repository.uploadImage(body,code)
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<ResponseBody>>() {
            @Override
            public void onSubscribe(Disposable d) {
                uploadStatus.postValue(new StatusWithMessage(Status.LOADING));
            }

            @Override
            public void onSuccess(Response<ResponseBody> responseBodyResponse) {
                if (responseBodyResponse.isSuccessful()){
                    uploadStatus.postValue(new StatusWithMessage(Status.SUCCESS,responseBodyResponse.body().toString()));
                } else {
                    uploadStatus.postValue(new StatusWithMessage(Status.ERROR,responseBodyResponse.errorBody().toString()));
                }
            }

            @Override
            public void onError(Throwable e) {
                uploadStatus.postValue(new StatusWithMessage(Status.ERROR,getApplication().getString(R.string.error_while_uploading_image)));
            }
        });
    }

}