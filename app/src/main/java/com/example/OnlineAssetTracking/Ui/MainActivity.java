package com.example.OnlineAssetTracking.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.Util.CommunicationData;
import com.example.OnlineAssetTracking.Util.LocaleHelper;
import com.example.OnlineAssetTracking.databinding.ActivityMainBinding;
import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerUnavailableException;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static int USER_ID ;
    public static String ORDER_ID;
    private static BarcodeReader barcodeReader;
    private static BarcodeReader barcodeReaderSequence;
    public static  String DEVICE_SERIAL_NO;
    private AidcManager manager;

    public static String BASE_URL ;

    public static void refreshUi(MainActivity mainActivity) {
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mainActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BASE_URL = getBaseURL();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LocaleHelper.onCreate(this);
        if (LocaleHelper.getLanguage(this).equals("ar")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        installToolBar();
        AidcManager.create(this, aidcManager -> {
            manager = aidcManager;
            barcodeReader = manager.createBarcodeReader();
            barcodeReaderSequence = manager.createBarcodeReader();
            try {
                DEVICE_SERIAL_NO = barcodeReader.getInfo().getScannerId();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
            }
        });


    }

    private String getBaseURL() {
        Application application = getApplication();
        if (!CommunicationData.getPortNumber(application).isEmpty())
            return CommunicationData.getProtocol(application)+"://"+CommunicationData.getIpAddress(application)+":"+CommunicationData.getPortNumber(application)+"/api/AssetTracking/";
        else
            return CommunicationData.getProtocol(application)+"://"+CommunicationData.getIpAddress(application)+"/api/AssetTracking/";
    }

    public static BarcodeReader getBarcodeObject() {
        return barcodeReader;
    }
    public static BarcodeReader getBarcodeObjectsequence() {
        return barcodeReaderSequence;
    }


    private void installToolBar() {
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: onBackPressed(); break;
            case R.id.log_out:{
//                NavController navController = Navigation.findNavController(this, R.id.fragment);
//                navController.navigateUp();
//                navController.popBackStack();
//                navController.navigate(R.id.signInFragment);
                refreshUi(this);
            } break;
//            case R.id.change_password:{
//                NavController navController = Navigation.findNavController(this, R.id.myNavhostfragment);
//                navController.navigateUp();
//                navController.navigate(R.id.fragment_change_password);
//            } break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    public View noLocationText() {
        return binding.noLocations;
    }
}