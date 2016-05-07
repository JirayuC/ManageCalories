package com.collegienproject.rank4.managecalories.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.collegienproject.rank4.managecalories.R;

/**
 * Created by JirayuPC on 06 พ.ค. 2559.
 */
public class ActPickerListAdapter extends BaseAdapter {

    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    public ActPickerListAdapter(Context c, String[] web,int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }
    @Override
    public int getCount() {
        return web.length;
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
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.list_item_activity_picker, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
            textView.setText(web[position]);
            imageView.setImageResource(Imageid[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }



}
