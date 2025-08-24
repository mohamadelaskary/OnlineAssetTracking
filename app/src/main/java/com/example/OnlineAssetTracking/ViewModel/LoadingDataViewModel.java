package com.example.OnlineAssetTracking.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Error;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.User;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.ApiResponseAssetConditions;
import com.example.OnlineAssetTracking.Model.ApiResponseAssets;
import com.example.OnlineAssetTracking.Model.ApiResponseUserLocations;
import com.example.OnlineAssetTracking.Model.ApiResponseUsers;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;
import com.example.OnlineAssetTracking.Repository.ApiFactory;
import com.example.OnlineAssetTracking.Repository.ApiInterface;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoadingDataViewModel extends AndroidViewModel {
    private SingleLiveEvent<Status> usersStatus;
    private SingleLiveEvent<List<User>> usersListLiveData = new SingleLiveEvent<>();
    private SingleLiveEvent<Status> assetConditionsStatus;
    private SingleLiveEvent<Status> userLocationsStatus;
    private SingleLiveEvent<Status> assetsStatus;
    private SingleLiveEvent<Integer> usersCount;
    private SingleLiveEvent<Status> usersCountStatus;
    private SingleLiveEvent<Error> userError;
    private SingleLiveEvent<Error> locationsError;
    private SingleLiveEvent<Error> assetsError;
    private SingleLiveEvent<Error> conditionsError;
    private AssetTrackingDataBase dataBase;
    private ApiInterface apiInterface;
    private int progress ;
    private MutableLiveData<Integer> progressLiveData;

    public LoadingDataViewModel(@NonNull Application application) {
        super(application);
        dataBase = DataBase.getInstance(application.getApplicationContext());
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        usersStatus = new SingleLiveEvent<>();
        userError = new SingleLiveEvent<>();
        assetConditionsStatus = new SingleLiveEvent<>();
        userLocationsStatus = new SingleLiveEvent<>();
        assetsStatus = new SingleLiveEvent<>();
        usersCount = new SingleLiveEvent<>();
        usersCountStatus = new SingleLiveEvent<>();
        progressLiveData = new MutableLiveData<>();
        locationsError = new SingleLiveEvent<>();
        userError = new SingleLiveEvent<>();
        assetsError = new SingleLiveEvent<>();
        conditionsError = new SingleLiveEvent<>();
        progress = 0;
//        userLocationsCountStatus = new SingleLiveEvent<>();
//        userLocationsCount = new SingleLiveEvent<>();
//        assetConditionsCount = new SingleLiveEvent<>();
//        assetConditionsCountStatus = new SingleLiveEvent<>();
//        assetsCount = new SingleLiveEvent<>();
//        assetsCountStatus = new SingleLiveEvent<>();
        deleteAllUsers();
        deleteAllConditions();
        deleteAllLocations();
//        deleteAllAssets(null);
        //        getUsersDataFromApi();
//        getUserLocationsDataFromApi();
//        getAssetsDataFromApi();
    }





    public void deleteAllUsers(){
        dataBase.dao().deleteAllUsers().subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                usersStatus.postValue(Status.LOADING);
            }

            @Override
            public void onComplete() {
                getUsersDataFromApi();
//                progress=progress+1;
//                progressLiveData.postValue(progress);
            }

            @Override
            public void onError(Throwable e) {
                usersStatus.postValue(Status.ERROR);
                userError.postValue(Error.DELETE);
            }
        });
    }
    public void getUsersDataFromApi(){
        apiInterface.GetAllUsers().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<ApiResponseUsers>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        usersStatus.postValue(Status.LOADING);
                    }

                    @Override
                    public void onSuccess(ApiResponseUsers listApiResponse) {
                        if (listApiResponse.getUsers()!=null) {
                            insertUsersInDatabase(listApiResponse.getUsers());
                        }
//                        progress=progress+1;
//                        progressLiveData.postValue(progress);
                    }

                    @Override
                    public void onError(Throwable e) {
                        usersStatus.postValue(Status.ERROR);
                        userError.postValue(Error.API);
                    }
                });
    }
    public void insertUsersInDatabase (List<User> users){
        dataBase.dao().insertUsers(users).subscribeOn(Schedulers.io())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {

//                        progress=progress+1;
//                        progressLiveData.postValue(progress);
                        usersListLiveData.postValue(users);
                        usersStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        usersStatus.postValue(Status.ERROR);
                        userError.postValue(Error.INSERT);
                    }
                });
    }
    private void deleteAllLocations() {
        dataBase.dao().deleteAllUserLocations().subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                userLocationsStatus.postValue(Status.LOADING);
            }

            @Override
            public void onComplete() {
                getUserLocationsDataFromApi();
                Log.d("LoadingDataViewModel", "onComplete: LocationsDeleted");
                if (progress < 3){
                    progress = progress + 1;
                    progressLiveData.postValue(progress);
                } else {
                    progress = 0;
                }
            }

            @Override
            public void onError(Throwable e) {
                userLocationsStatus.postValue(Status.ERROR);
                locationsError.postValue(Error.DELETE);
            }
        });
    }

    public SingleLiveEvent<List<UserLocation>> getUserLocationsLiveData() {
        return userLocationsLiveData;
    }

    private SingleLiveEvent<List<UserLocation>> userLocationsLiveData = new SingleLiveEvent<>();
    public void getUserLocationsDataFromApi(){
        apiInterface.GetAllLocations().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<ApiResponseUserLocations>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(ApiResponseUserLocations listApiResponse) {
                        if (listApiResponse.getUserLocation()!=null) {
                            insertUserLocationInDatabase(listApiResponse.getUserLocation());
                            userLocationsLiveData.postValue(listApiResponse.getUserLocation());
                        }
                        if (progress < 3){
                            progress = progress + 1;
                            progressLiveData.postValue(progress);
                        } else {
                            progress = 0;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        userLocationsStatus.postValue(Status.ERROR);
                        locationsError.postValue(Error.API);
                    }
                });
    }
    public void insertUserLocationInDatabase (List<UserLocation> userLocations){
        dataBase.dao().insertUserLocations(userLocations).subscribeOn(Schedulers.io())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (progress < 3){
                            progress = progress + 1;
                            progressLiveData.postValue(progress);
                        } else {
                            progress = 0;
                        }
                        userLocationsStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        userLocationsStatus.postValue(Status.ERROR);
                        locationsError.postValue(Error.INSERT);
                    }
                });
    }
     public void deleteAllAssets(Integer userId,Integer trackingOrderId) {
        dataBase.dao().deleteAllAssets().subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                assetsStatus.postValue(Status.LOADING);
            }

            @Override
            public void onComplete() {
                getAssetsDataFromApi(userId,trackingOrderId);
                if (progress < 3){
                    progress = progress + 1;
                    progressLiveData.postValue(progress);
                } else {
                    progress = 0;
                }
            }

            @Override
            public void onError(Throwable e) {
                assetsStatus.postValue(Status.ERROR);
                assetsError.postValue(Error.DELETE);
            }
        });

    }
    @SuppressLint("CheckResult")
    public void getAssetsDataFromApi(Integer userId,Integer trackingOrderId){
            apiInterface.GetAssetData(userId,trackingOrderId).subscribeOn(Schedulers.io())
                    .subscribeWith(new SingleObserver<ApiResponseAssets>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onSuccess(ApiResponseAssets listApiResponse) {
                            if (listApiResponse.getAssets() != null)
                                insertAssetsInDatabase(listApiResponse.getAssets());
                            if (progress < 3){
                                progress = progress + 1;
                                progressLiveData.postValue(progress);
                            } else {
                                progress = 0;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            assetsStatus.postValue(Status.ERROR);
                            assetsError.postValue(Error.API);
                        }
                    });

    }
    public void insertAssetsInDatabase (List<Asset> assets){
        dataBase.dao().insertAssets(assets).subscribeOn(Schedulers.io())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        if (progress < 3){
                            progress = progress + 1;
                            progressLiveData.postValue(progress);
                        } else {
                            progress = 0;
                        }
                        assetsStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        assetsStatus.postValue(Status.ERROR);
                        assetsError.postValue(Error.INSERT);
                    }
                });
    }
    public void deleteAllConditions(){
        dataBase.dao().deleteAllConditions().subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                assetConditionsStatus.postValue(Status.LOADING);
            }

            @Override
            public void onComplete() {
                getAssetConditionsDataFromApi();
                progress=progress+1;
                progressLiveData.postValue(progress);
            }

            @Override
            public void onError(Throwable e) {
                assetConditionsStatus.postValue(Status.ERROR);
                conditionsError.postValue(Error.DELETE);
            }
        });
    }
    public void getAssetConditionsDataFromApi(){
        apiInterface.GetAssetConditions().subscribeOn(Schedulers.io())
                .subscribeWith(new SingleObserver<ApiResponseAssetConditions>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(ApiResponseAssetConditions listApiResponse) {
                        if (listApiResponse.getAssetConditon()!=null)
                            insertAssetConditionsInDatabase(listApiResponse.getAssetConditon());
                        progress=progress+1;
                        progressLiveData.postValue(progress);
                    }

                    @Override
                    public void onError(Throwable e) {
                        assetConditionsStatus.postValue(Status.ERROR);
                        conditionsError.postValue(Error.API);
                    }
                });
    }
    public void insertAssetConditionsInDatabase (List<AssetCondition> conditions){
        dataBase.dao().insertAssetConditions(conditions).subscribeOn(Schedulers.io())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {

                        progress=progress+1;
                        progressLiveData.postValue(progress);
                        assetConditionsStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        assetConditionsStatus.postValue(Status.ERROR);
                        conditionsError.postValue(Error.INSERT);
                    }
                });
    }




    public MutableLiveData<Status> getUsersStatus() {
        return usersStatus;
    }

    public MutableLiveData<Status> getAssetConditionsStatus() {
        return assetConditionsStatus;
    }

    public MutableLiveData<Status> getUserLocationsStatus() {
        return userLocationsStatus;
    }

    public MutableLiveData<Status> getAssetsStatus() {
        return assetsStatus;
    }



    public SingleLiveEvent<Error> getUserError() {
        return userError;
    }

    public MutableLiveData<Integer> getProgressLiveData() {
        return progressLiveData;
    }

    public SingleLiveEvent<List<User>> getUsersListLiveData() {
        return usersListLiveData;
    }
}