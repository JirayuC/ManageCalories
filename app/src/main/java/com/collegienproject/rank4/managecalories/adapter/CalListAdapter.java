package com.collegienproject.rank4.managecalories.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.collegienproject.rank4.managecalories.dao.CaloriesReal;
import com.collegienproject.rank4.managecalories.view.CaloriesDateListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yu-pc on 5/12/2016.
 */
public class CalListAdapter extends BaseAdapter{

    List<CaloriesReal> calList = new ArrayList<CaloriesReal>();
    float avgCalList;

    public CalListAdapter(ArrayList<CaloriesReal> detail, FragmentActivity activity, float avgCal) {
        this.calList = detail;
        this.avgCalList = avgCal;
    }

    @Override
    public int getCount() {
        if(calList == null){
            return 0;
        }
        return calList.size();
    }

    @Override
    public Object getItem(int position) {
        return calList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CaloriesDateListItem item;
        if (convertView != null)
            item = (CaloriesDateListItem) convertView;
        else
            item = new CaloriesDateListItem(parent.getContext());


        CaloriesReal program = (CaloriesReal) getItem(position);
        item.setDateText(program.getDate());
        item.setCalText(program.getCal(),avgCalList);


        /*if(position> lastPosition) {
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPosition = position;
        }*/
        return item;
    }
}
