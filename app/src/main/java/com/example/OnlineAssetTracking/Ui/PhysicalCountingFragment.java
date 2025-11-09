package com.example.OnlineAssetTracking.Ui;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.OnlineAssetTracking.Ui.MainActivity.BASE_URL;
import static com.example.OnlineAssetTracking.Ui.MainActivity.ORDER_ID;
import static com.example.OnlineAssetTracking.Ui.MainActivity.USER_ID;
import static com.example.OnlineAssetTracking.Ui.SelectRoomFragment.ROOM_CODE;
import static com.example.OnlineAssetTracking.Ui.SelectRoomFragment.USER_LOCATION;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.OnlineAssetTracking.Adapters.AssetConditionsAdapter;
import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.DataBase.UserLocation;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.Tools;
import com.example.OnlineAssetTracking.MyMethods.SetUpBarCodeReader;
import com.example.OnlineAssetTracking.ViewModel.PhysicalCountingViewModel;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.databinding.PhysicalCountingFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class PhysicalCountingFragment extends Fragment implements AssetConditionsAdapter.OnAssetConditionSelected, View.OnClickListener, View.OnKeyListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {

    public static final String ASSET_DATA = "ASSET_DATA" ;
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
        loadingDialog = Tools.showLoadingDialog(getContext());
    }
    private LoadingDialog loadingDialog;
    private final ActivityResultLauncher<String> requestCameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openCamera();
                } else {
                    Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    // 2️⃣ Launcher لفتح الكاميرا
    private final ActivityResultLauncher<Uri> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
                if (result) {
                    File file = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp_image.jpg");
                    uploadImage(file);
                } else {
                    Toast.makeText(requireContext(), "No photo captured", Toast.LENGTH_SHORT).show();
                }
            });

    private void uploadImage(File file) {
        viewModel.uploadImage(asset.getBarcode(), file);
    }


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
                    Tools.showSuccessAlerter(getString(R.string.saved_successfully),getActivity());
//                    binding.dataLayout.setVisibility(View.GONE);
//                    binding.assetCode.getEditText().setText("");
                    break;
                case ERROR:
                    loadingDialog.dismiss();
                    break;

            }
        });
    }
    private void checkCameraPermissionAndOpen() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }
    Uri imageUri;
    private void openCamera() {
        File photoFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "temp_image.jpg");
        imageUri = FileProvider.getUriForFile(requireContext(),
                requireActivity().getPackageName() + ".provider", photoFile);
        takePictureLauncher.launch(imageUri);
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
                binding.assetStatusDesc.oldAssetStatus.setVisibility(VISIBLE);
                binding.assetStatusDesc.oldAssetStatus.setText(asset.getAssetConditionName());
                binding.assetStatusDesc.newAssetStatus.setText(newAssetStatus.getAssetConditionName());
            } else {
                binding.assetStatusDesc.newAssetStatus.setText(asset.getAssetConditionName());
                binding.assetStatusDesc.oldAssetStatus.setVisibility(GONE);
            }
        } else {
            binding.assetStatusDesc.newAssetStatus.setText(asset.getAssetConditionName());
            binding.assetStatusDesc.oldAssetStatus.setVisibility(GONE);
        }
    }

    private void hideBottomSheet() {
        assetConditionsBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        binding.disableColor.setVisibility(GONE);
    }

    private void showBottomSheet(){
        assetConditionsBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        binding.disableColor.setVisibility(VISIBLE);
        adapter.setSelectedPosition(-1);
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
        Tools.clearInputLayoutError(binding.assetCode);
    }

    private void observeGettingAssetDataStatus() {
        statusObserver = status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    binding.dataLayout.setVisibility(GONE);
                    binding.assetCode.setError(null);
                    break;
                case IDLE:
                    loadingDialog.dismiss();
                    binding.dataLayout.setVisibility(GONE);
                    binding.assetCode.setError(null);
                    break;
                case SUCCESS:
                    loadingDialog.dismiss();
                    binding.dataLayout.setVisibility(VISIBLE);
                    binding.assetCode.setError(null);
                    break;
                case ERROR:
                    loadingDialog.dismiss();
                    binding.dataLayout.setVisibility(GONE);
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
            viewModel.saveScannedAsset(asset);
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
        binding.assetDescription.mainCategory.setText(asset.getMainCategoryName());
        binding.assetDescription.subCategory.setText(asset.getSubCategory2Name());
        binding.assetDescription.assetDescription.setText(asset.getDescription());
//        if (asset.getFileBasse()!=null || !asset.getFileBasse().isEmpty()) {
////            binding.assetDescription.assetImage.setImageBitmap(convertBase64toBitmap(asset.getImage()));
            Glide.with(requireContext())
                    .load(BASE_URL+"image/"+asset.getBarcode())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            binding.assetDescription.addImage.setVisibility(VISIBLE);
                            binding.assetDescription.replaceImage.setVisibility(GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.assetDescription.addImage.setVisibility(GONE);
                            binding.assetDescription.replaceImage.setVisibility(VISIBLE);
                            return false;
                        }
                    })
                    .into(binding.assetDescription.assetImage);

            binding.assetDescription.assetImage.setVisibility(VISIBLE);
            binding.assetDescription.assetImage.invalidate();
//        }
//        else
//            binding.assetDescription.assetImage.setVisibility(GONE);
        handleAssetConditionChange();
        if (newAssetStatus!=null) {
            asset.setNewAssetConditionId(newAssetStatus.getAssetConditionId());
            asset.setIsSameCondition("0");
        } else {
            asset.setNewAssetConditionId(asset.getAssetConditionId());
            asset.setIsSameCondition("1");
        }
        if (!roomCode.isEmpty()) {
            asset.setNewRoomCode(userLocation.getRoomCode());
            asset.setNewBuildingId(userLocation.getBuildingId());
            asset.setNewFloorId(userLocation.getFloorId());
            Log.d(TAG, "fillAssetData: "+userLocation.getCompanyId());
            asset.setTrackingOrderId(ORDER_ID);
            if (asset.getNewRoomCode().equals(asset.getRoomCode()))
                asset.setIsSameLocation("1");
            else
                asset.setIsSameLocation("0");


        } else {
            asset.setNewFloorId(userLocation.getFloorId());
            if (asset.getNewFloorId()==asset.getFloorId())
                asset.setIsSameLocation("1");
            else
                asset.setIsSameLocation("0");
        }

                if (ORDER_ID!=null){
                    asset.setOrderId(Integer.parseInt(ORDER_ID));
                }
                asset.setUserId(USER_ID);
                asset.setDate(Tools.todayDate());

        binding.assetStatusDesc.oldAssetStatus.setVisibility(GONE);
        newAssetStatus = null;
        viewModel.saveScannedAsset(asset);
    }

    private void fillRoomData() {
        binding.locationInfo.buildingName.setText(userLocation.getBuildingName());
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
        binding.assetDescription.addImage.setOnClickListener(view -> {
            checkCameraPermissionAndOpen();
        });
        binding.assetDescription.replaceImage.setOnClickListener(view -> {
            checkCameraPermissionAndOpen();
        });
    }

    private void setTexts() {
        binding.assetCode.setHint(R.string.asset_code);
        binding.assetCode.setHelperText(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        Tools.changeTitle(getString(R.string.physical_counting),(MainActivity) getActivity());
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
            if (newAssetStatus != null) {
                asset.setNewAssetConditionId(newAssetStatus.getAssetConditionId());
                asset.setIsSameCondition("0");
            } else {
                asset.setNewAssetConditionId(asset.getAssetConditionId());
                asset.setIsSameCondition("1");
            }
            if (!roomCode.isEmpty()) {
                asset.setNewRoomId(userLocation.getRoomId());
                asset.setNewBuildingId(userLocation.getBuildingId());
                asset.setNewFloorId(userLocation.getFloorId());


                if (asset.getNewRoomId() == asset.getRoomId())
                    asset.setIsSameLocation("1");
                else
                    asset.setIsSameLocation("0");

                if (asset.getNewBuildingId() == asset.getBuildingId()) {
                    asset.setIsSameBuilding("1");
                } else {
                    asset.setIsSameBuilding("0");
                }
                if (asset.getNewFloorId() == asset.getFloorId()) {
                    asset.setIsSameFloor("1");
                } else {
                    asset.setIsSameFloor("0");
                }
            } else {
                asset.setNewFloorId(userLocation.getFloorId());
                if (asset.getNewFloorId() == asset.getFloorId())
                    asset.setIsSameLocation("1");
                else
                    asset.setIsSameLocation("0");
            }

//                if (ORDER_ID!=null){
//                    asset.setOrderId(ORDER_ID);
//                }
//                    asset.setUserId(String.valueOf(USER_ID));
//                asset.setUserId(String.valueOf(USER_ID));
//                asset.setDate(MyMethods.todayDate());

            binding.assetStatusDesc.oldAssetStatus.setVisibility(GONE);
            newAssetStatus = null;
            viewModel.saveScannedAsset(asset);
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