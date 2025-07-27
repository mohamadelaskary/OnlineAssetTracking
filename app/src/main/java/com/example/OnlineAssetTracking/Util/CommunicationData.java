package com.example.OnlineAssetTracking.Util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class CommunicationData {

    private static final String PROTOCOL_TYPE_NAME = "protocol";
    private static final String IP_ADDRESS_NAME = "Ip_address";
    private static final String PORT_NUM_NAME = "port_num";

    public static String getProtocol(Application application){
        SharedPreferences sharedPreferences = application.getApplicationContext().getSharedPreferences(
                application.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(PROTOCOL_TYPE_NAME, null)!=null?sharedPreferences.getString(PROTOCOL_TYPE_NAME, null):"http";
    }
    public static String getIpAddress(Application application){
        SharedPreferences sharedPreferences = application.getApplicationContext().getSharedPreferences(
                application.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(IP_ADDRESS_NAME, null)!=null?sharedPreferences.getString(IP_ADDRESS_NAME, null):"45.241.58.79";
    }
    public static String getPortNumber(Application application){
        SharedPreferences sharedPreferences = application.getApplicationContext().getSharedPreferences(
                application.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(PORT_NUM_NAME, null)!=null?sharedPreferences.getString(PORT_NUM_NAME, null):"7747";
    }

    public static void saveProtocol(Application application, String protocol){
        SharedPreferences sharedPreferences = application.getApplicationContext().getSharedPreferences(
                application.getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(PROTOCOL_TYPE_NAME,protocol).apply();
    }
    public static void saveIPAddress(Application application, String ipAddress){
        SharedPreferences sharedPreferences = application.getApplicationContext().getSharedPreferences(
                application.getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(IP_ADDRESS_NAME,ipAddress).apply();
    }
    public static void savePortNum(Application application, String portNum){
        SharedPreferences sharedPreferences = application.getApplicationContext().getSharedPreferences(
                application.getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(PORT_NUM_NAME,portNum).apply();
    }
}
