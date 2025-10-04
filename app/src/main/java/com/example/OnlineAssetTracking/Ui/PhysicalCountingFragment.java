package com.example.OnlineAssetTracking.Ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.getEditTextText;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.multipleChoiceConfirmationDialog;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.warningDialog;
import static com.example.OnlineAssetTracking.Ui.SelectRoomFragment.ROOM_CODE;
import static com.example.OnlineAssetTracking.Ui.SelectRoomFragment.USER_LOCATION;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.example.OnlineAssetTracking.Adapters.AssetConditionsAdapter;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.AssetWithUserLocation;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.User;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.MyMethods;
import com.example.OnlineAssetTracking.MyMethods.SetUpBarCodeReader;
import com.example.OnlineAssetTracking.ViewModel.PhysicalCountingViewModel;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.databinding.PhysicalCountingFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class PhysicalCountingFragment extends Fragment implements AssetConditionsAdapter.OnAssetConditionSelected, View.OnClickListener, View.OnKeyListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {

    public static final String ASSET_DATA = "ASSET_DATA" ;
    public static final String SAME_LOCATION = "T";
    public static final String DIFFERENT_LOCATION_USER_APPROVED = "L";
    public static final String DIFFERENT_LOCATION_USER_DECLINED = "F";

    private PhysicalCountingViewModel viewModel;

    PhysicalCountingFragmentBinding binding;
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = PhysicalCountingFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barCodeReader = new SetUpBarCodeReader(this,this);
        loadingDialog = MyMethods.showLoadingDialog(getContext());
    }
    private LoadingDialog loadingDialog;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PhysicalCountingViewModel.class);
        setTexts();
        attachListener();
        getRoomData();
        fillRoomData();
        observeGettingAssetData();
        observeGettingAssetDataStatus();
        handleOnTextChange();
        setUpAssetConditionsRecyclerView();
        getAssetConditions();
        observeGettingAssetConditions();
        setUpBottomSheet();
        observeSaveScannedAsset();
        observeGettingUsersList();
        setOnEmployeeClicked();
        final Boolean shouldSave;

    }
    private User selectedUser = null;
    private void setOnEmployeeClicked() {
        binding.employeeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (User user:usersList) {
                    if (user.getEmployeeId().equals(getEditTextText(binding.employee))) {
                        selectedUser = user;
                        break;
                    }
                }
                if (selectedUser!=null){
                    binding.employeeSpinner.setText(selectedUser.getEmployeeId(),false);
                    if (!selectedUser.getEmployeeName().isEmpty()){
                        binding.employeeName.setText(selectedUser.getEmployeeName());
                        binding.employeeName.setVisibility(VISIBLE);
                    } else {
                        binding.employeeName.setVisibility(GONE);
                    }
                } else {
                    binding.employeeSpinner.setText("",false);
                }
            }
        });
    }

    private ArrayAdapter usersAdapter;
    private List<User> usersList = new ArrayList<>();
    private void observeGettingUsersList() {
        viewModel.getGettingUsersListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                loadingDialog.show();
            } else if (status.equals(Status.SUCCESS)) {
                loadingDialog.dismiss();
            } else {
                loadingDialog.dismiss();
                warningDialog(requireContext(),"No employees added");
            }
        });
        viewModel.getGettingUsersList().observe(getViewLifecycleOwner(),users -> {
            usersList = users;
            usersAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_expandable_list_item_1,usersList);
            binding.employeeSpinner.setAdapter(usersAdapter);
        });
    }

    private void observeSaveScannedAsset() {
        viewModel.getSaveAssetStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    break;
                case SUCCESS:
                    loadingDialog.dismiss();
                    MyMethods.showSuccessAlerter(getString(R.string.saved_successfully),getActivity());
//                    binding.dataLayout.setVisibility(View.GONE);
//                    binding.assetCode.getEditText().setText("");
                    break;
                case ERROR:
                    loadingDialog.dismiss();
                    break;

            }
        });
    }

    private BottomSheetBehavior assetConditionsBottomSheetBehavior;
    private void setUpBottomSheet() {
        assetConditionsBottomSheetBehavior = BottomSheetBehavior.from(binding.assetConditionBottomSheet.getRoot());
        hideBottomSheet();
        binding.assetConditionBottomSheet.save.setOnClickListener(v -> {
            newAssetStatus = selectedAssetCondition;
            hideBottomSheet();
            handleAssetConditionChange();

        });

        binding.assetConditionBottomSheet.cancel.setOnClickListener(v->{
            newAssetStatus = null;
            hideBottomSheet();
            handleAssetConditionChange();
        });
    }

    private void handleAssetConditionChange() {
        if (newAssetStatus!=null&&selectedAssetCondition!=null) {
            if (!newAssetStatus.getAssetConditionName().equals(assetWithUserLocation.getAssetConditionName().replace("\"",""))) {
                binding.assetStatusDesc.oldAssetStatus.setVisibility(VISIBLE);
                binding.assetStatusDesc.oldAssetStatus.setText(assetWithUserLocation.getAssetConditionName());
                binding.assetStatusDesc.newAssetStatus.setText(newAssetStatus.getAssetConditionName());
            } else {
                binding.assetStatusDesc.newAssetStatus.setText(assetWithUserLocation.getAssetConditionName());
                binding.assetStatusDesc.oldAssetStatus.setVisibility(GONE);
            }
        } else {
            binding.assetStatusDesc.newAssetStatus.setText(assetWithUserLocation.getAssetConditionName());
            binding.assetStatusDesc.oldAssetStatus.setVisibility(GONE);
        }
    }

    private void hideBottomSheet() {
        assetConditionsBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        binding.disableColor.setVisibility(GONE);
    }

    private void showBottomSheet(){
        assetConditionsBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        binding.disableColor.setVisibility(VISIBLE);
    }

    private void observeGettingAssetConditions() {
        viewModel.getGettingAssetConditions().observe(getViewLifecycleOwner(),assetConditions -> adapter.setAssetConditions(assetConditions));
    }


    private void getAssetConditions() {
        viewModel.getAssetConditions();
    }

    private AssetConditionsAdapter adapter;
    private void setUpAssetConditionsRecyclerView() {
        adapter = new AssetConditionsAdapter(getContext(),this);
        binding.assetConditionBottomSheet.assetConditions.setAdapter(adapter);
    }

    private void handleOnTextChange() {
        MyMethods.clearInputLayoutError(binding.assetCode);
    }

    private void observeGettingAssetDataStatus() {
        statusObserver = status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    binding.dataLayout.setVisibility(GONE);
                    binding.assetCode.setError(null);
                    break;
                case IDLE:
                    loadingDialog.dismiss();
                    binding.dataLayout.setVisibility(GONE);
                    binding.assetCode.setError(null);
                    break;
                case SUCCESS:
                    loadingDialog.dismiss();
                    binding.dataLayout.setVisibility(VISIBLE);
                    binding.assetCode.setError(null);
                    break;
                case ERROR:
                    loadingDialog.dismiss();
                    binding.dataLayout.setVisibility(GONE);
                    binding.assetCode.setError(getString(R.string.asset_not_found));
                    break;
            }
        };
        viewModel.getGettingAssetDataStatus().observe(getViewLifecycleOwner(), statusObserver);
    }
    private AssetWithUserLocation assetWithUserLocation;
    private void observeGettingAssetData() {
        viewModel.getAssetDataLiveData().observe(getViewLifecycleOwner(),asset -> {
            this.assetWithUserLocation = asset;
//            getNewAssetStatus();
            fillAssetData();
        });
    }

//    private void getNewAssetStatus() {
//        if (getArguments().getParcelable(NEW_ASSET_CONDITION)!=null){
//            AssetCondition newAssetCondition = getArguments().getParcelable(NEW_ASSET_CONDITION);
//            newAssetStatus = newAssetCondition.getAssetConditionName();
//        }
//    }

    private AssetCondition newAssetStatus;
    private Asset asset;
    private void fillAssetData() {
        asset = new Asset(
                assetWithUserLocation.getAssetId(),
                assetWithUserLocation.getBarcode(),
                assetWithUserLocation.getCompanyId(),
                assetWithUserLocation.getRoomId(),
                assetWithUserLocation.getDescription(),
                assetWithUserLocation.getScanStatus(),
                assetWithUserLocation.getSerialNumber(),
                assetWithUserLocation.getUserId()
        );
        binding.assetNo.barcodeInputLayout.getEditText().setText(assetWithUserLocation.getSerialNumber());
        binding.assetDescription.mainCategory.setText(assetWithUserLocation.getMainCategoryName());
        binding.assetDescription.subCategory.setText(assetWithUserLocation.getSubCategory2Name());
        binding.assetDescription.assetDescription.setText(assetWithUserLocation.getDescription());
        selectedUser = null;
        if (!assetWithUserLocation.getUserId().isEmpty()) {
            binding.employeeSpinner.setText(assetWithUserLocation.getUserId(),false);
        }else {
            binding.employeeSpinner.setText("",false);
        }
        if (assetWithUserLocation.getEmployeeName()!=null) {
            binding.employeeName.setText(assetWithUserLocation.getEmployeeName());
            binding.employeeName.setVisibility(VISIBLE);
        }else {
            binding.employeeName.setText("");
            binding.employeeName.setVisibility(GONE);
        }

        if (assetWithUserLocation.getFileBasse()!=null) {
            Glide.with(getContext())
                    .load(assetWithUserLocation.getFileBasse())
                    .into(binding.assetDescription.assetImage);
            binding.assetDescription.assetImage.setVisibility(VISIBLE);
            binding.assetDescription.assetImage.invalidate();
        }
        else
            binding.assetDescription.assetImage.setVisibility(GONE);
        handleAssetConditionChange();
        if (!assetWithUserLocation.isScanned()) {
            if (assetWithUserLocation.getRoomId().equals(userLocation.getRoomId())) {
                saveScannedAssetData(null, true);
            } else {
                MultipleChoiceConfirmationDialog multipleChoiceConfirmationDialog = new MultipleChoiceConfirmationDialog(
                        requireContext(),
                        getString(R.string.wrong_location),
                        getString(R.string.this_asset_should_be_in)+ " " +assetWithUserLocation.getCompanyName()+" - "+ assetWithUserLocation.getRoomName()+ "! " +getString(R.string.do_you_want_to_change_location),
                        getString(R.string.accept),
                        getString(R.string.decline),
                        new MultipleChoiceConfirmationDialog.OnDialogButtonsClicked() {
                            @Override
                            public void OnPositiveButtonClicked(DialogInterface dialogInterface) {
                                saveScannedAssetData(true,false);
                                dialogInterface.dismiss();
                            }

                            @Override
                            public void OnNegativeButtonClicked(DialogInterface dialogInterface) {
                                saveScannedAssetData(false,false);
                                dialogInterface.dismiss();
                            }
                        }
                );
                multipleChoiceConfirmationDialog.show();
                barCodeReader.onPause();
                multipleChoiceConfirmationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        barCodeReader.onResume();
                    }
                });
            }
        } else {
            if (asset.getRoomId().equals(userLocation.getRoomId()))
                warningDialog(requireContext(),getString(R.string.scanned_before));
            else {
                MultipleChoiceConfirmationDialog multipleChoiceConfirmationDialog = new MultipleChoiceConfirmationDialog(
                        requireContext(),
                        getString(R.string.scanned_before),
                        getString(R.string.this_asset_is_scanned_before_in) + " " + assetWithUserLocation.getNewCompanyName() + " - " + assetWithUserLocation.getNewRoomName() + "! " + getString(R.string.do_you_want_to_change_location),
                        getString(R.string.accept),
                        getString(R.string.decline),
                        new MultipleChoiceConfirmationDialog.OnDialogButtonsClicked() {
                            @Override
                            public void OnPositiveButtonClicked(DialogInterface dialogInterface) {
                                saveScannedAssetData(true, false);
                                dialogInterface.dismiss();
                            }

                            @Override
                            public void OnNegativeButtonClicked(DialogInterface dialogInterface) {
//                                saveScannedAssetData(false,false);
                                dialogInterface.dismiss();
                            }
                        }
                );
                multipleChoiceConfirmationDialog.show();
                barCodeReader.onPause();
                multipleChoiceConfirmationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        barCodeReader.onResume();
                    }
                });
            }
        }
    }

    private void fillRoomData() {
        binding.locationInfo.companyName.setText(userLocation.getCompanyName());
        if (!roomCode.isEmpty())
            binding.locationInfo.roomName.setText(userLocation.getRoomName());
        else {
            String floorText =getString(R.string.floor) + userLocation.getFloorName();
            binding.locationInfo.roomName.setText(floorText);
        }
    }

    private UserLocation userLocation =null;
    private String roomCode;
    private void getRoomData() {
        if (getArguments()!=null){
            userLocation = getArguments().getParcelable(USER_LOCATION);
            roomCode     =getArguments().getString(ROOM_CODE);
        }
    }

    private void attachListener() {
        binding.assetStatus.setOnClickListener(this);
        binding.assetList.setOnClickListener(this);
        binding.assetCode.getEditText().setOnKeyListener(this);
        binding.assetList.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.clearEmployee.setOnClickListener(this);
    }

    private void setTexts() {
        binding.assetCode.setHint(R.string.asset_code);
        binding.assetCode.setHelperText(null);
        binding.assetNo.barcodeInputLayout.setHint(R.string.serial_number);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyMethods.changeTitle(getString(R.string.physical_counting),(MainActivity) getActivity());
        viewModel.getUsersList();
        barCodeReader.onResume();
        Log.d("PhysicalCountingFragment", "onResume: ");
    }
    private Bundle bundle;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.asset_status) {
            showBottomSheet();
        } else if (id == R.id.asset_list) {
            bundle = new Bundle();
            bundle.putParcelable(USER_LOCATION, userLocation);
            bundle.putString(ROOM_CODE, roomCode);
            Navigation.findNavController(v).navigate(R.id.action_physicalCountingFragment_to_assetListFragment, bundle);
        } else if (id == R.id.save) {
            saveScannedAssetUser();
        } else if (id == R.id.clear_employee) {
            selectedUser = null;
            binding.employeeSpinner.setText("",false);
        }
    }

    private void saveScannedAssetUser() {
        if (selectedUser == null) {
            assetWithUserLocation.setUserId("");
            asset.setUserId("");
            viewModel.saveScannedAsset(asset);
        } else {
            if (!assetWithUserLocation.getUserId().equals(selectedUser.getEmployeeId())) {
                asset.setUserId(selectedUser.getEmployeeId());
                Log.d("PhysicalCountingFragment", "saveScannedAssetUser: "+selectedUser.getEmployeeId());
                viewModel.saveScannedAsset(asset);
            } else {
                warningDialog(requireContext(), getString(R.string.same_employee));
            }
        }

    }

    private void saveScannedAssetData(Boolean userApproved,boolean sameLocation) {
        if (sameLocation){
            asset.setIsInSamePlace("1");
            asset.setScanStatus(SAME_LOCATION);
        } else {
            if (userApproved){
                asset.setCompanyId(userLocation.getCompanyId());
                asset.setRoomId(userLocation.getRoomId());
            }
            asset.setScanStatus(userApproved?DIFFERENT_LOCATION_USER_APPROVED:DIFFERENT_LOCATION_USER_DECLINED);
        }
        asset.setNewCompanyId(userLocation.getCompanyId());
        asset.setNewCompanyName(userLocation.getCompanyName());
        asset.setNewRoomId(userLocation.getRoomId());
        asset.setNewRoomName(userLocation.getRoomName());
        asset.setScanned(true);
        asset.setUserApproved(userApproved);
        asset.setDate(MyMethods.todayDate());
        viewModel.saveScannedAsset(asset);
    }

    private Observer<Status> statusObserver;
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
        {
            String assetCode = binding.assetCode.getEditText().getText().toString().trim();
            if (!assetCode.isEmpty())
                getAssetData(assetCode);
            else
                binding.assetCode.setError(getString(R.string.please_enter_a_valid_asset_code));
            return true;
        }
        return false;
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReader.scannedData(barcodeReadEvent).trim();
            if (!scannedText.isEmpty()) {
                getAssetData(scannedText);
                binding.assetCode.getEditText().setText(scannedText);
            }else
                binding.assetCode.setError(getString(R.string.please_scan_avalid_asset_barcode));
        });
    }

    private void getAssetData(String assetCode) {
        viewModel.getAssetData(assetCode);
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);
    }
    private AssetCondition selectedAssetCondition;
    @Override
    public void onAssetConditionSelected(AssetCondition newAssetCondition) {
        this.selectedAssetCondition = newAssetCondition;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("PhysicalCountingFragment", "onStop: ");
//        viewModel.getSaveAssetStatus().removeObserver(statusObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
        Log.d("PhysicalCountingFragment", "onPause: ");
    }
}