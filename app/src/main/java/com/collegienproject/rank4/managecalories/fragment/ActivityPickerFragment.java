package com.collegienproject.rank4.managecalories.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.adapter.ActPickerListAdapter;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ActivityPickerFragment extends Fragment {

    GridView gridView;

    float[] met = {
            6f, 3.5f, 4.5f,
            5.5f, 11.1f, 7.2f,
            3f, 13.4f, 7f,
            11.5f, 7f, 8f,
            12.9f, 6.5f, 5.1f,
            7f, 10f, 4f,
            12.6f, 10.3f, 4.5f,
            9.02f, 4.7f, 6.5f,
            4.5f, 13.3f, 4.1f,
            15f, 6f, 3.4f,
            10.9f, 3.2f ,12.9f

    };
    int[] imageId = {
            R.drawable.playing_football, R.drawable.hurdles, R.drawable.archery,
            R.drawable.javelin, R.drawable.american_football, R.drawable.long_jumping,
            R.drawable.baseball_shot, R.drawable.mountain_climbing, R.drawable.basketball,
            R.drawable.playing_rugby, R.drawable.beach_volleyball, R.drawable.playing_tennis,
            R.drawable.cricket_catch, R.drawable.playing_volleyball, R.drawable.cycling,
            R.drawable.river_rafting, R.drawable.fencing, R.drawable.running_marathon,
            R.drawable.golfing, R.drawable.shot_put, R.drawable.high_jump,
            R.drawable.skateboarding, R.drawable.shot_put, R.drawable.high_jump,
            R.drawable.skateboarding, R.drawable.shot_put, R.drawable.high_jump,
            R.drawable.skateboarding, R.drawable.shot_put, R.drawable.high_jump,
            R.drawable.skateboarding, R.drawable.shot_put
    };

    String[] web = {
            "Aerobic dancing", "Badminton Doubles", "Badminton Singles",
            "Baseball", "Basketball", "Bicycling",
            "Bowling", "Boxing", "Canoeing",
            "Cross-country skiing", "Equestrianism", "Fencing",
            "Figure skating", "American football", "Golf",
            "Gymnastics", "Handball", "Home calisthenics",
            "Rugby", "football", "Softball",
            "Swimming", "Table tennis", "Tennis Singles",
            "Tennis Doubles", "Marathon", "High jump",
            "Long jump", "Volleyball", "Walking for exercise",
            "Weight training", "Yoga", "Running"


    } ;

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
        View rootView = inflater.inflate(R.layout.fragment_activity_picker, container, false);
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

        gridView = (GridView) rootView.findViewById(R.id.gridview);
        ActPickerListAdapter adapter = new ActPickerListAdapter(getContext(), web, imageId);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
