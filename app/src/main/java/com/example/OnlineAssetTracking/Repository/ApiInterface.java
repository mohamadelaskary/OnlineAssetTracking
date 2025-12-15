package com.example.OnlineAssetTracking.Repository;

import com.example.OnlineAssetTracking.ApiResponse.ApiResponse;
import com.example.OnlineAssetTracking.ApiResponse.GetAssetDataByAssetCodeResponse;
import com.example.OnlineAssetTracking.ApiResponse.GetAssetsByDescriptionResponse;
import com.example.OnlineAssetTracking.ApiResponse.GetRoomDataByCodeResponse;
import com.example.OnlineAssetTracking.ApiResponse.UpdateAssetsConditionResponse;
import com.example.OnlineAssetTracking.ApiResponse.UserSignInResponse;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.Model.ApiResponseAssetConditions;
import com.example.OnlineAssetTracking.Model.ApiResponseAssets;
import com.example.OnlineAssetTracking.Model.ApiResponseUserLocations;
import com.example.OnlineAssetTracking.Model.ApiResponseUsers;
import com.example.OnlineAssetTracking.Model.SaveAssetTrackingBody;
import com.example.OnlineAssetTracking.Model.UpdateAssetsConditionBody;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("GetAssetConditions")
    Single<ApiResponseAssetConditions> GetAssetConditions();
    @GET("GetAssetData")
    Single<ApiResponseAssets> GetAssetData();
    @GET("GetAssetData")
    Single<ApiResponseAssets> GetAssetData(
            @Query("UserId") int userId,
            @Query("TrackingOrderId") int trackingOrderId
    );
    @GET("GetAssetDataInBuilding")
    Single<ApiResponseAssets> GetAssetDataInBuilding(
            @Query("BuildingId") int BuildingId
    );
    @GET("GetAllLocations")
    Single<ApiResponseUserLocations> GetAllLocations();
    @GET("GetUserLocations")
    Single<ApiResponseUserLocations> GetUserLocations(@Query("UserId") int userId);
    @GET("GetAllUsers")
    Single<ApiResponseUsers> GetAllUsers();
    @POST("saveAssetTracking")
    Single<ApiResponse> saveAssetTracking(@Body SaveAssetTrackingBody assetTrackingBody);

    @GET("UserSignIn")
    Single<UserSignInResponse> getUserInformation(@Query("UserName") String userName,@Query("Pass") String password);

    @GET("GetAssetDataByAssetCode")
    Single<GetAssetDataByAssetCodeResponse> GetAssetDataByAssetCode(@Query("AssetCode") String AssetCode);

    @GET("GetAssetsInLocation")
    Single<GetAssetDataByAssetCodeResponse> getAllAssetsInRoom(@Query("LocationCode") String roomCode,@Query("trackingOrderId") int trackingOrderId);
    @Multipart
    @POST("upload")
    Single<Response<ResponseBody>> uploadImage(
            @Part("assetCode") RequestBody assetCode,
            @Part MultipartBody.Part file
    );
    @GET("GetAssetsByDescription")
    Single<GetAssetsByDescriptionResponse> getAssetsByDescription(@Query("description") String description);
    @GET("GetRoomDataByCode")
    Single<GetRoomDataByCodeResponse> getRoomDataByCode(@Query("RoomCode") String roomCode);

    @POST("UpdateAssetsCondition")
    Single<UpdateAssetsConditionResponse> updateAssetsCondition(@Body UpdateAssetsConditionBody body);
}
