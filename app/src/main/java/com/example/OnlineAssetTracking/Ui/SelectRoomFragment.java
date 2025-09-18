package com.example.OnlineAssetTracking.Ui;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.changeTitle;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsBuilding;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsCentralDepartments;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsCompany;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsDepartment;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsFloor;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsGeneralDepartment;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.containsRoom;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.Building;
import com.example.OnlineAssetTracking.Model.CentralDepartment;
import com.example.OnlineAssetTracking.Model.Company;
import com.example.OnlineAssetTracking.Model.Department;
import com.example.OnlineAssetTracking.Model.Floor;
import com.example.OnlineAssetTracking.Model.GeneralDepartment;
import com.example.OnlineAssetTracking.Model.Room;
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
                binding.companyName,
                binding.centralDepartmentName,
                binding.generalDepartmentName,
                binding.departmentName,
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
        companiesAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, companies);
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
    private ArrayAdapter<Company> companiesAdapter;
    private ArrayAdapter<CentralDepartment> centralDepartmentsAdapter;
    private ArrayAdapter<GeneralDepartment> generalDepartmentsAdapter;
    private ArrayAdapter<Department> departmentsAdapter;
    private ArrayAdapter<Building> buildingAdapter;
    private ArrayAdapter<Floor> floorAdapter;
    private ArrayAdapter<Room> roomAdapter;
    private List<Company> companies = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<CentralDepartment> centralDepartments = new ArrayList<>();
    private List<GeneralDepartment> generalDepartments = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();
    private List<Floor> floors = new ArrayList<>();
    String selectedCompanyId = "",selectedRoomId="";
    int selectedCentralDepartmentId = -2, selectedGeneralDepartmentId =-2, selectedDepartmentId =-2,selectedBuildingId = -2,selectedFloorId=-2;
    private String selectedSectorName="",selectedBuildingName="",selectedFloorName="",selectedCentralDepartment="",selectedGeneralDepartment="",selectedDepartment="",selectedRoomName="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpLocationsSpinners() {
        viewModel.getAllUserLocation().observe(getViewLifecycleOwner(),userLocations -> {
            this.userLocations = userLocations;
            clearCompany();
            for (UserLocation userLocation:userLocations){
                if (!containsCompany(companies,userLocation.getCompanyId())) {
                    Company company = new Company(userLocation.getCompanyId(),userLocation.getCompanyName());
                    companies.add(company);
                }
            }
            companiesAdapter.notifyDataSetChanged();
        });

        binding.companyNameSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedCompanyId = companies.get(position).getCompanyId();
            clearRoom();
            for (UserLocation userLocation : userLocations) {
                if (userLocation.getCompanyId().trim().equals(selectedCompanyId)) {
                    if (!containsRoom(rooms, userLocation.getRoomId())){
                        Room room = new Room(userLocation.getRoomId(), userLocation.getRoomName());
                        rooms.add(room);
                        roomAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        binding.roomCodeSpinner.setOnItemClickListener((adapterView, view, index, l) -> {
            selectedRoomId = rooms.get(index).getRoomId();
            for (UserLocation userLocation1:userLocations){
                if (userLocation1.getRoomId().equals(selectedRoomId)&&userLocation1.getCompanyId().equals(selectedCompanyId)){
                    userLocation = userLocation1;
                }
            }
        });
    }

    private void clearRoom() {
        selectedRoomId = "";
        rooms.clear();
        roomAdapter.notifyDataSetChanged();
        binding.roomCodeSpinner.setText("",false);
    }


    private void clearCompany() {
        selectedCompanyId = "";
        companies.clear();
        companiesAdapter.notifyDataSetChanged();
        binding.companyNameSpinner.setText("",false);
        selectedRoomId = "";

    }


    private UserLocation userLocation;

    private void attachButtonsToListener() {
        binding.startAudit.setOnClickListener(this);
        binding.clear.setOnClickListener(this);
        binding.roomCode.getEditText().setOnKeyListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.start_audit) {
            if (!selectedCompanyId.isEmpty()) {
                if (!selectedRoomId.isEmpty()) {
                    if (userLocation != null) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(USER_LOCATION, userLocation);
                        bundle.putString(ROOM_CODE, binding.roomCode.getEditText().getText().toString().trim());
                        Navigation.findNavController(v).navigate(R.id.action_selectRoomFragment_to_physicalCountingFragment, bundle);
                    } else {
                        binding.roomCode.setError(getString(R.string.scanned_room_doesnt_match_selected_location));
                    }
                } else
                    binding.roomCode.setError(getString(R.string.please_select_location));
            } else binding.companyName.setError(getString(R.string.please_select_plant));
        } else if (id == R.id.clear) {//                userLocation = null;
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
        clearCompany();
        clearRoom();
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

            return true;
        }
        return false;
    }

}