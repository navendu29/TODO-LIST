package com.example.saakshi.caltodo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static com.example.saakshi.caltodo.ExpenseOpenHelper.EXPENSE_TITLE2;


public class MainActivity extends AppCompatActivity implements OnListButtonClickedListener {
    LinearLayout rl;
    SharedPreferences sharedPreferences;
    String name;
    String[] arr = null;
    String[] category = null;
    ListView listView;
    static int pos = 0;
    static String titlee;
    static int idd;
    static String descriptionn;
    static String datee;
    int categoryy;
    static String timee;
    static String currdatee;
    static String currtimee;
    ListView lv;
    LinearLayout rl2;
    ListView lv2;
    View vi;
    FloatingActionButton fab;
    FrameLayout fl;
    ArrayList<Expense> expenseList;
    ExpenseListAdapter expenseListAdapter;
     /*GestureListener gestureListener;
    GestureDetector gestureDetector;ish
    TouchListener onTouchListener;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ToDo");
        /*TextView ttv=(TextView) findViewById(R.id.ttv);
        ttv.setText("//*/
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.menu2);
        category = getResources().getStringArray(R.array.category);
        rl2 = (LinearLayout) findViewById(R.id.rl2);
        Button finish = (Button) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl2.setVisibility(View.GONE);
                for (int i = 0; i < expenseList.size(); i++) {
                    if (expenseList.get(i).isChecked()) {


                        ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(MainActivity.this);
                        SQLiteDatabase database = expenseopenhelper.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put(ExpenseOpenHelper.EXPENSE_TITLE2, expenseList.get(i).title);
                        cv.put(ExpenseOpenHelper.EXPENSE_DATE2, expenseList.get(i).date);
                        cv.put(ExpenseOpenHelper.EXPENSE_TIME2, expenseList.get(i).time);
                        cv.put(ExpenseOpenHelper.EXPENSE_DESCRIPTION2, expenseList.get(i).description);
                        cv.put(ExpenseOpenHelper.EXPENSE_CATEGORY2, expenseList.get(i).category);

                        cv.put(ExpenseOpenHelper.EXPENSE_CURRDATE2, expenseList.get(i).currdate);
                        cv.put(ExpenseOpenHelper.EXPENSE_CURRTIME2, expenseList.get(i).currtime);

                        database.insert(ExpenseOpenHelper.EXPENSE_TABLE_NAME2, null, cv);


                        //    Cursor cursor = database.query(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, ExpenseOpenHelper.EXPENSE_ID+"="+expenseList.get(i).id, null, null, null, null);
                        //                      database.insert(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, cv);
                        database.delete(ExpenseOpenHelper.EXPENSE_TABLE_NAME, ExpenseOpenHelper.EXPENSE_ID + "=" + expenseList.get(i).id, null);


                        expenseList.remove(i);
                        i--;


                        //      expenseListAdapter.notifyDataSetChanged();
                    }
                }
                expenseListAdapter.notifyDataSetChanged();


            }
        });

        Button dell = (Button) findViewById(R.id.dell);
        dell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl2.setVisibility(View.GONE);
                for (int i = 0; i < expenseList.size(); i++) {
                    if (expenseList.get(i).isChecked()) {

                        int x = expenseList.get(i).id;
                        expenseList.remove(i);
                        expenseListAdapter.notifyDataSetChanged();

                        ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(MainActivity.this);
                        SQLiteDatabase database = expenseopenhelper.getWritableDatabase();
//                        Cursor cursor = database.query(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, ExpenseOpenHelper.EXPENSE_ID+"="+x, null, null, null, null);
                        //                      database.insert(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, cv);
                        database.delete(ExpenseOpenHelper.EXPENSE_TABLE_NAME, ExpenseOpenHelper.EXPENSE_ID + "=" + x, null);

                        updateExpenseList();
                        i--;

                    }}
            }
        });

        SearchView searchView = (SearchView) findViewById(R.id.searching);


        //setting listener to read the typed text from searchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                expenseListAdapter.filter(newText);
                return false;
            }
        });

        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        //Spinner in toolbar
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.category, R.layout.spinner_dropdown_item);
        Spinner navigationSpinner = new Spinner(getSupportActionBar().getThemedContext());
        navigationSpinner.setAdapter(spinnerAdapter);
        navigationSpinner.setPadding(1, 1, 1, 1);
        toolbar.addView(navigationSpinner, 3);


        navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)

                    if (position == 1) {
                        ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(MainActivity.this);
                        expenseList.clear();
                        SQLiteDatabase database = expenseopenhelper.getWritableDatabase();
                        Cursor cursor = database.query(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, null, null, null, null, ExpenseOpenHelper.EXPENSE_CURRDATE + " , " + ExpenseOpenHelper.EXPENSE_CURRTIME, null);
                        while (cursor.moveToNext()) {
                            String title = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TITLE));
                            int idd = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_ID));
                            String description = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DESCRIPTION));
                            String time = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TIME));
                            String date = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DATE));
                            int category = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CATEGORY));
                            String Date2 = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CURRDATE));
                            String Time2 = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CURRTIME));

                            Expense e = new Expense(title, idd, description, time, date, Date2, Time2, category);
                            expenseList.add(e);


                        }
                        expenseListAdapter.notifyDataSetChanged();


                    }
                if (position == 2) {
                    ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(MainActivity.this);
                    expenseList.clear();
                    SQLiteDatabase database = expenseopenhelper.getWritableDatabase();
                    Cursor cursor = database.query(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, null, null, null, null, ExpenseOpenHelper.EXPENSE_CATEGORY, null);
                    while (cursor.moveToNext()) {
                        String title = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TITLE));
                        int idd = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_ID));
                        String description = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DESCRIPTION));
                        String time = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TIME));
                        String date = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DATE));
                        int category = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CATEGORY));
                        String Date2 = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CURRDATE));
                        String Time2 = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CURRTIME));

                        Expense e = new Expense(title, idd, description, time, date, Date2, Time2, category);
                        expenseList.add(e);


                    }
                    expenseListAdapter.notifyDataSetChanged();
                }
                if (position == 3) {

//Toast.makeText(MainActivity.this,"hii",Toast.LENGTH_SHORT).show();
                    ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(MainActivity.this);
                    expenseList.clear();
                    SQLiteDatabase database = expenseopenhelper.getReadableDatabase();
                    Cursor cursor = database.query(ExpenseOpenHelper.EXPENSE_TABLE_NAME2, null, null, null, null, null, ExpenseOpenHelper.EXPENSE_CURRDATE2 + " , " + ExpenseOpenHelper.EXPENSE_CURRTIME2, null);
                    while (cursor.moveToNext()) {
                        String title = cursor.getString(cursor.getColumnIndex(EXPENSE_TITLE2));
                        int idd = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_ID2));
                        String description = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DESCRIPTION2));
                        String time = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TIME2));
                        String date = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DATE2));
                        int category = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CATEGORY2));
                        String Date2 = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CURRDATE2));
                        String Time2 = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CURRTIME2));

                        Expense e = new Expense(title, idd, description, time, date, Date2, Time2, category);
                        expenseList.add(e);


                    }
                    expenseListAdapter.notifyDataSetChanged();


                }

                   /* Toast.makeText(MainActivity.this,
                            "you selected: " + category[position],
                            Toast.LENGTH_SHORT).show();
           */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fl = (FrameLayout) findViewById(R.id.fl);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent i = new Intent(MainActivity.this, ExpenseDetailActivity.class);
                i.putExtra(Intent_Constants.EXPENSE_TITLE, " ");
                i.putExtra(Intent_Constants.EXPENSE_DESCRIPTION," ");
                i.putExtra(Intent_Constants.EXPENSE_DATE, " ");
                i.putExtra(Intent_Constants.EXPENSE_TIME, " ");
               // i.putExtra("pos", position);

                startActivityForResult(i, 2);*/
                Intent i = new Intent(MainActivity.this, ExpenseDetailActivity.class);
                startActivityForResult(i, 1);

            }
        });

        listView = (ListView) findViewById(R.id.expenseListView);
        expenseList = new ArrayList<>();


        expenseListAdapter = new ExpenseListAdapter(this, expenseList);
        listView.setAdapter(expenseListAdapter);
        expenseListAdapter.setOnListButtonClickedListener(MainActivity.this);

        updateExpenseList();
/*

        gestureDetector = new GestureDetector(this, new GestureListener());
        onTouchListener = new TouchListener();
        listView.setOnTouchListener(onTouchListener);


*/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                lv = (ListView) findViewById(R.id.expenseListView);
                lv.setEnabled(false);
                vi = view;
                vi.setBackgroundResource(R.color.grey);
                fl.setVisibility(View.GONE);
                rl = (LinearLayout) findViewById(R.id.rl);
                rl.setVisibility(View.VISIBLE);


                Button b1 = (Button) rl.findViewById(R.id.del);
                Button b2 = (Button) findViewById(R.id.edit);
                Button b3 = (Button) findViewById(R.id.vie);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rl.setVisibility(View.GONE);
                        lv.setEnabled(true);
                        vi.setBackgroundResource(R.color.white);

                        fl.setVisibility(View.VISIBLE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                        builder.setTitle("Delete");
                        builder.setCancelable(false);

                        View ve = getLayoutInflater().inflate(R.layout.dialog_view, null);

                        final TextView tv = (TextView) ve.findViewById(R.id.dialogTextView);
                        tv.setText("Are you sure you want to delete ??");
                        builder.setView(ve);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pos = position;
                                titlee = expenseList.get(position).title;

                                idd = expenseList.get(position).id;

                                descriptionn = expenseList.get(position).description;
                                datee = expenseList.get(position).date;
                                timee = expenseList.get(position).time;
                                currdatee = expenseList.get(position).currdate;
                                currtimee = expenseList.get(position).currtime;

                                categoryy = expenseList.get(position).category;
                                int x = expenseList.get(position).id;
                                expenseList.remove(position);
                                expenseListAdapter.notifyDataSetChanged();

                                ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(MainActivity.this);
                                SQLiteDatabase database = expenseopenhelper.getWritableDatabase();
//                        Cursor cursor = database.query(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, ExpenseOpenHelper.EXPENSE_ID+"="+x, null, null, null, null);
                                //                      database.insert(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, cv);
                                database.delete(ExpenseOpenHelper.EXPENSE_TABLE_NAME, ExpenseOpenHelper.EXPENSE_ID + "=" + x, null);
//                                database.delete(ExpenseOpenHelper.EXPENSE_TABLE_NAME2, ExpenseOpenHelper.EXPENSE_ID + "=" + x, null);

                                updateExpenseList();
                                CoordinatorLayout rl = (CoordinatorLayout) findViewById(R.id.cl);

                                Snackbar.make(rl,
                                        "Deleted something accidently?", Snackbar.LENGTH_LONG)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Expense e = new Expense(titlee, idd, descriptionn, timee, datee, currdatee, currtimee, categoryy);

                                                expenseList.add(pos, e);
                                                expenseListAdapter.notifyDataSetChanged();

                                                ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(MainActivity.this);
                                                SQLiteDatabase database = expenseopenhelper.getWritableDatabase();
                                                ContentValues cv = new ContentValues();
                                                cv.put(ExpenseOpenHelper.EXPENSE_TITLE, titlee);
                                                cv.put(ExpenseOpenHelper.EXPENSE_DATE, datee);
                                                cv.put(ExpenseOpenHelper.EXPENSE_TIME, timee);
                                                cv.put(ExpenseOpenHelper.EXPENSE_DESCRIPTION, descriptionn);
                                                cv.put(ExpenseOpenHelper.EXPENSE_CATEGORY, categoryy);

                                                cv.put(ExpenseOpenHelper.EXPENSE_CURRDATE, currdatee);
                                                cv.put(ExpenseOpenHelper.EXPENSE_CURRTIME, currtimee);

                                                database.insert(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, cv);
                                                updateExpenseList();

                                            }
                                        }).show();


                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();


                    }


                });
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rl = (LinearLayout) findViewById(R.id.rl);
                        rl.setVisibility(View.GONE);
                        vi.setBackgroundResource(R.color.white);

                        fl.setVisibility(View.VISIBLE);
                        lv.setEnabled(true);
                        Intent i = new Intent(MainActivity.this, ExpenseDetailActivity.class);
                        i.putExtra(Intent_Constants.EXPENSE_ID, expenseList.get(position).id);

                        i.putExtra(Intent_Constants.EXPENSE_TITLE, expenseList.get(position).title);
                        i.putExtra(Intent_Constants.EXPENSE_DESCRIPTION, expenseList.get(position).description);


                        i.putExtra("pos", position);
                        startActivityForResult(i, 1);
                    }
                });

                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rl = (LinearLayout) findViewById(R.id.rl);
                        rl.setVisibility(View.GONE);
                        vi.setBackgroundResource(R.color.white);

                        fl.setVisibility(View.VISIBLE);
                        lv.setEnabled(true);
                        Intent i = new Intent(MainActivity.this, ScrollingActivity.class);

                        i.putExtra(Intent_Constants.EXPENSE_TITLE, expenseList.get(position).title);
                        i.putExtra(Intent_Constants.EXPENSE_ID, expenseList.get(position).id);
                        i.putExtra(Intent_Constants.EXPENSE_DESCRIPTION, expenseList.get(position).description);
                        i.putExtra(Intent_Constants.EXPENSE_DATE, expenseList.get(position).date);
                        i.putExtra(Intent_Constants.EXPENSE_TIME, expenseList.get(position).time);

                        i.putExtra("pos", position);
                        startActivity(i);

                    }
                });
                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this, ExpenseDetailActivity.class);
                i.putExtra(Intent_Constants.EXPENSE_ID, expenseList.get(position).id);

                i.putExtra(Intent_Constants.EXPENSE_TITLE, expenseList.get(position).title);
                i.putExtra(Intent_Constants.EXPENSE_DESCRIPTION, expenseList.get(position).description);


                i.putExtra("pos", position);
                startActivityForResult(i, 1);


            }
        });

    }

    private void updateExpenseList() {

        ExpenseOpenHelper expenseopenhelper = new ExpenseOpenHelper(this);
        expenseList.clear();
        SQLiteDatabase database = expenseopenhelper.getWritableDatabase();
        Cursor cursor = database.query(ExpenseOpenHelper.EXPENSE_TABLE_NAME, null, null, null, null, null, ExpenseOpenHelper.EXPENSE_CURRDATE + " , " + ExpenseOpenHelper.EXPENSE_CURRTIME, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TITLE));
            int id = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_ID));
            String description = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DESCRIPTION));
            String time = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_TIME));
            String date = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_DATE));
            int category = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CATEGORY));
            String Date2 = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CURRDATE));
            String Time2 = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.EXPENSE_CURRTIME));

            Expense e = new Expense(title, id, description, time, date, Date2, Time2, category);
            expenseList.add(e);


        }
        expenseListAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {


                updateExpenseList();


            } else {

            }
        }

    }


/*
    public boolean onCreateOptionsMenu(Menu menu) {
        //   getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        MenuItem items = menu.findItem(R.id.search);
        android.support.v7.widget.SearchView searchview = (android.support.v7.widget.SearchView) items.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //  expenseListAdapter.getFilter.filter(newText);
                expenseListAdapter.filter(newText);
               // listView.onRemoteAdapterDisconnected();
                return false;
            }
        });


        return true;
    }
*/

    @Override
    // public boolean onOptionsItemSelected(MenuItem item) {
/*
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.star) {
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(i);
        }

        if (id == R.id.AboutUs) {

            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("http://www.codingninjas.in");
            i.setData(uri);
            startActivity(i);

        }
*///      return true;


//    }


    public void onBackPressed() {

        LinearLayout rl = (LinearLayout) findViewById(R.id.rl);
        if (rl.getVisibility() == View.VISIBLE) {
            rl.setVisibility(View.GONE);
            lv.setEnabled(true);

            vi.setBackgroundResource(R.color.white);
            fl.setVisibility(View.VISIBLE);
        } else if (rl2.getVisibility() == View.VISIBLE) {
            rl2.setVisibility(View.INVISIBLE);
    /*       for(int i=0;i<expenseList.size();i++)
               expenseList.get(i).checkbox.setChecked(false);
*/

            fl.setVisibility(View.VISIBLE);
        } else
            super.onBackPressed();

    }


    @Override
    public void listButtonClicked(View v, int pos) {

        fl.setVisibility(View.GONE);
        rl2.setVisibility(View.VISIBLE);
       /* for (int i = 0; i < expenseList.size(); i++) {

            if (expenseList.get(i).checkbox.isChecked()) {
                expenseList.remove(i);
                expenseListAdapter.notifyDataSetChanged();
            }
        }*/
    }
}
/*



                String newTitle = data.getStringExtra(Intent_Constants.EXPENSE_TITLE);
                String newDescription = data.getStringExtra(Intent_Constants.EXPENSE_DESCRIPTION);
//                String newDate = data.getStringExtra(Intent_Constants.EXPENSE_DATE);
  //              String newTime = data.getStringExtra(Intent_Constants.EXPENSE_TIME);
                //Date newDate = data.getStringExtra(Intent_Constants.EXPENSE_DATE);
                //Date newTime = data.getStringExtra(Intent_Constants.EXPENSE_TIME);

                int z = data.getIntExtra("pos", 0);
                expenseList.get(z).title = newTitle;
                expenseList.get(z).description = newDescription;
                // expenseList.get(z).date=newDate;


                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
                String formattedDate = df.format(c.getTime());
               // Date date = df.parse(formattedDate, null);
                expenseList.get(z).date = df.parse(formattedDate,null);
//                expenseList.get(z).date = formattedDate;

//                dateTextView.setText(formattedDate);
                df = new SimpleDateFormat("HH:mm:ss");
                formattedDate = df.format(c.getTime());
               // expenseList.get(z).time = formattedDate;
                expenseList.get(z).time = df.parse(formattedDate,null);


                // expenseList.get(z).time=newTime;
                expenseListAdapter.notifyDataSetChanged();

*/

//}
/*
                name = null;
                for (int i = 0; i < expenseList.size(); i++) {

                    if (name != null)
                        name = name + (expenseList.get(i).title + ";" + expenseList.get(i).description + ";");
                    else
                        name = (expenseList.get(i).title + ";" + expenseList.get(i).description + ";");

                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("str", name);
                editor.commit();
*//*


            } else if (resultCode == RESULT_CANCELED) {

            }
        } else {
            if (resultCode == RESULT_OK) {
                String newTitle = data.getStringExtra(Intent_Constants.EXPENSE_TITLE);
                String newDescription = data.getStringExtra(Intent_Constants.EXPENSE_DESCRIPTION);
                //String newDate = data.getStringExtra(Intent_Constants.EXPENSE_DATE);
                //String newTime = data.getStringExtra(Intent_Constants.EXPENSE_TIME);
                int col = data.getIntExtra("col", 0);
                Expense e = new Expense();
                e.title = newTitle;
                e.description = newDescription;
                //e.time=newTime;
                //    e.date=newDate;

                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
                String formattedDate = df.format(c.getTime());
//                Date date = df.parse(formattedDate, null);

                ParsePosition pp=new ParsePosition(0);

                e.date =df.parse (formattedDate,pp);
//                e.date = formattedDate;

//                dateTextView.setText(formattedDate);
                df = new SimpleDateFormat("HH:mm:ss");
                formattedDate = df.format(c.getTime());
               // e.time = formattedDate;
                e.time = df.parse(formattedDate,pp);
//                timeTextView.setText(formattedDate);

                expenseList.add(e);
                expenseListAdapter.notifyDataSetChanged();




               *//*
                name = null;
                for (int i = 0; i < expenseList.size(); i++) {

                    if (name != null)
                        name = name + (expenseList.get(i).title + ";" + expenseList.get(i).description + ";");
                    else
                        name = (expenseList.get(i).title + ";" + expenseList.get(i).description + ";");

                }
                Log.i("MainActivityTag", name);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("str", name);
                editor.commit();

*//*

            } else if (resultCode == RESULT_CANCELED) {

            }

*/
// }



/*
    protected class GestureListener extends GestureDetector.SimpleOnGestureListener
    {
        private static final int SWIPE_MIN_DISTANCE = 150;
        private static final int SWIPE_MAX_OFF_PATH = 100;
        private static final int SWIPE_THRESHOLD_VELOCITY = 100;

        private MotionEvent mLastOnDownEvent = null;

        @Override
        public boolean onDown(MotionEvent e)
        {
            mLastOnDownEvent = e;

            return super.onDown(e);
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if(e1 == null){
                e1 = mLastOnDownEvent;
            }
            if(e1==null || e2==null){
                return false;
            }

            float dX = e2.getX() - e1.getX();
            float dY = e1.getY() - e2.getY();

            if (Math.abs(dY) < SWIPE_MAX_OFF_PATH && Math.abs(velocityX) >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dX) >= SWIPE_MIN_DISTANCE ) {
                if (dX > 0) {
                    Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            return false;
        }
    }
    protected class TouchListener implements View.OnTouchListener
    {
        @Override
        public boolean onTouch(View v, MotionEvent e)
        {
            if (gestureDetector.onTouchEvent(e)){

                return true;
            }else{
                return false;
            }
        }
    }
*/

