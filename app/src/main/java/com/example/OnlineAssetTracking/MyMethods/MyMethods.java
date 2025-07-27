package com.example.OnlineAssetTracking.MyMethods;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.OnlineAssetTracking.Model.Building;
import com.example.OnlineAssetTracking.Model.CentralDepartment;
import com.example.OnlineAssetTracking.Model.Department;
import com.example.OnlineAssetTracking.Model.Floor;
import com.example.OnlineAssetTracking.Model.GeneralDepartment;
import com.example.OnlineAssetTracking.Model.Sector;
import com.example.OnlineAssetTracking.Ui.MainActivity;
import com.example.OnlineAssetTracking.R;
import com.google.android.material.textfield.TextInputLayout;
import com.tapadoo.alerter.Alerter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MyMethods {
    public static boolean containsOnlyDigits(String s) {
        return s.matches("\\d+");
    }
    public static ProgressDialog loadingProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        return progressDialog;
    }
    public static void hideToolBar(MainActivity mainActivity) {
        mainActivity.getSupportActionBar().hide();
    }
    public static void showToolBar(MainActivity mainActivity) {
        mainActivity.getSupportActionBar().show();
    }
    public static void changeTitle(String mainTitle, MainActivity mainActivity) {
        final ActionBar abar = mainActivity.getSupportActionBar();
//        abar.setBackgroundDrawable(mainActivity.getResources().getDrawable(R.drawable.actionbar_background));//line under the action bar
        View viewActionBar = mainActivity.getLayoutInflater().inflate(R.layout.tool_bar_title, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.tvTitle);
        textviewTitle.setText(mainTitle);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(android.R.color.transparent);
        abar.setHomeButtonEnabled(true);
//        mainActivity.getSupportActionBar().setTitle(mainTitle);
    }

    public static void warningDialog(Context context,String message){
        new CustomDialog(context,message, R.raw.warning_anim).show();
    }
    public static void successDialog(Context context,String message){
        new CustomDialog(context,message, R.raw.done_anim).show();
    }
    public static void  back(Fragment fragment){
        NavController navController = NavHostFragment.findNavController(fragment);
        navController.popBackStack();
    }
    public static void  clearInputLayoutError(TextInputLayout... inputLayouts){
        for (TextInputLayout inputLayout:inputLayouts) {
            inputLayout.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    inputLayout.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    inputLayout.setError(null);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    inputLayout.setError(null);
                }
            });
        }
    }
    public static void hideKeyboard(Activity activity) {
        if (activity!=null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager)   activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    public static void activateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f,1.0f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }

    public static void deactivateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f,0.4f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }

    public static long getRemainingTime(String expectedSignOut) {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(expectedSignOut);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d.getTime() - currentDate.getTime();
    }

    public static void startRemainingTimeTimer(long remainingTime, TextView remainingTimeTv){
        if (remainingTime>0) {
            new CountDownTimer(remainingTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTimeTv.setText(convertToTimeFormat(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    remainingTimeTv.setText("Operation Finished");
                }
            }.start();
        } else
            remainingTimeTv.setText("Operation Finished");
    }

    public static String convertToTimeFormat(long millisUntilFinished) {
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisUntilFinished);
        return formatter.format(millisUntilFinished);
    }
    public static String getEditTextText(TextInputLayout editText){
        return editText.getEditText().getText().toString().trim();
    }

    public static void showSuccessAlerter(String message,Activity activity){
       Alerter.create(activity).setText(message)
                .setIcon(R.drawable.ic_done)
                .setBackgroundColorInt(activity.getResources().getColor(R.color.done))
                .setDuration(1000)
                .setTextAppearance(R.style.alerter_text_appearance)
                .setEnterAnimation(R.anim.push_down_in)
                .setExitAnimation(R.anim.push_up_out)
                .show();
    }

    public static void showErrorAlerter(String message,Activity activity){
        Alerter.create(activity).setText(message)
                .setIcon(R.drawable.ic_warning_alert)
                .setBackgroundColorInt(activity.getResources().getColor(com.tapadoo.alerter.R.color.alert_default_error_background))
                .setDuration(1000)
                .setTextAppearance(R.style.alerter_text_appearance)
                .setEnterAnimation(R.anim.push_down_in)
                .setExitAnimation(R.anim.push_up_out)
                .show();
    }

    public static LoadingDialog showLoadingDialog(Context context){
        return new LoadingDialog(context);
    }

    public static void saveFile(String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "جرد الأصول");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileOnInternalStorage(String sFileName, String sBody,Activity activity){
        File dir = new File("/sdcard/جرد الأصول");
        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
            File gpxfile = new File(dir, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            showSuccessAlerter(activity.getString(R.string.saved_successfully),activity);
            Log.d(TAG, "writeFileOnInternalStorage: done writing");
        } catch (Exception e){
            e.printStackTrace();
            showErrorAlerter(activity.getString(R.string.error_while_saving_file),activity);
            Log.d(TAG, "writeFileOnInternalStorage: error "+e.getMessage());
//            showErrorAlerter(e.getMessage(),activity);
        }
    }

    public static void writeToFile(String data,Context context) {
        FileOutputStream fOut = null;
        File directory = new File(Environment.getExternalStorageDirectory(), "AutoWriter");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            //Create the stream pointing at the file location
            fOut = new FileOutputStream(new File(directory, "samplefile.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap convertBase64toBitmap(String encodedImage){
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static String todayDate (){
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        Date date = calendar.getTime();
        return arabicToDecimal(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date));
    }

    public static String convertToEnglishDigits(String value)
    {

        return value.replace("١", "1")
                .replace("٢", "2")
                .replace("٣", "3")
                .replace("٤", "4")
                .replace("٥", "5")
                .replace("٦", "6")
                .replace("٨", "8")
                .replace("٩", "9")
                .replace("٠", "0")
                .replace("٧", "7");
    }
    public static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for(int i=0;i<number.length();i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean containsSector(final List<Sector> list, final String name){
        return list.stream().anyMatch(o -> Objects.equals(o.getSectorName(), name));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean containsCentralDepartments(final List<CentralDepartment> list, final String name){
        return list.stream().anyMatch(o -> o.getCentralDepartmentName().equals(name));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean containsBuilding(final List<Building> list, final String name){
        return list.stream().anyMatch(o -> o.getBuildingName().equals(name));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean containsFloor(final List<Floor> list, final String name){
        return list.stream().anyMatch(o -> o.getFloorName().equals(name));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean containsGeneralDepartment(final List<GeneralDepartment> list, final String name){
        return list.stream().anyMatch(o -> o.getGeneralDepartmentName().equals(name));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean containsDepartment(final List<Department> list, final String name){
        return list.stream().anyMatch(o -> o.getDepartmentName().equals(name));
    }
}

