package com.example.expensetracker;
//add, search, delete queries
//

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String COLUMN_ID = "COLUMN_ID";
    public static final String COLUMN_CATEGORY = "COLUMN_CATEGORY";
    public static final String COLUMN_NOTE = "COLUMN_NOTE";
    public static final String COLUMN_EXPENSE = "COLUMN_EXPENSE";
    public static final String COLUMN_DATE = "COLUMN_DATE";
    public static final String COLUMN_INCOME = "COLUMN_INCOME";
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DatabaseHelper(@Nullable Context context) {
        super(context, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDatabaseStat = "CREATE TABLE " + EXPENSE_TABLE +
                "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, " + COLUMN_NOTE + " TEXT, " + COLUMN_EXPENSE + " REAL, " + COLUMN_INCOME + " REAL)";

        db.execSQL(createDatabaseStat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addExpenseData(ExpenseNIncomeModel expenseNIncomeModel){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String dateStr = dateFormat.format(expenseNIncomeModel.getDate());

        cv.put(COLUMN_DATE,dateStr);
        cv.put(COLUMN_CATEGORY,expenseNIncomeModel.getCategory());
        cv.put(COLUMN_NOTE,expenseNIncomeModel.getNote());
        cv.put(COLUMN_EXPENSE,expenseNIncomeModel.getExpense());

        long insert = db.insert(EXPENSE_TABLE, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteExpenseData(ExpenseNIncomeModel expenseNIncomeModel){

        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + EXPENSE_TABLE + " WHERE " + COLUMN_ID + " = " + expenseNIncomeModel.getId();

        Cursor cursor = db.rawQuery(deleteQuery,null);
        if(cursor.moveToFirst())
            return true;
        else
            return false;
    }

    public List<ExpenseNIncomeModel> getSearchedData(String item){

        //create an empty arrayList
        List<ExpenseNIncomeModel> detailList = new ArrayList<>();
        //search query
        String queryString = "SELECT * FROM " + EXPENSE_TABLE + " WHERE " + COLUMN_CATEGORY + "= " + item;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String dateStr = cursor.getString(1);
                String cat = cursor.getString(2);
                String note = cursor.getString(3);
                double expense = cursor.getDouble(4);
                try {
                    Date date = dateFormat.parse(dateStr);
                    ExpenseNIncomeModel expenseNIncomeModel1 = new ExpenseNIncomeModel(id,date,cat,note,expense);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());

        }else { }

        return detailList;
    }

    public List<ExpenseNIncomeModel> getAllDataList(){
        List<ExpenseNIncomeModel> allDataList = new ArrayList<>();

        String queryString = "SELECT * FROM " + EXPENSE_TABLE;

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String dateStr = cursor.getString(1);
                String cat = cursor.getString(2);
                String note = cursor.getString(3);
                double expense = cursor.getDouble(4);
                try {
                    Date date = dateFormat.parse(dateStr);
                    ExpenseNIncomeModel expenseNIncomeModel1 = new ExpenseNIncomeModel(id,date,cat,note,expense);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while(cursor.moveToNext());
        }else { }

        cursor.close();
        db.close();

        return allDataList;
    }
}
