package com.example.OnlineAssetTracking.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={User.class,AssetCondition.class,UserLocation.class,Asset.class},version = 29,exportSchema = false)
public abstract class AssetTrackingDataBase extends RoomDatabase {
    public abstract AssetTrackingDao dao();
}
