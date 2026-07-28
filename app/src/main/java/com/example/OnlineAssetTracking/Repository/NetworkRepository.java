package com.example.OnlineAssetTracking.Repository;

import static com.example.OnlineAssetTracking.Ui.MainActivity.USER;

import com.example.OnlineAssetTracking.ApiResponse.ApiResponse;
import com.example.OnlineAssetTracking.ApiResponse.GetAssetDataByAssetCodeResponse;
import com.example.OnlineAssetTracking.ApiResponse.GetAssetsByDescriptionResponse;
import com.example.OnlineAssetTracking.ApiResponse.GetRoomDataByCodeResponse;
import com.example.OnlineAssetTracking.ApiResponse.UpdateAssetsConditionResponse;
import com.example.OnlineAssetTracking.ApiResponse.UserSignInResponse;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.User;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.ApiResponseAssetConditions;
import com.example.OnlineAssetTracking.Model.ApiResponseUserLocations;
import com.example.OnlineAssetTracking.Model.SaveAssetTrackingBody;
import com.example.OnlineAssetTracking.Model.UpdateAssetsConditionBody;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class NetworkRepository {
    private ApiInterface apiInterface;
    public NetworkRepository(){
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
    }
    public Single<UserSignInResponse> getUserInformation(String username, String password){
        return apiInterface.getUserInformation(username,password);
    }
    public Single<ApiResponseUserLocations> getAllUserLocation(String userId){
        return apiInterface.GetUserLocations(userId);
    }
    public Single<GetAssetDataByAssetCodeResponse> getAssetData(String assetCode){
        return apiInterface.GetAssetDataByAssetCode(assetCode);
    }
    public Single<ApiResponseAssetConditions> getAssetConditions(){
        return apiInterface.GetAssetConditions();
    }
    public Single<ApiResponse> saveScannedAsset(SaveAssetTrackingBody body) {
        return apiInterface.saveAssetTracking(body);
    }
//    public Observable<List<Asset>> getAssetListInFloor(int floorId) {
//        return apiInterface.getAllAssetsInFloor(
//                floorId
//        );
//    }
    public Single<GetAssetDataByAssetCodeResponse> getAssetListInRoom(String roomCode,int trackingOrderId) {
        return apiInterface.getAllAssetsInRoom(
                roomCode,
                trackingOrderId,
                USER.getUserId()
        );
    }

    public Single<Response<ResponseBody>> uploadImage(MultipartBody.Part body, RequestBody code){
        return apiInterface.uploadImage(code,body);
    }

    public Single<GetAssetsByDescriptionResponse> getAssetsByDescription(String assetDescription){
        return apiInterface.getAssetsByDescription(assetDescription);
    }

    public Single<GetRoomDataByCodeResponse> getRoomDataByCode(String roomCode){
        return apiInterface.getRoomDataByCode(roomCode);
    }
    public Single<UpdateAssetsConditionResponse> updateAssetsCondition(UpdateAssetsConditionBody body){
        return apiInterface.updateAssetsCondition(body);
    }

 }
