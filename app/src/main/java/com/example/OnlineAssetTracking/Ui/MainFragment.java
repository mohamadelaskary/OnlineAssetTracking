package com.example.OnlineAssetTracking.Ui;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.arabicToDecimal;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.showErrorAlerter;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.showLoadingDialog;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.todayDate;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.warningDialog;
import static com.example.OnlineAssetTracking.Ui.SignInFragment.USER_TYPE;

import android.Manifest;
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

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetTrackingDataBase;
import com.example.OnlineAssetTracking.DataBase.DataBase;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.MyMethods;
import com.example.OnlineAssetTracking.MyMethods.ReadWriteExcelSheet;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.ViewModel.MainFragmentViewModel;
import com.example.OnlineAssetTracking.databinding.FragmentMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainFragment extends Fragment implements View.OnClickListener {


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
        loadingDialog = MyMethods.showLoadingDialog(getContext());
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
    String[][] fileData;
    private void observeGettingScannedAssets() {
        viewModel.getGetScannedAssets().observe(requireActivity(),scannedAssets->{
            fileData = new String[scannedAssets.size()][8];
            setHeaderData(fileData);
            for (int i = 0; i < scannedAssets.size()-1; i++) {
                Asset asset = scannedAssets.get(i);
//                fileContent.append(asset.toFileRow()).append("\n");
                fileData[i+1][0] = asset.getBarcode();
                fileData[i+1][1] = asset.getCompanyId();
                fileData[i+1][2] = asset.getRoomId();
                fileData[i+1][3] = asset.getDescription();
                fileData[i+1][4] = asset.getScanStatus();
                fileData[i+1][5] = asset.getSerialNumber();
                fileData[i+1][6] = asset.getUserId();
                fileData[i+1][7] = asset.getDate();
                asset.setExported(true);
            }
            checkPermission();
            dataBase.dao().updateAllStatus().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) {
                    loadingDialog.show();
                }

                @Override
                public void onComplete() {
                    loadingDialog.dismiss();
//                    showSuccessAlerter(getString(R.string.saved_successfully),requireActivity());
                }

                @Override
                public void onError(Throwable e) {
                    loadingDialog.dismiss();
                    showErrorAlerter(getString(R.string.error_while_saving_file),requireActivity());
                }
            });
        });

    }

    private void setHeaderData(String[][] fileData) {
        fileData[0][0]="Equipment";
        fileData[0][1] = "Plnt";
        fileData[0][2] = "SLoc";
        fileData[0][3] = "Equip Desc";
        fileData[0][4] = "Scan Status";
        fileData[0][5] = "Serial Number";
        fileData[0][6] = "Employee ID";
        fileData[0][7] = "Last Seen";
    }

    private void observeGettingScannedAssetsStatus() {
        viewModel.getGetScannedAssetsStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    break;
                case SUCCESS:
                case ERROR:
                    loadingDialog.dismiss();
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
//                    MyMethods.writeFileOnInternalStorage(fileName,fileContent.toString(),getActivity());
                    ReadWriteExcelSheet.createEncryptedExcel(todayDate(),fileData,"222",todayDate(),requireActivity());
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
//            MyMethods.writeFileOnInternalStorage(fileName,fileContent.toString(),getActivity());
            ReadWriteExcelSheet.createEncryptedExcel(todayDate(),fileData,"222",todayDate(), requireActivity());
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
                binding.editAssetStatus.setVisibility(View.GONE);
            }
        }
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
//                Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_loadingDataFragment);
//            else
                Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_fileLoadingDataFragment);
        } else if (id == R.id.export_assets_data) {
            getAllScannedAssets();
        }
    }

    private void getAllScannedAssets() {
        viewModel.getScannedAssets(binding.file.isChecked());
    }

    @Override
    public void onResume() {
        super.onResume();
        MyMethods.showToolBar((MainActivity) getActivity());
        MyMethods.changeTitle(getString(R.string.home_page),(MainActivity) getActivity());
    }
}