package com.collegienproject.rank4.managecalories.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.activity.ActivityPickerActivity;
import com.collegienproject.rank4.managecalories.activity.CaloriesCountActivity;
import com.collegienproject.rank4.managecalories.adapter.ActPickerListAdapter;
import com.collegienproject.rank4.managecalories.dao.ActivityDao;
import com.collegienproject.rank4.managecalories.dao.DateForActivity;
import com.collegienproject.rank4.managecalories.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ActivityPickerFragment extends Fragment {

    ListView listView;

    ActPickerListAdapter listAdapter;
    DatabaseHelper db;
    Context context;
    ArrayList<DateForActivity> dateForActivities;
    //int Date_id;

    static  final int[] imageId = {
            R.drawable.long_jumping, R.drawable.long_jumping, R.drawable.long_jumping,
            R.drawable.long_jumping, R.drawable.long_jumping, R.drawable.long_jumping,
            R.drawable.long_jumping, R.drawable.long_jumping, R.drawable.long_jumping,
            R.drawable.long_jumping, R.drawable.long_jumping, R.drawable.long_jumping,
            R.drawable.long_jumping, R.drawable.long_jumping, R.drawable.long_jumping,
            R.drawable.long_jumping, R.drawable.long_jumping, R.drawable.long_jumping,
            R.drawable.long_jumping, R.drawable.long_jumping, R.drawable.long_jumping,
            R.drawable.long_jumping, R.drawable.long_jumping, R.drawable.long_jumping,
            R.drawable.long_jumping, R.drawable.long_jumping, R.drawable.high_jump,
            R.drawable.long_jumping, R.drawable.long_jumping, R.drawable.long_jumping,
            R.drawable.long_jumping, R.drawable.long_jumping, R.drawable.long_jumping
    };


    public ActivityPickerFragment() {
        super();
    }

    public static ActivityPickerFragment newInstance() {
        ActivityPickerFragment fragment = new ActivityPickerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);



        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = inflater.getContext();
        View rootView = inflater.inflate(R.layout.fragment_activity_picker, container, false);
        initInstances(rootView, savedInstanceState);
        //Date_id = ((ActivityPickerActivity)context).Date_id;
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        listView = (ListView) rootView.findViewById(R.id.listViewPicker);

        db = new DatabaseHelper(getActivity());
        ArrayList<ActivityDao> detail;
        detail = (ArrayList<ActivityDao>) db.getActPickerList();


        List listRowItems;
        listRowItems = new ArrayList();
        for (int i = 0; i < imageId.length; i++) {
            ActivityDao item = new ActivityDao(imageId[i], detail.get(i).getActivity_name());
            listRowItems.add(item);
        }


        listAdapter = new ActPickerListAdapter(detail,getActivity(),listRowItems);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int Date_id = ((ActivityPickerActivity)context).Date_id;
                db.addDateForActivity(Date_id,position+1);
                //Log.d("Biw","Date_id = "+Date_id+" , Activity_id = "+position);
                //Toast.makeText(context,"Date_id = "+Date_id+" , Activity_id = "+position,Toast.LENGTH_SHORT).show();

               /* dateForActivities = (ArrayList<DateForActivity>) db.getDateForActivity(Date_id);
                float met = dateForActivities.get(position).getMet();
                String activity_name = dateForActivities.get(position).getActivity_name();
                int DFA_id = dateForActivities.get(position).getDFA_id();

                Intent intent = new Intent(context, CaloriesCountActivity.class);
                intent.putExtra("met", met);
                intent.putExtra("activity_name", activity_name);
                intent.putExtra("DFA_id", DFA_id);
                intent.putExtra("Date_id",Date_id);

                startActivity(intent);*/
                getActivity().finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

}
