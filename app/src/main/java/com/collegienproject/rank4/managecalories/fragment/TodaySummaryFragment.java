package com.collegienproject.rank4.managecalories.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.activity.CreateActActivity;
import com.collegienproject.rank4.managecalories.adapter.CalListAdapter;
import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.collegienproject.rank4.managecalories.sqlite.DatabaseHelper;

import java.util.Date;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class TodaySummaryFragment extends Fragment {

    Button btnSaveplay;
    DatabaseHelper db;

    RecyclerView recyclerView;

    int Program_id;

    public TodaySummaryFragment() {
        super();
    }

    public static TodaySummaryFragment newInstance(ProgramDao model) {
        TodaySummaryFragment fragment = new TodaySummaryFragment();
        Bundle args = new Bundle();
        args.putInt("Program_id",model.getProgram_id());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        Program_id = getArguments().getInt("Program_id");
        Log.d("Biw","Program_id = "+Program_id);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today_summary, container, false);
        initInstances(rootView, savedInstanceState);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.cal_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        CalListAdapter calListAdapter = new CalListAdapter();
        recyclerView.setAdapter(calListAdapter);

        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {

        db = new DatabaseHelper(getActivity());

        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState
        btnSaveplay = (Button) rootView.findViewById(R.id.btn_savePlay);
        btnSaveplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int date_id = db.getDateForActivityID(new Date(),Program_id);
                Log.d("Biw","getDateForActivityID = "+date_id);
                Intent intent = new Intent(getActivity(), CreateActActivity.class);
                intent.putExtra("Date_id",date_id);
                startActivity(intent);
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
