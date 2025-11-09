package com.example.OnlineAssetTracking.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.R;
import com.example.OnlineAssetTracking.databinding.AssetItemBinding;

import java.util.List;

public class AssetListAdapter extends RecyclerView.Adapter<AssetListAdapter.AssetListViewHolder> {
    private List<Asset> assetList;
    private Context context;
    public AssetListAdapter(Context context) {
        this.context = context;
    }

    public void setAssetList(List<Asset> assetList) {
        this.assetList = assetList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssetListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AssetItemBinding binding =AssetItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AssetListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetListViewHolder holder, int position) {
        Asset asset = assetList.get(position);
        AssetItemBinding binding = holder.binding;
        Log.d("AssetListAdapter", "onBindViewHolder: "+asset.getDescription());
        binding.assetBarcode.setText(asset.getBarcode());
        binding.assetStatus.setText(asset.getAssetConditionName());
        binding.assetDescription.setText(asset.getDescription());
        binding.mainCategory.setText(asset.getMainCategoryName());
        binding.subCategory.setText(asset.getSubCategory2Name());
        if (!asset.getIsSameLocation().isEmpty()) {
            if (asset.getIsSameLocation().equals("1"))
                holder.itemView.setBackground(context.getDrawable(R.drawable.exist_asset_item_background));
            else
                holder.itemView.setBackground(context.getDrawable(R.drawable.different_place_asset_background));
        } else
            holder.itemView.setBackground(context.getDrawable(R.drawable.default_asset_item_background));
    }

    @Override
    public int getItemCount() {
        return assetList==null?0:assetList.size();
    }

    static class AssetListViewHolder extends RecyclerView.ViewHolder{
        private AssetItemBinding binding;
        public AssetListViewHolder(@NonNull AssetItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
