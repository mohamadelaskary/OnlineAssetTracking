package com.example.OnlineAssetTracking.Ui;

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

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.Tools;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.ViewModel.SearchAssetsViewModel;
import com.example.OnlineAssetTracking.databinding.SearchAssetsFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchAssetsFragment extends Fragment {

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SearchAssetsViewModel.class);
        loadingDialog = Tools.showLoadingDialog(getContext());
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
                Asset asset =  assetsAdapter.getItem(position);
                binding.dataLayout.setVisibility(View.VISIBLE);
                fillAssetData(asset);
            }
        });
    }

    private void fillAssetData(Asset asset) {
        binding.assetCode.getEditText().setText(asset.getBarcode());
        binding.assetDescription.mainCategory.setText(asset.getMainCategoryName());
        binding.assetDescription.subCategory.setText(asset.getSubCategory2Name());
        binding.assetDescription.assetDescription.setText(asset.getDescription());
        binding.assetStatusDesc.newAssetStatus.setText(asset.getAssetConditionName());
        if (asset.getRoomName()!=null){
            binding.locationInfo.roomName.setText(asset.getRoomName());
            binding.locationInfo.roomName.setVisibility(View.VISIBLE);
        } else {
            binding.locationInfo.roomName.setVisibility(View.GONE);
        }
//        if (asset.getFileBasse()!=null) {
////            binding.assetDescription.assetImage.setImageBitmap(convertBase64toBitmap(asset.getImage()));
//            Glide.with(getContext())
//                    .load(asset.getFileBasse())
//                    .into(binding.assetDescription.assetImage);
//            binding.assetDescription.assetImage.setVisibility(View.VISIBLE);
//            binding.assetDescription.assetImage.invalidate();
//        }
//        else
            binding.assetDescription.assetImage.setVisibility(View.GONE);
        binding.locationInfo.buildingName.setText(asset.getBuildingName());
    }

    private List<Asset> assetList = new ArrayList<>();
    ArrayAdapter<Asset> assetsAdapter;
    private void setUpAssetsSpinner() {
        Log.d("assetNo",assetList.size()+"");
        assetsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,assetList);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        Tools.changeTitle(getString(R.string.search_assets),(MainActivity) getActivity());
    }
}