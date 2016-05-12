package com.collegienproject.rank4.managecalories.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.collegienproject.rank4.managecalories.activity.ActivityPickerActivity;
import com.collegienproject.rank4.managecalories.activity.CaloriesCountActivity;
import com.collegienproject.rank4.managecalories.dao.ActivityDao;
import com.collegienproject.rank4.managecalories.dao.DateForActivity;
import com.collegienproject.rank4.managecalories.view.ActPickListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JirayuPC on 06 พ.ค. 2559.
 */
public class ActPickerListAdapter extends BaseAdapter {

    private Context mContext;


    List<ActivityDao> ActList = new ArrayList<ActivityDao>();
   // List<ActivityDao> ActListi = new ArrayList<ActivityDao>();

    int lastPosition = -1;


    public ActPickerListAdapter(List<ActivityDao> detail, Context context, List listRowItems) {
        //ActivityDao test = new ActivityDao();
        //test.setActivity_name();
        //this.ActList.add(test.);
       //this.ActList = detail;
        ActList=listRowItems;
    }



    @Override
    public int getCount() {
        if(ActList == null){
            return 0;
        }
        return ActList.size();
    }

    @Override
    public Object getItem(int position) {
        return ActList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ActPickListItem item;
        if (convertView != null)
            item = (ActPickListItem) convertView;
        else
            item = new ActPickListItem(parent.getContext());

        ActivityDao act = (ActivityDao) getItem(position);

        item.setImageView(act.getImage_id());
        item.setActText(act.getActivity_name());
        Log.d("actName : ", act.getActivity_name());



        /*if(position> lastPosition) {
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPosition = position;
        }*/
        return item;
    }



}
