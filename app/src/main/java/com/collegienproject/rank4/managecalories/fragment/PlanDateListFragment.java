package com.collegienproject.rank4.managecalories.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.adapter.ShowDateListAdapter;
import com.collegienproject.rank4.managecalories.dao.DateDao;
import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.collegienproject.rank4.managecalories.sqlite.DatabaseHelper;

import java.util.ArrayList;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PlanDateListFragment extends Fragment {

    ProgramDao model ;
    ListView listView;
    ShowDateListAdapter listAdapter;
    DatabaseHelper dateList;


    public interface FragmentListener{
        void onDateItemClicked(DateDao model);
    }



    public PlanDateListFragment() {
        super();
    }

    public static PlanDateListFragment newInstance(ProgramDao model) {
        PlanDateListFragment fragment = new PlanDateListFragment();
        Bundle args = new Bundle();
        args.putParcelable("model",model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        model = getArguments().getParcelable("model");

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
        int x = model.getProgram_id();
        detail = (ArrayList<DateDao>) dateList.getListOfProgramDAO(x);
        listAdapter = new ShowDateListAdapter(detail,getActivity());
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentListener listener = (FragmentListener) getActivity();
                listener.onDateItemClicked(null);
               // final int InternalPosition = position;
               // Object o = MyListActivity.this.getListView().getItemAtPosition(InternalPosition);
                Toast.makeText(getContext(), "" + id, Toast.LENGTH_SHORT).show();
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
