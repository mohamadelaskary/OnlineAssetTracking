package com.example.OnlineAssetTracking.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.databinding.ConditionItemBinding;

import java.util.List;

public class AssetConditionsAdapter extends RecyclerView.Adapter<AssetConditionsAdapter.AssetConditionViewHolder> {
    private List<AssetCondition> assetConditions;
    private Context context;
    private OnAssetConditionSelected onAssetConditionSelected;
    public void setAssetConditions(List<AssetCondition> assetConditions) {
        this.assetConditions = assetConditions;
        notifyDataSetChanged();
    }

    public AssetConditionsAdapter(Context context,OnAssetConditionSelected onAssetConditionSelected) {
        this.context = context;
        this.onAssetConditionSelected = onAssetConditionSelected;
    }

    @NonNull
    @Override
    public AssetConditionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConditionItemBinding binding = ConditionItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AssetConditionViewHolder(binding);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    private int selectedPosition = -1;
    @Override
    public void onBindViewHolder(@NonNull AssetConditionViewHolder holder, int position) {
        int currentPosition = position;
        AssetCondition assetCondition = assetConditions.get(currentPosition);
        holder.binding.assetCondition.setText(assetCondition.getAssetConditionName());
        if (currentPosition == selectedPosition)
            activateItem(holder);
        else
            deactivateItem(holder);
        holder.itemView.setOnClickListener(v -> {
            selectedPosition = currentPosition;
            onAssetConditionSelected.onAssetConditionSelected(assetCondition);
            notifyDataSetChanged();
        });
    }

    private void activateItem(AssetConditionViewHolder holder) {
        CardView cardView = (CardView) holder.itemView;
        holder.binding.assetCondition.setTextColor(context.getResources().getColor(R.color.white));
        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.selected_asset_condition_color));
    }

    @Override
    public int getItemCount() {
        return assetConditions==null?0: assetConditions.size();
    }

    private void deactivateItem (AssetConditionViewHolder holder) {
        CardView cardView = (CardView) holder.itemView;
        holder.binding.assetCondition.setTextColor(context.getResources().getColor(R.color.black));
        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
    }
    static class AssetConditionViewHolder extends RecyclerView.ViewHolder {
        private ConditionItemBinding binding;
        public AssetConditionViewHolder(@NonNull ConditionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnAssetConditionSelected {
        void onAssetConditionSelected(AssetCondition assetCondition);
    }
}
