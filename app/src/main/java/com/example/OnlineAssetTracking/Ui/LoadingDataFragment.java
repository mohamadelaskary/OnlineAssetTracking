package com.example.OnlineAssetTracking.Ui;

import static com.example.OnlineAssetTracking.DataBase.Error.API;
import static com.example.OnlineAssetTracking.DataBase.Error.DELETE;
import static com.example.OnlineAssetTracking.DataBase.Error.INSERT;
import static com.example.OnlineAssetTracking.MyMethods.Constants.SELECTED_TRACKING_ORDER_ID_KEY;
import static com.example.OnlineAssetTracking.MyMethods.Constants.SELECTED_USER_ID_KEY;
import static com.example.OnlineAssetTracking.MyMethods.Tools.containsTrackingOrder;
import static com.example.OnlineAssetTracking.MyMethods.Tools.saveIntegerDataToLocalStorage;
import static com.example.OnlineAssetTracking.MyMethods.Tools.successDialog;
import static com.example.OnlineAssetTracking.MyMethods.Tools.warningDialog;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.OnlineAssetTracking.DataBase.User;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.Model.TrackingOrder;
import com.example.OnlineAssetTracking.MyMethods.CustomDialogWithChoices;
import com.example.OnlineAssetTracking.ViewModel.LoadingDataViewModel;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.Tools;
import com.example.OnlineAssetTracking.R;
//import com.example.OnlineAssetTracking.MyMethods.ReadSvgFile;
import com.example.OnlineAssetTracking.databinding.LoadingDataFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class LoadingDataFragment extends Fragment implements View.OnClickListener {

    private LoadingDataViewModel viewModel;

    public static LoadingDataFragment newInstance() {
        return new LoadingDataFragment();
    }
    LoadingDataFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LoadingDataFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoadingDataViewModel.class);

    }
    LoadingDialog loadingDialog;
    CustomDialogWithChoices dialogWithChoices;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.loadAssets.setOnClickListener(this);
        loadingDialog = Tools.showLoadingDialog(getContext());
        dialogWithChoices = new CustomDialogWithChoices(getContext());
        observeInsertingUsers();
//        observeInsertingConditions();
        observeInsertingUserLocations();
        observeInsertingAssets();
        observeUserError();
        observeLoadingDataProgress();
        setUpUsersSpinner();
        setUpTrackingOrderNumbersSpinner();
    //        observeGettingAssetConditionCount();
//        observeGettingAssetConditionCountStatus();
//        observeGettingAssetsCount();
//        observeGettingAssetsCountStatus();
//        observeGettingUserLocationsCount();
//        observeGettingUserLocationsCountStatus();
//        observeGettingUsersCount();
//        observeGettingUsersCountStatus();
    }
    private ArrayAdapter trackingOrdersAdapter;
    private List<TrackingOrder> trackingOrders = new ArrayList<>();
    private Integer selectedTrackingOrderId = null;
    private void setUpTrackingOrderNumbersSpinner() {
        trackingOrdersAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_expandable_list_item_1,trackingOrders);
        binding.trackingOrderNumberSpinner.setAdapter(trackingOrdersAdapter);
        binding.trackingOrderNumberSpinner.setOnItemClickListener((adapterView, view, i, l) -> {
            selectedTrackingOrderId = trackingOrders.get(i).getTrackingOrderId();
        });
    }

    private Integer selectedUserId = null;
    private List<User> userList = new ArrayList<>();
    ArrayAdapter usersAdapter;
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpUsersSpinner() {
        usersAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_expandable_list_item_1,userList);
        binding.usersSpinner.setAdapter(usersAdapter);
        binding.usersSpinner.setOnItemClickListener((adapterView, view, position, l) -> {
            selectedUserId = userList.get(position).getUserId();
            trackingOrders.clear();
            for (UserLocation userLocation:userLocationList){
                if (userLocation.getUserID().equals(selectedUserId)) {
                    if (!containsTrackingOrder(trackingOrders,userLocation.getOrderNumber()))
                        trackingOrders.add(new TrackingOrder(userLocation.getTrackingOrderId(),userLocation.getOrderNumber()));
                }
            }
            binding.trackingOrderNumberSpinner.setText("",false);
            if (trackingOrders.isEmpty()){
                warningDialog(requireContext(),getString(R.string.selected_users_has_no_locations_to_track));
            }
            trackingOrdersAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_expandable_list_item_1,trackingOrders);
            binding.trackingOrderNumberSpinner.setAdapter(trackingOrdersAdapter);
        });
    }

    private void observeLoadingDataProgress() {
        viewModel.getProgressLiveData().observe(getViewLifecycleOwner(),progress ->{
            this.progress = (int) progress;
            Log.d("progress",progress+"");
        });
    }

    private void observeUserError() {
        viewModel.getUserError().observe(getViewLifecycleOwner(),error -> {
            if (DELETE.equals(error)) {
                warningDialog(requireContext(),getString(R.string.error_in_initiating_database));
            } else if (API.equals(error)) {
                warningDialog(requireContext(),getString(R.string.error_in_getting_data));
            } else if (INSERT.equals(error)) {
                warningDialog(requireContext(),getString(R.string.error_inserting_data));
            }
        });
    }

    private  int progress = 0;
    private void observeInsertingConditions() {
        viewModel.getAssetConditionsStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    break;
                case SUCCESS:
//                    binding.loadingDots.setVisibility(View.GONE);
                    if (progress==9) {
                        loadingDialog.dismiss();
                    }
                    break;
                case ERROR:
                    loadingDialog.dismiss();
                    warningDialog(requireContext(),getString(R.string.error_in_inserting_asset_conditions));
                    break;
            }
        });
    }
    private List<UserLocation> userLocationList = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void observeInsertingUserLocations() {
        viewModel.getUserLocationsStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                break;
                case SUCCESS:
                    loadingDialog.dismiss();
                    break;
                case ERROR:
                    loadingDialog.dismiss();
                    warningDialog(requireContext(),getString(R.string.error_in_inserting_users_locations));
                    break;
            }
        });
        viewModel.getUserLocationsLiveData().observe(getViewLifecycleOwner(),userLocations -> {
            if (!userLocations.isEmpty()){
                userLocationList = userLocations;
            } else {
                warningDialog(requireContext(),getString(R.string.no_users_locations_found));
            }
        });
    }

    private void observeInsertingAssets() {
        viewModel.getAssetsStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    break;
                case SUCCESS:
                    loadingDialog.dismiss();
                    successDialog(requireContext(),getString(R.string.assets_loaded_successfully));
                    break;
                case ERROR:
                    loadingDialog.dismiss();
                    warningDialog(requireContext(),getString(R.string.error_in_inserting_assets));
                    break;
            }
        });
    }

    private void observeInsertingUsers() {
        viewModel.getUsersStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    break;
                case SUCCESS:
//                    binding.loadingDots.setVisibility(View.GONE);
                    if (progress==9) {
                        loadingDialog.dismiss();
                    }
                    break;
                case ERROR:
                   warningDialog(requireContext(),getString(R.string.error_in_inserting_users));
                    break;
            }
        });
        viewModel.getUsersListLiveData().observe(getViewLifecycleOwner(),users -> {
            userList = users;
            usersAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_expandable_list_item_1,userList);
            binding.usersSpinner.setAdapter(usersAdapter);
        });
    }



    @Override
    public void onClick(View v) {
        int buttonId = v.getId();
        if (buttonId == R.id.load_assets){
            if (isReadyForSave()) {
                viewModel.deleteAllAssets(selectedUserId, selectedTrackingOrderId);
                saveSelectedUserId(selectedUserId);
                saveSelectedTrackingOrderNumber(selectedTrackingOrderId);
            }else
                binding.users.setError(getString(R.string.please_select_a_user));
        }
    }

    private void saveSelectedUserId(Integer selectedUserId) {
        saveIntegerDataToLocalStorage(requireActivity(),selectedUserId,SELECTED_USER_ID_KEY);
    }

    private void saveSelectedTrackingOrderNumber(Integer selectedTrackingOrderId) {
        saveIntegerDataToLocalStorage(requireActivity(),selectedTrackingOrderId,SELECTED_TRACKING_ORDER_ID_KEY);
    }

    private boolean isReadyForSave() {
        boolean isReady = true;
        if (selectedUserId==null){
            isReady = false;
            binding.users.setError(getString(R.string.please_select_a_user));
        }
        if (selectedTrackingOrderId==null){
            isReady = false;
            binding.trackingOrderNumber.setError(getString(R.string.please_select_tracking_order_number));
        }
        return isReady;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();
        Tools.changeTitle(getString(R.string.loading_data),(MainActivity) getActivity());
    }


}