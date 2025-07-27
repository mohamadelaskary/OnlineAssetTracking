package com.example.OnlineAssetTracking.Ui;

import static com.example.OnlineAssetTracking.DataBase.Error.API;
import static com.example.OnlineAssetTracking.DataBase.Error.DELETE;
import static com.example.OnlineAssetTracking.DataBase.Error.INSERT;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieDrawable;
import com.example.OnlineAssetTracking.MyMethods.CustomDialogWithChoices;
import com.example.OnlineAssetTracking.ViewModel.LoadingDataViewModel;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.MyMethods;
import com.example.OnlineAssetTracking.R;
//import com.example.OnlineAssetTracking.MyMethods.ReadSvgFile;
import com.example.OnlineAssetTracking.databinding.LoadingDataFragmentBinding;

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
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        attachButtonsToListener();
        loadingDialog = MyMethods.showLoadingDialog(getContext());
        dialogWithChoices = new CustomDialogWithChoices(getContext());
        observeInsertingUsers();
        observeInsertingConditions();
        observeInsertingUserLocations();
        observeInsertingAssets();
        observeUserError();
        observeLoadingDataProgress();
    //        observeGettingAssetConditionCount();
//        observeGettingAssetConditionCountStatus();
//        observeGettingAssetsCount();
//        observeGettingAssetsCountStatus();
//        observeGettingUserLocationsCount();
//        observeGettingUserLocationsCountStatus();
//        observeGettingUsersCount();
//        observeGettingUsersCountStatus();
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
                binding.loadErrorMessage.setText(R.string.error_in_initiating_database);
            } else if (API.equals(error)) {
                binding.loadErrorMessage.setText(R.string.error_in_getting_data);
            } else if (INSERT.equals(error)) {
                binding.loadErrorMessage.setText(R.string.error_inserting_data);
            }
        });
    }

//    private void observeGettingAssetsCountStatus() {
//        viewModel.getAssetsCountStatus().observe(getViewLifecycleOwner(),status -> {
//            switch (status){
//                case LOADING:
//                    binding.assetConditionLoadingProgressBar.setVisibility(View.VISIBLE);
//                    binding.assetConditionFileStatus.setVisibility(View.GONE);
//                    break;
//                case SUCCESS:
//                    binding.assetConditionLoadingProgressBar.setVisibility(View.GONE);
//                    binding.assetConditionFileStatus.setVisibility(View.GONE);
//                    break;
//                case ERROR:
//                    binding.assetConditionLoadingProgressBar.setVisibility(View.GONE);
//                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
//                    binding.assetConditionFileStatus.setImageResource(R.drawable.ic_error);
//                    break;
//            }
//        });
//    }
//
//    private void observeGettingAssetsCount() {
//        viewModel.getAssetsCount().observe(getViewLifecycleOwner(),count ->{
//            if (count>0){
//                dialogWithChoices.setMessage(getString(R.string.are_you_sure_that_you_want_to_update_assets));
//                dialogWithChoices.setOnOkClickedListener(() -> {
//                    binding.loadAssetsErrorMessage.setVisibility(View.GONE);
////                    viewModel.insertAssetsInDatabase(ReadSvgFile.readAssetsFile(assetsUri,getContext()));
//                    dialogWithChoices.dismiss();
//                });
//                dialogWithChoices.show();
//            } else {
//                binding.loadAssetsErrorMessage.setVisibility(View.GONE);
//                try {
////                    viewModel.insertAssetsInDatabase(ReadSvgFile.readAssetsFile(assetsUri,getContext()));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    private void observeGettingUserLocationsCountStatus() {
//        viewModel.getUsersCountStatus().observe(getViewLifecycleOwner(),status -> {
//            switch (status){
//                case LOADING:
//                    binding.usersLoadingProgressBar.setVisibility(View.VISIBLE);
//                    binding.usersFileStatus.setVisibility(View.GONE);
//                    break;
//                case SUCCESS:
//                    binding.usersLoadingProgressBar.setVisibility(View.GONE);
//                    binding.usersFileStatus.setVisibility(View.GONE);
//                    break;
//                case ERROR:
//                    binding.usersLoadingProgressBar.setVisibility(View.GONE);
//                    binding.usersFileStatus.setVisibility(View.VISIBLE);
//                    binding.usersFileStatus.setImageResource(R.drawable.ic_error);
//                    break;
//            }
//        });
//    }
//
//    private void observeGettingUsersCount() {
//        viewModel.getUsersCount().observe(getViewLifecycleOwner(),count ->{
//            if (count>0){
//                dialogWithChoices.setMessage(getString(R.string.are_you_sure_that_you_want_to_update_users));
//                dialogWithChoices.setOnOkClickedListener(() -> {
//                    binding.loadUsersErrorMessage.setVisibility(View.GONE);
//                    viewModel.insertUsersInDatabase(ReadSvgFile.readUsers(usersUri,getContext()));
//                    dialogWithChoices.dismiss();
//                });
//                dialogWithChoices.show();
//            } else {
//                binding.loadUsersErrorMessage.setVisibility(View.GONE);
//                try {
//                    viewModel.insertUsersInDatabase(ReadSvgFile.readUsers(usersUri,getContext()));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    private void observeGettingUsersCountStatus() {
//        viewModel.getUserLocationsCountStatus().observe(getViewLifecycleOwner(),status -> {
//            switch (status){
//                case LOADING:
//                    binding.userLocationLoadingProgressBar.setVisibility(View.VISIBLE);
//                    binding.userLocationFileStatus.setVisibility(View.GONE);
//                    break;
//                case SUCCESS:
//                    binding.userLocationLoadingProgressBar.setVisibility(View.GONE);
//                    binding.userLocationFileStatus.setVisibility(View.GONE);
//                    break;
//                case ERROR:
//                    binding.userLocationLoadingProgressBar.setVisibility(View.GONE);
//                    binding.userLocationFileStatus.setVisibility(View.VISIBLE);
//                    binding.userLocationFileStatus.setImageResource(R.drawable.ic_error);
//                    break;
//            }
//        });
//    }
//
//    private void observeGettingUserLocationsCount() {
//        viewModel.getUserLocationsCount().observe(getViewLifecycleOwner(),count ->{
//            if (count>0){
//                dialogWithChoices.setMessage(getString(R.string.are_you_sure_that_you_want_to_update_user_Locations));
//                dialogWithChoices.setOnOkClickedListener(() -> {
//                    binding.loadUserLocationsErrorMessage.setVisibility(View.GONE);
//                    viewModel.insertUserLocationInDatabase(ReadSvgFile.readUserLocationFile(userLocationsUri,getContext()));
//                    dialogWithChoices.dismiss();
//                });
//                dialogWithChoices.show();
//            } else {
//                binding.loadUserLocationsErrorMessage.setVisibility(View.GONE);
//                try {
//                    viewModel.insertUserLocationInDatabase(ReadSvgFile.readUserLocationFile(userLocationsUri,getContext()));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//
//    private void observeGettingAssetConditionCountStatus() {
//        viewModel.getAssetConditionsCountStatus().observe(getViewLifecycleOwner(),status -> {
//            switch (status){
//                case LOADING:
//                    binding.assetConditionLoadingProgressBar.setVisibility(View.VISIBLE);
//                    binding.assetConditionFileStatus.setVisibility(View.GONE);
//                    break;
//                case SUCCESS:
//                    binding.assetConditionLoadingProgressBar.setVisibility(View.GONE);
//                    binding.assetConditionFileStatus.setVisibility(View.GONE);
//                    break;
//                case ERROR:
//                    binding.assetConditionLoadingProgressBar.setVisibility(View.GONE);
//                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
//                    binding.assetConditionFileStatus.setImageResource(R.drawable.ic_error);
//                    break;
//            }
//        });
//    }
//
//    private void observeGettingAssetConditionCount() {
//        viewModel.getAssetConditionsCount().observe(getViewLifecycleOwner(),count ->{
//            if (count>0){
//                dialogWithChoices.setMessage(getString(R.string.are_you_sure_that_you_want_to_update_asset_conditions));
//                dialogWithChoices.setOnOkClickedListener(() -> {
//                    binding.loadAssetConditionErrorMessage.setVisibility(View.GONE);
//                    viewModel.insertAssetConditionsInDatabase(ReadSvgFile.readAssetConditions(conditionsUri,getContext()));
//                    dialogWithChoices.dismiss();
//                });
//                dialogWithChoices.show();
//            } else {
//                binding.loadAssetConditionErrorMessage.setVisibility(View.GONE);
//                try {
//                    viewModel.insertAssetConditionsInDatabase(ReadSvgFile.readAssetConditions(conditionsUri,getContext()));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
    private  int progress = 0;
    private void observeInsertingConditions() {
        viewModel.getAssetConditionsStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status){
                case LOADING:
                    binding.loadingDots.setVisibility(View.VISIBLE);
//                    binding.loadingDots.setVisibility(View.GONE);
                    binding.loadErrorMessage.setText(R.string.loading);
                    break;
                case SUCCESS:
//                    binding.loadingDots.setVisibility(View.GONE);
                    if (progress==12) {
                        binding.loadingDots.setAnimation(R.raw.success_anim);
                        binding.loadingDots.playAnimation();
//                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
                        binding.loadErrorMessage.setText(R.string.data_loaded_successfully);
                    }
                    break;
                case ERROR:
                    binding.loadingDots.setAnimation(R.raw.warning);
//                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
//                    binding.loadErrorMessage.setText(R.string.data_loaded_successfully);
                    break;
            }
        });
    }

    private void observeInsertingUserLocations() {
        viewModel.getUserLocationsStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status){
                case LOADING:
                    binding.loadingDots.setVisibility(View.VISIBLE);
//                    binding.loadingDots.setVisibility(View.GONE);
                    binding.loadErrorMessage.setText(R.string.loading);
                    break;
                case SUCCESS:
//                    binding.loadingDots.setVisibility(View.GONE);
                    if (progress==12) {
                        binding.loadingDots.setAnimation(R.raw.success_anim);
                        binding.loadingDots.playAnimation();
//                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
                        binding.loadErrorMessage.setText(R.string.data_loaded_successfully);
                    }
                    break;
                case ERROR:
                    binding.loadingDots.setAnimation(R.raw.warning);
//                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
//                    binding.loadErrorMessage.setText(R.string.data_loaded_successfully);
                    break;
            }
        });
    }

    private void observeInsertingAssets() {
        viewModel.getAssetsStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status){
                case LOADING:
                    binding.loadingDots.setVisibility(View.VISIBLE);
//                    binding.loadingDots.setVisibility(View.GONE);
                    binding.loadErrorMessage.setText(R.string.loading);
                    break;
                case SUCCESS:
//                    binding.loadingDots.setVisibility(View.GONE);
                    if (progress==12) {
                        binding.loadingDots.setAnimation(R.raw.success_anim);
                        binding.loadingDots.playAnimation();
//                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
                        binding.loadErrorMessage.setText(R.string.data_loaded_successfully);
                    }
                    break;
                case ERROR:
                    binding.loadingDots.setAnimation(R.raw.warning);
//                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
//                    binding.loadErrorMessage.setText(R.string.data_loaded_successfully);
                    break;
            }
        });
    }

    private void observeInsertingUsers() {
        viewModel.getUsersStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status){
                case LOADING:
                    binding.loadingDots.setVisibility(View.VISIBLE);
//                    binding.loadingDots.setVisibility(View.GONE);
                    binding.loadErrorMessage.setText(R.string.loading);
                    break;
                case SUCCESS:
//                    binding.loadingDots.setVisibility(View.GONE);
                    if (progress==12) {
                        binding.loadingDots.setAnimation(R.raw.success_anim);
                    binding.loadingDots.playAnimation();
//                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
                        binding.loadErrorMessage.setText(R.string.data_loaded_successfully);
                    }
                    break;
                case ERROR:
                    binding.loadingDots.setAnimation(R.raw.warning);
//                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
//                    binding.loadErrorMessage.setText(R.string.data_loaded_successfully);
                    break;
            }
        });
    }

//    private void attachButtonsToListener() {
//        binding.loadUsersFile.setOnClickListener(this);
//        binding.assetConditionLoadFile.setOnClickListener(this);
//        binding.loadUserLocationFile.setOnClickListener(this);
//        binding.loadAssetFile.setOnClickListener(this);
//    }
//    private Uri usersUri;
//    ActivityResultLauncher<String> getUsersFileContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
//            new ActivityResultCallback<Uri>() {
//                @Override
//                public void onActivityResult(Uri uri) {
//                    try {
//                        if (ReadSvgFile.isUsersFile(uri,getContext())){
//                            usersUri = uri;
//                            viewModel.GetUsersCount();
//                        } else {
//                            binding.usersFileStatus.setImageResource(R.drawable.ic_error);
//                            binding.usersFileStatus.setVisibility(View.VISIBLE);
//                            binding.loadUsersErrorMessage.setText(R.string.selected_file_is_not_user_file);
//                            binding.loadUsersErrorMessage.setVisibility(View.VISIBLE);
//                        }
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//    private Uri userLocationsUri;
//    ActivityResultLauncher<String> getUserLocationFileContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
//            new ActivityResultCallback<Uri>() {
//                @Override
//                public void onActivityResult(Uri uri) {
//                    try {
//                        if (ReadSvgFile.isUserLocationFile(uri,getContext())){
//                            viewModel.GetUserLocationsCount();
//                            userLocationsUri = uri;
//                        } else {
//                            binding.userLocationFileStatus.setImageResource(R.drawable.ic_error);
//                            binding.userLocationFileStatus.setVisibility(View.VISIBLE);
//                            binding.loadUserLocationsErrorMessage.setText(R.string.selected_file_is_not_user_location_file);
//                            binding.loadUserLocationsErrorMessage.setVisibility(View.VISIBLE);
//                        }
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//    private Uri assetsUri;
//    ActivityResultLauncher<String> getAssetsFileContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
//            new ActivityResultCallback<Uri>() {
//                @Override
//                public void onActivityResult(Uri uri) {
//                    try {
//                        if (ReadSvgFile.isAssetsFile(uri,getContext())){
//                            viewModel.GetAssetsCount();
//                            assetsUri = uri;
//                        } else {
//                            binding.assetFileStatus.setImageResource(R.drawable.ic_error);
//                            binding.assetFileStatus.setVisibility(View.VISIBLE);
//                            binding.loadAssetsErrorMessage.setText(R.string.selected_file_is_not_asset_file);
//                            binding.loadAssetsErrorMessage.setVisibility(View.VISIBLE);
//                        }
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//    private Uri conditionsUri;
//    ActivityResultLauncher<String> getAssetConditionsFileContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
//            new ActivityResultCallback<Uri>() {
//                @Override
//                public void onActivityResult(Uri uri) {
//                    try {
//                        if (ReadSvgFile.isAssetConditionsFile(uri,getContext())){
//                            viewModel.GetAssetConditionsCount();
//                            conditionsUri = uri;
//                        } else {
//                            binding.assetConditionFileStatus.setImageResource(R.drawable.ic_error);
//                            binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
//                            binding.loadAssetConditionErrorMessage.setText(R.string.selected_file_is_not_asset_condition_file);
//                            binding.loadAssetConditionErrorMessage.setVisibility(View.VISIBLE);
//                        }
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });


    @Override
    public void onClick(View v) {
//            buttonId = v.getId();
//            checkPermission();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        viewModel.deleteAllUsers();
//        viewModel.deleteAllConditions();
    }
//    private  int buttonId;
//    private void checkPermission() {
//        if (ContextCompat.checkSelfPermission(
//                getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
//                PackageManager.PERMISSION_GRANTED) {
//            // You can use the API that requires the permission.
//            switch (buttonId){
//                case R.id.load_users_file:
//                    getUsersFileContent.launch("*/*");
//                    break;
//                case R.id.asset_condition_load_file:
//                    getAssetConditionsFileContent.launch("*/*");
//                    break;
//                case R.id.load_asset_file:
//                    getAssetsFileContent.launch("*/*");
//                    break;
//                case R.id.load_user_location_file:
//                    getUserLocationFileContent.launch("*/*");
//                    break;
//            }
//        } else {
//            // You can directly ask for the permission.
//            // The registered ActivityResultCallback gets the result of this request.
//            requestPermissionLauncher.launch(
//                    Manifest.permission.READ_EXTERNAL_STORAGE);
//        }
//    }
//    private ActivityResultLauncher<String> requestPermissionLauncher =
//            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//                if (isGranted) {
//                    // Permission is granted. Continue the action or workflow in your
//                    // app.
//                    switch (buttonId){
//                        case R.id.load_users_file:
//                            getUsersFileContent.launch("*/*");
//                            break;
//                        case R.id.asset_condition_load_file:
//                            getAssetConditionsFileContent.launch("*/*");
//                            break;
//                        case R.id.load_asset_file:
//                            getAssetsFileContent.launch("*/*");
//                            break;
//                        case R.id.load_user_location_file:
//                            getUserLocationFileContent.launch("*/*");
//                            break;
//                    }
//                } else {
//                    // Explain to the user that the feature is unavailable because the
//                    // features requires a permission that the user has denied. At the
//                    // same time, respect the user's decision. Don't link to system
//                    // settings in an effort to convince the user to change their
//                    // decision.
//                    MyMethods.warningDialog(getContext(),getString(R.string.you_shold_accept_read_storage_permisstion_to_be_able_to_load_installation_files));
//                }
//            });

    @Override
    public void onResume() {
        super.onResume();
        MyMethods.changeTitle(getString(R.string.loading_data),(MainActivity) getActivity());
    }


}