package com.crazydudes.viewpagerexample.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.crazydudes.viewpagerexample.R;

import java.util.ArrayList;
import java.util.List;

public class RecordsAdapter extends BaseAdapter {

    public static final int MODE_ALL = 0;
    public static final int MODE_PASSED = 1;
    public static final int MODE_FAILED = 2;
    public static final int MIN_GRADE = 50;

    private Context context;
    private int mode;
    private List<Record> list;

    public RecordsAdapter(Context context, int mode) {
        this.context = context;
        this.mode = mode;
        list = new ArrayList<Record>();
    }

    /**
     * This method gets the list created in the main activity and adds the
     * relevant records to its own internal list.
     * @param data
     */
    public void setData(List<Record> data) {
        list.clear();
        switch (mode) {
            case (MODE_ALL):
                list.addAll(data);
                break;
            case (MODE_PASSED):
                for (Record record : data) {
                    if (record.getGrade() >= MIN_GRADE) {
                        list.add(record);
                    }
                }
                break;
            case (MODE_FAILED):
                for (Record record : data) {
                    if (record.getGrade() <= MIN_GRADE) {
                        list.add(record);
                    }
                }
                break;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * For this view i'm going to use a simple textview
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }else {
            view = convertView;
        }
        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView grade = (TextView) view.findViewById(R.id.tv_grade);
        final Record record = (Record) getItem(position);
        name.setText(record.getName());
        grade.setText(Integer.toString(record.getGrade()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, record.getName() + " had " + record.getGrade(), Toast.LENGTH_SHORT).show();
            }
        });

         return view;
    }
}
