package com.collegienproject.rank4.managecalories.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.collegienproject.rank4.managecalories.sqlite.SqlDatabase;
import com.collegienproject.rank4.managecalories.view.ProgramListItem;

import java.util.ArrayList;

/**
 * Created by JirayuPC on 09 เม.ย. 2559.
 */
public class ProgramListAdapter extends BaseAdapter {


    ArrayList<ProgramDao> programList;
    SqlDatabase db;

    public ProgramListAdapter(){


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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProgramListItem item;
        if (convertView != null)
            item = (ProgramListItem) convertView;
        else
            item = new ProgramListItem(parent.getContext());


        db.getProgramList();
        ProgramDao program = programList.get(position);
        item.setIdText(program.getProgram_id());
        item.setNameText(program.getProgram_name());


        return item;
    }

}

