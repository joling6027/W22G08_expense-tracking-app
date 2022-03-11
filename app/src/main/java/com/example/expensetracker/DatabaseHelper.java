package com.example.expensetracker;

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
    public static final String COLUMN_AMOUNT = "COLUMN_AMOUNT";
    public static final String COLUMN_DATE = "COLUMN_DATE";
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD");

    public DatabaseHelper(@Nullable Context context) {
        super(context, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDatabaseStat = "CREATE TABLE " + EXPENSE_TABLE +
                "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, " + COLUMN_NOTE + " TEXT, " + COLUMN_AMOUNT + " REAL)";

        db.execSQL(createDatabaseStat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addData(ExpenseNIncomeModel expenseNIncomeModel){
        try {
            SQLiteDatabase db =this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_DATE, dateFormat.format(expenseNIncomeModel.getDate()));
            cv.put(COLUMN_CATEGORY,expenseNIncomeModel.getCategory());
            cv.put(COLUMN_AMOUNT,expenseNIncomeModel.getAmount());
            cv.put(COLUMN_NOTE,expenseNIncomeModel.getNote());
            return db.insert(EXPENSE_TABLE, null, cv) > 0;
        } catch (Exception ex) {
            return false;
        }
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
                double amount = cursor.getDouble(4);
                try {
                    Date date = dateFormat.parse(dateStr);
                    ExpenseNIncomeModel expenseNIncomeModel1 = new ExpenseNIncomeModel(id,date,cat,note,amount);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());

        }else { }

        return detailList;
    }
}
