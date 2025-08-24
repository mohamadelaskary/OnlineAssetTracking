package com.example.OnlineAssetTracking.ViewModel;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.Ui.MainActivity.ORDER_ID;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.User;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SignInViewModel extends AndroidViewModel {

    private AssetTrackingDataBase dataBase;
    private SingleLiveEvent<User> signInLiveData ;
    private SingleLiveEvent<Status> status;
    private SingleLiveEvent<Status> getUserDataStatus;
    private SingleLiveEvent<Status> signInAndUserDataStatus;
    private Application application;
    public SignInViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        dataBase = DataBase.getInstance(application.getApplicationContext());
        signInLiveData = new SingleLiveEvent<>();
        status = new SingleLiveEvent<>();
        getUserDataStatus = new SingleLiveEvent<>();
        signInAndUserDataStatus = new SingleLiveEvent<>();
    }

    public void signIn(String userName){
        dataBase.dao().getUserInformation(userName).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> status.postValue(Status.LOADING))
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        Log.d("===userId",user.getUserId()+"");
                        signInLiveData.postValue(user);
//                        getOrderId(user);
//                        signInStatus.postValue(Status.SUCCESS);
                    }

                    @Override
                    public void onError(Throwable e) {
                        status.postValue(Status.ERROR);
                    }
                });
    }
//    public void getOrderId(User user){
//        dataBase.dao().getUserLocations(
//                user.getUserId()
//                ).subscribeOn(Schedulers.io())
////                .doOnSubscribe(disposable -> signInStatus.postValue(Status.LOADING))
//                .subscribeWith(new DisposableSingleObserver<List<UserLocation>>() {
//                    @Override
//                    public void onSuccess(List<UserLocation> userLocations) {
//                        if (!userLocations.isEmpty())
//                            ORDER_ID = String.valueOf(userLocations.get(0).getTrackingOrderId());
//                        else
//                            ORDER_ID = null;
//                        signInLiveData.postValue(user);
//                        status.postValue(Status.SUCCESS);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        ORDER_ID = null;
//                        status.postValue(Status.ERROR);
//                    }
//                });
//    }

    public SingleLiveEvent<User> getSignInLiveData() {
        return signInLiveData;
    }

    public SingleLiveEvent<Status> getStatus() {
        return status;
    }

    public SingleLiveEvent<Status> getSignInAndUserDataStatus() {
        return signInAndUserDataStatus;
    }
}