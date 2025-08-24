package com.example.OnlineAssetTracking.Ui;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.MyMethods.Constants.SELECTED_TRACKING_ORDER_ID_KEY;
import static com.example.OnlineAssetTracking.MyMethods.Constants.SELECTED_USER_ID_KEY;
import static com.example.OnlineAssetTracking.MyMethods.Tools.clearInputLayoutError;
import static com.example.OnlineAssetTracking.MyMethods.Tools.getIntegerDataFromLocalStorage;
import static com.example.OnlineAssetTracking.MyMethods.Tools.warningDialog;
import static com.example.OnlineAssetTracking.Ui.MainActivity.ORDER_ID;
import static com.example.OnlineAssetTracking.Ui.MainActivity.USER_ID;
import static com.example.OnlineAssetTracking.Ui.MainActivity.refreshUi;
import static com.example.OnlineAssetTracking.Util.Constants.ADMIN_PASSWORD;
import static com.example.OnlineAssetTracking.Util.Constants.ADMIN_USER_NAME;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.OnlineAssetTracking.MyMethods.EncryptionManager;
import com.example.OnlineAssetTracking.MyMethods.LoadingDialog;
import com.example.OnlineAssetTracking.MyMethods.Tools;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.Util.LocaleHelper;
import com.example.OnlineAssetTracking.ViewModel.SignInViewModel;
import com.example.OnlineAssetTracking.databinding.SignInFragmentBinding;

import java.util.Locale;

public class SignInFragment extends Fragment implements View.OnClickListener {

    private SignInViewModel viewModel;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }
    SignInFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SignInFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private EncryptionManager encryptionManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        encryptionManager = new EncryptionManager();
    }
    LoadingDialog loadingDialog;
    private ChangeSettingsDialog changeSettingsDialog;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attachButtonsToListener();
        loadingDialog = Tools.showLoadingDialog(getContext());
        changeSettingsDialog = new ChangeSettingsDialog(getContext(),getActivity().getApplication(),getActivity());
        observeSignInUser();
        observeSignInStatus();

        currentLang = LocaleHelper.getLanguage(getContext());
        defaultLanguage = Locale.getDefault().getLanguage();
        Log.d("languageStored",currentLang);
        Log.d("languageDefault",defaultLanguage);
//        if (!defaultLanguage.equals(currentLang)){
//            if (currentLang.equals("ar")&&!defaultLanguage.equals("ar")) {
//                LocaleHelper.setLocale(getContext(),"ar");
//                refreshUi((MainActivity) getActivity());
//            } else if (currentLang.equals("en")&&!defaultLanguage.equals("en")){
//                LocaleHelper.setLocale(getContext(),"en");
//                refreshUi((MainActivity) getActivity());
//            }
//        }
        handleLanguageButton();
        clearInputLayoutError(binding.userName);
        clearInputLayoutError(binding.password);

    }
    private String defaultLanguage;
    private void handleLanguageButton() {
        Log.d("language",currentLang+" lang");
                if (defaultLanguage.equals("ar")) {
                    binding.language.setText("E");
                } else if (currentLang.equals("en")){
                    binding.language.setText("ع");
                }
    }

    private void observeSignInUser() {
        viewModel.getSignInLiveData().observe(getViewLifecycleOwner(),user -> {
            String enteredPassword = binding.password.getEditText().getText().toString().trim();
            String encryptedPassword = encryptionManager.encrypt(enteredPassword.getBytes()).trim();
            Log.d(TAG, "observeSignInUser: "+getSelectedUserId());
            if (encryptedPassword.equals(user.getPassword().trim())){
                if(getSelectedUserId()==user.getUserId()) {
                    USER_ID = user.getUserId();
                    ORDER_ID = getSelectedTrackingOrderId();
                    bundle.putString(USER_TYPE, "not_admin");
                    Navigation.findNavController(getView()).navigate(R.id.action_signInFragment_to_mainFragment, bundle);
                    Log.d(TAG, "observeSignInUserOrderId: " + ORDER_ID);
                    Log.d(TAG, "observeSignInUserOrderId: " + user.getRoleId());
                    if (ORDER_ID.equals("0")) {
//                    if (user.getRoleId()==2)
                        ((MainActivity) getActivity()).noLocationText().setVisibility(View.VISIBLE);
//                    else
//                        ((MainActivity) getActivity()).noLocationText().setVisibility(View.GONE);
                    } else {
                        ((MainActivity) getActivity()).noLocationText().setVisibility(View.GONE);
                    }
                } else {
                    warningDialog(requireContext(),getString(R.string.the_entered_user_isn_t_the_user_selected_for_tracking_with_this_device));
                }
            } else
                binding.password.setError(getString(R.string.wrong_password));
            loadingDialog.dismiss();
        });
    }

    private String getSelectedTrackingOrderId() {
        return String.valueOf(getIntegerDataFromLocalStorage(requireActivity(),SELECTED_TRACKING_ORDER_ID_KEY));
    }

    private int getSelectedUserId() {
        return getIntegerDataFromLocalStorage(requireActivity(),SELECTED_USER_ID_KEY);
    }


    private void observeSignInStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status){
                case LOADING:
                    loadingDialog.show();
                    break;
                case SUCCESS:
//                    if (binding.password.getEditText().getText().toString().trim().equals(password)){
//                        bundle.putString(USER_TYPE,"not_admin");
//                        viewModel.getOrderId(USER_ID);
//                        Navigation.findNavController(getView()).navigate(R.id.action_signInFragment_to_mainFragment,bundle);
//                    } else {
//                        binding.password.setError(getString(R.string.wrong_password));
//                    }
                    loadingDialog.dismiss();
                    break;
                case ERROR:
                    loadingDialog.dismiss();
                    binding.userName.setError(getString(R.string.wrong_user_name));
                    break;
            }
        });
    }
    private String currentLang;
    private void attachButtonsToListener() {
        binding.signIn.setOnClickListener(this);
//        binding.language.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
//
//
//            if (isChecked) {
//                if (checkedId == R.id.arabic&&currentLang.equals("en")) {
//                    LocaleHelper.setLocale(getContext(), "ar");
//                    refreshUi((MainActivity) getActivity());
//                } else if (checkedId == R.id.english&&currentLang.equals("ar")) {
//                    LocaleHelper.setLocale(getContext(), "en");
//                    refreshUi((MainActivity) getActivity());
//                }
//
//            }
//        });;
        binding.language.setOnClickListener(this);
        binding.settings.setOnClickListener(this);
    }
    public static String USER_TYPE = "userType";
    Bundle bundle = new Bundle();
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sign_in) {
            String userName = binding.userName.getEditText().getText().toString().trim();
            String password = binding.password.getEditText().getText().toString().trim();
            if (!userName.isEmpty()) {
                if (!password.isEmpty()) {
                    if (userName.equals(ADMIN_USER_NAME) && password.equals(ADMIN_PASSWORD)) {
                        bundle.putString(USER_TYPE, "admin");
                        Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_mainFragment, bundle);
                    } else {
                        viewModel.signIn(userName);
                    }
                } else {
                    binding.password.setError(getString(R.string.please_enter_password));
                }
            } else {
                binding.userName.setError(getString(R.string.please_enter_user_name));
            }
        } else if (id == R.id.language) {
            if (currentLang.equals("ar")) {
                LocaleHelper.setLocale(getContext(), "en");
                refreshUi((MainActivity) getActivity());
            } else if (currentLang.equals("en")) {
                LocaleHelper.setLocale(getContext(), "ar");
                refreshUi((MainActivity) getActivity());
            }
//                MainActivity.refreshUi((MainActivity) getActivity());
        } else if (id == R.id.settings) {
            changeSettingsDialog.show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Tools.hideToolBar((MainActivity) getActivity());
    }

}