package com.example.OnlineAssetTracking.Ui;


import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.getEditTextText;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.warningDialog;
import static com.example.OnlineAssetTracking.Ui.MainActivity.refreshUi;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.Util.CommunicationData;
import com.example.OnlineAssetTracking.databinding.ChangeSettingsDialogLayoutBinding;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChangeSettingsDialog extends Dialog implements View.OnClickListener {
    private Application application;
    private Activity activity;
    public ChangeSettingsDialog(@NonNull Context context, Application application, Activity activity) {
        super(context);
        this.application = application;
        this.activity =activity;
    }

    private ChangeSettingsDialogLayoutBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChangeSettingsDialogLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        this.getWindow().setLayout((6 * width)/7, (4 * height)/7);
        progressDialog = loadingProgressDialog(getContext());
        switch (CommunicationData.getProtocol(application)) {
            case "http":
                binding.http.setChecked(true);
                break;
            case "https":
                binding.https.setChecked(true);
                break;
        }
        binding.ip.getEditText().setText(CommunicationData.getIpAddress(application));
        binding.port.getEditText().setText(CommunicationData.getPortNumber(application));
        binding.save.setOnClickListener(this);
    }

    private String ipAddress, portNum = "", protocol;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            int protocolId = binding.protocol.getCheckedRadioButtonId();
            if (protocolId == R.id.http) {
                protocol = "http";
            } else if (protocolId == R.id.https) {
                protocol = "https";
            }
            ipAddress = getEditTextText(binding.ip);
            portNum = getEditTextText(binding.port);
            if (!ipAddress.isEmpty()) {
                progressDialog.show();
                if (!portNum.isEmpty())
                    hasInternetConnection(protocol + "://" + ipAddress + ":" + portNum + "/api/AssetTracking/GetAssetConditions").subscribe();
                else
                    hasInternetConnection(protocol + "://" + ipAddress + "/api/AssetTracking/GetAssetConditions").subscribe();
            } else binding.ip.setError(application.getString(R.string.please_enter_ip_address));
        }
    }

    public Single<Boolean> hasInternetConnection(String newBaseUrl) {
        return Single.fromCallable(() -> {
            boolean isOnline = false;
            try {
                Log.d(TAG, "hasInternetConnection: "+newBaseUrl);
                URL url = new URL(newBaseUrl);
                trustEveryone();
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("User-Agent", "Android Application:" + Build.VERSION.SDK_INT);
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000 * 30); // mTimeout is in seconds
                urlc.connect();

                if (urlc.getResponseCode() == 200) {
                    isOnline = true;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                isOnline = false;
            }

            return isOnline;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnEvent((aBoolean, throwable) -> {
            progressDialog.dismiss();
            if (aBoolean) {
                CommunicationData.saveProtocol(application, protocol);
                CommunicationData.saveIPAddress(application, ipAddress);
                CommunicationData.savePortNum(application, portNum);
                showSuccessAlerter(getContext().getString(R.string.saved_successfully),activity);
                refreshUi((MainActivity) activity);
            } else
                warningDialog(getContext(), getContext().getString(R.string.wrong_ip));
        });

    }
    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}
