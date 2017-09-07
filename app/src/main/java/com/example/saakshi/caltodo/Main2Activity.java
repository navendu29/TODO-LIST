package com.example.saakshi.caltodo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    String title;
    String description;
    TextView titleTextView;
    TextView timeTextView;
    TextView dateTextView;
    int y;
TextView categoryTextView;
    String time;
    String date;
    TextView descriptionTextView;
int category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        titleTextView = (TextView) findViewById(R.id.DetailTitleTextView);
        timeTextView = (TextView) findViewById(R.id.DetailTimeTextView);
        dateTextView = (TextView) findViewById(R.id.DetailDateTextView);
categoryTextView=(TextView)findViewById(R.id.DetailCategoryTextView);
        descriptionTextView = (TextView) findViewById(R.id.DetailDescriptionTextView);
        Intent i = getIntent();

        int eid = i.getIntExtra(Intent_Constants.EXPENSE_ID, 0);


        ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(this);
        SQLiteDatabase database = expenseopenhelper.getReadableDatabase();
        Cursor cursor = database.query(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, ExpenseOpenHelper.EXPENSE_ID + " = " + eid, null, null, null, null);

        while (cursor.moveToNext()) {
            date = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DATE));
category=cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CATEGORY));
            time = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TIME));
            title = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TITLE));
            int id = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_ID));
            description = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DESCRIPTION));


        }
        dateTextView.setText(date);
        timeTextView.setText(time);
        //categoryTextView.setText("Personal");
        titleTextView.setText(title);
        descriptionTextView.setText(description);


    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}












        /*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

*/
/*
        time = i.getStringExtra(Intent_Constants.EXPENSE_TIME);
        date = i.getStringExtra(Intent_Constants.EXPENSE_DATE);
*//*


        time = sdf.parse(i.getStringExtra(Intent_Constants.EXPENSE_TIME),null);
        sdf=new SimpleDateFormat("HH:mm:ss");
        date = sdf.parse(i.getStringExtra(Intent_Constants.EXPENSE_DATE),null);

        title = i.getStringExtra(Intent_Constants.EXPENSE_TITLE);
        description = i.getStringExtra(Intent_Constants.EXPENSE_DESCRIPTION);
        titleTextView.setText(title);
        dateTextView.setText(date+"");
        timeTextView.setText(time+"");
        descriptionTextView.setText(description);

*/

