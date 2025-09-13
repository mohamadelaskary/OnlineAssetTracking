package com.example.OnlineAssetTracking.Repository;

import android.util.Log;

import com.example.OnlineAssetTracking.ApiResponse.ApiResponse;
import com.example.OnlineAssetTracking.Model.SaveAssetTrackingBody;

import io.reactivex.Single;

public class NetworkRepository {
    private ApiInterface apiService;

    public NetworkRepository(ApiInterface apiService) {
        this.apiService = apiService;
    }
    public void updateApiService(String newBaseUrl) {
        ApiFactory.updateBaseUrl(newBaseUrl);
        Log.d("NetworkRepository", "updateApiService: "+newBaseUrl);
        this.apiService = ApiFactory.createService(ApiInterface.class);
    }

    public Single<Boolean> checkConnectivity() {
        return apiService.checkConnection()
                .map(response -> true) // لو جاله أي response يبقى الاتصال ناجح
                .onErrorReturnItem(false); // لو حصل error يبقى false
    }
    public  Single<ApiResponse> uploadData (SaveAssetTrackingBody body){
        return apiService.saveAssetTracking(body);
    }
}
