package com.example.OnlineAssetTracking.ViewModel;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.Ui.MainActivity.ORDER_ID;
import static com.example.OnlineAssetTracking.Ui.MainActivity.USER_ID;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.ApiResponseUserLocations;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;
import com.example.OnlineAssetTracking.Repository.NetworkRepository;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SelectRoomViewModel extends AndroidViewModel {
    private AssetTrackingDataBase dataBase;
    private SingleLiveEvent<UserLocation> roomDataLiveData;
    private SingleLiveEvent<Status> roomDataStatus;
    private SingleLiveEvent<List<UserLocation>> allUserLocation;
    private SingleLiveEvent<Status> allLocationStatus;
    private SingleLiveEvent<UserLocation> floorDataLiveData;
    private SingleLiveEvent<Status> floorDataStatus;
    private NetworkRepository repository;

    public SelectRoomViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        roomDataLiveData = new SingleLiveEvent<>();
        roomDataStatus = new SingleLiveEvent<>();
        allUserLocation = new SingleLiveEvent<>();
        allLocationStatus = new SingleLiveEvent<>();
        repository = new NetworkRepository();
    }
    public void getAllLocations(){
        repository.getAllUserLocation(
                    USER_ID
            ).subscribeOn(Schedulers.io())
            .subscribe(new SingleObserver<ApiResponseUserLocations>() {
                @Override
                public void onSubscribe(Disposable d) {
                    allLocationStatus.postValue(Status.LOADING);
                }

                @Override
                public void onSuccess(ApiResponseUserLocations userLocation) {
                    if (userLocation!=null) {
                        allUserLocation.postValue(userLocation.getUserLocation());
                        allLocationStatus.postValue(Status.SUCCESS);
                    } else {
                        allLocationStatus.postValue(Status.ERROR);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "onError: ", e);
                    allLocationStatus.postValue(Status.ERROR);
                }
            });
    }


    public MutableLiveData<UserLocation> getRoomDataLiveData() {
        return roomDataLiveData;
    }

    public MutableLiveData<Status> getRoomDataStatus() {
        return roomDataStatus;
    }

    public MutableLiveData<List<UserLocation>> getAllUserLocation() {
        return allUserLocation;
    }

    public MutableLiveData<Status> getAllLocationStatus() {
        return allLocationStatus;
    }

    public MutableLiveData<UserLocation> getFloorDataLiveData() {
        return floorDataLiveData;
    }

    public MutableLiveData<Status> getFloorDataStatus() {
        return floorDataStatus;
    }

}