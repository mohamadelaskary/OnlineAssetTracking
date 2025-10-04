package com.example.OnlineAssetTracking.Ui;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.Ui.PhysicalCountingFragment.DIFFERENT_LOCATION_USER_APPROVED;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetWithUserLocation;
import com.example.OnlineAssetTracking.MyMethods.AssetSearchAdapter;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.MyMethods;
import com.example.OnlineAssetTracking.MyMethods.SetUpBarCodeReader;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.ViewModel.SearchAssetsViewModel;
import com.example.OnlineAssetTracking.databinding.SearchAssetsFragmentBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class SearchAssetsFragment extends Fragment implements BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {

    private SearchAssetsViewModel viewModel;

    public static SearchAssetsFragment newInstance() {
        return new SearchAssetsFragment();
    }
    SearchAssetsFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SearchAssetsFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private SetUpBarCodeReader barcodeReader;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SearchAssetsViewModel.class);
        loadingDialog = MyMethods.showLoadingDialog(getContext());
        barcodeReader = new SetUpBarCodeReader(this,this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextAndHint();
        getAssetsList();
        setUpAssetsSpinner();
        observeGettingAssetList();
        observeGettingAssetsListStatus();
//        handleOnAssetSelected();
    }

    private void handleOnAssetSelected() {
        binding.assetDescriptionSpinner.spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AssetWithUserLocation asset =  assetsAdapter.getItem(position);

                fillAssetData(asset);
            }
        });
    }

    private void fillAssetData(AssetWithUserLocation asset) {
        binding.dataLayout.setVisibility(View.VISIBLE);

        binding.assetNo.getEditText().setText(asset.getSerialNumber());
        binding.assetCode.getEditText().setText(asset.getBarcode());
        binding.assetDescription.mainCategory.setText(asset.getMainCategoryName());
        binding.assetDescription.subCategory.setText(asset.getSubCategory2Name());
        binding.assetDescription.assetDescription.setText(asset.getDescription());
        binding.assetStatusDesc.newAssetStatus.setText(asset.getAssetConditionName());
//        if (asset.getRoomName()!=null){
//            binding.locationInfo.roomName.setText(asset.getRoomName());
//            binding.locationInfo.roomName.setVisibility(View.VISIBLE);
//        } else {
//            binding.locationInfo.roomName.setVisibility(View.GONE);
//        }
        Log.d(TAG, "fillAssetData: assetScanStatus"+asset.getScanStatus());
        Log.d(TAG, "fillAssetData: different"+DIFFERENT_LOCATION_USER_APPROVED);
        binding.locationInfo.companyName.setText(asset.getScanStatus().equals(DIFFERENT_LOCATION_USER_APPROVED)?asset.getNewCompanyName():asset.getCompanyName());
        binding.locationInfo.roomName.setText(asset.getScanStatus().equals(DIFFERENT_LOCATION_USER_APPROVED)?asset.getNewRoomName():asset.getRoomName());
        Log.d(TAG, "fillAssetData: "+binding.locationInfo.companyName.getText().toString());
        Log.d(TAG, "fillAssetData: employeeName employeeId"+asset.getEmployeeName()+" - "+asset.getUserId());
        binding.employee.getEditText().setText(asset.getEmployeeName()+" - "+asset.getUserId());
        if (asset.getFileBasse()!=null) {
//            binding.assetDescription.assetImage.setImageBitmap(convertBase64toBitmap(asset.getImage()));
            Glide.with(getContext())
                    .load(asset.getFileBasse())
                    .into(binding.assetDescription.assetImage);
            binding.assetDescription.assetImage.setVisibility(View.VISIBLE);
            binding.assetDescription.assetImage.invalidate();
            Log.d(TAG, "fillAssetData: assetUserId"+asset.getUserId());

        }

        else
            binding.assetDescription.assetImage.setVisibility(View.GONE);
    }

    private List<AssetWithUserLocation> assetList = new ArrayList<>();
    AssetSearchAdapter assetsAdapter;
    private void setUpAssetsSpinner() {
        Log.d("assetNo",assetList.size()+"");
        assetsAdapter = new AssetSearchAdapter(getContext(), assetList);
//        assetsAdapter = new AssetSpinnerAdapter(getContext(), android.R.layout.simple_gallery_item,assetList);
        binding.assetDescriptionSpinner.spinner.setAdapter(assetsAdapter);
        handleOnAssetSelected();
    }
    private LoadingDialog loadingDialog;
    private void observeGettingAssetsListStatus() {
        viewModel.getGetAllAssetsDataStatus().observe(getViewLifecycleOwner(),status -> {
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

    private void observeGettingAssetList() {
        viewModel.getGetAllAssetsDataLiveData().observe(getViewLifecycleOwner(),assetList -> {
            this.assetList = assetList;
            setUpAssetsSpinner();
        });
    }


    private void getAssetsList() {
        viewModel.getAllAssetsData();
    }

    private void editTextAndHint() {
        binding.assetDescriptionSpinner.menu.setHint(getString(R.string.asset_description));
        binding.assetNo.setHint(getString(R.string.asset_no));
    }

    @Override
    public void onResume() {
        super.onResume();
        MyMethods.changeTitle(getString(R.string.search_assets),(MainActivity) getActivity());
        barcodeReader.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeReader.onPause();
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        requireActivity().runOnUiThread(()->{
            String scannedCode = barcodeReader.scannedData(barcodeReadEvent);
            for (AssetWithUserLocation asset:assetList){
                if (scannedCode.equals(asset.getBarcode())){
                    binding.assetDescriptionSpinner.spinner.setText(asset.getDescription());
                    fillAssetData(asset);
                    break;
                }
            }
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barcodeReader.onTrigger(triggerStateChangeEvent);
    }
}