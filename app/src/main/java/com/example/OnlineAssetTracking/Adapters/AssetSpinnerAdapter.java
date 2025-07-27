package com.example.OnlineAssetTracking.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.R;

import java.util.List;

public class AssetSpinnerAdapter extends ArrayAdapter<Asset> {
    private final List<Asset> assets;
    public AssetSpinnerAdapter(@NonNull Context context, int resource,List<Asset> assets) {
        super(context, resource);
        this.assets = assets;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.asset_spinner_item, parent,false);
        }
        TextView assetDesc = v.findViewById(R.id.asset_description);
        assetDesc.setText(assets.get(position).getDescription());
        TextView assetCode = v.findViewById(R.id.asset_code);
        assetCode.setText(assets.get(position).getBarcode());
        return v;
    }

    @Override
    public int getCount() {
        return assets==null?0: assets.size();
    }
}
