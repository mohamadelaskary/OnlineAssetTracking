package com.example.OnlineAssetTracking.MyMethods;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.OnlineAssetTracking.databinding.CustomDialogWithChoicesBinding;

import java.io.FileNotFoundException;

public class CustomDialogWithChoices extends Dialog {
    private String message;
    private OnOkClickedListener onOkClickedListener;
    public CustomDialogWithChoices(@NonNull Context context) {
        super(context);
    }
    CustomDialogWithChoicesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CustomDialogWithChoicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.message.setText(message);
//        binding.imageView.setImageResource(image);
        binding.ok.setOnClickListener(v-> {
            try {
                onOkClickedListener.onOkClickedListener();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        binding.cancel.setOnClickListener(v->dismiss());
    }

    public void setOnOkClickedListener(OnOkClickedListener onOkClickedListener) {
        this.onOkClickedListener = onOkClickedListener;
    }

    public interface OnOkClickedListener{
        void onOkClickedListener() throws FileNotFoundException;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.message.setText(message);
    }
}
