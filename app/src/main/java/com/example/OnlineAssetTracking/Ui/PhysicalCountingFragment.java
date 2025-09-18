package com.example.OnlineAssetTracking.Ui;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.MyMethods.Tools.getEditTextText;
import static com.example.OnlineAssetTracking.MyMethods.Tools.multipleChoiceConfirmationDialog;
import static com.example.OnlineAssetTracking.Ui.MainActivity.ORDER_ID;
import static com.example.OnlineAssetTracking.Ui.MainActivity.USER_ID;
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

import com.example.OnlineAssetTracking.Adapters.AssetConditionsAdapter;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.Tools;
import com.example.OnlineAssetTracking.MyMethods.SetUpBarCodeReader;
import com.example.OnlineAssetTracking.ViewModel.PhysicalCountingViewModel;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.databinding.PhysicalCountingFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

public class PhysicalCountingFragment extends Fragment implements AssetConditionsAdapter.OnAssetConditionSelected, View.OnClickListener, View.OnKeyListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {

    public static final String ASSET_DATA = "ASSET_DATA" ;
    public static final int SAME_LOCATION = 1;
    public static final int DIFFERENT_LOCATION_USER_APPROVED = 2;
    public static final int DIFFERENT_LOCATION_USER_DECLINE = 3;
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
        loadingDialog = Tools.showLoadingDialog(getContext());
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
        final Boolean shouldSave;

    }

    private void observeSaveScannedAsset() {
        viewModel.getSaveAssetStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    break;
                case SUCCESS:
                    loadingDialog.dismiss();
                    Tools.showSuccessAlerter(getString(R.string.saved_successfully),getActivity());
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
            if (!newAssetStatus.getAssetConditionName().equals(asset.getAssetConditionName().replace("\"",""))) {
                binding.assetStatusDesc.oldAssetStatus.setVisibility(View.VISIBLE);
                binding.assetStatusDesc.oldAssetStatus.setText(asset.getAssetConditionName());
                binding.assetStatusDesc.newAssetStatus.setText(newAssetStatus.getAssetConditionName());
            } else {
                binding.assetStatusDesc.newAssetStatus.setText(asset.getAssetConditionName());
                binding.assetStatusDesc.oldAssetStatus.setVisibility(View.GONE);
            }
        } else {
            binding.assetStatusDesc.newAssetStatus.setText(asset.getAssetConditionName());
            binding.assetStatusDesc.oldAssetStatus.setVisibility(View.GONE);
        }
    }

    private void hideBottomSheet() {
        assetConditionsBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        binding.disableColor.setVisibility(View.GONE);
    }

    private void showBottomSheet(){
        assetConditionsBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        binding.disableColor.setVisibility(View.VISIBLE);
        adapter.setSelectedPosition(-1);
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
        Tools.clearInputLayoutError(binding.assetCode);
    }

    private void observeGettingAssetDataStatus() {
        statusObserver = status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.assetCode.setError(null);
                    break;
                case IDLE:
                    loadingDialog.dismiss();
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.assetCode.setError(null);
                    break;
                case SUCCESS:
                    loadingDialog.dismiss();
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    binding.assetCode.setError(null);
                    break;
                case ERROR:
                    loadingDialog.dismiss();
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.assetCode.setError(getString(R.string.asset_not_found));
                    break;
            }
        };
        viewModel.getGettingAssetDataStatus().observe(getViewLifecycleOwner(), statusObserver);
    }
    private Asset asset;
    private void observeGettingAssetData() {
        viewModel.getAssetDataLiveData().observe(getViewLifecycleOwner(),asset -> {
            this.asset = asset;
            if (asset.getRoomId() != userLocation.getRoomId() && asset.getStatus() == 0) {
                multipleChoiceConfirmationDialog(
                        requireContext(),
                        getString(R.string.different_location),
                        getString(R.string.this_asset_is_in_different_location_than_the_location_you_scanned),
                        getString(R.string.ok),
                        getString(R.string.decline),
                        new MultipleChoiceConfirmationDialog.OnDialogButtonsClicked() {
                            @Override
                            public void OnPositiveButtonClicked(DialogInterface dialogInterface) {
                                PhysicalCountingFragment.this.asset.setStatus(DIFFERENT_LOCATION_USER_APPROVED);
                                fillAssetData();
                                dialogInterface.dismiss();
                            }

                            @Override
                            public void OnNegativeButtonClicked(DialogInterface dialogInterface) {
                                PhysicalCountingFragment.this.asset.setStatus(DIFFERENT_LOCATION_USER_DECLINE);
                                fillAssetData();
                                dialogInterface.dismiss();
                            }
                        }
                ).show();
            } else {
                this.asset.setStatus(SAME_LOCATION);
                fillAssetData();
            }
        });
    }



    private AssetCondition newAssetStatus;
    private void fillAssetData() {
        binding.assetDescription.serialNumber.setText(asset.getSerialNumber());
        binding.assetDescription.assetDescription.setText(asset.getDescription());
        binding.assetDescription.assetImage.setVisibility(View.GONE);
        handleAssetConditionChange();
        if (newAssetStatus!=null) {
            asset.setNewAssetConditionId(newAssetStatus.getAssetConditionId());
            asset.setIsSameCondition("0");
        } else {
            asset.setNewAssetConditionId(asset.getAssetConditionId());
            asset.setIsSameCondition("1");
        }
        if (!roomCode.isEmpty()) {
            asset.setNewRoomId(userLocation.getRoomId());
            asset.setNewBuildingId(userLocation.getBuildingId());
            asset.setNewFloorId(userLocation.getFloorId());
            asset.setTrackingOrderId(ORDER_ID);
            if (asset.getNewRoomId()==asset.getRoomId())
                asset.setIsInSamePlace("1");
            else
                asset.setIsInSamePlace("0");

            if (asset.getNewBuildingId()==asset.getBuildingId()){
                asset.setIsSameBuilding("1");
            } else {
                asset.setIsSameBuilding("0");
            }
            if (asset.getNewRoomId()==asset.getRoomId()){
                asset.setIsSameRoom("1");
            } else {
                asset.setIsSameRoom("0");
            }
            if (asset.getNewFloorId()==asset.getFloorId()){
                asset.setIsSameFloor("1");
            } else {
                asset.setIsSameFloor("0");
            }
        } else {
            asset.setNewFloorId(userLocation.getFloorId());
            if (asset.getNewFloorId()==asset.getFloorId())
                asset.setIsInSamePlace("1");
            else
                asset.setIsInSamePlace("0");
        }

                if (ORDER_ID!=null){
                    asset.setOrderId(Integer.parseInt(ORDER_ID));
                }
                asset.setUserId(USER_ID);
                asset.setDate(Tools.todayDate());

        binding.assetStatusDesc.oldAssetStatus.setVisibility(View.GONE);
        newAssetStatus = null;
        viewModel.saveScannedAsset(asset);
    }

    private void fillRoomData() {
        binding.locationInfo.companyName.setText(userLocation.getCompanyName());
        binding.locationInfo.roomName.setText(userLocation.getRoomName());
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
        binding.carInfo.setOnClickListener(this);
    }

    private void setTexts() {
        binding.assetCode.setHint(R.string.asset_code);
        binding.assetCode.setHelperText(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        Tools.changeTitle(getString(R.string.physical_counting),(MainActivity) getActivity());
        barCodeReader.onResume();
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
                asset.setNotes(getEditTextText(binding.notes));
                if (newAssetStatus != null) {
                    asset.setNewAssetConditionId(newAssetStatus.getAssetConditionId());
                    asset.setIsSameCondition("0");
                } else {
                    asset.setNewAssetConditionId(asset.getAssetConditionId());
                    asset.setIsSameCondition("1");
                }
                if (!roomCode.isEmpty()) {
                    asset.setNewRoomId(userLocation.getRoomId());
                    asset.setNewBuildingId(userLocation.getBuildingId());
                    asset.setNewFloorId(userLocation.getFloorId());


                    if (asset.getNewRoomId() == asset.getRoomId())
                        asset.setIsInSamePlace("1");
                    else
                        asset.setIsInSamePlace("0");

                    if (asset.getNewBuildingId() == asset.getBuildingId()) {
                        asset.setIsSameBuilding("1");
                    } else {
                        asset.setIsSameBuilding("0");
                    }
                    if (asset.getNewFloorId() == asset.getFloorId()) {
                        asset.setIsSameFloor("1");
                    } else {
                        asset.setIsSameFloor("0");
                    }
                } else {
                    asset.setNewFloorId(userLocation.getFloorId());
                    if (asset.getNewFloorId() == asset.getFloorId())
                        asset.setIsInSamePlace("1");
                    else
                        asset.setIsInSamePlace("0");
                }

//                if (ORDER_ID!=null){
//                    asset.setOrderId(ORDER_ID);
//                }
//                    asset.setUserId(String.valueOf(USER_ID));
//                asset.setUserId(String.valueOf(USER_ID));
//                asset.setDate(MyMethods.todayDate());
                binding.assetStatusDesc.oldAssetStatus.setVisibility(View.GONE);
                newAssetStatus = null;
                viewModel.saveScannedAsset(asset);

        } else if (id == R.id.car_info) {

        }
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
//        viewModel.getSaveAssetStatus().removeObserver(statusObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
    }
}