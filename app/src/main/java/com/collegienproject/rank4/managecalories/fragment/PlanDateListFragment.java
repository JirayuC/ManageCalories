package com.collegienproject.rank4.managecalories.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.adapter.ShowDateListAdapter;
import com.collegienproject.rank4.managecalories.dao.DateDao;
import com.collegienproject.rank4.managecalories.sqlite.DatabaseHelper;

import java.util.ArrayList;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PlanDateListFragment extends Fragment {

    ListView listView;
    ShowDateListAdapter listAdapter;
    DatabaseHelper dateList;

    public PlanDateListFragment() {
        super();
    }

    public static PlanDateListFragment newInstance() {
        PlanDateListFragment fragment = new PlanDateListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_plan_date_list, container, false);
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

        listView = (ListView) rootView.findViewById(R.id.listViewPlanDate);
        dateList = new DatabaseHelper(getActivity());
        ArrayList<DateDao> detail;
        detail = (ArrayList<DateDao>) dateList.getDateList();
        listAdapter = new ShowDateListAdapter(detail,getActivity());
        listView.setAdapter(listAdapter);
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
