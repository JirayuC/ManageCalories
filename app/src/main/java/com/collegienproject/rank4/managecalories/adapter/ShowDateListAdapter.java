package com.collegienproject.rank4.managecalories.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.dao.DateDao;
import com.collegienproject.rank4.managecalories.view.DateListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JirayuPC on 01 เม.ย. 2559.
 */
public class ShowDateListAdapter extends BaseAdapter{

    List<DateDao> DateList = new ArrayList<DateDao>();

    int lastPosition = -1;
    public ShowDateListAdapter(List<DateDao> dateList, FragmentActivity activity) {
        this.DateList = dateList;

    }
    @Override
    public int getCount() {
        if(DateList == null){
            return 0;
        }
        return DateList.size();
    }

    @Override
    public Object getItem(int position) {
        return DateList.get(position);
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

        DateDao date = (DateDao) getItem(position);
        item.setIdDate(String.valueOf(date.getDate_id()));
        item.setDateText(String.valueOf(date.getDatetime()));

        if(position> lastPosition) {
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPosition = position;
        }
        return item;
    }
}
