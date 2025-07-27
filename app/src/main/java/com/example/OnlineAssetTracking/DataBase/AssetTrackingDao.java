package com.example.OnlineAssetTracking.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface AssetTrackingDao {

    @Insert(onConflict = REPLACE)
    Completable insertUsers(List<User> users);
    @Insert(onConflict = REPLACE)
    Completable insertAssetConditions(List<AssetCondition> conditions);
    @Insert(onConflict = REPLACE)
    Completable insertUserLocations(List<UserLocation> userLocations);
    @Insert(onConflict = REPLACE)
    Completable insertAssets(List<Asset> assets);
    @Update
    Completable updateLocation(Asset asset);
    @Query("select * from User where userName = :userName")
    Single<User> getUserInformation(String userName);
    @Query("delete from User")
    Completable deleteAllUsers();
    @Query("delete from Asset")
    Completable deleteAllAssets();
    @Query("delete from UserLocation")
    Completable deleteAllUserLocations();
    @Query("delete from AssetCondition")
    Completable deleteAllConditions();
    @Query("select * from AssetCondition")
    Single<List<AssetCondition>> getAllAssetConditions();
    @Query("select * from UserLocation "
            +
            "where roomCode = :roomCode"
    )
    Single<UserLocation> getRoomData(
            String roomCode
    );
    @Query("select * from UserLocation where floorId = :floorId")
    Single<UserLocation> getFloorData(
            String floorId
    );

    @Query("select * from Asset where barcode = :assetCode")
    Single<Asset> getAssetData(String assetCode);


    @Query("select * from Asset")
    Single<List<Asset>> getAllAssetsData();
    @Query("select * from Asset"
           + " where floorId = :floorId"
    )
    Observable<List<Asset>> getAllAssetsInFloor(
            int floorId
    );
    @Query("select * from Asset"
            + " where roomId = :roomId"
    )
    Single<List<Asset>> getAllAssetsInRoom(
            int roomId
    );
    @Query("select * from Asset")
    Single<List<Asset>> getAllScannedAssets();
    @Query("select * from UserLocation " +
            "where userId = :userId")
    Single<List<UserLocation>> getUserLocations(
            int userId
    );
    @Query("Select Count(*) from user")
    Single<Integer> usersCount();
    @Query("Select Count(*) from UserLocation")
    Single<Integer> userLocationsCount();
    @Query("Select Count(*) from Asset")
    Single<Integer> assetsCount();
    @Query("Select Count(*) from AssetCondition")
    Single<Integer> assetConditionsCount();
//    @Insert
//    Completable insertScannedAsset(ScannedAsset asset);

}
