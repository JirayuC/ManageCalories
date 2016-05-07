package com.collegienproject.rank4.managecalories.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.collegienproject.rank4.managecalories.view.ActListItem;

/**
 * Created by JirayuPC on 03 พ.ค. 2559.
 */
public class ActListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
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
        ActListItem item;
        if (convertView != null)
            item = (ActListItem) convertView;
        else
            item = new ActListItem(parent.getContext());
        return item;

    }
}
