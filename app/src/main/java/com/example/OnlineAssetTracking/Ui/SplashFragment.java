package com.example.OnlineAssetTracking.Ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.OnlineAssetTracking.MyMethods.MyMethods;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.databinding.FragmentSplashBinding;

public class SplashFragment extends Fragment implements View.OnClickListener {



    public SplashFragment() {
        // Required empty public constructor
    }


    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentSplashBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startAnimation();
        attachButtonToListener();

    }

    private void attachButtonToListener() {
        binding.importFile.setOnClickListener(this);
    }

    private void startAnimation() {
        Animation logoAnim = AnimationUtils.loadAnimation( getContext(), R.anim.side_slide);
        binding.logo.startAnimation(logoAnim);

    }

    @Override
    public void onResume() {
        super.onResume();
        MyMethods.hideToolBar((MainActivity) getActivity());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_signInFragment);
            }
        }, 1100);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        switch (id){
//            case R.id.import_file:
//                Navigation.findNavController(v).navigate(R.id.action_splashFragment_to_selectRoomFragment);
//                break;
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
        MyMethods.showToolBar((MainActivity) getActivity());
    }
}