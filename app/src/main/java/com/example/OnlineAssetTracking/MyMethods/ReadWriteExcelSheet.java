package com.example.OnlineAssetTracking.MyMethods;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
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
}
