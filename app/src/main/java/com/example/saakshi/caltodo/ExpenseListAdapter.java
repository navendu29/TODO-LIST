package com.example.saakshi.caltodo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by saakshi on 26/6/17.
 */

public class ExpenseListAdapter extends ArrayAdapter<Expense> {
    ArrayList<Expense> expenseArrayList;
    Context context;
    ArrayList<Expense> objects;

    Boolean x = true;
    OnListButtonClickedListener listener;


    void setOnListButtonClickedListener(OnListButtonClickedListener listener) {
        this.listener = listener;
    }

    public ExpenseListAdapter(Context context, ArrayList<Expense> expenseArrayList) {
        super(context, 0);
        this.context = context;
        objects = new ArrayList<>();
        objects.addAll(expenseArrayList);
        this.expenseArrayList = expenseArrayList;
    }

    @Override
    public int getCount() {
        return expenseArrayList.size();
    }

    static class ExpenseViewHolder {

        TextView nameTextView;
        TextView dateTextView;
        TextView timeTextView;
        Button b;
        CheckBox checkbox;

        ExpenseViewHolder(TextView nameTextView, CheckBox checkbox, TextView dateTextView, TextView timeTextView, Button b) {

            this.nameTextView = nameTextView;
            this.dateTextView = dateTextView;
            this.timeTextView = timeTextView;
            this.checkbox = checkbox;
            this.b = b;

        }

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.expenseNameTextView);

            TextView dateTextView = (TextView) convertView.findViewById(R.id.expenseDateTextView);

            TextView timeTextView = (TextView) convertView.findViewById(R.id.expenseTimeTextView);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.button);
            Button b = (Button) convertView.findViewById(R.id.buttons);
            //  button.setBackgroundResource(R.mipmap.smallb);

            ExpenseViewHolder expenseViewHolder = new ExpenseViewHolder(nameTextView, checkBox, dateTextView, timeTextView, b);

            convertView.setTag(expenseViewHolder);

        }
        Expense e = expenseArrayList.get(position);
        final ExpenseViewHolder expenseViewHolder = (ExpenseViewHolder) convertView.getTag();
        expenseViewHolder.nameTextView.setText(e.title);
        expenseViewHolder.timeTextView.setText(e.currtime + "");
        expenseViewHolder.dateTextView.setText(e.currdate + "");
        if (e.category == 0) {
            expenseViewHolder.b.setBackgroundResource(R.mipmap.dimage);
        }
        if (e.category == 1) {
            expenseViewHolder.b.setBackgroundResource(R.mipmap.bimage);
        }
        if (e.category == 2) {
            expenseViewHolder.b.setBackgroundResource(R.mipmap.oimage);
        }
        if (e.category == 3) {
            expenseViewHolder.b.setBackgroundResource(R.mipmap.pimage);
        }


        // expenseViewHolder.b.setBackgroundResource(R.mipmap.dimage);

      /*  expenseViewHolder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }

        });
*/
        expenseViewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expenseArrayList.get(position).isChecked())
                expenseArrayList.get(position).setChecked(false);
                else
                    expenseArrayList.get(position).setChecked(true);

                CheckBox cb = (CheckBox) v;
                if (cb.isChecked())
                    if (listener != null) {
                        listener.listButtonClicked(v, position);
                    }
            }
        });

        return convertView;
    }

    public void filter(String text) {
        text = text.toLowerCase();
        expenseArrayList.clear();

        if (text.length() == 0) {

            // adding the original contents back to the objects arrayList
            expenseArrayList.addAll(objects);
        } else {

            // filtering and adding relevant objects to objects arrayList
            for (int i = 0; i < objects.size(); i++) {
                Expense tempObject = objects.get(i);
                if (tempObject.title.toLowerCase()
                        .contains(text)) {
                    expenseArrayList.add(tempObject);
                }
            }

            notifyDataSetChanged();
        }
    }


}

interface OnListButtonClickedListener {
    void listButtonClicked(View v, int pos);
}

