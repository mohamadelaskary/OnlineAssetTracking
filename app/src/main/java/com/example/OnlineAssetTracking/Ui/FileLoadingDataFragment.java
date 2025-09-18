package com.example.OnlineAssetTracking.Ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.OnlineAssetTracking.MyMethods.CustomDialogWithChoices;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.MyMethods;
import com.example.OnlineAssetTracking.MyMethods.ReadWriteExcelSheet;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.ViewModel.FileLoadingDataViewModel;
import com.example.OnlineAssetTracking.databinding.FileLoadingDataFragmentBinding;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FileLoadingDataFragment extends Fragment implements View.OnClickListener {

    private FileLoadingDataViewModel viewModel;

    public static FileLoadingDataFragment newInstance() {
        return new FileLoadingDataFragment();
    }
    FileLoadingDataFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FileLoadingDataFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FileLoadingDataViewModel.class);
        dialogWithChoices = new CustomDialogWithChoices(getContext());
    }
    LoadingDialog loadingDialog;
    CustomDialogWithChoices dialogWithChoices;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachButtonsToListener();
        loadingDialog = MyMethods.showLoadingDialog(getContext());

        observeInsertingUsers();
        observeInsertingConditions();
        observeInsertingUserLocations();
        observeInsertingAssets();
//        observeGettingAssetConditionCount();
//        observeGettingAssetConditionCountStatus();
        observeGettingAssetsCount();
        observeGettingAssetsCountStatus();
        observeGettingUserLocationsCount();
        observeGettingUserLocationsCountStatus();
        observeGettingUsersCount();
        observeGettingUsersCountStatus();
    }

    private void observeGettingAssetsCountStatus() {
        viewModel.getAssetsCountStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    binding.assetConditionLoadingProgressBar.setVisibility(View.VISIBLE);
                    binding.assetConditionFileStatus.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.assetConditionLoadingProgressBar.setVisibility(View.GONE);
                    binding.assetConditionFileStatus.setVisibility(View.GONE);
                    break;
                case ERROR:
                    binding.assetConditionLoadingProgressBar.setVisibility(View.GONE);
                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
                    binding.assetConditionFileStatus.setImageResource(R.drawable.ic_error);
                    break;
            }
        });
    }

    private void observeGettingAssetsCount() {
        viewModel.getAssetsCount().observe(getViewLifecycleOwner(),count ->{
            getAssetsDataFromFile(count);
//            if (count>0){
//
//                String errorMassage = getString(R.string.are_you_sure_that_you_want_to_update_assets);
//                dialogWithChoices.setMessage(errorMassage);
//                Log.d("areYouSure",dialogWithChoices.getMessage());
//                dialogWithChoices.setOnOkClickedListener(() -> {
//                    binding.loadAssetsErrorMessage.setVisibility(View.GONE);
////                    viewModel.deleteAllAssets(ReadSvgFile.readAssetsFile(assetsUri,getContext()));
//                    getAssetsDataFromFile(count);
//                    dialogWithChoices.dismiss();
//                });
//                dialogWithChoices.show();
//            } else {
//                binding.loadAssetsErrorMessage.setVisibility(View.GONE);
//                //                    viewModel.insertAssetsInDatabase(ReadSvgFile.readAssetsFile(assetsUri,getContext()));
//                getAssetsDataFromFile(count);
//            }
        });
    }

    private void getAssetsDataFromFile(Integer count) {
        final List[] assets = new List[]{new ArrayList<>()};
        Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    assets[0] = viewModel.getAssetsData(assetsUri);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        binding.assetLoadingProgressBar.setVisibility(View.VISIBLE);
                        binding.assetFileStatus.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        if (count>0){
                            String errorMassage = getString(R.string.are_you_sure_that_you_want_to_update_assets);
                            dialogWithChoices.setMessage(errorMassage);
                            Log.d("areYouSure",dialogWithChoices.getMessage());
                            dialogWithChoices.setOnOkClickedListener(() -> {
                                binding.loadAssetsErrorMessage.setVisibility(View.GONE);
                                 viewModel.deleteAllAssets(assets[0]);
//                                getAssetsDataFromFile(count);
                                dialogWithChoices.dismiss();
                            });
                            dialogWithChoices.show();
                        } else {
                            binding.loadAssetsErrorMessage.setVisibility(View.GONE);
                            viewModel.insertAssetsInDatabase(assets[0]);
//                            getAssetsDataFromFile(count);
                        }
                        binding.assetLoadingProgressBar.setVisibility(View.GONE);
                        binding.assetFileStatus.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        binding.assetLoadingProgressBar.setVisibility(View.GONE);
                        binding.assetFileStatus.setVisibility(View.VISIBLE);
                        binding.assetFileStatus.setImageResource(R.drawable.ic_error);
                    }
                });
    }

    private void observeGettingUserLocationsCountStatus() {
        viewModel.getUsersCountStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    binding.usersLoadingProgressBar.setVisibility(View.VISIBLE);
                    binding.usersFileStatus.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.usersLoadingProgressBar.setVisibility(View.GONE);
                    binding.usersFileStatus.setVisibility(View.GONE);
                    break;
                case ERROR:
                    binding.usersLoadingProgressBar.setVisibility(View.GONE);
                    binding.usersFileStatus.setVisibility(View.VISIBLE);
                    binding.usersFileStatus.setImageResource(R.drawable.ic_error);
                    break;
            }
        });
    }

    private void observeGettingUsersCount() {
        viewModel.getUsersCount().observe(getViewLifecycleOwner(),count ->{
            if (count>0){
                String errorMassage = getString(R.string.are_you_sure_that_you_want_to_update_users);
                dialogWithChoices.setMessage(errorMassage);
                Log.d("areYouSure",dialogWithChoices.getMessage());
                dialogWithChoices.setOnOkClickedListener(() -> {
                    binding.loadUsersErrorMessage.setVisibility(View.GONE);
                    viewModel.deleteAllUsers(viewModel.getUserData(usersUri));
                    dialogWithChoices.dismiss();
                });
                dialogWithChoices.show();
            } else {
                binding.loadUsersErrorMessage.setVisibility(View.GONE);
                try {
                    viewModel.insertUsersInDatabase(viewModel.getUserData(usersUri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void observeGettingUsersCountStatus() {
        viewModel.getUserLocationsCountStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    binding.userLocationLoadingProgressBar.setVisibility(View.VISIBLE);
                    binding.userLocationFileStatus.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.userLocationLoadingProgressBar.setVisibility(View.GONE);
                    binding.userLocationFileStatus.setVisibility(View.GONE);
                    break;
                case ERROR:
                    binding.userLocationLoadingProgressBar.setVisibility(View.GONE);
                    binding.userLocationFileStatus.setVisibility(View.VISIBLE);
                    binding.userLocationFileStatus.setImageResource(R.drawable.ic_error);
                    break;
            }
        });
    }

    private void observeGettingUserLocationsCount() {
        viewModel.getUserLocationsCount().observe(getViewLifecycleOwner(),count ->{
            if (count>0){
                String errorMassage = getString(R.string.are_you_sure_that_you_want_to_update_user_Locations);
                dialogWithChoices.setMessage(errorMassage);
                Log.d("areYouSure",dialogWithChoices.getMessage());
                dialogWithChoices.setOnOkClickedListener(() -> {
                    binding.loadUserLocationsErrorMessage.setVisibility(View.GONE);
                    viewModel.deleteAllUserLocations(viewModel.getLocationsData(userLocationsUri));
                    dialogWithChoices.dismiss();
                });
                dialogWithChoices.show();
            }else {
                binding.loadUserLocationsErrorMessage.setVisibility(View.GONE);
                try {
                    viewModel.insertUserLocationInDatabase(viewModel.getLocationsData(userLocationsUri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void observeGettingAssetConditionCountStatus() {
        viewModel.getAssetConditionsCountStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    binding.assetConditionLoadingProgressBar.setVisibility(View.VISIBLE);
                    binding.assetConditionFileStatus.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.assetConditionLoadingProgressBar.setVisibility(View.GONE);
                    binding.assetConditionFileStatus.setVisibility(View.GONE);
                    break;
                case ERROR:
                    binding.assetConditionLoadingProgressBar.setVisibility(View.GONE);
                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
                    binding.assetConditionFileStatus.setImageResource(R.drawable.ic_error);
                    break;
            }
        });
    }

    private void observeGettingAssetConditionCount() {
        viewModel.getAssetConditionsCount().observe(getViewLifecycleOwner(),count ->{
            if (count>0){
                String errorMassage = getString(R.string.are_you_sure_that_you_want_to_update_asset_conditions);
                dialogWithChoices.setMessage(errorMassage);
                dialogWithChoices.setOnOkClickedListener(() -> {
                    binding.loadAssetConditionErrorMessage.setVisibility(View.GONE);
//                    viewModel.deleteAllConditions(viewModel.readAssetConditions(conditionsUri,getContext()));
                    dialogWithChoices.dismiss();
                });
                dialogWithChoices.show();
            } else {
                binding.loadAssetConditionErrorMessage.setVisibility(View.GONE);
                try {
//                    viewModel.insertConditionsInDatabase(ReadSvgFile.readAssetConditions(conditionsUri,getContext()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void observeInsertingConditions() {
        viewModel.getInsertAssetConditionsStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    binding.assetConditionLoadingProgressBar.setVisibility(View.VISIBLE);
                    binding.assetConditionFileStatus.setVisibility(View.GONE);
                    binding.loadAssetConditionErrorMessage.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.assetConditionLoadingProgressBar.setVisibility(View.GONE);
                    binding.assetConditionFileStatus.setImageResource(R.drawable.ic_done);
                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
                    binding.loadAssetConditionErrorMessage.setVisibility(View.GONE);
                    break;
                case ERROR:
                    binding.assetConditionLoadingProgressBar.setVisibility(View.GONE);
                    binding.assetConditionFileStatus.setImageResource(R.drawable.ic_error);
                    binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
                    binding.loadAssetConditionErrorMessage.setText(R.string.error_inserting_data);
                    binding.loadAssetConditionErrorMessage.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    private void observeInsertingUserLocations() {
        viewModel.getInsertUserLocationsStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    binding.userLocationLoadingProgressBar.setVisibility(View.VISIBLE);
                    binding.userLocationFileStatus.setVisibility(View.GONE);
                    binding.loadUserLocationsErrorMessage.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.userLocationLoadingProgressBar.setVisibility(View.GONE);
                    binding.userLocationFileStatus.setImageResource(R.drawable.ic_done);
                    binding.userLocationFileStatus.setVisibility(View.VISIBLE);
                    binding.loadUserLocationsErrorMessage.setVisibility(View.GONE);
                    break;
                case ERROR:
                    binding.userLocationLoadingProgressBar.setVisibility(View.GONE);
                    binding.userLocationFileStatus.setImageResource(R.drawable.ic_error);
                    binding.userLocationFileStatus.setVisibility(View.VISIBLE);
                    binding.loadUserLocationsErrorMessage.setText(R.string.error_inserting_data);
                    binding.loadUserLocationsErrorMessage.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    private void observeInsertingAssets() {
        viewModel.getInsertAssetsStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    binding.assetLoadingProgressBar.setVisibility(View.VISIBLE);
                    binding.assetFileStatus.setVisibility(View.GONE);
                    binding.loadAssetsErrorMessage.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.assetLoadingProgressBar.setVisibility(View.GONE);
                    binding.assetFileStatus.setImageResource(R.drawable.ic_done);
                    binding.assetFileStatus.setVisibility(View.VISIBLE);
                    binding.loadAssetsErrorMessage.setVisibility(View.GONE);
                    break;
                case ERROR:
                    binding.assetLoadingProgressBar.setVisibility(View.GONE);
                    binding.assetFileStatus.setImageResource(R.drawable.ic_error);
                    binding.assetFileStatus.setVisibility(View.VISIBLE);
                    binding.loadAssetsErrorMessage.setText(R.string.error_inserting_data);
                    binding.loadAssetsErrorMessage.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    private void observeInsertingUsers() {
        viewModel.getInsertUsersStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    binding.userLocationLoadingProgressBar.setVisibility(View.VISIBLE);
                    binding.usersFileStatus.setVisibility(View.GONE);
                    binding.loadUsersErrorMessage.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.userLocationLoadingProgressBar.setVisibility(View.GONE);
                    binding.usersFileStatus.setImageResource(R.drawable.ic_done);
                    binding.usersFileStatus.setVisibility(View.VISIBLE);
                    binding.loadUsersErrorMessage.setVisibility(View.GONE);
                    break;
                case ERROR:
                    binding.userLocationLoadingProgressBar.setVisibility(View.GONE);
                    binding.usersFileStatus.setImageResource(R.drawable.ic_error);
                    binding.usersFileStatus.setVisibility(View.VISIBLE);
                    binding.loadUsersErrorMessage.setText(R.string.error_inserting_data);
                    binding.loadUsersErrorMessage.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    private void attachButtonsToListener() {
        binding.loadUsersFile.setOnClickListener(this);
        binding.assetConditionLoadFile.setOnClickListener(this);
        binding.loadUserLocationFile.setOnClickListener(this);
        binding.loadAssetFile.setOnClickListener(this);
    }
    private Uri usersUri;
    ActivityResultLauncher<String> getUsersFileContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri != null) {
                        try {
                            if (viewModel.checkUsersFile(uri)) {
                                usersUri = uri;
                                viewModel.GetUsersCount();
                            } else {
                                binding.usersFileStatus.setImageResource(R.drawable.ic_error);
                                binding.usersFileStatus.setVisibility(View.VISIBLE);
                                binding.loadUsersErrorMessage.setText(R.string.selected_file_is_not_user_file);
                                binding.loadUsersErrorMessage.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            binding.usersFileStatus.setImageResource(R.drawable.ic_error);
                            binding.usersFileStatus.setVisibility(View.VISIBLE);
                            binding.loadUsersErrorMessage.setText(R.string.selected_file_is_not_user_file);
                            binding.loadUsersErrorMessage.setVisibility(View.VISIBLE);
                        }
                } else {
                        binding.usersFileStatus.setImageResource(R.drawable.ic_error);
                        binding.usersFileStatus.setVisibility(View.VISIBLE);
                        binding.loadUsersErrorMessage.setText(R.string.no_file_selected);
                        binding.loadUsersErrorMessage.setVisibility(View.VISIBLE);
                }
                }
            });
    private Uri userLocationsUri;
    ActivityResultLauncher<String> getUserLocationFileContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri != null) {
                        try {
                            if (viewModel.checkLocationsFile(uri)) {
                                viewModel.GetUserLocationsCount();
                                userLocationsUri = uri;
                            } else {
                                binding.userLocationFileStatus.setImageResource(R.drawable.ic_error);
                                binding.userLocationFileStatus.setVisibility(View.VISIBLE);
                                binding.loadUserLocationsErrorMessage.setText(R.string.selected_file_is_not_user_location_file);
                                binding.loadUserLocationsErrorMessage.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            binding.userLocationFileStatus.setImageResource(R.drawable.ic_error);
                            binding.userLocationFileStatus.setVisibility(View.VISIBLE);
                            binding.loadUserLocationsErrorMessage.setText(R.string.selected_file_is_not_user_location_file);
                            binding.loadUserLocationsErrorMessage.setVisibility(View.VISIBLE);
                        }
                    } else {
                        binding.userLocationFileStatus.setImageResource(R.drawable.ic_error);
                        binding.userLocationFileStatus.setVisibility(View.VISIBLE);
                        binding.loadUserLocationsErrorMessage.setText(R.string.no_file_selected);
                        binding.loadUserLocationsErrorMessage.setVisibility(View.VISIBLE);
                        }
                }
            });
    private Uri assetsUri;
    ActivityResultLauncher<String> getAssetsFileContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri != null) {
                        try {
                            if (viewModel.checkAssetsFile(uri)){
                                viewModel.GetAssetsCount();
                                assetsUri = uri;
                            } else {
                                binding.assetFileStatus.setImageResource(R.drawable.ic_error);
                                binding.assetFileStatus.setVisibility(View.VISIBLE);
                                binding.loadAssetsErrorMessage.setText(R.string.selected_file_is_not_asset_file);
                                binding.loadAssetsErrorMessage.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            binding.assetFileStatus.setImageResource(R.drawable.ic_error);
                            binding.assetFileStatus.setVisibility(View.VISIBLE);
                            binding.loadAssetsErrorMessage.setText(R.string.selected_file_is_not_asset_file);
                            binding.loadAssetsErrorMessage.setVisibility(View.VISIBLE);
                        }
                    } else {
                        binding.assetFileStatus.setImageResource(R.drawable.ic_error);
                        binding.assetFileStatus.setVisibility(View.VISIBLE);
                        binding.loadAssetsErrorMessage.setText(R.string.no_file_selected);
                        binding.loadAssetsErrorMessage.setVisibility(View.VISIBLE);
                    }
                }
            });
//    private Uri conditionsUri;
//    ActivityResultLauncher<String> getAssetConditionsFileContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
//            new ActivityResultCallback<Uri>() {
//                @Override
//                public void onActivityResult(Uri uri) {
//                    if (uri != null) {
//                    try {
//                        if (ReadWriteExcelSheet(uri,getContext())){
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
//                    } else {
//                        binding.assetConditionFileStatus.setImageResource(R.drawable.ic_error);
//                        binding.assetConditionFileStatus.setVisibility(View.VISIBLE);
//                        binding.loadAssetConditionErrorMessage.setText(R.string.no_file_selected);
//                        binding.loadAssetConditionErrorMessage.setVisibility(View.VISIBLE);
//                    }
//                }
//            });


    @Override
    public void onClick(View v) {
            buttonId = v.getId();
            checkPermission();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        viewModel.deleteAllUsers();
//        viewModel.deleteAllConditions();
    }
    private  int buttonId;
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            if (buttonId == R.id.load_users_file) {
                getUsersFileContent.launch("*/*");
//            } else if (buttonId == R.id.asset_condition_load_file) {
//                getAssetConditionsFileContent.launch("*/*");
            } else if (buttonId == R.id.load_asset_file) {
                getAssetsFileContent.launch("*/*");
            } else if (buttonId == R.id.load_user_location_file) {
                getUserLocationFileContent.launch("*/*");
            }
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    if (buttonId == R.id.load_users_file) {
                        getUsersFileContent.launch("*/*");
//                    } else if (buttonId == R.id.asset_condition_load_file) {
//                        getAssetConditionsFileContent.launch("*/*");
                    } else if (buttonId == R.id.load_asset_file) {
                        getAssetsFileContent.launch("*/*");
                    } else if (buttonId == R.id.load_user_location_file) {
                        getUserLocationFileContent.launch("*/*");
                    }
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    MyMethods.warningDialog(getContext(),getString(R.string.you_shold_accept_read_storage_permisstion_to_be_able_to_load_installation_files));
                }
            });

    @Override
    public void onResume() {
        super.onResume();
        MyMethods.changeTitle(getString(R.string.loading_data),(MainActivity) getActivity());
    }


}