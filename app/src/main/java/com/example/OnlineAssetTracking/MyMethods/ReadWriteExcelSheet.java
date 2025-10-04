package com.example.OnlineAssetTracking.MyMethods;

import static com.example.OnlineAssetTracking.MyMethods.EncryptionManager.TAG;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.showErrorAlerter;
import static com.example.OnlineAssetTracking.MyMethods.MyMethods.showSuccessAlerter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.OnlineAssetTracking.DataBase.Status;
import com.example.OnlineAssetTracking.Model.StatusWithMessage;
import com.example.OnlineAssetTracking.R;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteExcelSheet {
    public static List<String> getExcelSheetHeader(Uri fileUri, Context context){
        List<String> headerContent = new ArrayList<>();
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
            if (inputStream == null)
                return headerContent;

            // افتح الملف كـ Workbook
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // أول شيت

                for (Cell cell : sheet.getRow(0)) {
                    if (cell.getCellType() == CellType.STRING) {
                        Log.d("ExcelData", "String: " + cell.getStringCellValue());
                        headerContent.add(cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        Log.d("ExcelData", "Number: " + cell.getNumericCellValue());
                        headerContent.add(String.valueOf(cell.getNumericCellValue()));
                    } else {
                        headerContent.add(cell.toString());
                        Log.d("ExcelData", "Other: " + cell.toString());
                    }
                }


            workbook.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ExcelError", "Error reading Excel file: " + e.getMessage());
        }
        return headerContent;
    }

    public static String[][] getExcelSheetContent(Uri fileUri, Context context){
        String[][] sheetContent = new String[0][];
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
            if (inputStream == null)
                return null;

            // افتح الملف كـ Workbook
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);// أول شيت
            sheetContent = new String[sheet.getLastRowNum()-1][sheet.getRow(0).getLastCellNum()];
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                for (int j = 0; j <sheet.getRow(i).getLastCellNum() ; j++) {
                    Cell cell = sheet.getRow(i).getCell(j);
                    if (cell.getCellType() == CellType.STRING) {
                        Log.d("ExcelData", "String: " + cell.getStringCellValue());
                        sheetContent[i][j] = cell.getStringCellValue();
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        Log.d("ExcelData", "Number: " + cell.getNumericCellValue());
                        sheetContent[i][j] = String.valueOf(cell.getNumericCellValue());
                    } else {
                        sheetContent[i][j]= cell.toString();
                        Log.d("ExcelData", "Other: " + cell.toString());
                    }
                }
            }
            workbook.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ExcelError", "Error reading Excel file: " + e.getMessage());
        }
        return sheetContent;
    }

    public static List<String> getExcelSheetRowContent(Uri fileUri,int index, Context context){
        List<String> rowContent = new ArrayList<>();
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
            if (inputStream == null)
                return null;
            Log.d(TAG, "=======getExcelSheetRowContent: fileOpened");
            // افتح الملف كـ Workbook
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);// أول شيت
//            sheetContent = new String[sheet.getLastRowNum()-1][sheet.getRow(0).getLastCellNum()];
            Row row = sheet.getRow(index);
            Log.d(TAG, "=======getExcelSheetRowContent: fileOpened"+sheet.getLastRowNum());
//            for (int i = 1; i < sheet.getLastRowNum(); i++) {
            if (sheet.getLastRowNum()>index) {
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell.getCellType() == CellType.STRING) {
                        Log.d("ExcelData", "String: " + cell.getStringCellValue());
                        rowContent.add(cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        Log.d("ExcelData", "Number: " + cell.getNumericCellValue());
                        rowContent.add(String.valueOf(cell.getNumericCellValue()));
                    } else {
                        rowContent.add(cell.toString());
                        Log.d("ExcelData", "Other: " + cell.toString());
                    }
                }
            } else {
                rowContent = new ArrayList<>();
            }
//            }
            workbook.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ExcelError", "Error reading Excel file: " + e.getMessage());
        }
        return rowContent;
    }
    public static Status createEncryptedExcel(Context context, String fileName, String[][] data, String password, String date) {
        Status status = null;
        try {
            // 1. إنشاء Workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(date);

            // إضافة بيانات
            int rowNum = 0;
            for (String[] rowData : data) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (String field : rowData) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(field);
                }
            }

            // 2. حفظ مؤقت
            File tempFile = File.createTempFile("temp_excel", ".xlsx", context.getCacheDir());
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                workbook.write(fos);
            }
            workbook.close();

            // 3. فتح الـ OPCPackage للتشفير
            OPCPackage opc = OPCPackage.open(tempFile);
            POIFSFileSystem fs = new POIFSFileSystem();
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
            Encryptor enc = info.getEncryptor();
            enc.confirmPassword(password);

            try (OutputStream os = enc.getDataStream(fs)) {
                opc.save(os);
            }

            // 4. تحديد مكان التخزين المناسب
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10+ → استخدم MediaStore
                ContentValues values = new ContentValues();
                values.put(MediaStore.Downloads.DISPLAY_NAME, fileName + ".xlsx");
                values.put(MediaStore.Downloads.MIME_TYPE,
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                values.put(MediaStore.Downloads.RELATIVE_PATH, "Download/Asset tracking");

                ContentResolver resolver = context.getContentResolver();
                Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

                if (uri != null) {
                    try (OutputStream fosEnc = resolver.openOutputStream(uri)) {
                        fs.writeFilesystem(fosEnc);
                    }
                } else {
                    throw new Exception("فشل إنشاء URI من MediaStore");
                }

            } else {
                // Android 9 وأقل → كتابة مباشرة
                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File dir = new File(downloadsDir, "Asset tracking");
                if (!dir.exists()) dir.mkdirs();
                File outFile = new File(dir, fileName + ".xlsx");

                try (FileOutputStream fosEnc = new FileOutputStream(outFile)) {
                    fs.writeFilesystem(fosEnc);
                }
                MediaScannerConnection.scanFile(
                        context,
                        new String[]{outFile.getAbsolutePath()},
                        new String[]{"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
                        (path, uri2) -> Log.d("Excel", "createEncryptedExcel: تم إنشاء الملف: " + path)
                );

            }

            // 5. حذف المؤقت
            tempFile.delete();
            status = Status.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            status = Status.ERROR;
        }
        return status;
    }


}
