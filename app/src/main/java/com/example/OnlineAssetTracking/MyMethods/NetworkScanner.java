package com.example.OnlineAssetTracking.MyMethods;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.Model.StatusWithMessage;
import com.example.OnlineAssetTracking.Repository.ApiFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;


import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

public class NetworkScanner {

    private static final String TAG = "NetworkScanner";
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<String> foundBaseUrlLiveData = new MutableLiveData<>();

    public LiveData<String> getFoundBaseUrlLiveData() {
        return foundBaseUrlLiveData;
    }

    private final MutableLiveData<StatusWithMessage> foundBaseUrlStatus = new MutableLiveData<>();

    public LiveData<StatusWithMessage> getFoundBaseUrlStatus() {
        return foundBaseUrlStatus;
    }



    // get device IPv4 (first non-loopback) - used to extract prefix
    private String getDeviceIp() {
        try {
            for (NetworkInterface intf : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress addr : Collections.list(intf.getInetAddresses())) {
                    if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                        return addr.getHostAddress(); // e.g. "192.168.42.10"
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getDeviceIp error", e);
        }
        return null;
    }

    /**
     * Start scanning the subnet and set base URL on ApiFactory when found.
     *
     * @param port              port of your API (e.g., 7000)
     * @param healthPath        path to check (e.g., "WeatherForecast" or "health")
     * @param concurrency       max parallel requests (e.g., 20)
     * @param perRequestTimeoutMs timeout per request in ms (e.g., 1000 or 1500)
     */
    public void scanAndSetBaseUrl(String port,
                                  String healthPath,
                                  int concurrency,
                                  int perRequestTimeoutMs) {

        String deviceIp = getDeviceIp();
        if (deviceIp == null) {
            foundBaseUrlLiveData.postValue(null);
            foundBaseUrlStatus.postValue(new StatusWithMessage(Status.ERROR,"Device not connected Or Usb tethering ont enabled!"));
            return;
        }

        // extract prefix e.g. "192.168.42" from "192.168.42.10"
        final String prefix = deviceIp.substring(0, deviceIp.lastIndexOf('.'));

        // OkHttpClient with short timeout per request
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(perRequestTimeoutMs, TimeUnit.MILLISECONDS)
                .readTimeout(perRequestTimeoutMs, TimeUnit.MILLISECONDS)
                .writeTimeout(perRequestTimeoutMs, TimeUnit.MILLISECONDS)
                .build();

        // Observable of last octet 1..254 (skip 0 and 255 by default)
        disposables.add(
                Observable.range(1, 254) // 1..254
                        .map(i -> prefix + "." + i)
                        .flatMapSingle(ip -> checkHost(client, ip, port, healthPath)
                                .subscribeOn(Schedulers.io()) /*maxConcurrency*/)
                        .filter(result -> result != null) // keep only successful ip strings
                        .firstElement()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(foundIp -> {
                            // foundIp is e.g. "192.168.42.21"
                            if (!foundIp.isEmpty()){
                                String baseUrl = "http://" + foundIp + ":" + port + "/";
                            // update your ApiFactory (static)
                            ApiFactory.updateBaseUrl(baseUrl + "api/AssetTracking/"); // ensure you have this method
                            foundBaseUrlLiveData.setValue(baseUrl);
                            foundBaseUrlStatus.postValue(new StatusWithMessage(Status.SUCCESS));
                            Log.d(TAG, "Found server at: " + baseUrl + "api/AssetTracking/");
                            } else {
                                ApiFactory.updateBaseUrl("http://192.168.1.23:7000/api/AssetTracking/"); // ensure you have this method
                                foundBaseUrlLiveData.setValue("http://192.168.1.23:7000/api/AssetTracking/");
                                foundBaseUrlStatus.postValue(new StatusWithMessage(Status.ERROR,"Device not connected Or Usb tethering ont enabled!"));
                            }
                        }, throwable -> {
                            Log.e(TAG, "Scan error or not found", throwable);
                            foundBaseUrlLiveData.setValue("http://192.168.1.23:7000/");
                            foundBaseUrlStatus.postValue(new StatusWithMessage(Status.ERROR,"Device not connected Or Usb tethering ont enabled!"));
                        }, () -> {
                            // completed without finding any host
                            Log.d(TAG, "Scan completed - nothing found");
                            foundBaseUrlStatus.postValue(new StatusWithMessage(Status.ERROR,"Device not connected Or Usb tethering ont enabled!"));
                            // if nothing found, LiveData will already be null or set accordingly
                        })
        );
    }

    // helper: tries GET http://ip:port/healthPath, returns ip on success, null on failure
    private Single<String> checkHost(OkHttpClient client, String ip, String port, String healthPath) {
        return Single.fromCallable(() -> {
            String url = "http://" + ip + ":" + port + "/" + healthPath;
            Log.d(TAG, "checkHost: "+url);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    return ip; // 🟢 found working IP
                }
            } catch (Exception ignored) {
                // connection failed or timeout
            }
            return ""; // ❌ لازم نرجع قيمة non-null
        });
    }


    public void clear() {
        disposables.clear();
    }
}
