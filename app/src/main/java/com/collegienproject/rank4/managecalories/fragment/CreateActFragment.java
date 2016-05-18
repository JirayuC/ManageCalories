package com.collegienproject.rank4.managecalories.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.activity.ActivityPickerActivity;
import com.collegienproject.rank4.managecalories.activity.CaloriesCountActivity;
import com.collegienproject.rank4.managecalories.activity.CreateActActivity;
import com.collegienproject.rank4.managecalories.dao.DateForActivity;
import com.collegienproject.rank4.managecalories.sqlite.DatabaseHelper;

import java.util.ArrayList;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class CreateActFragment extends Fragment {


    Context context;
    ListView listView;

    DatabaseHelper db;

    int Date_id;

    ArrayList<DateForActivity> dateForActivities;

    public CreateActFragment() {
        super();
    }

    public static CreateActFragment newInstance() {
        CreateActFragment fragment = new CreateActFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_create_act, container, false);
        Date_id = ((CreateActActivity)context).Date_id;

        initInstances(rootView, savedInstanceState);
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

        db = new DatabaseHelper(getActivity());


        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(dateForActivities.get(position).getStatus() == 0) {
                    float met = dateForActivities.get(position).getMet();
                    String activity_name = dateForActivities.get(position).getActivity_name();
                    int DFA_id = dateForActivities.get(position).getDFA_id();

                    Intent intent = new Intent(context, CaloriesCountActivity.class);
                    intent.putExtra("met", met);
                    intent.putExtra("activity_name", activity_name);
                    intent.putExtra("DFA_id", DFA_id);

                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(),"Already success.",Toast.LENGTH_SHORT).show();
                }
            }
        });


        initDataListView(db);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.addActButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityPickerActivity.class);
                intent.putExtra("Date_id",Date_id);
                startActivity(intent);
            }
        });
        db.closeDB();
    }

    private void initDataListView(DatabaseHelper db) {

        dateForActivities = (ArrayList<DateForActivity>) db.getDateForActivity(Date_id);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1);

        Log.d("Biw","dateForActivities = "+dateForActivities.size());
        for (DateForActivity d : dateForActivities)
        {
            //Log.d("Biw",""+""+d.getDate_time()+" , "+d.getActivity_name());
            stringArrayAdapter.add(""+d.getDate_time()+" , "+d.getActivity_name());
        }

        listView.setAdapter(stringArrayAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initDataListView(db);
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


