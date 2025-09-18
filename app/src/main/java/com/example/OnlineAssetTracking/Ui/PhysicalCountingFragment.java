package com.example.OnlineAssetTracking.Ui;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.warningDialog;
import static com.example.OnlineAssetTracking.Ui.MainActivity.ORDER_ID;
import static com.example.OnlineAssetTracking.Ui.MainActivity.USER_ID;
import static com.example.OnlineAssetTracking.Ui.SelectRoomFragment.ROOM_CODE;
import static com.example.OnlineAssetTracking.Ui.SelectRoomFragment.USER_LOCATION;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import com.bumptech.glide.Glide;
import com.example.OnlineAssetTracking.Adapters.AssetConditionsAdapter;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.CarInfo;
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
    private void fillAssetData() {
        binding.assetNo.barcodeInputLayout.getEditText().setText(asset.getSerialNumber());
        binding.assetDescription.mainCategory.setText(asset.getMainCategoryName());
        binding.assetDescription.subCategory.setText(asset.getSubCategory2Name());
        binding.assetDescription.assetDescription.setText(asset.getDescription());
        if (asset.getFileBasse()!=null) {
//            binding.assetDescription.assetImage.setImageBitmap(convertBase64toBitmap(asset.getImage()));
            Glide.with(getContext())
                    .load(asset.getFileBasse())
                    .into(binding.assetDescription.assetImage);
            binding.assetDescription.assetImage.setVisibility(View.VISIBLE);
            binding.assetDescription.assetImage.invalidate();
        }
        else
            binding.assetDescription.assetImage.setVisibility(View.GONE);
        handleAssetConditionChange();

        if (!roomCode.isEmpty()) {
            asset.setNewRoomId(userLocation.getRoomId());
            asset.setNewCompanyId(userLocation.getCompanyId());
            if (asset.getNewRoomId().equals(asset.getRoomId())) {
                asset.setIsInSamePlace("1");
                asset.setScanStatus(SAME_LOCATION);
            } else {
                asset.setIsInSamePlace("0");

            }

            if (asset.getNewCompanyId().equals(asset.getCompanyId())){
                asset.setIsSameCompany("1");
            } else {
                asset.setIsSameCompany("0");
            }
            if (asset.getNewRoomId().equals(asset.getRoomId())){
                asset.setIsSameRoom("1");
            } else {
                asset.setIsSameRoom("0");
            }

        }

        asset.setUserId(USER_ID);
        asset.setDate(MyMethods.todayDate());

        binding.assetStatusDesc.oldAssetStatus.setVisibility(View.GONE);
        newAssetStatus = null;
        viewModel.saveScannedAsset(asset);
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
        binding.carInfo.setOnClickListener(this);
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

            if (!roomCode.isEmpty()) {
                asset.setNewRoomId(userLocation.getRoomId());

                if (asset.getNewRoomId().equals(asset.getRoomId()))
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
            if (!asset.getCarNo().isEmpty()) {
                CarInfoDialog carInfoDialog = new CarInfoDialog(requireContext(),
                        new CarInfo(asset.getCarNo(), asset.getModelOfYear(), asset.getMotorNo(), asset.getBodyNo(), asset.getFuelType(), asset.getOrcalSerialNo()));
                carInfoDialog.show();
            } else warningDialog(requireContext(), getString(R.string.no_car_info_found));
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