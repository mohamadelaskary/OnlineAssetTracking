package com.example.OnlineAssetTracking.ViewModel;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.Ui.MainActivity.ORDER_ID;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.ApiResponse.UserSignInResponse;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.User;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.MyMethods.SingleLiveEvent;
import com.example.OnlineAssetTracking.Repository.LocalRepository;
import com.example.OnlineAssetTracking.Repository.NetworkRepository;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SignInViewModel extends AndroidViewModel {

    private SingleLiveEvent<User> signInLiveData ;
    private SingleLiveEvent<Status> status;
    private SingleLiveEvent<Status> signInAndUserDataStatus;
    private Application application;

    private NetworkRepository repository;
    public SignInViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = new NetworkRepository();
        signInLiveData = new SingleLiveEvent<>();
        status = new SingleLiveEvent<>();
        signInAndUserDataStatus = new SingleLiveEvent<>();
    }

    public void signIn(String userName, String password){
        repository.getUserInformation(userName,password).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> status.postValue(Status.LOADING))
                .subscribe(new DisposableSingleObserver<UserSignInResponse>() {
                    @Override
                    public void onSuccess(UserSignInResponse user) {
                        if (user.getSuccess().getSuccess()) {
                            signInLiveData.postValue(user.getUserinfo());
                            status.postValue(Status.SUCCESS);
                        } else {
                            status.postValue(Status.ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        status.postValue(Status.ERROR);
                    }
                });
    }

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