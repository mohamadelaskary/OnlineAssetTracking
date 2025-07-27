package com.example.OnlineAssetTracking.MyMethods;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.example.OnlineAssetTracking.databinding.LoadingDialogLayoutBinding;

public class LoadingDialog extends Dialog {
    private Context context;
    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }
    private LoadingDialogLayoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = LoadingDialogLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        binding.progressBar.show();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        this.getWindow().setLayout((6 * width)/7, (2 * height)/7);
    }
}
