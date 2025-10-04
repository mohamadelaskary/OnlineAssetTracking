package com.example.OnlineAssetTracking.MyMethods;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetWithUserLocation;

import java.util.ArrayList;
import java.util.List;

public class AssetSearchAdapter extends ArrayAdapter<AssetWithUserLocation> {
    private List<AssetWithUserLocation> originalList;
    private List<AssetWithUserLocation> filteredList;
    private Filter filter;

    public AssetSearchAdapter(Context context, List<AssetWithUserLocation> list) {
        super(context, android.R.layout.simple_list_item_1, list);
        this.originalList = new ArrayList<>(list);
        this.filteredList = list;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public AssetWithUserLocation getItem(int position) {
        return filteredList.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    if (constraint != null && constraint.length() > 0) {
                        String searchStr = constraint.toString().toLowerCase();
                        List<AssetWithUserLocation> resultList = new ArrayList<>();

                        for (AssetWithUserLocation item : originalList) {
                            // 🔎 هنا بتحدد attributes اللي عايز تعمل filter عليها
                            if (item.getBarcode().toLowerCase().contains(searchStr) ||
                                    item.getSerialNumber().toLowerCase().contains(searchStr) ||
                                    item.getDescription().toLowerCase().contains(searchStr)) {
                                resultList.add(item);
                            }
                        }

                        results.values = resultList;
                        results.count = resultList.size();
                    } else {
                        results.values = originalList;
                        results.count = originalList.size();
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredList = (List<AssetWithUserLocation>) results.values;
                    notifyDataSetChanged();
                }
            };
        }
        return filter;
    }
}
