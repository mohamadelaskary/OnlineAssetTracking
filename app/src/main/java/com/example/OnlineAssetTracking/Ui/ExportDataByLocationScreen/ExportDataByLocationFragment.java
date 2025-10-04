package com.example.OnlineAssetTracking.Ui.ExportDataByLocationScreen;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.arabicToDecimal;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.changeTitle;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsCompany;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsRoom;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.showErrorAlerter;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.showLoadingDialog;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.todayDate;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.warningDialog;
import static com.example.OnlineAssetTracking.Ui.PhysicalCountingFragment.DIFFERENT_LOCATION_USER_APPROVED;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.Company;
import com.example.OnlineAssetTracking.Model.Room;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.ReadWriteExcelSheet;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.Ui.MainActivity;
import com.example.OnlineAssetTracking.databinding.FragmentExportDataByLocationBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ExportDataByLocationFragment extends Fragment {

    private ExportDataByLocationViewModel viewModel;

    public static ExportDataByLocationFragment newInstance() {
        return new ExportDataByLocationFragment();
    }

    private LoadingDialog loadingDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExportDataByLocationViewModel.class);
        loadingDialog = showLoadingDialog(requireContext());
    }
    private FragmentExportDataByLocationBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentExportDataByLocationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeLiveData();
        binding.export.setOnClickListener(v->{
            checkPermission();
        });
    }
    private Room selectedRoom = null;
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    if (selectedRoom!=null){
                        viewModel.getAssetsByLocation(selectedRoom.getRoomId());
                    } else {
                        warningDialog(requireContext(),getString(R.string.please_select_the_location));
                    }
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    warningDialog(requireContext(),getString(R.string.you_must_give_the_application_the_permission_to_be_able_to_export_excel_sheet));
                }
            });


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+
            if (Environment.isExternalStorageManager()) {
                Log.d(TAG, "checkPermission: MANAGE_EXTERNAL_STORAGE granted");
                if (selectedRoom!=null){
                    viewModel.getAssetsByLocation(selectedRoom.getRoomId());
                } else {
                    warningDialog(requireContext(),getString(R.string.please_select_the_location));
                }
            } else {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.setData(Uri.parse("package:" + requireContext().getPackageName()));
                    startActivity(intent);
                } catch (Exception e) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+
            if (ContextCompat.checkSelfPermission(
                    getContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "checkPermission: permission granted");
                if (selectedRoom!=null){
                    viewModel.getAssetsByLocation(selectedRoom.getRoomId());
                } else {
                    warningDialog(requireContext(),getString(R.string.please_select_the_location));
                }
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
            // Android 10 وأقل
            if (ContextCompat.checkSelfPermission(
                    getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "checkPermission: permission granted");
                if (selectedRoom!=null){
                    viewModel.getAssetsByLocation(selectedRoom.getRoomId());
                } else {
                    warningDialog(requireContext(),getString(R.string.please_select_the_location));
                }
            } else {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    private String generateFileName(Room selectedRoom) {
        StringBuilder fileName = new StringBuilder();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm");
        fileName.append("AssetTrackingLocation").append(selectedRoom.getRoomName()).append("-").append(arabicToDecimal(dateFormat.format(date)));
        return fileName.toString();
    }

    private void observeLiveData() {
        observeGettingLocations();
        observeGettingAssets();
        observeCreatingFile();
    }

    private void observeCreatingFile() {
        viewModel.getCreateFileStatus().observe(getViewLifecycleOwner(),statusWithMessage -> {
            switch (statusWithMessage.getStatus()){
                case LOADING: loadingDialog.show(); break;
                case SUCCESS:
                    loadingDialog.hide();
                    showSuccessAlerter(statusWithMessage.getStatusMessage(),requireActivity());
                    viewModel.setAssetsAsExported(selectedRoom.getRoomId());
                    Log.d(TAG, "observeCreatingFile: "+viewModel.filePath);

                    break;
                case ERROR:
                    loadingDialog.hide();
                    showErrorAlerter(statusWithMessage.getStatusMessage(), requireActivity());
                    break;
            }
        });
    }

    private List<Asset> selectedLocationAssets = new ArrayList<>();
    private void observeGettingAssets() {
        viewModel.getGetAssetsStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING: loadingDialog.show(); break;
                case SUCCESS: loadingDialog.hide(); break;
                case ERROR:
                    loadingDialog.hide();
                    warningDialog(requireContext(),getString(R.string.error_in_getting_assets));
            }
        });
        viewModel.getGetAssets().observe(getViewLifecycleOwner(),assets->{
            selectedLocationAssets.clear();
            Log.d(TAG, "observeGettingAssets: "+assets.size());
            if (!assets.isEmpty()) {
                selectedLocationAssets = assets;
                viewModel.createExcelSheet(getAssetArray(assets), generateFileName(selectedRoom));
            } else
                warningDialog(requireContext(),getString(R.string.no_assets_in_selected_location));
        });
    }

    private String[][] getAssetArray(List<Asset> assets) {
        String[][] fileData = new String[assets.size()+1][8];
        setHeaderData(fileData);
        for (int i = 0; i < assets.size(); i++) {
            Asset asset = assets.get(i);
            fileData[i+1][0] = asset.getBarcode();
            fileData[i+1][1] = asset.getScanStatus().equals(DIFFERENT_LOCATION_USER_APPROVED)?asset.getNewCompanyId():asset.getCompanyId();
            fileData[i+1][2] = asset.getScanStatus().equals(DIFFERENT_LOCATION_USER_APPROVED)?asset.getNewRoomId():asset.getRoomId();
            fileData[i+1][3] = asset.getDescription();
            fileData[i+1][4] = asset.getScanStatus();
            fileData[i+1][5] = asset.getSerialNumber();
            fileData[i+1][6] = asset.getUserId();
            fileData[i+1][7] = asset.getDate();
            asset.setExported(true);
        }
        return fileData;
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

    private List<Company> companiesList = new ArrayList<>();
    private List<UserLocation> locationList = new ArrayList<>();
    private void observeGettingLocations() {
        viewModel.getAllLocationStatus().observe(getViewLifecycleOwner(),status -> {
            if (Objects.requireNonNull(status) == Status.LOADING) {
                loadingDialog.show();
            } else if (status == Status.SUCCESS) {
                loadingDialog.hide();
            } else if (status == Status.ERROR) {
                loadingDialog.hide();
                warningDialog(requireContext(), getString(R.string.error_in_getting_locations));
            }
        });
        viewModel.getAllUserLocation().observe(getViewLifecycleOwner(),userLocations -> {
            locationList = userLocations;
            setUpCompaniesSpinner();
        });
    }


    private void setUpCompaniesSpinner() {
        companiesList.clear();
        for(UserLocation location:locationList){
            if (!containsCompany(companiesList, location.getCompanyId()))
                companiesList.add(new Company(location.getCompanyId(),location.getCompanyName()));
        }
        ArrayAdapter<Company> companyArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,companiesList);
        binding.companyNameSpinner.setAdapter(companyArrayAdapter);
        binding.companyNameSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
            setUpRoomsSpinner(companiesList.get(i).getCompanyId());
        });
    }
    private List<Room> roomsList = new ArrayList<>();

    private void setUpRoomsSpinner(String companyId) {
        roomsList.clear();
        for (UserLocation location:locationList){
            if (location.getCompanyId().equals(companyId)){
                if (!containsRoom(roomsList,location.getRoomId()))
                    roomsList.add(new Room(location.getRoomId(),location.getRoomName()));
            }
        }
        ArrayAdapter<Room> roomArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,roomsList);
        binding.roomCodeSpinner.setAdapter(roomArrayAdapter);
        binding.roomCodeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRoom = roomsList.get(i);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.export_assets_data),(MainActivity) requireActivity());
        viewModel.getAllLocations();
    }
}