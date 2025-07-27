package com.example.OnlineAssetTracking.ViewModel;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.User;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FileLoadingDataViewModel extends AndroidViewModel {
    private SingleLiveEvent<Status> insertUsersStatus ;
    private SingleLiveEvent<Status> insertAssetConditionsStatus ;
    private SingleLiveEvent<Status> insertUserLocationsStatus ;
    private SingleLiveEvent<Status> insertAssetsStatus ;
    private SingleLiveEvent<Integer> usersCount;
    private SingleLiveEvent<Status> usersCountStatus;
    private SingleLiveEvent<Integer> userLocationsCount;
    private SingleLiveEvent<Status> userLocationsCountStatus;
    private SingleLiveEvent<Integer> assetsCount;
    private SingleLiveEvent<Status> assetsCountStatus;
    private SingleLiveEvent<Integer> assetConditionsCount;
    private SingleLiveEvent<Status> assetConditionsCountStatus;
    private AssetTrackingDataBase dataBase;

    public FileLoadingDataViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        insertUsersStatus = new SingleLiveEvent<>();
        insertAssetConditionsStatus = new SingleLiveEvent<>();
        insertUserLocationsStatus = new SingleLiveEvent<>();
        insertAssetsStatus = new SingleLiveEvent<>();
        usersCount = new SingleLiveEvent<>();
        usersCountStatus = new SingleLiveEvent<>();
        userLocationsCountStatus = new SingleLiveEvent<>();
        userLocationsCount = new SingleLiveEvent<>();
        assetConditionsCount = new SingleLiveEvent<>();
        assetConditionsCountStatus = new SingleLiveEvent<>();
        assetsCount = new SingleLiveEvent<>();
        assetsCountStatus = new SingleLiveEvent<>();

    }

    public void GetUsersCount() {
        dataBase.dao().usersCount().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        usersCountStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        usersCount.postValue(integer);
                        usersCountStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        usersCountStatus.postValue(Status.ERROR);
                    }
                });
    }

    public void GetUserLocationsCount() {
        dataBase.dao().userLocationsCount().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        userLocationsCountStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        userLocationsCount.postValue(integer);
                        userLocationsCountStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        userLocationsCountStatus.postValue(Status.ERROR);
                    }
                });
    }

    public void GetAssetsCount() {
        dataBase.dao().assetsCount().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        assetsCountStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        assetsCount.postValue(integer);
                        assetsCountStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        assetsCountStatus.postValue(Status.ERROR);
                    }
                });
    }

    public void GetAssetConditionsCount() {
        dataBase.dao().assetConditionsCount().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        assetConditionsCountStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        assetConditionsCount.postValue(integer);
                        assetConditionsCountStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        assetConditionsCountStatus.postValue(Status.ERROR);
                    }
                });
    }

    public void insertUsersInDatabase(List<User> users) {
        dataBase.dao().insertUsers(users).subscribeOn(Schedulers.io())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        insertUsersStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        insertUsersStatus.postValue(Status.ERROR);
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    public void insertUserLocationInDatabase(List<UserLocation> userLocations) {
        dataBase.dao().insertUserLocations(userLocations).subscribeOn(Schedulers.io())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        insertUserLocationsStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        insertUserLocationsStatus.postValue(Status.ERROR);
                        Log.d(TAG, "onError: "+e.getMessage());                    }
                });
    }

    public void insertAssetsInDatabase(List<Asset> assets) {
        dataBase.dao().insertAssets(assets).subscribeOn(Schedulers.io())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        insertAssetsStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        insertAssetsStatus.postValue(Status.ERROR);
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    public void insertConditionsInDatabase(List<AssetCondition> assetConditions) {
        dataBase.dao().insertAssetConditions(assetConditions).subscribeOn(Schedulers.io())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        insertAssetConditionsStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        insertAssetConditionsStatus.postValue(Status.ERROR);
                        Log.d(TAG, "onError: "+e.getMessage());                    }
                });
    }

    public void deleteAllUsers(List<User> users) {
        dataBase.dao().deleteAllUsers().subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                insertUsersStatus.postValue(Status.LOADING);
            }

            @Override
            public void onComplete() {
                insertUsersInDatabase(users);
            }

            @Override
            public void onError(Throwable e) {
                insertUsersStatus.postValue(Status.ERROR);
                Log.d(TAG, "onErrorDelete: "+e.getMessage());
            }
        });
    }

    public void deleteAllConditions(List<AssetCondition> assetConditions) {
        dataBase.dao().deleteAllConditions().subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                insertAssetConditionsStatus.postValue(Status.LOADING);
            }

            @Override
            public void onComplete() {
                insertConditionsInDatabase(assetConditions);
            }

            @Override
            public void onError(Throwable e) {
                insertAssetConditionsStatus.postValue(Status.ERROR);
                Log.d(TAG, "onErrorDelete: "+e.getMessage());
            }
        });
    }

    public void deleteAllUserLocations(List<UserLocation> userLocations) {
        dataBase.dao().deleteAllUserLocations().subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                insertUserLocationsStatus.postValue(Status.LOADING);
            }

            @Override
            public void onComplete() {
                insertUserLocationInDatabase(userLocations);
            }

            @Override
            public void onError(Throwable e) {
                insertUserLocationsStatus.postValue(Status.ERROR);
                Log.d(TAG, "onErrorDelete: "+e.getMessage());
            }
        });
    }

    public void deleteAllAssets(List<Asset> assets) {
        dataBase.dao().deleteAllAssets().subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                insertAssetsStatus.postValue(Status.LOADING);
            }

            @Override
            public void onComplete() {
                insertAssetsInDatabase(assets);
            }

            @Override
            public void onError(Throwable e) {
                insertAssetsStatus.postValue(Status.ERROR);
                Log.d(TAG, "onErrorDelete: "+e.getMessage());
            }
        });
    }

    public MutableLiveData<Status> getInsertUsersStatus() {
        return insertUsersStatus;
    }

    public MutableLiveData<Status> getInsertAssetConditionsStatus() {
        return insertAssetConditionsStatus;
    }

    public MutableLiveData<Status> getInsertUserLocationsStatus() {
        return insertUserLocationsStatus;
    }

    public MutableLiveData<Status> getInsertAssetsStatus() {
        return insertAssetsStatus;
    }

    public MutableLiveData<Status> getUsersCountStatus() {
        return usersCountStatus;
    }

    public MutableLiveData<Status> getUserLocationsCountStatus() {
        return userLocationsCountStatus;
    }

    public MutableLiveData<Status> getAssetsCountStatus() {
        return assetsCountStatus;
    }

    public MutableLiveData<Status> getAssetConditionsCountStatus() {
        return assetConditionsCountStatus;
    }

    public MutableLiveData<Integer> getUsersCount() {
        return usersCount;
    }

    public MutableLiveData<Integer> getUserLocationsCount() {
        return userLocationsCount;
    }

    public MutableLiveData<Integer> getAssetsCount() {
        return assetsCount;
    }

    public MutableLiveData<Integer> getAssetConditionsCount() {
        return assetConditionsCount;
    }
}