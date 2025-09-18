package com.example.OnlineAssetTracking.ViewModel;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.Ui.MainActivity.USER_ID;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;

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


    public SelectRoomViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        roomDataLiveData = new SingleLiveEvent<>();
        roomDataStatus = new SingleLiveEvent<>();
        allUserLocation = new SingleLiveEvent<>();
        allLocationStatus = new SingleLiveEvent<>();
    }
    public void getAllLocations(){
        dataBase.dao().getUserLocations().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<List<UserLocation>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        allLocationStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(List<UserLocation> userLocation) {
                        allUserLocation.postValue(userLocation);
                        allLocationStatus.postValue(Status.SUCCESS);
                        Log.d("====userLocationNum",userLocation.size()+"");
                    }

                    @Override
                    public void onError(Throwable e) {
                        allLocationStatus.postValue(Status.ERROR);
                    }
                });
    }
    public void getRoomData(String roomCode){
        Log.d("===roomCode",roomCode);
        dataBase.dao().getRoomData(
                roomCode
        ).subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<UserLocation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        roomDataStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(UserLocation userLocation) {
                        roomDataLiveData.postValue(userLocation);
                        roomDataStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        roomDataStatus.postValue(Status.ERROR);
                        Log.d("erroor",e.getMessage());
                    }
                });
    }
    public void getFloorData(int floorId){
        dataBase.dao().getFloorData(floorId).subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<UserLocation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        floorDataStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(UserLocation userLocation) {
                        floorDataLiveData.postValue(userLocation);
                        floorDataStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        floorDataStatus.postValue(Status.ERROR);
                        Log.d("erroor",e.getMessage());
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