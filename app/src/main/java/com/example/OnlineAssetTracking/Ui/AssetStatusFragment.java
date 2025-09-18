package com.example.OnlineAssetTracking.Ui;

import static com.example.OnlineAssetTracking.Ui.PhysicalCountingFragment.ASSET_DATA;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.ViewModel.AssetStatusViewModel;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.MyMethods.MyMethods;
import com.example.OnlineAssetTracking.databinding.AssetStatusFragmentBinding;


import java.util.List;

public class AssetStatusFragment extends Fragment implements View.OnClickListener {

    public static final String NEW_ASSET_CONDITION = "new_asset_condition";
    private AssetStatusViewModel viewModel;

    public static AssetStatusFragment newInstance() {
        return new AssetStatusFragment();
    }
    private AssetStatusFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AssetStatusFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AssetStatusViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getAssetConditionsFromDatabase();
        observeAssetConditionsData();
        observeAssetConditionsDataStatus();
        getAssetData();
        attachToListener();
    }

    private void attachToListener() {
        binding.save.setOnClickListener(this);
    }

    private Asset asset;
    private void getAssetData() {
        if (getArguments()!=null){
            asset = getArguments().getParcelable(ASSET_DATA);
            fillData();
        }
    }

    private void fillData() {
        binding.locationInfo.roomName.setText(asset.getRoomName());
        binding.locationInfo.companyName.setText(asset.getCompanyName());
        binding.assetDescription.mainCategory.setText(asset.getMainCategoryName());
        binding.assetDescription.subCategory.setText(asset.getSubCategory2Name());
        binding.assetDescription.assetDescription.setText(asset.getDescription());
        binding.assetStatusDesc.newAssetStatus.setText(asset.getAssetConditionName());
    }

    private void observeAssetConditionsDataStatus() {
    }
    private ArrayAdapter<AssetCondition> spinnerAdapter;
    private void observeAssetConditionsData() {
        viewModel.getAssetConditionsData().observe(getViewLifecycleOwner(), this::setUpAssetStatusSpinner);
    }
    private AssetCondition newAssetCondition;
    private void setUpAssetStatusSpinner(List<AssetCondition> assetConditions) {
        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,assetConditions);
        binding.newStatus.spinner.setAdapter(spinnerAdapter);
//        AssetCondition currentAssetCondition = new AssetCondition(asset.getAssetConditionId(), asset.getAssetConditionName());
//        binding.newStatus.spinner.setSelection(assetConditions.indexOf(currentAssetCondition));
//        binding.newStatus.spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                newAssetCondition = assetConditions.get(position);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MyMethods.changeTitle(getActivity().getString(R.string.change_asset_status),(MainActivity) getActivity());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(NEW_ASSET_CONDITION, newAssetCondition);
            Navigation.findNavController(v).navigate(R.id.action_assetStatusFragment_to_physicalCountingFragment, bundle);
        }
    }
}