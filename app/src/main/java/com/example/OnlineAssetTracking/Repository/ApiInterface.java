package com.example.OnlineAssetTracking.Repository;

import com.example.OnlineAssetTracking.ApiResponse.ApiResponse;
import com.example.OnlineAssetTracking.Model.ApiResponseAssetConditions;
import com.example.OnlineAssetTracking.Model.ApiResponseAssets;
import com.example.OnlineAssetTracking.Model.ApiResponseUserLocations;
import com.example.OnlineAssetTracking.Model.ApiResponseUsers;
import com.example.OnlineAssetTracking.Model.SaveAssetTrackingBody;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("GetAssetConditions")
    Single<ApiResponseAssetConditions> GetAssetConditions();
    @GET("GetAssetData")
    Single<ApiResponseAssets> GetAssetData();
    @GET("GetAllLocations")
    Single<ApiResponseUserLocations> GetAllLocations();
    @GET("GetAllUsers")
    Single<ApiResponseUsers> GetAllUsers();
    @POST("saveAssetTracking")
    Single<ApiResponse> saveAssetTracking(@Body SaveAssetTrackingBody assetTrackingBody);

}
