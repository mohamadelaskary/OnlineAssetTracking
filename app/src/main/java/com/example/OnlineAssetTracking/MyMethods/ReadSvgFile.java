package com.example.OnlineAssetTracking.MyMethods;

import static android.content.ContentValues.TAG;
import static com.example.OnlineAssetTracking.MyMethods.Tools.containsOnlyDigits;
import static com.example.OnlineAssetTracking.MyMethods.Tools.warningDialog;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.OnlineAssetTracking.DataBase.Asset;
import com.example.OnlineAssetTracking.DataBase.AssetCondition;
import com.example.OnlineAssetTracking.DataBase.User;
import com.example.OnlineAssetTracking.DataBase.UserLocation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReadSvgFile {
    public static boolean isUsersFile(Uri uri, Context context) throws FileNotFoundException {
        boolean isValid = false;
        if (uri!=null) {
            InputStream in = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = null;
            reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)
            );
            String line = "";
            try {
                line = reader.readLine();
                String[] tokens = line.split(",");
                if (
                        tokens[0].equals("\uFEFFUser ID") &&
                                tokens[1].equals("User Name") &&
                                tokens[2].equals("employee ID") &&
                                tokens[3].equals("employee Name") &&
                                tokens[4].equals("Email") &&
                                tokens[5].equals("Role ID") &&
                                tokens[6].equals("Role Name") &&
                                tokens[7].equals("User Password")
                )
                    isValid = true;
            } catch (IOException e) {
                Log.wtf("Error", "Error in this" + line);
                e.printStackTrace();
            }
        }
        return isValid;
    }

    public static List<User> readUsers(Uri uri,Context context) throws FileNotFoundException {
        List<User> users = new ArrayList<>();
        if (uri!=null) {
            InputStream in = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = null;
            reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)
            );
            int row = 0;
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    if (row == 0) {
                        row++;
                        continue;
                    }
                    String[] tokens = line.replace("\"","").split(",");
                    User user = new User(
                            containsOnlyDigits(tokens[0])?Integer.parseInt(tokens[0]):0,
                            tokens[1].replace("\"",""),
                            containsOnlyDigits(tokens[2])?Integer.parseInt(tokens[2].replace("\"","")):0,
                            tokens[3].replace("\"",""),
                            tokens[4].replace("\"",""),
                            containsOnlyDigits(tokens[5])?Integer.parseInt(tokens[5].replace("\"","")):0,
                            tokens[6].replace("\"",""),
                            tokens[7].replace("\"","")
                    );

                    users.add(user);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
    public static boolean isAssetConditionsFile(Uri uri, Context context) throws FileNotFoundException {
        boolean isValid = false;
        if (uri!=null) {
            InputStream in = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = null;
            reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)
            );
            String line = "";
            try {
                line = reader.readLine();
                String[] tokens = line.split(",");
                if (
                        tokens[0].equals("\uFEFFAssetCondition ID") &&
                                tokens[1].equals("AssetCondition Name")
                )
                    isValid = true;
            } catch (IOException e) {
                Log.wtf("Error", "Error in this" + line);
                e.printStackTrace();
            }
        }
        return isValid;
    }

    public static List<AssetCondition> readAssetConditions(Uri uri, Context context) throws FileNotFoundException {
        List<AssetCondition> conditions = new ArrayList<>();
        if (uri!=null) {
            InputStream in = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = null;
            reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)
            );
            int row = 0;
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    if (row == 0) {
                        row++;
                        continue;
                    }
                    String[] tokens = line.replace("\"","").split(",");
                    AssetCondition condition = new AssetCondition(
                            Integer.parseInt(tokens[0].replace("\"","")),
                            tokens[1].replace("\"","")
                    );
                    Log.d("fileContent", condition.getAssetConditionId() + "");
                    conditions.add(condition);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return conditions;
    }
    public static boolean isUserLocationFile(Uri uri, Context context) throws FileNotFoundException {
        boolean isValid = false;
        if (uri!=null) {
            InputStream in = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = null;
            reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)
            );

            String line = "";
            try {
                line = reader.readLine();
                Log.d(TAG, "isUserLocationFile: "+line);
                String[] tokens = line.split(",");
                if (
                        tokens[0].equals("\uFEFFUserLocation Id") &&
                                tokens[1].equals("User Id") &&
                                tokens[2].equals("User Name") &&
                                tokens[3].equals("Company ID") &&
                                tokens[4].equals("Company Name") &&
                                tokens[5].equals("Site ID") &&
                                tokens[6].equals("Site Name") &&
                                tokens[7].equals("Building ID") &&
                                tokens[8].equals("Building Name") &&
                                tokens[9].equals("Floor ID") &&
                                tokens[10].equals("Floor Name") &&
                                tokens[11].equals("Room ID") &&
                                tokens[12].equals("Room Name") &&
                                tokens[13].equals("Room Code") &&
                                tokens[14].equals("Sector ID")&&
                                tokens[15].equals("Sector Name") &&
                                tokens[16].equals("Central Department ID") &&
                                tokens[17].equals("Central Department Name") &&
                                tokens[18].equals("Genral Department ID") &&
                                tokens[19].equals("Genral Department Name") &&
                                tokens[20].equals("Department ID") &&
                                tokens[21].equals("Department Name")&&
                                tokens[22].equals("Order ID") &&
                                tokens[23].equals("Order Number")

                )
                    isValid = true;

            } catch (IOException e) {
                Log.d("Error", "Error in this" + line+" "+e.getMessage());
                e.printStackTrace();
            }
        }
        return isValid;
    }

    public static List<UserLocation> readUserLocationFile(Uri uri, Context context) throws FileNotFoundException {
        List<UserLocation> userLocations = new ArrayList<>();
        if (uri!=null) {
            InputStream in = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = null;
            reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)
            );
            int row = 0;
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    if (row == 0) {
                        row++;
                        continue;
                    }
                    String[] tokens = line.replace("\"","").split(",");
                    Log.d("LocationFloorIdFile", tokens[10] + "");
                    UserLocation userLocation = new UserLocation(
                            containsOnlyDigits(tokens[0])?Integer.parseInt(tokens[0].replace("\"","")):0,
                            containsOnlyDigits(tokens[1])?Integer.parseInt(tokens[1].replace("\"","")):0,
                            tokens[2].replace("\"",""),
                            containsOnlyDigits(tokens[3])?Integer.parseInt(tokens[3].replace("\"","")):0,
                            tokens[4].replace("\"",""),
                            containsOnlyDigits(tokens[5])?Integer.parseInt(tokens[5].replace("\"","")):0,
                            tokens[6].replace("\"",""),
                            containsOnlyDigits(tokens[7])?Integer.parseInt(tokens[7].replace("\"","")):0,
                            tokens[8].replace("\"",""),
                            containsOnlyDigits(tokens[0])?Integer.parseInt(tokens[9].replace("\"","")):0,
                            tokens[10].replace("\"",""),
                            containsOnlyDigits(tokens[0])?Integer.parseInt(tokens[11].replace("\"","")):0,
                            tokens[12].replace("\"",""),
                            tokens[13].replace("\"",""),
                            containsOnlyDigits(tokens[14])?Integer.parseInt(tokens[14].replace("\"","")):0,
                            tokens[15].replace("\"",""),
                            containsOnlyDigits(tokens[16])?Integer.parseInt(tokens[16].replace("\"","")):0,
                            tokens[17].replace("\"",""),
                            containsOnlyDigits(tokens[18])?Integer.parseInt(tokens[18].replace("\"","")):0,
                            tokens[19].replace("\"",""),
                            containsOnlyDigits(tokens[20])?Integer.parseInt(tokens[20].replace("\"","")):0,
                            tokens[21].replace("\"",""),
                            containsOnlyDigits(tokens[22])?Integer.parseInt(tokens[22].replace("\"","")):0,
                            containsOnlyDigits(tokens[23])?Integer.parseInt(tokens[23].replace("\"","")):0
                    );
                    Log.d("LocationFloorIdData", tokens[10] + "");
                    userLocations.add(userLocation);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userLocations;
    }
    public static boolean isAssetsFile(Uri uri, Context context) throws FileNotFoundException {
        boolean isValid = false;
        if (uri!=null) {
            InputStream in = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = null;
            reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)
            );
            String line = "";
            try {
                line = reader.readLine();

                String[] tokens = line.replace("\"","").split(",");
                for (int i = 0; i < tokens.length; i++) {
                    Log.d(TAG, "isAssetsFile: "+i+" \""+tokens[i]+"\"");
                }
                if (
                        tokens[0].equals("\uFEFFAsset ID") &&
                                tokens[1].equals("Barcode") &&
                                tokens[3].equals("Description") &&
                                tokens[4].equals("Room ID") &&
                                tokens[5].equals("Room Name") &&
                                tokens[6].equals("Floor ID") &&
                                tokens[7].equals("Floor Name") &&
                                tokens[8].equals("Building ID") &&
                                tokens[9].equals("Building Name") &&
                                tokens[15].equals("Main Category Name") &&
                                tokens[17].equals("Sub Category1 Name") &&
                                tokens[38].equals("AssetCondition ID") &&
                                tokens[39].equals("AssetCondition Name")


                )
                    isValid = true;
                Log.d(TAG, "isAssetsFileIsValid: "+isValid);
            } catch (IOException e) {
                Log.wtf("Error", "Error in this" + line);
                e.printStackTrace();
            }
        }
        return isValid;
    }
    public static List<Asset> readAssetsFile(Uri uri, Context context) throws FileNotFoundException {

        List<Asset> assets = new ArrayList<>();
        if (uri!=null) {
            InputStream in = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = null;
            reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)
            );
            int row = 0;
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    if (row == 0) {
                        row++;
                        continue;
                    } else
                        row++;
                    String[] tokens = line.replace("\"","").split(",");

                    Log.d("AssetLine", line.replace("\"",""));
                    if (tokens.length == 40) {
                        Asset asset = new Asset();
//                        Asset asset = new Asset(
//                                containsOnlyDigits(tokens[0])?Integer.parseInt(tokens[0].replace("\"","").trim()):0,
//                                tokens[1].replace("\"","").trim(),
//                                tokens[2].replace("\"","").trim(),
//                                containsOnlyDigits(tokens[4])?Integer.parseInt(tokens[4].replace("\"","").trim()):0,
//                                tokens[5].replace("\"","").trim(),
//                                containsOnlyDigits(tokens[6])?Integer.parseInt(tokens[6].replace("\"","").trim()):0,
//                                tokens[7].replace("\"","").trim(),
//                                containsOnlyDigits(tokens[8])?Integer.parseInt(tokens[8].replace("\"","").trim()):0,
//                                tokens[9].replace("\"","").trim(),
//                                tokens[11].replace("\"","").trim(),
//                                tokens[13].replace("\"","").trim(),
//                                containsOnlyDigits(tokens[14])?Integer.parseInt(tokens[14].replace("\"","").trim()):0,
//                                tokens[15].replace("\"","").trim()
//                        );
                        assets.add(asset);
                    } else {
                        Log.d("=====error in line", row + "\n" + line);
                        warningDialog(context,"Some empty data in row "+row);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return assets;
    }
}
