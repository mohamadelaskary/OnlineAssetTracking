package com.example.OnlineAssetTracking.Ui;

import static com.example.OnlineAssetTracking.MyMethods.Tools.getEditTextText;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;

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
    final Handler handler = new Handler(Looper.getMainLooper());
    final long DEBOUNCE_DELAY = 500; // 500ms
    Runnable workRunnable = null;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextAndHint();
        getAssetsList();
        setUpAssetsSpinner();
        observeGettingAssetList();
        observeGettingAssetsListStatus();
        setUpAssetsSpinner();
//        handleOnAssetSelected();
        binding.assetDescriptionSpinner.spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String query = charSequence.toString().trim();

                // أي Dropdown ظاهر يختفي طول الكتابة
                binding.assetDescriptionSpinner.spinner.dismissDropDown();

                // أي Runnable سابق متأجل يتم إلغاؤه
                if (workRunnable != null) {
                    handler.removeCallbacks(workRunnable);
                }

                // نجهز Runnable جديد للتنفيذ بعد الـ debounce
                workRunnable = () -> {
                    if (!query.isEmpty()) {
                        // ننده الـ API
                        viewModel.getAllAssetsData(query);
                    }
                };

                // تنفيذ الـ Runnable بعد 500ms من التوقف عن الكتابة
                handler.postDelayed(workRunnable, DEBOUNCE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
        assetsAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,assetList){
            @Override
            public Filter getFilter() {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        FilterResults results = new FilterResults();
                        results.values = assetList; // <— اعرض كل البيانات اللي راجعة من الـ API
                        results.count = assetList.size();
                        return results;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        notifyDataSetChanged();
                    }
                };
            }
        };
        binding.assetDescriptionSpinner.spinner.setAdapter(assetsAdapter);

        handleOnAssetSelected();
    }
    private LoadingDialog loadingDialog;
    private void observeGettingAssetsListStatus() {
        viewModel.getGetAllAssetsDataStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
//                    loadingDialog.show();
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
            assetsAdapter.clear();
            assetsAdapter.addAll(assetList);
            assetsAdapter.notifyDataSetChanged();
            binding.assetDescriptionSpinner.spinner.setAdapter(assetsAdapter);
            binding.assetDescriptionSpinner.spinner.showDropDown();
        });
    }


    private void getAssetsList() {
//        viewModel.getAllAssetsData();
//        viewModel.getAllAssetsData("كرسي");
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