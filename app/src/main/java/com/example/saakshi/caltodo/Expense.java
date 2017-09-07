package com.example.saakshi.caltodo;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by saakshi on 26/6/17.
 */

public class Expense implements Serializable {
    String title;
    int id;
    String date;
    String time;
    boolean isChecked;

int category;
    String currdate;
    String currtime;
    Button button;
    CheckBox checkbox;

    String description;


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Expense(String title, int id, String description, String time, String date, String currdate, String currtime, int category) {
        this.title = title;
        this.id = id;
        this.date = date;
        this.time = time;
        this.currdate = currdate;
        this.currtime = currtime;
        this.category=category;
        this.description = description;
    }


}
