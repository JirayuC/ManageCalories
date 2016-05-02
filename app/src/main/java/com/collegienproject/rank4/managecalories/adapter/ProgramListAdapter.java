package com.collegienproject.rank4.managecalories.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.collegienproject.rank4.managecalories.view.ProgramListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JirayuPC on 09 เม.ย. 2559.
 */
public class ProgramListAdapter extends BaseAdapter {
    FragmentActivity activity;

    List<ProgramDao> programList = new ArrayList<ProgramDao>();

    int lastPosition = -1;
    public ProgramListAdapter(List<ProgramDao> myList, FragmentActivity activity) {
            this.programList = myList;
            this.activity = activity;
    }

    @Override
    public int getCount() {
        if(programList == null){
            return 0;
        }
        return programList.size();
    }

    @Override
    public Object getItem(int position)

    {
        return programList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProgramListItem item;
        if (convertView != null)
            item = (ProgramListItem) convertView;
        else
            item = new ProgramListItem(parent.getContext());


        ProgramDao program = (ProgramDao) getItem(position);
        item.setDateText(String.valueOf(program.getStart_date()));
        item.setNameText(program.getProgram_name());

        if(position> lastPosition) {
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPosition = position;
        }
        return item;
    }

}

