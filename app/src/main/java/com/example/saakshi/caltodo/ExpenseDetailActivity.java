package com.example.saakshi.caltodo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.saakshi.caltodo.R.array.Cats;
import static com.example.saakshi.caltodo.R.array.category;
import static com.example.saakshi.caltodo.R.styleable.CompoundButton;

public class ExpenseDetailActivity extends AppCompatActivity {

    String title;
    EditText titleTextView;
    EditText dateTextView;
    EditText timeTextView;
    EditText descriptionTextView;
    int y;
    int category;
    int id;
    int eid;
    long dates;
    String date;
    String time;
    int hours;
    int minutes;
    Calendar calendar;
    int years;
    int months;
    int days;
    String description;
    Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        LinearLayout l = (LinearLayout) findViewById(R.id.spin);

        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Cats, R.layout.spinner_dropdown_item);
        s = (Spinner) findViewById(R.id.spinner);
        s.setAdapter(spinnerAdapter);


        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                   /* Toast.makeText(ExpenseDetailActivity.this,
                            "you selected: " ,
                            Toast.LENGTH_SHORT).show();*/ {
                    if (position == 1)
                        category = 1;
                    else if (position == 2)
                        category = 2;
                    else if (position == 3)
                        category = 3;
                    else
                        category = 4;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        titleTextView = (EditText) findViewById(R.id.expenseDetailTitleTextView);
        descriptionTextView = (EditText) findViewById(R.id.expenseDetailDescriptionTextView);
        dateTextView = (EditText) findViewById(R.id.expenseDateTextView);
        timeTextView = (EditText) findViewById(R.id.expenseTimeTextView);
        /*Switch ss = (Switch) findViewById(R.id.switch1);
        if (ss != null) {
            ss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AlarmManager am = (AlarmManager) ExpenseDetailActivity.this.getSystemService(Context.ALARM_SERVICE);
                        Intent i = new Intent(ExpenseDetailActivity.this, AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(ExpenseDetailActivity.this, 1, i, 0);

                        // am.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, pendingIntent);
                        am.set(AlarmManager.RTC, dates, pendingIntent);


                    } else {
                        Toast.makeText(ExpenseDetailActivity.this, "iihi", Toast.LENGTH_SHORT);

                    }
                }
            });
        }*/


        Button submitButton = (Button) findViewById(R.id.expenseDetailSubmitButton);
        Intent i = getIntent();
        eid = i.getIntExtra(Intent_Constants.EXPENSE_ID, 0);


        ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(this);
        SQLiteDatabase database = expenseopenhelper.getReadableDatabase();

        Cursor cursor = database.query(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, ExpenseOpenHelper.EXPENSE_ID + " = " + eid, null, null, null, null);

        while (cursor.moveToNext()) {
            date = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DATE));
            category = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CATEGORY));
            time = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TIME));
            title = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TITLE));
            id = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_ID));
            description = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DESCRIPTION));

        }
        dateTextView.setText(date);
        timeTextView.setText(time);

        titleTextView.setText(title);
        descriptionTextView.setText(description);


        if (eid == 0) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newTitle = titleTextView.getText().toString();
                    String newTime = timeTextView.getText().toString();
                    String newDate = dateTextView.getText().toString();
                    String newDescription = descriptionTextView.getText().toString();
                    int newCategory = s.getSelectedItemPosition();

                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String Date2 = df.format(c.getTime());

                    df = new SimpleDateFormat("HH:mm:ss");
                    String Time2 = df.format(c.getTime());
                    if (TextUtils.isEmpty(newTitle)) {
                        titleTextView.setError("Missing Title");
                        return;
                    }

                    if (TextUtils.isEmpty(newTime)) {
                        if (!TextUtils.isEmpty(newDate)) {
                            timeTextView.setError("Missing Time");
                            return;

                        }
                    }


                    ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(ExpenseDetailActivity.this);
                    SQLiteDatabase database = expenseopenhelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(ExpenseOpenHelper.EXPENSE_TITLE, newTitle);
                    cv.put(ExpenseOpenHelper.EXPENSE_DATE, newDate);
                    cv.put(ExpenseOpenHelper.EXPENSE_TIME, newTime);
                    cv.put(ExpenseOpenHelper.EXPENSE_DESCRIPTION, newDescription);
                    cv.put(ExpenseOpenHelper.EXPENSE_CATEGORY, newCategory);

                    cv.put(ExpenseOpenHelper.EXPENSE_CURRDATE, Date2);
                    cv.put(ExpenseOpenHelper.EXPENSE_CURRTIME, Time2);

                    database.insert(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, cv);
                    setResult(RESULT_OK);

                    finish();
                }
            });


        } else {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String newTitle = titleTextView.getText().toString();
                    String newTime = timeTextView.getText().toString();
                    String newDate = dateTextView.getText().toString();
                    int newCategory = s.getSelectedItemPosition();
                    String newDescription = descriptionTextView.getText().toString();

                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String Date2 = df.format(c.getTime());

                    df = new SimpleDateFormat("HH:mm:ss");
                    String Time2 = df.format(c.getTime());

                    if (TextUtils.isEmpty(newTitle)) {
                        titleTextView.setError("Missing Title");
                        return;
                    }

                    if (TextUtils.isEmpty(newTime)) {
                        if (!TextUtils.isEmpty(newDate)) {
                            timeTextView.setError("Missing Time");
                            return;
                        }

                    }

                    if(!TextUtils.isEmpty(newDate))
                    {
                        if(!TextUtils.isEmpty(newTime))
                        {
                            AlarmManager am = (AlarmManager) ExpenseDetailActivity.this.getSystemService(Context.ALARM_SERVICE);
                            Intent i = new Intent(ExpenseDetailActivity.this, AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(ExpenseDetailActivity.this, 1, i, 0);

                            // am.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, pendingIntent);
                            am.set(AlarmManager.RTC, dates, pendingIntent);







                        }

                    }



                    ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(ExpenseDetailActivity.this);
                    SQLiteDatabase database = expenseopenhelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(ExpenseOpenHelper.EXPENSE_TITLE, newTitle);
                    cv.put(ExpenseOpenHelper.EXPENSE_DATE, newDate);
                    cv.put(ExpenseOpenHelper.EXPENSE_TIME, newTime);
                    cv.put(ExpenseOpenHelper.EXPENSE_CURRDATE, Date2);
                    cv.put(ExpenseOpenHelper.EXPENSE_CURRTIME, Time2);
                    cv.put(ExpenseOpenHelper.EXPENSE_CATEGORY, newCategory);

                    cv.put(ExpenseOpenHelper.EXPENSE_DESCRIPTION, newDescription);
                    database.update(ExpenseOpenHelper.EXPENSE_TABLE_NAME, cv, ExpenseOpenHelper.EXPENSE_ID + "=" + eid, null);


                    setResult(RESULT_OK);


                    finish();
                }
            });


        }
        TextView calender = (TextView) findViewById(R.id.calender);
        dateTextView = (EditText) findViewById(R.id.expenseDateTextView);
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                int month = newCalendar.get(Calendar.MONTH);  // Current month
                int year = newCalendar.get(Calendar.YEAR);   // Current year
                showDatePicker(ExpenseDetailActivity.this, year, month, 1);
            }
        });

        TextView clock = (TextView) findViewById(R.id.clock);
        timeTextView = (EditText) findViewById(R.id.expenseTimeTextView);

        clock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ExpenseDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hours = selectedHour;
                        minutes = selectedMinute;
                        timeTextView.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


    }


    public void showDatePicker(Context context, int initialYear, int initialMonth, int initialDay) {

        // Creating datePicker dialog object
        // It requires context and listener that is used when a date is selected by the user.

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    //This method is called when the user has finished selecting a date.
                    // Arguments passed are selected year, month and day
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {

                        // To get epoch, You can store this date(in epoch) in database
                        calendar = Calendar.getInstance();
                        calendar.set(year, month, day, hours, minutes);
                        calendar.set(Calendar.SECOND, 0);

                        dates = calendar.getTime().getTime();
                        if(dates<System.currentTimeMillis()-1)
                        {Toast.makeText(ExpenseDetailActivity.this,"Invalid Date or Time",Toast.LENGTH_SHORT).show();
                            return;

                        }
                        //dates = calendar.getTimeInMillis();
                        // dates = calendar.getTime().getTime();
                        // Setting date selected in the edit text
                        dateTextView.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, initialYear, initialMonth, initialDay);

        //Call show() to simply show the dialog
        datePickerDialog.show();

    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }


}




