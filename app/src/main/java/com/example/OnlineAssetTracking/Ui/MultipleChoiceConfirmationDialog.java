package com.example.OnlineAssetTracking.Ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.OnlineAssetTracking.databinding.MultipleChoiceConfirmationDialogBinding;

public class MultipleChoiceConfirmationDialog extends Dialog {
    private String title;
    private String message;
    private String positiveButtonText;
    private String negativeButtonText;
    private OnDialogButtonsClicked onDialogButtonsClicked;

    public MultipleChoiceConfirmationDialog(Context context,String title, String message, String positiveButtonText, String negativeButtonText, OnDialogButtonsClicked onDialogButtonsClicked) {
        super(context);
        this.title = title;
        this.message = message;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
        this.onDialogButtonsClicked = onDialogButtonsClicked;
    }
    private MultipleChoiceConfirmationDialogBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MultipleChoiceConfirmationDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.title.setText(title);
        binding.message.setText(message);
        binding.positiveButton.setText(positiveButtonText);
        binding.negativeButton.setText(negativeButtonText);
        binding.positiveButton.setOnClickListener(view -> {
            onDialogButtonsClicked.OnPositiveButtonClicked(this);
        });
        binding.negativeButton.setOnClickListener(view ->{
            onDialogButtonsClicked.OnNegativeButtonClicked(this);
        });
    }

    public  interface OnDialogButtonsClicked {
        void OnPositiveButtonClicked(DialogInterface dialogInterface);
        void OnNegativeButtonClicked(DialogInterface dialogInterface);
    }
}
