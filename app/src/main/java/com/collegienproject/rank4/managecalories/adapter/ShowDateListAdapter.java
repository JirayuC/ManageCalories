package com.collegienproject.rank4.managecalories.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.collegienproject.rank4.managecalories.view.DateListItem;

/**
 * Created by JirayuPC on 01 เม.ย. 2559.
 */
public class ShowDateListAdapter extends BaseAdapter{
    @Override
    public int getCount() {
        return 10000;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       DateListItem item;
        if (convertView != null)
            item = (DateListItem) convertView;
        else
            item = new DateListItem(parent.getContext());
        return item;
    }
}
