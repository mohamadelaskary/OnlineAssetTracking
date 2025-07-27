package com.example.OnlineAssetTracking.DataBase;


import android.content.Context;

import androidx.room.Room;

public class DataBase {
    private static AssetTrackingDataBase dataBase;
    public static AssetTrackingDataBase getInstance(Context context){
        if (dataBase==null){
            dataBase = Room.databaseBuilder(context,
                    AssetTrackingDataBase.class, "Asset tracking database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dataBase;
    }
}
