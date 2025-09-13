package com.example.OnlineAssetTracking.Ui;

import static com.example.OnlineAssetTracking.MyMethods.Tools.clearInputLayoutError;
import static com.example.OnlineAssetTracking.MyMethods.Tools.getEditTextText;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.databinding.ChangePortDialogLayoutBinding;

public class ChangePortDialog extends Dialog {
    private Context context;
    private OnChangePortDialogSaveButtonClicked onChangePortDialogSaveButtonClicked;
    public ChangePortDialog(@NonNull Context context,OnChangePortDialogSaveButtonClicked onChangePortDialogSaveButtonClicked) {
        super(context);
        this.context = context;
        this.onChangePortDialogSaveButtonClicked = onChangePortDialogSaveButtonClicked;
    }
    private ChangePortDialogLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChangePortDialogLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        clearInputLayoutError(binding.port);
        binding.save.setOnClickListener(v->{
            String portNo = getEditTextText(binding.port);
            if (!portNo.isEmpty()){
                onChangePortDialogSaveButtonClicked.onChangePortDialogSaveButtonClicked(portNo,this);
            } else {
                binding.port.setError(context.getString(R.string.please_enter_port_number));
            }
        });
    }

    public interface OnChangePortDialogSaveButtonClicked {
        void onChangePortDialogSaveButtonClicked(String portNo, DialogInterface dialogInterface);
    }
}
