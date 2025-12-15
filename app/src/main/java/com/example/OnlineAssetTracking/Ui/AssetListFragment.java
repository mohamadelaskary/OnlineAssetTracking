package com.example.OnlineAssetTracking.Ui;

import static com.example.OnlineAssetTracking.MyMethods.Tools.changeTitle;
import static com.example.OnlineAssetTracking.MyMethods.Tools.showLoadingDialog;
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

import com.example.OnlineAssetTracking.Adapters.AssetListAdapter;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.ViewModel.AssetListViewModel;
import com.example.OnlineAssetTracking.databinding.AssetListFragmentBinding;

import java.util.Collections;


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

    private void observeGettingAssetList() {
        viewModel.getGettingAssetListLiveData().observe(getViewLifecycleOwner(),assets -> {
            Collections.sort(assets, (o2, o1) -> {
                if (o1.getIsSameLocation() == null) {
                    return (o2.getIsSameLocation() == null) ? 0 : -1;
                }
                if (o2.getIsSameLocation() == null) {
                    return 1;
                }
                return o1.getIsSameLocation().compareTo(o2.getIsSameLocation());
            });
            Log.d("AssetListFragment", "observeGettingAssetList: "+assets.size());
            adapter.setAssetList(assets);

            int scannedAssetsNo = 0,allAssetsNo = assets.size();
            for (Asset asset:assets){
                if (!asset.getIsSameLocation().isEmpty())
                    scannedAssetsNo++;
            }
            binding.scannedAssetsNo.getEditText().setText(String.valueOf(scannedAssetsNo));
            binding.totalAssetNo.getEditText().setText(String.valueOf(allAssetsNo));
        });
    }

    private AssetListAdapter adapter;
    private void setUpAssetListRecyclerView() {
        adapter = new AssetListAdapter(getContext());
        binding.assetList.setAdapter(adapter);
    }

    private void getAssetList() {
//        if (roomCode.isEmpty())
//            viewModel.getAssetListInFloor(userLocation.getFloorId());
//        else
            viewModel.getAssetListInRoom(userLocation.getRoomCode(), userLocation.getTrackingOrderId());
    }

    private void fillData() {
        binding.buildingName.setText(userLocation.getBuildingName());
        if (userLocation.getFloorName().isEmpty()) {
            binding.floor.setVisibility(View.GONE);
            binding.floorArrow.setVisibility(View.GONE);
        } else {
            binding.floor.setText(userLocation.getFloorName());
            binding.floor.setVisibility(View.VISIBLE);
            binding.floorArrow.setVisibility(View.VISIBLE);
        }
        if (roomCode.isEmpty()) {
            binding.roomName.setVisibility(View.GONE);
            binding.roomArrow.setVisibility(View.GONE);
        } else {
            binding.roomName.setText(userLocation.getRoomName());
            binding.roomName.setVisibility(View.VISIBLE);
            binding.roomArrow.setVisibility(View.VISIBLE);
        }
    }

    private UserLocation userLocation;
    private String roomCode;
    private void getData() {
        if (getArguments()!=null) {
            userLocation = getArguments().getParcelable(USER_LOCATION);
            roomCode     = getArguments().getString(ROOM_CODE);
            Log.d("AssetListFragment", "getData: userLocation"+userLocation);
            Log.d("AssetListFragment", "getData: roomCode"+roomCode);
        }
    }
}