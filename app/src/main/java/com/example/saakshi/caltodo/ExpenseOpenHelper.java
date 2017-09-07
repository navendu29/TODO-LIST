package com.example.saakshi.caltodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by saakshi on 28/6/17.
 */

public class ExpenseOpenHelper extends SQLiteOpenHelper {

    public final static String EXPENSE_TABLE_NAME = "Expense";

    public final static String EXPENSE_TITLE = "title";
    public final static String EXPENSE_CATEGORY = "category";

    public final static String EXPENSE_DATE = "date";
    public final static String EXPENSE_CURRDATE = "currdate";

    public final static String EXPENSE_CURRTIME = "currtime";
    public final static String EXPENSE_TIME = "time";
    public final static String EXPENSE_DESCRIPTION = "description";

    public final static String EXPENSE_ID = "_id";




    public final static String EXPENSE_TABLE_NAME2 = "Expense2";

    public final static String EXPENSE_TITLE2 = "title2";
    public final static String EXPENSE_CATEGORY2 = "category2";

    public final static String EXPENSE_DATE2= "date2";
    public final static String EXPENSE_CURRDATE2 = "currdate2";

    public final static String EXPENSE_CURRTIME2 = "currtime2";
    public final static String EXPENSE_TIME2 = "time2";
    public final static String EXPENSE_DESCRIPTION2 = "description2";

    public final static String EXPENSE_ID2 = "_id2";














    public ExpenseOpenHelper(Context context) {

        super(context, "ToDo.db",null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + EXPENSE_TABLE_NAME + " ( " + EXPENSE_ID + " integer primary key autoincrement, " + EXPENSE_TITLE + " text, "
                + EXPENSE_DESCRIPTION + " text, " + EXPENSE_TIME + " text , " + EXPENSE_DATE + " text ," + EXPENSE_CURRDATE + " text ," + EXPENSE_CURRTIME + " text ," + EXPENSE_CATEGORY + " integer );";

        db.execSQL(query);

        String query2 = "create table " + EXPENSE_TABLE_NAME2 + " ( " + EXPENSE_ID2 + " integer , " + EXPENSE_TITLE2 + " text, "
                + EXPENSE_DESCRIPTION2 + " text, " + EXPENSE_TIME2 + " text , " + EXPENSE_DATE2 + " text ," + EXPENSE_CURRDATE2 + " text ," + EXPENSE_CURRTIME2 + " text ," + EXPENSE_CATEGORY2 + " integer );";

        db.execSQL(query2);








    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {






    }

}
