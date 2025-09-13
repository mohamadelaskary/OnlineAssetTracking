package com.example.OnlineAssetTracking.Ui;

import static com.example.OnlineAssetTracking.MyMethods.Tools.changeTitle;
import static com.example.OnlineAssetTracking.MyMethods.Tools.clearInputLayoutError;
import static com.example.OnlineAssetTracking.MyMethods.Tools.containsBuilding;
import static com.example.OnlineAssetTracking.MyMethods.Tools.containsCentralDepartments;
import static com.example.OnlineAssetTracking.MyMethods.Tools.containsCompany;
import static com.example.OnlineAssetTracking.MyMethods.Tools.containsDepartment;
import static com.example.OnlineAssetTracking.MyMethods.Tools.containsFloor;
import static com.example.OnlineAssetTracking.MyMethods.Tools.containsGeneralDepartment;
import static com.example.OnlineAssetTracking.MyMethods.Tools.containsRoom;
import static com.example.OnlineAssetTracking.MyMethods.Tools.containsSector;
import static com.example.OnlineAssetTracking.MyMethods.Tools.getEditTextText;
import static com.example.OnlineAssetTracking.MyMethods.Tools.warningDialog;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.Building;
import com.example.OnlineAssetTracking.Model.CentralDepartment;
import com.example.OnlineAssetTracking.Model.Company;
import com.example.OnlineAssetTracking.Model.Department;
import com.example.OnlineAssetTracking.Model.Floor;
import com.example.OnlineAssetTracking.Model.GeneralDepartment;
import com.example.OnlineAssetTracking.Model.Room;
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
                binding.companyName,
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
        companiesAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,companies);
        binding.companyNameSpinner.setAdapter(companiesAdapter);
        companiesAdapter.setNotifyOnChange(true);
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
        roomAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, rooms);
        binding.roomCodeSpinner.setAdapter(roomAdapter);
        roomAdapter.setNotifyOnChange(true);

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
    private ArrayAdapter<Company> companiesAdapter;
    private ArrayAdapter<CentralDepartment> centralDepartmentsAdapter;
    private ArrayAdapter<GeneralDepartment> generalDepartmentsAdapter;
    private ArrayAdapter<Department> departmentsAdapter;
    private ArrayAdapter<Building> buildingAdapter;
    private ArrayAdapter<Floor> floorAdapter;
    private ArrayAdapter<Room> roomAdapter;
    private List<Sector> sectors = new ArrayList<>();
    private List<Company> companies = new ArrayList<>();
    private List<CentralDepartment> centralDepartments = new ArrayList<>();
    private List<GeneralDepartment> generalDepartments = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();
    private List<Floor> floors = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    int selectedCompanyId = -2,selectedCentralDepartmentId = -2, selectedGeneralDepartmentId =-2, selectedDepartmentId =-2,selectedBuildingId = -2,selectedFloorId=-2,selectedRoomId=-2;
    private String selectedCompanyName="",selectedBuildingName="",selectedFloorName="",selectedCentralDepartment="",selectedGeneralDepartment="",selectedDepartment="",selectedRoomName="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpLocationsSpinners() {
        viewModel.getAllUserLocation().observe(getViewLifecycleOwner(),userLocations -> {
            this.userLocations = userLocations;
            companies.clear();
            for (UserLocation userLocation:userLocations){
                Company company = new Company(userLocation.getCompanyId(),userLocation.getCompanyName());
                if (!containsCompany(companies,userLocation.getCompanyName()))
                    companies.add(company);
//
            }
            companiesAdapter.notifyDataSetChanged();
        });

        binding.companyNameSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedCompanyId = companies.get(position).getCompanyId();
            selectedCompanyName = binding.companyNameSpinner.getText().toString().trim();
            binding.roomCodeSpinner.setText("",false);
            rooms.clear();
            for (UserLocation userLocation:userLocations){
                if (userLocation.getCompanyId().equals(selectedCompanyId)){
                    if (!containsRoom(rooms, userLocation.getRoomCode())){
                        Room room = new Room(userLocation.getRoomId(), userLocation.getRoomCode(),userLocation.getRoomName());
                        rooms.add(room);
                    }
                }
            }
            roomAdapter.notifyDataSetChanged();
        });

    }



    private void clearRoom() {
        rooms.clear();
        roomAdapter.notifyDataSetChanged();
        binding.roomCodeSpinner.setText("",false);
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
        int id = v.getId();
        if (id == R.id.start_audit) {
            String roomCode = getEditTextText(binding.roomCode);
                if (!roomCode.isEmpty()){
                    boolean validRoomCode = false;
                    for (UserLocation userLocation1:userLocations){
                        if (userLocation1.getRoomCode().equals(roomCode)){
                            validRoomCode = true;
                            userLocation = userLocation1;
                            break;
                        }
                    }
                    if (validRoomCode){
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(USER_LOCATION, userLocation);
                        bundle.putString(ROOM_CODE, binding.roomCode.getEditText().getText().toString().trim());
                        Navigation.findNavController(v).navigate(R.id.action_selectRoomFragment_to_physicalCountingFragment, bundle);
                    } else {
                        binding.roomCode.setError(getString(R.string.wrong_room_code));
                    }
                }

        } else if (id == R.id.clear) {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_selectRoomFragment_self);
        }

    }



    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.choose_location),(MainActivity) getActivity());
        barCodeReader.onResume();
        selectedDepartmentId = -2;
        binding.companyNameSpinner.setText("",false);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        requireActivity().runOnUiThread(() -> {
            String scannedCode = barCodeReader.scannedData(barcodeReadEvent).trim();
            if (!scannedCode.isEmpty()) {
                for(UserLocation userLocation2:userLocations) {
                    if (userLocation2.getRoomCode().trim().equals(scannedCode)) {
                        binding.companyNameSpinner.setText(userLocation2.getCompanyName(),false);
                        binding.roomCodeSpinner.setText(userLocation2.getRoomCode(),false);
                        break;
                    } else {
                        binding.roomCode.setError(getString(R.string.wrong_room_code));
                    }
                }
            } else {
                binding.roomCode.setError(getString(R.string.please_enter_a_valid_room_code));
            }
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
            if (!roomCode.isEmpty()) {
                for(UserLocation userLocation2:userLocations) {
                    if (userLocation2.getRoomCode().trim().equals(roomCode)) {
                        binding.companyNameSpinner.setText(userLocation2.getCompanyName(),false);
                        binding.roomCodeSpinner.setText(userLocation2.getRoomCode(),false);
                        break;
                    } else {
                        binding.roomCode.setError(getString(R.string.wrong_room_code));
                    }
                }
            } else {
                binding.roomCode.setError(getString(R.string.please_enter_a_valid_room_code));
            }
            return true;
        }
        return false;
    }

}