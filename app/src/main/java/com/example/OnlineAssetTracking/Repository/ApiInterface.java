package com.example.OnlineAssetTracking.Repository;

import com.example.OnlineAssetTracking.ApiResponse.ApiResponse;
import com.example.OnlineAssetTracking.Model.ApiResponseAssetConditions;
import com.example.OnlineAssetTracking.Model.ApiResponseAssets;
import com.example.OnlineAssetTracking.Model.ApiResponseUserLocations;
import com.example.OnlineAssetTracking.Model.ApiResponseUsers;
import com.example.OnlineAssetTracking.Model.CheckConnectionApiResponse;
import com.example.OnlineAssetTracking.Model.SaveAssetTrackingBody;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @GET("GetAllUsers")
    Single<ApiResponseUsers> GetAllUsers();
    @POST("saveAssetTracking")
    Single<ApiResponse> saveAssetTracking(@Body SaveAssetTrackingBody assetTrackingBody);

    @GET("CheckConnection")
    Single<CheckConnectionApiResponse> checkConnection();

}
