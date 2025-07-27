package com.example.OnlineAssetTracking.Ui;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.changeTitle;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsBuilding;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsCentralDepartments;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsDepartment;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsFloor;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsGeneralDepartment;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsSector;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.warningDialog;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.Building;
import com.example.OnlineAssetTracking.Model.CentralDepartment;
import com.example.OnlineAssetTracking.Model.Department;
import com.example.OnlineAssetTracking.Model.Floor;
import com.example.OnlineAssetTracking.Model.GeneralDepartment;
import com.example.OnlineAssetTracking.Model.Sector;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.SetUpBarCodeReader;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.ViewModel.SelectRoomViewModel;
import com.example.OnlineAssetTracking.databinding.SelectRoomFragmentBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectRoomFragment extends Fragment implements View.OnKeyListener, View.OnClickListener, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener {

    public static final String USER_LOCATION = "user_location";
    public static final String ROOM_CODE = "room_code";
    public static final String SECTOR_ID = "sector_id" ;
    public static final String CENTRAL_DEPARTMENT_ID = "central_department_id";
    public static final String BUILDING_ID = "building_id";
    public static final String GENERAL_DEPARTMENT_ID = "general_department_id";
    public static final String DEPARTMENT_ID = "department_id";
    public static final String SECTOR_NAME = "sector_name";
    public static final String BUILDING_NAME = "building_name";
    private SelectRoomViewModel viewModel;
    private SetUpBarCodeReader barCodeReader;

    public static SelectRoomFragment newInstance() {
        return new SelectRoomFragment();
    }
    private SelectRoomFragmentBinding binding;
    private LoadingDialog loadingDialog;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SelectRoomFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barCodeReader = new SetUpBarCodeReader(this,this);
        loadingDialog = new LoadingDialog(getContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SelectRoomViewModel.class);
//        changeEditTextHint();
        attachButtonsToListener();

//        clearLocation();
//        viewModel.getAllLocations();
        setUpAdapters();
        clearInputLayoutError(
                binding.sectorName,
                binding.centralDepartmentName,
                binding.generalDepartmentName,
                binding.departmentName,
                binding.buildingName,
                binding.floorName,
                binding.roomCode
        );
//        setUpLocationsSpinners();
//        clearBranch();
//        clearBuilding();
//        clearSite();
//        clearFloor();

    }

    private void setUpAdapters() {
        sectorsAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, sectors);
        binding.sectorNameSpinner.setAdapter(sectorsAdapter);
        sectorsAdapter.setNotifyOnChange(true);
        centralDepartmentsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, centralDepartments);
        binding.centralDepartmentNameSpinner.setAdapter(centralDepartmentsAdapter);
        centralDepartmentsAdapter.setNotifyOnChange(true);
        generalDepartmentsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, generalDepartments);
        binding.generalDepartmentNameSpinner.setAdapter(generalDepartmentsAdapter);
        generalDepartmentsAdapter.setNotifyOnChange(true);
        departmentsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, departments);
        binding.departmentNameSpinner.setAdapter(departmentsAdapter);
        departmentsAdapter.setNotifyOnChange(true);
        buildingAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, buildings);
        binding.buildingNameSpinner.setAdapter(buildingAdapter);
        generalDepartmentsAdapter.setNotifyOnChange(true);
        floorAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, floors);
        binding.floorNameSpinner.setAdapter(floorAdapter);
        floorAdapter.setNotifyOnChange(true);
    }

    private void observeLocationDataStatus() {
        viewModel.getAllLocationStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    break;
                case SUCCESS:
                    loadingDialog.dismiss();
                    break;
                case ERROR:
                    loadingDialog.dismiss();
                    warningDialog(getContext(),getString(R.string.error_in_getting_locations));
                    break;
            }
        });
    }
    private List<UserLocation> userLocations = new ArrayList<>();
    private ArrayAdapter<Sector> sectorsAdapter;
    private ArrayAdapter<CentralDepartment> centralDepartmentsAdapter;
    private ArrayAdapter<GeneralDepartment> generalDepartmentsAdapter;
    private ArrayAdapter<Department> departmentsAdapter;
    private ArrayAdapter<Building> buildingAdapter;
    private ArrayAdapter<Floor> floorAdapter;
    private List<Sector> sectors = new ArrayList<>();
    private List<CentralDepartment> centralDepartments = new ArrayList<>();
    private List<GeneralDepartment> generalDepartments = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();
    private List<Floor> floors = new ArrayList<>();
    int selectedSectorId = -2,selectedCentralDepartmentId = -2, selectedGeneralDepartmentId =-2, selectedDepartmentId =-2,selectedBuildingId = -2,selectedFloorId=-2,selectedRoomId=-2;
    private String selectedSectorName="",selectedBuildingName="",selectedFloorName="",selectedCentralDepartment="",selectedGeneralDepartment="",selectedDepartment="",selectedRoomName="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpLocationsSpinners() {
        viewModel.getAllUserLocation().observe(getViewLifecycleOwner(),userLocations -> {
            this.userLocations = userLocations;
            Log.d(TAG, "setUpLocationsSpinners: "+userLocations.size());
            for (UserLocation userLocation:userLocations){
                Sector sector = new Sector(userLocation.getSectorId(),userLocation.getSectorName());
                if (!containsSector(sectors,userLocation.getSectorName()))
                    sectors.add(sector);
            }
            sectorsAdapter.notifyDataSetChanged();
        });
//        binding.branchNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                binding.roomBarcode.getRoot().setVisibility(View.GONE);
//                int branchId = branches.get(position).getBranchId();
//                for (UserLocation userLocation:userLocations){
//                    if (Integer.parseInt(userLocation.getBranchId())==branchId){
//                        sites.add(new Site(Integer.parseInt(userLocation.getSiteId()),userLocation.getSiteName()));
//                    }
//                }
//                sitesAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,sites);
//                binding.siteNameSpinner.setAdapter(sitesAdapter);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                binding.roomBarcode.getRoot().setVisibility(View.VISIBLE);
//            }
//        });
//
        binding.sectorNameSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedSectorId = sectors.get(position).getSectorId();
            selectedSectorName = binding.sectorNameSpinner.getText().toString().trim();
            if (selectedSectorId ==-2) {
//                binding.roomBarcodeLayout.setVisibility(View.VISIBLE);
                binding.centralDepartmentName.setVisibility(View.GONE);
            } else {
                userLocation=null;
                clearFloor();
                clearBuilding();
                clearCentralDepartment();
                clearDepartment();
                clearGeneralDepartment();
                for (UserLocation userLocation : userLocations) {
                    if (userLocation.getSectorName().trim().equals(selectedSectorName)) {
                        if (userLocation.getCentralDepartmentId()!=-1){
                            if (!containsCentralDepartments(centralDepartments,userLocation.getCentralDepartmentName().trim()))
                                centralDepartments.add(new CentralDepartment(userLocation.getCentralDepartmentId(), userLocation.getCentralDepartmentName()));
                        }
                        if (!containsBuilding(buildings,userLocation.getBuildingName().trim()))
                            buildings.add(new Building(userLocation.getBuildingId(),userLocation.getBuildingName()));
                    }
                }
                if (centralDepartments.isEmpty()){
                    binding.centralDepartmentName.setVisibility(View.GONE);
                } else
                    binding.centralDepartmentName.setVisibility(View.VISIBLE);

                centralDepartmentsAdapter.notifyDataSetChanged();
                buildingAdapter.notifyDataSetChanged();
            }
            binding.generalDepartmentName.setVisibility(View.GONE);
            binding.departmentName.setVisibility(View.GONE);
        });
        binding.centralDepartmentNameSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedCentralDepartmentId = centralDepartments.get(position).getCentralDepartmentId();
            selectedCentralDepartment = centralDepartments.get(position).getCentralDepartmentName();
            if (selectedCentralDepartmentId!=-2) {
                clearGeneralDepartment();
                clearDepartment();
                clearBuilding();
                clearFloor();
                for (UserLocation userLocation : userLocations) {
                    if (userLocation.getSectorName().equals(selectedSectorName) &&
                            (selectedCentralDepartment.isEmpty()||userLocation.getCentralDepartmentName().equals(selectedCentralDepartment))) {
                        if (userLocation.getGeneralDepartmentId()!=-1) {
                            if (!containsGeneralDepartment(generalDepartments,userLocation.getGeneralDepartmentName()))
                                generalDepartments.add(new GeneralDepartment(userLocation.getGeneralDepartmentId(), userLocation.getGeneralDepartmentName()));
                        }
                        if (!containsBuilding(buildings,userLocation.getBuildingName()))
                            buildings.add(new Building(userLocation.getBuildingId(),userLocation.getBuildingName()));
                    }
                }
                if (generalDepartments.isEmpty())
                    binding.generalDepartmentName.setVisibility(View.GONE);
                else
                    binding.generalDepartmentName.setVisibility(View.VISIBLE);
                generalDepartmentsAdapter.notifyDataSetChanged();
                buildingAdapter.notifyDataSetChanged();
                binding.departmentName.setVisibility(View.GONE);
            }
        });
        binding.generalDepartmentNameSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedGeneralDepartmentId = generalDepartments.get(position).getGeneralDepartmentId();
            selectedGeneralDepartment = binding.generalDepartmentNameSpinner.getText().toString().trim();
            if (selectedGeneralDepartmentId !=-2) {
                clearBuilding();
                clearDepartment();
                clearFloor();
                for (UserLocation userLocation : userLocations) {
                    if (userLocation.getSectorName().equals(selectedSectorName) &&
                            (selectedCentralDepartment.isEmpty()||userLocation.getCentralDepartmentName().equals(selectedCentralDepartment))&&
                            (selectedGeneralDepartment.isEmpty()||userLocation.getGeneralDepartmentName().equals(selectedGeneralDepartment))) {
                        if (userLocation.getDepartmentId()!=-1) {
                            if (!containsDepartment(departments,userLocation.getDepartmentName()))
                                departments.add(new Department(userLocation.getDepartmentId(), userLocation.getDepartmentName()));
                        }
                        if (!containsBuilding(buildings,userLocation.getBuildingName()))
                            buildings.add(new Building(userLocation.getBuildingId(), userLocation.getBuildingName()));
                    }
                }

                if (departments.isEmpty())
                    binding.departmentName.setVisibility(View.GONE);
                else
                    binding.departmentName.setVisibility(View.VISIBLE);
                departmentsAdapter.notifyDataSetChanged();
                buildingAdapter.notifyDataSetChanged();
            }
        });
        binding.departmentNameSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedDepartmentId = departments.get(position).getDepartmentId();
            if (selectedDepartmentId!=-2) {
                clearBuilding();
                clearFloor();
                for (UserLocation userLocation : userLocations) {
                    if (userLocation.getSectorName().equals(binding.sectorNameSpinner.getText().toString()) &&
                            (selectedCentralDepartment.isEmpty()||userLocation.getCentralDepartmentName().equals(selectedCentralDepartment))&&
                            (selectedGeneralDepartment.isEmpty()||userLocation.getGeneralDepartmentName().equals(selectedGeneralDepartment)) &&
                                    (selectedDepartment.isEmpty()||userLocation.getDepartmentName().equals(selectedDepartment))) {
                        if (!containsBuilding(buildings,userLocation.getBuildingName()))
                            buildings.add(new Building(userLocation.getBuildingId(), userLocation.getBuildingName()));
                    }
                }
                buildingAdapter.notifyDataSetChanged();
            }
        });
        binding.buildingNameSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedBuildingId = buildings.get(position).getBuildingId();
            selectedBuildingName = buildings.get(position).getBuildingName();
            if (selectedBuildingId!=-2){
                clearFloor();
                for (UserLocation userLocation:userLocations){
                    if (userLocation.getSectorName().equals(binding.sectorNameSpinner.getText().toString()) &&
                            (selectedCentralDepartment.isEmpty()||userLocation.getCentralDepartmentName().equals(selectedCentralDepartment))&&
                            (selectedGeneralDepartment.isEmpty()||userLocation.getGeneralDepartmentName().equals(selectedGeneralDepartment)) &&
                            (selectedDepartment.isEmpty()||userLocation.getDepartmentName().equals(selectedDepartment))&&
                            userLocation.getBuildingName().trim().equals(binding.buildingNameSpinner.getText().toString().trim())
                    ) {

                        if (!containsFloor(floors,userLocation.getFloorName()))
                            floors.add(new Floor(userLocation.getFloorId(), userLocation.getFloorName()));
                        floorAdapter.setNotifyOnChange(true);

                    }
                }
            }

        });
        binding.floorNameSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedFloorId = floors.get(position).getFloorId();
            selectedFloorName = floors.get(position).getFloorName();
        });
    }

    private void clearFloor() {
        selectedFloorId = -2;
        selectedFloorName ="";
        floors.clear();
        floorAdapter.notifyDataSetChanged();
        binding.floorNameSpinner.setText("",false);
    }

    private void clearCentralDepartment() {
        selectedCentralDepartmentId = -2;
        selectedCentralDepartment = "";
        centralDepartments.clear();
        centralDepartmentsAdapter.notifyDataSetChanged();
        binding.centralDepartmentNameSpinner.setText("",false);
    }

    private void clearGeneralDepartment() {
        selectedGeneralDepartmentId = -2;
        selectedGeneralDepartment = "";
        generalDepartments.clear();
        generalDepartmentsAdapter.notifyDataSetChanged();
        binding.generalDepartmentNameSpinner.setText("",false);
    }

    private void clearDepartment() {
        selectedDepartmentId = -2;
        selectedDepartment = "";
        departments.clear();
        departmentsAdapter.notifyDataSetChanged();
        binding.departmentNameSpinner.setText("",false);
    }

    private void clearBuilding() {
        selectedBuildingId = -2;
        selectedBuildingName = "";
        buildings.clear();
        buildingAdapter.notifyDataSetChanged();
        binding.buildingNameSpinner.setText("",false);
    }

//    private void clearFloor() {
//        departments.clear();
//        departmentsAdapter.notifyDataSetChanged();
//        binding.floorNameSpinner.setText("",false);
//    }
//
//    private void clearSite() {
//        centralDepartments.clear();
//        centralDepartmentsAdapter.notifyDataSetChanged();
//        binding.siteNameSpinner.setText("",false);
//    }


    private void observeRoomDataStatus() {
        viewModel.getRoomDataStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status) {
                case SUCCESS:
                    loadingDialog.dismiss();
                    binding.dataLayout.setVisibility(View.VISIBLE);
//                    binding.roomBarcode.barcodeInputLayout.setError(null);
                    break;
                case ERROR:
                    loadingDialog.dismiss();
//                    binding.dataLayout.setVisibility(View.GONE);
//                    binding.roomBarcode.barcodeInputLayout.setError(getString(R.string.error_in_getting_data));
                    break;
                case LOADING:
                    loadingDialog.show();
//                    binding.dataLayout.setVisibility(View.GONE);
//                    binding.roomBarcode.barcodeInputLayout.setError(null);
                    break;
            }
        });
    }
    private UserLocation userLocation;
    private void observeRoomData() {
        viewModel.getRoomDataLiveData().observe(getViewLifecycleOwner(),userLocation -> {
            this.userLocation = userLocation;
//            fillData();
        });
    }

//    private void fillData() {
//        binding.branchName.getEditText().setText(userLocation.getBranchName());
//        binding.siteName.getEditText().setText(userLocation.getSiteName());
//        binding.buildingName.getEditText().setText(userLocation.getBuildingName());
////        binding.floorName.getEditText().setText(userLocation.getFloorName());
//        binding.branchNameSpinner.setEnabled(false);
//        binding.siteNameSpinner.setEnabled(false);
//        binding.buildingNameSpinner.setEnabled(false);
//        binding.floorNameSpinner.setEnabled(false);
//    }

    private void attachButtonsToListener() {
        binding.startAudit.setOnClickListener(this);
        binding.clear.setOnClickListener(this);
        binding.roomCode.getEditText().setOnKeyListener(this);
    }

//    private void getRoomData(String roomCode) {
//        viewModel.getRoomData(roomCode);
//    }

//    private void changeEditTextHint() {
//        binding.roomBarcode.barcodeInputLayout.setHint(getString(R.string.room_code));
////        binding.branchName.barcodeInputLayout.setHint(getString(R.string.branch_name));
//        binding.branchName.setStartIconDrawable(R.drawable.ic_git_branch);
////        binding.siteName.barcodeInputLayout.setHint(getString(R.string.site_name));
//        binding.siteName.setStartIconDrawable(R.drawable.ic_location);
////        binding.buildingName.barcodeInputLayout.setHint(getString(R.string.building_name));
//        binding.buildingName.setStartIconDrawable(R.drawable.ic_building);
////        binding.floorName.barcodeInputLayout.setHint(getString(R.string.floor_name));
//        binding.floorName.setStartIconDrawable(R.drawable.floor_icon);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_audit:
//                String roomCode = "";
//                if (binding.roomBarcodeLayout.getVisibility()==View.VISIBLE){
//                    roomCode = binding.roomBarcode.barcodeInputLayout.getEditText().getText().toString().trim();
//                    if (!roomCode.isEmpty()){
//                        if (userLocation==null){
//                            binding.roomBarcode.barcodeInputLayout.setError(getString(R.string.please_enter_valid_room_code_and_press_enter));
//                        } else {
//
//                                Bundle bundle = new Bundle();
//                                bundle.putParcelable(USER_LOCATION, userLocation);
//                                bundle.putString(ROOM_CODE,roomCode);
//                                Navigation.findNavController(v).navigate(R.id.action_selectRoomFragment_to_physicalCountingFragment, bundle);
//                        }
//                    } else {
//                        binding.roomBarcode.barcodeInputLayout.setError(getString(R.string.please_scan_or_enter_room_code));
//                    }
//                } else {
//                    if (selectedDepartmentId !=-1){
//                        if (userLocation!=null) {
//                            Bundle bundle = new Bundle();
//                            bundle.putParcelable(USER_LOCATION, userLocation);
//                            bundle.putString(ROOM_CODE,roomCode);
//                            Navigation.findNavController(v).navigate(R.id.action_selectRoomFragment_to_physicalCountingFragment, bundle);
//                        }
//                    } else {
//                        binding.floorName.setError(getString(R.string.please_select_floor));
//                    }
//                }
                if (selectedSectorId !=-2){
                    if (selectedBuildingId != -2) {
                        if (selectedFloorId != -2) {
                            if (selectedRoomId != 2){
                                if (userLocation!=null) {
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable(USER_LOCATION, userLocation);
                                    bundle.putString(ROOM_CODE, binding.roomCode.getEditText().getText().toString().trim());
                                    Navigation.findNavController(v).navigate(R.id.action_selectRoomFragment_to_physicalCountingFragment, bundle);
                                } else { binding.roomCode.setError(getString(R.string.scanned_room_doesnt_match_selected_location));}
                            } else binding.roomCode.setError(getString(R.string.please_enter_a_valid_room_code));
                        } else binding.floorName.setError(getString(R.string.please_select_floor));
                    } else
                        binding.buildingName.setError(getString(R.string.please_select_a_building));
                } else
                    binding.sectorName.setError(getString(R.string.please_select_a_sector));

                break;
            case R.id.clear:
//                userLocation = null;
//                binding.roomBarcode.barcodeInputLayout.getEditText().setText("");
////                clearBranch();
//                binding.branchNameSpinner.setText("");
//                binding.branchNameSpinner.setEnabled(true);
////                binding.siteNameSpinner.setText("",false);
//                binding.siteNameSpinner.setEnabled(true);
////                binding.buildingNameSpinner.setText("",false);
//                binding.buildingNameSpinner.setEnabled(true);
////                binding.floorNameSpinner.setText("",false);
//                binding.floorNameSpinner.setEnabled(true);
//                clearSite();
//                clearBuilding();
//                clearFloor();
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_selectRoomFragment_self);
        }

    }

//    private void clearLocation() {
//        userLocation = null;
//        binding.branchNameSpinner.setText(null,false);
//        binding.siteNameSpinner.setText(null,false);
//        binding.buildingNameSpinner.setText(null,false);
//        binding.floorNameSpinner.setText(null,false);
//        binding.branchNameSpinner.setAdapter(sectorsAdapter);
//        binding.siteNameSpinner.setAdapter(centralDepartmentsAdapter);
//        binding.buildingNameSpinner.setAdapter(generalDepartmentsAdapter);
//        binding.floorNameSpinner.setAdapter(departmentsAdapter);
//        binding.branchNameSpinner.setEnabled(true);
//        binding.siteNameSpinner.setEnabled(true);
//        binding.buildingNameSpinner.setEnabled(true);
//        binding.floorNameSpinner.setEnabled(true);
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.choose_location),(MainActivity) getActivity());
        barCodeReader.onResume();
        selectedDepartmentId = -2;
        binding.sectorNameSpinner.setText("",false);
        binding.buildingNameSpinner.setText("",false);
        clearBuilding();
        viewModel.getAllLocations();
        setUpAdapters();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setUpLocationsSpinners();
        }
//        clearLocation();
        observeLocationDataStatus();
//        observeRoomData();
//        observeRoomDataStatus();
    }

//    private void clearBranch() {
////        branches.clear();
//        sectorsAdapter.notifyDataSetChanged();
//        binding.branchNameSpinner.setText("",false);
//    }

    private UserLocation selectedUserLocation;
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedCode = barCodeReader.scannedData(barcodeReadEvent).trim();
            binding.roomCode.getEditText().setText(scannedCode);
            selectedRoomId = -2;
            if (selectedSectorId!=-2) {
                if (selectedBuildingId!=-2) {
                    if (selectedFloorId!=-2) {
                        if (!scannedCode.isEmpty()) {
                            for (UserLocation userLocation : userLocations) {
                                Log.d(TAG, "onBarcodeEvent: sectorUser "+userLocation.getSectorName().trim()+" "+userLocation.getSectorName().trim().length());
                                Log.d(TAG, "onBarcodeEvent: sectorSeleted "+selectedSectorName.trim()+" "+selectedSectorName.trim().length());
                                Log.d(TAG, "onBarcodeEvent: buildingUser "+userLocation.getBuildingName().trim()+" "+userLocation.getBuildingName().trim().length());
                                Log.d(TAG, "onBarcodeEvent: buildingSeleted "+selectedBuildingName.trim()+" "+selectedBuildingName.trim().length());
                                Log.d(TAG, "onBarcodeEvent: floorUser "+userLocation.getFloorName().trim()+" "+userLocation.getFloorName().trim().length());
                                Log.d(TAG, "onBarcodeEvent: floorSeleted "+selectedFloorName.trim()+" "+selectedFloorName.trim().length());
                                Log.d(TAG, "onBarcodeEvent: roomUser "+userLocation.getRoomCode().trim()+" "+userLocation.getRoomCode().trim().length());
                                Log.d(TAG, "onBarcodeEvent: roomSeleted "+scannedCode.trim()+" "+scannedCode.trim().length());

                                if (userLocation.getSectorId()==selectedSectorId &&
//                                        (selectedCentralDepartment.isEmpty() || userLocation.getCentralDepartmentName().equals(selectedCentralDepartment)) &&
//                                        (selectedGeneralDepartment.isEmpty() || userLocation.getGeneralDepartmentName().equals(selectedGeneralDepartment)) &&
//                                        (selectedDepartment.isEmpty() || userLocation.getDepartmentName().equals(selectedDepartment)) &&
                                        userLocation.getBuildingId()==selectedBuildingId &&
                                        userLocation.getFloorId()==selectedFloorId&&
                                        Objects.equals(userLocation.getRoomCode(), scannedCode.trim())
                                ) {
                                    selectedRoomName = userLocation.getRoomName();
                                    selectedRoomId = userLocation.getRoomId();
                                    this.userLocation = userLocation;
                                    break;
                                }

                            }
                        } else
                            binding.roomCode.setError(getString(R.string.please_enter_a_valid_room_code));
                    } else binding.floorName.setError(getString(R.string.please_select_floor));
                } else binding.buildingName.setError(getString(R.string.please_select_a_building));
            } else binding.sectorName.setError(getString(R.string.please_select_a_sector));
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
        {
            String roomCode = binding.roomCode.getEditText().getText().toString().trim();
            selectedRoomId = -2;
            if (selectedSectorId!=-2) {
                if (selectedBuildingId!=-2) {
                    if (selectedFloorId!=-2) {
                        if (!roomCode.isEmpty()) {
                            for (UserLocation userLocation : userLocations) {
                                if (userLocation.getSectorId()==selectedSectorId &&
//                                        (selectedCentralDepartment.isEmpty() || userLocation.getCentralDepartmentName().equals(selectedCentralDepartment)) &&
//                                        (selectedGeneralDepartment.isEmpty() || userLocation.getGeneralDepartmentName().equals(selectedGeneralDepartment)) &&
//                                        (selectedDepartment.isEmpty() || userLocation.getDepartmentName().equals(selectedDepartment)) &&
                                        userLocation.getBuildingId()==selectedBuildingId &&
                                        userLocation.getFloorId()==selectedFloorId&&
                                        userLocation.getRoomCode().trim().equals(roomCode)
                                ) {
                                    selectedRoomName = userLocation.getRoomName();
                                    selectedRoomId = userLocation.getRoomId();
                                    this.userLocation = userLocation;
                                    break;
                                }
//                                else binding.roomCode.setError(getString(R.string.scanned_room_doesnt_match_selected_location));
                            }
                        } else
                            binding.roomCode.setError(getString(R.string.please_enter_a_valid_room_code));
                    } else binding.floorName.setError(getString(R.string.please_select_floor));
                } else binding.buildingName.setError(getString(R.string.please_select_a_building));
            } else binding.sectorName.setError(getString(R.string.please_select_a_sector));
            return true;
        }
        return false;
    }

}