package com.example.OnlineAssetTracking.Ui;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.DataBase.Status.LOADING;
import static com.example.OnlineAssetTracking.MyMethods.Tools.arabicToDecimal;
import static com.example.OnlineAssetTracking.MyMethods.Tools.getStringDataFromLocalStorage;
import static com.example.OnlineAssetTracking.MyMethods.Tools.saveStringDataToLocalStorage;
import static com.example.OnlineAssetTracking.MyMethods.Tools.showSuccessAlerter;
import static com.example.OnlineAssetTracking.MyMethods.Tools.warningDialog;
import static com.example.OnlineAssetTracking.Ui.SignInFragment.USER_TYPE;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.Tools;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.ViewModel.MainFragmentViewModel;
import com.example.OnlineAssetTracking.databinding.FragmentMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String BASE_URL_KEY = "baseUrlKey";

    private MainFragmentViewModel viewModel;
    private AssetTrackingDataBase dataBase;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }
    private LoadingDialog loadingDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = Tools.showLoadingDialog(getContext());
        viewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        dataBase = DataBase.getInstance(getContext());
    }
    private FragmentMainBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachButtonsToListener();
        getUserType();
        observeUploadingScannedAssets();
        observeGettingScannedAssetsStatus();
        observeGettingScannedAssets();
        observeCheckingConnectivity();
//        dataBase.dao().deleteAllAssets().subscribeOn(Schedulers.io())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("DeleteAssets","success");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//        dataBase.dao().deleteAllUsers().subscribeOn(Schedulers.io())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("DeleteUsers","success");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });;
//        dataBase.dao().deleteAllUserLocations().subscribeOn(Schedulers.io())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("DeleteUserLocations","success");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });;
//        dataBase.dao().deleteAllConditions().subscribeOn(Schedulers.io())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("DeleteCondition","success");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });;
    }

    private void observeCheckingConnectivity() {
        viewModel.getIsConnected().observe(getViewLifecycleOwner(),isConnected ->{
            if (!isConnected){
                new ChangePortDialog(
                        requireContext(),
                        (portNo, dialogInterface) -> {
                            viewModel.startScan(portNo);
                            dialogInterface.dismiss();
                        }
                ).show();
            } else {
                Log.d(TAG, "observeCheckingConnectivity: isConnected");
                Log.d(TAG, "observeCheckingConnectivity: " + getSavedBaseUrl()+"api/AssetTracking/");
                viewModel.changeBaseUrl(getSavedBaseUrl()+"api/AssetTracking/");
            }
        });
    }

    private void observeGettingScannedAssets() {
        fileContent.append("\uFEFFAssetID,Barcode,AssetNumber,RoomID,NewRoomID,IsSameRoom,FloorID,NewFloorID,IsSameFloor,BuildingID,NewBuildingID,IsSameBuilding,SiteID,NewSiteID,IsSameSite,SectorID,NewSectorID,IsSameSector,CentralDepartmentID,NewCentralID,IsSameCentralID,GeneralDepartmentID,NewGeneralDepartmentID,IsSameGeneralDepartment,DepartmentID,NewDepartmentID,IsSameDepartment,CompanyID,NewCompanyID,IsSameCompany,AssetConditionID,NewAssetConditionID,IsSameAssetCondition,Date,UserID,OrderID,\n");
        viewModel.getGetScannedAssets().observe(requireActivity(),scannedAssets->{
            for (Asset asset :scannedAssets){
                fileContent.append(asset.toFileRow()).append("\n");
            }
            checkPermission();
        });

    }

    private void observeGettingScannedAssetsStatus() {
        viewModel.getGetScannedAssetsStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status.getStatus()){
                case LOADING:
                    loadingDialog.show();
                    break;
                case SUCCESS:
                    loadingDialog.dismiss();

                    break;
                case ERROR:
                    loadingDialog.dismiss();
                    warningDialog(requireContext(),status.getStatusMessage());
                        break;
            }
        });
    }
    private StringBuilder fileContent = new StringBuilder();
    private void observeUploadingScannedAssets() {
//        fileContent.append("\uFEFFBarcode,AssetID,Description,RoomID,NewRoomID,FloorID,NewFloorID,AssetConditionID,NewAssetConditionID,SameLocation,SameCondition,UserId,OrderId,Date").append("\n");
        viewModel.getuploadDataResponse().observe(getViewLifecycleOwner(),apiResponse->{
            if (apiResponse!=null){
                boolean isSuccess = apiResponse.getSuccess();
                Log.d("apiResponse",apiResponse.getMessage());
                if (isSuccess){
                    showSuccessAlerter(apiResponse.getMessage(),getActivity());
                } else {
                    warningDialog(getContext(),apiResponse.getMessage());
                }
            }
        });
    }
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    String fileName = generateFileName();
                    Tools.writeFileOnInternalStorage(fileName,fileContent.toString(),getActivity());
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            String fileName = generateFileName();
            Tools.writeFileOnInternalStorage(fileName,fileContent.toString(),getActivity());
            Log.d(TAG, "writeFileOnInternalStorage: permission granted");
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            Log.d(TAG, "writeFileOnInternalStorage: permission not granted");
        }
    }

    private String generateFileName() {
        StringBuilder fileName = new StringBuilder();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        fileName.append("جرد ").append(arabicToDecimal(dateFormat.format(date))).append(".csv");
        Log.d("fileNAme",fileName.toString());
        return fileName.toString();
    }

    private void getUserType() {
        if (getArguments()!=null){
            if (getArguments().getString(USER_TYPE).equals("admin")){
                checkConnectivity();
                binding.loadingAssetsData.setVisibility(View.VISIBLE);
//                binding.dataSource.setVisibility(View.VISIBLE);
                binding.exportAssetsData.setVisibility(View.VISIBLE);
                binding.assetTracking.setVisibility(View.GONE);
                binding.assetSearching.setVisibility(View.GONE);
                binding.editAssetStatus.setVisibility(View.GONE);
            } else {
                binding.exportAssetsData.setVisibility(View.GONE);
                binding.loadingAssetsData.setVisibility(View.GONE);
//                binding.dataSource.setVisibility(View.GONE);
                binding.assetTracking.setVisibility(View.VISIBLE);
                binding.assetSearching.setVisibility(View.VISIBLE);
                binding.editAssetStatus.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkConnectivity() {
        String savedBaseUrl = getSavedBaseUrl();
        if (savedBaseUrl.isEmpty()){
            viewModel.changeBaseUrl("http://192.168.42.121:6010/api/AssetTracking/");
        } else {
            viewModel.changeBaseUrl(savedBaseUrl+"api/AssetTracking/");
        }
        viewModel.checkConnectivity();
        viewModel.getBaseUrlLiveData().observe(this, baseUrl -> {
            if (baseUrl != null) {
                Log.d(TAG, "checkConnectivity: baseUrl "+baseUrl);
//                viewModel.changeBaseUrl();
                saveBaseUrl(baseUrl);
                // proceed: create services via ApiFactory.createService(...)
            } else {
                Toast.makeText(requireActivity(), getString(R.string.device_is_not_connected_or_usb_tethering_is_not_enabled), Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getCheckConnectivityStatus().observe(getViewLifecycleOwner(),statusWithMessage -> {
            switch (statusWithMessage.getStatus()){
                case LOADING: loadingDialog.show(); break;
                case SUCCESS:
                case ERROR:
                    loadingDialog.dismiss(); break;
            }
        });
    }

    private void attachButtonsToListener() {
        binding.assetTracking.setOnClickListener(this);
        binding.assetSearching.setOnClickListener(this);
        binding.editAssetStatus.setOnClickListener(this);
        binding.loadingAssetsData.setOnClickListener(this);
        binding.exportAssetsData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.asset_tracking) {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_selectRoomFragment);
        } else if (id == R.id.asset_searching) {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_searchAssetsFragment);
        } else if (id == R.id.edit_asset_status) {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_editRandomAssetStatusFragment);
        } else if (id == R.id.loading_assets_data) {
//            if (binding.database.isChecked())
                Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_loadingDataFragment);
//            else
//                Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_fileLoadingDataFragment);
        } else if (id == R.id.export_assets_data) {
            getAllScannedAssets();
        }
    }

    private void getAllScannedAssets() {
        viewModel.getScannedAssets(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Tools.showToolBar((MainActivity) getActivity());
        Tools.changeTitle(getString(R.string.home_page),(MainActivity) getActivity());
    }

    private void saveBaseUrl(String baseUrl){
        saveStringDataToLocalStorage(requireActivity(),baseUrl,BASE_URL_KEY);
    }
    private String getSavedBaseUrl(){
        return getStringDataFromLocalStorage(requireActivity(),BASE_URL_KEY);
    }
}