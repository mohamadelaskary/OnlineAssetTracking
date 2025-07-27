package com.example.OnlineAssetTracking.Ui;

import static com.example.OnlineAssetTracking.MyMethods.MyMethods.showSuccessAlerter;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.MyMethods;
import com.example.OnlineAssetTracking.MyMethods.SetUpBarCodeReader;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.ViewModel.EditRandomAssetStatusViewModel;
import com.example.OnlineAssetTracking.databinding.EditRandomAssetStatusFragmentBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class EditRandomAssetStatusFragment extends Fragment implements View.OnClickListener, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener {

    private EditRandomAssetStatusViewModel viewModel;
    private SetUpBarCodeReader barCodeReader;


    public static EditRandomAssetStatusFragment newInstance() {
        return new EditRandomAssetStatusFragment();
    }
    private EditRandomAssetStatusFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = EditRandomAssetStatusFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private LoadingDialog loadingDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EditRandomAssetStatusViewModel.class);
        barCodeReader = new SetUpBarCodeReader(this,this);
        loadingDialog = MyMethods.showLoadingDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTextAndHint();
        attachButtonsToListener();
        observeLocationInfo();
        observeLocationInfoStatus();
        addRoomCodeTextWatcher();
        observeAssetDataStatus();
        observeAssetInfo();
        viewModel.getAssetConditions();
        observeAssetConditions();
        observeSavingAsset();
    }

    private void attachButtonsToListener() {
        binding.save.setOnClickListener(this);
        binding.clearRoomCode.setOnClickListener(this);
    }

    private void observeSavingAsset() {
        viewModel.getSaveAssetStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    break;
                case SUCCESS:
                    loadingDialog.dismiss();
                    showSuccessAlerter(getString(R.string.saved_successfully),getActivity());
                    binding.assetInfo.setVisibility(View.GONE);
                    break;
                case ERROR:
                    MyMethods.warningDialog(getContext(),getString(R.string.error_in_saving_asset));
                    loadingDialog.dismiss();
                    break;
            }
        });
    }

    ArrayAdapter<AssetCondition> assetConditionsAdapter;
    private void setUpAssetConditionsSpinner() {
        assetConditionsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,assetConditions);
        binding.newStatus.spinner.setAdapter(assetConditionsAdapter);
        handleOnAssetSelected();
    }
    private void handleOnAssetSelected() {
        binding.newStatus.spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newAssetStatus = assetConditionsAdapter.getItem(position);
                handleAssetConditionChange();
            }
        });
    }
    private AssetCondition newAssetStatus;
    private void handleAssetConditionChange() {
        if (newAssetStatus!=null) {
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

    private List<AssetCondition> assetConditions =  new ArrayList<>();
    private void observeAssetConditions() {
        viewModel.getAssetCoditionsMutableLiveData().observe(getViewLifecycleOwner(),assetConditions -> {
            this.assetConditions = assetConditions;
            setUpAssetConditionsSpinner();
        });
    }

    private Asset asset;
    private void observeAssetInfo() {
        viewModel.getAssetInfo().observe(getViewLifecycleOwner(),asset -> {
            this.asset = asset;
            fillAssetInfo();
        });
    }

    private void fillAssetInfo() {
        binding.assetDescription.assetDescription.setText(asset.getDescription());
        binding.assetDescription.mainCategory.setText(asset.getMainCategoryName());
        binding.assetDescription.subCategory.setText(asset.getSubCategory2Name());
        binding.assetStatusDesc.oldAssetStatus.setText(asset.getAssetConditionName());
    }

    private UserLocation userLocation;
    private void observeLocationInfo() {
        viewModel.getLocationInfo().observe(getViewLifecycleOwner(),userLocation -> {
            this.userLocation = userLocation;
            fillLocationInfo();
        });
    }

    private void fillLocationInfo() {
        binding.locationInfo.buildingName.setText(userLocation.getBuildingName());
        binding.locationInfo.roomName.setText(userLocation.getRoomName());
    }

    private void observeAssetDataStatus() {
        viewModel.getAssetInfoStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    binding.assetInfo.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    loadingDialog.dismiss();
                    binding.assetInfo.setVisibility(View.VISIBLE);
                    break;
                case ERROR:
                    binding.assetBarcode.barcodeInputLayout.setError(getString(R.string.wrong_asset_code));
                    binding.assetInfo.setVisibility(View.GONE);
                    loadingDialog.dismiss();
                    break;
            }
        });
    }

    private void addRoomCodeTextWatcher() {
        binding.roomBarcode.barcodeInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.roomData.setVisibility(View.GONE);
                binding.assetInfo.setVisibility(View.GONE);
                binding.roomBarcode.barcodeInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.roomBarcode.barcodeInputLayout.setError(null);
            }
        });
        binding.assetBarcode.barcodeInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.assetInfo.setVisibility(View.GONE);
                binding.roomBarcode.barcodeInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.assetBarcode.barcodeInputLayout.setError(null);
            }
        });
        binding.roomBarcode.barcodeInputLayout.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    getRoomInfo(binding.roomBarcode.barcodeInputLayout.getEditText().getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
        binding.assetBarcode.barcodeInputLayout.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    getAssetInfo(binding.assetBarcode.barcodeInputLayout.getEditText().getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

    private void observeLocationInfoStatus() {
        viewModel.getLocationInfoStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
//                    binding.locationInfo.getRoot().setVisibility(View.GONE);
//                    binding.assetBarcode.getRoot().setVisibility(View.GONE);
                    binding.roomData.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    loadingDialog.dismiss();
//                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                    binding.roomData.setVisibility(View.VISIBLE);
//                    binding.assetBarcode.getRoot().setVisibility(View.VISIBLE);
                    break;
                case ERROR:
                    binding.roomBarcode.barcodeInputLayout.setError(getString(R.string.wrong_room_code));
                    loadingDialog.dismiss();
//                    binding.locationInfo.getRoot().setVisibility(View.GONE);
//                    binding.assetBarcode.getRoot().setVisibility(View.GONE);
                    binding.roomData.setVisibility(View.GONE);
                    break;
            }
        });
    }

    private void setTextAndHint() {
        binding.roomBarcode.barcodeInputLayout.setHint(getString(R.string.room_code));
        binding.assetBarcode.barcodeInputLayout.setHint(getString(R.string.asset_code));
    }

    @Override
    public void onResume() {
        super.onResume();
        MyMethods.changeTitle(getString(R.string.edit_asset_status),(MainActivity) getActivity());
        barCodeReader.onResume();
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReader.scannedData(barcodeReadEvent).trim();
            boolean isAssetCodeEditTextVisible=binding.roomData.getVisibility() == View.VISIBLE;
//            Toast.makeText(getContext(), isAssetCodeEditTextVisible+"", Toast.LENGTH_SHORT).show();
            if (!isAssetCodeEditTextVisible) {
                binding.roomBarcode.barcodeInputLayout.getEditText().setText(scannedText);
                getRoomInfo(scannedText);
            } else {
                binding.assetBarcode.barcodeInputLayout.getEditText().setText(scannedText);
                getAssetInfo(scannedText);
            }
        });
    }

    private void getAssetInfo(String assetCode) {
        viewModel.getAssetInfo(assetCode);
    }

    private void getRoomInfo(String roomCode) {
        viewModel.getLocationInfo(roomCode);
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                if (newAssetStatus!=null) {
                    asset.setNewAssetConditionId(newAssetStatus.getAssetConditionId());
                    asset.setIsSameCondition("0");
                } else {
                    asset.setNewAssetConditionId(asset.getAssetConditionId());
                    asset.setIsSameCondition("1");
                }

                asset.setNewRoomId(userLocation.getRoomId());
                if (asset.getNewRoomId()==asset.getRoomId())
                    asset.setIsInSamePlace("1");
                else
                    asset.setIsInSamePlace("0");
//                if (ORDER_ID!=null){
//                    asset.setOrderId(ORDER_ID);
//                }
//                asset.setUserId(String.valueOf(USER_ID));
                viewModel.saveScannedAsset(asset);
                break;
            case R.id.clear_room_code:
                binding.roomBarcode.barcodeInputLayout.getEditText().setText("");
                break;
        }
    }
}