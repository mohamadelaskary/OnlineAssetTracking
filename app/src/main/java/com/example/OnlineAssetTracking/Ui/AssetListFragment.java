package com.example.OnlineAssetTracking.Ui;

import static com.example.OnlineAssetTracking.MyMethods.MyMethods.changeTitle;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.showLoadingDialog;
import static com.example.OnlineAssetTracking.Ui.PhysicalCountingFragment.DIFFERENT_LOCATION_USER_APPROVED;
import static com.example.OnlineAssetTracking.Ui.PhysicalCountingFragment.DIFFERENT_LOCATION_USER_DECLINED;
import static com.example.OnlineAssetTracking.Ui.PhysicalCountingFragment.SAME_LOCATION;
import static com.example.OnlineAssetTracking.Ui.SelectRoomFragment.ROOM_CODE;
import static com.example.OnlineAssetTracking.Ui.SelectRoomFragment.USER_LOCATION;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.OnlineAssetTracking.Adapters.AssetListAdapter;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.ViewModel.AssetListViewModel;
import com.example.OnlineAssetTracking.databinding.AssetListFragmentBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AssetListFragment extends Fragment {

    private AssetListViewModel viewModel;

    public static AssetListFragment newInstance() {
        return new AssetListFragment();
    }
    AssetListFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AssetListFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AssetListViewModel.class);
        loadingDialog = showLoadingDialog(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.asset_list),
                (MainActivity) getActivity());
    }
    private LoadingDialog loadingDialog;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
        fillData();
        getAssetList();
        setUpAssetListRecyclerView();
        observeGettingAssetList();
        observeGettingAssetListStatus();
        handleFilterCheckbox();
        onCheckBoxCheckChange();
    }

    private void onCheckBoxCheckChange() {
        binding.notTracked.setOnCheckedChangeListener((compoundButton, b) -> handleFilterCheckbox());
        binding.samePlace.setOnCheckedChangeListener((compoundButton, b) -> handleFilterCheckbox());
        binding.differentPlaceUserApproved.setOnCheckedChangeListener((compoundButton, b) -> handleFilterCheckbox());
        binding.differentPlaceUserDeclined.setOnCheckedChangeListener((compoundButton, b) -> handleFilterCheckbox());
    }

    private void handleFilterCheckbox() {
        boolean notScanned                    = binding.notTracked.isChecked();
        boolean sameLocation                  = binding.samePlace.isChecked();
        boolean differentLocationUserApproved = binding.differentPlaceUserApproved.isChecked();
        boolean differentLocationUserDeclined = binding.differentPlaceUserDeclined.isChecked();
        List<Asset> filteredList = new ArrayList<>();
        for(Asset asset:assetList){
            if (!asset.isScanned()&&notScanned){
                filteredList.add(asset);
            }
            if (sameLocation){
                if (asset.isScanned() && asset.getScanStatus().equals(SAME_LOCATION)){
                    filteredList.add(asset);
                }
            }
            if (differentLocationUserApproved){
                if (asset.isScanned() && asset.getScanStatus().equals(DIFFERENT_LOCATION_USER_APPROVED)){
                    filteredList.add(asset);
                }
            }
            if (differentLocationUserDeclined){
                if (asset.isScanned() && asset.getScanStatus().equals(DIFFERENT_LOCATION_USER_DECLINED)){
                    filteredList.add(asset);
                }
            }

        }
        adapter.setAssetList(filteredList);
        binding.scannedAssetsNo.getEditText().setText(String.valueOf(filteredList.size()));
    }

    private void observeGettingAssetListStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
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
    private List<Asset> assetList = new ArrayList<>();
    private void observeGettingAssetList() {
        viewModel.getGettingAssetListLiveData().observe(getViewLifecycleOwner(),assets -> {
            Collections.sort(assets, (o2, o1) -> {
                if (o1.getIsInSamePlace() == null) {
                    return (o2.getIsInSamePlace() == null) ? 0 : -1;
                }
                if (o2.getIsInSamePlace() == null) {
                    return 1;
                }
                return o1.getIsInSamePlace().compareTo(o2.getIsInSamePlace());
            });
            assetList = assets;
            adapter.setAssetList(assets);
            binding.scannedAssetsNo.getEditText().setText(String.valueOf(assets.size()));
            binding.totalAssetNo.getEditText().setText(String.valueOf(assets.size()));
        });
    }

    private AssetListAdapter adapter;
    private void setUpAssetListRecyclerView() {
        adapter = new AssetListAdapter(getContext());
        binding.assetList.setAdapter(adapter);
    }

    private void getAssetList() {
        if (roomCode.isEmpty())
            viewModel.getAssetListInFloor(userLocation.getFloorId());
        else
            viewModel.getAssetListInRoom(userLocation.getRoomId());
    }

    private void fillData() {
        binding.companyName.setText(userLocation.getCompanyName());
        binding.floor.setText(userLocation.getRoomName());
//        if (roomCode.isEmpty()) {
//            binding.roomName.setVisibility(View.GONE);
//            binding.roomArrow.setVisibility(View.GONE);
//        } else {
//            binding.roomName.setText(userLocation.getRoomName());
//            binding.roomName.setVisibility(View.VISIBLE);
//            binding.roomArrow.setVisibility(View.VISIBLE);
//        }
    }

    private UserLocation userLocation;
    private String roomCode;
    private void getData() {
        if (getArguments()!=null) {
            userLocation = getArguments().getParcelable(USER_LOCATION);
            roomCode     = getArguments().getString(ROOM_CODE);
        }
    }
}