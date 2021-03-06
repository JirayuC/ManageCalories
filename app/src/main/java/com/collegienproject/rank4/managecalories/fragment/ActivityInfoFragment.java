package com.collegienproject.rank4.managecalories.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.inthecheesefactory.thecheeselibrary.view.SlidingTabLayout;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ActivityInfoFragment extends Fragment {

    ViewPager viewPager;
    ProgramDao   model;
    private SlidingTabLayout slidingTabLayout;


    public ActivityInfoFragment() {
        super();
    }

    public static ActivityInfoFragment newInstance(ProgramDao model ) {
        ActivityInfoFragment fragment = new ActivityInfoFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_activity_info, container, false);
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

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return TodaySummaryFragment.newInstance(model);
                    case 1:
                        return PlanDateListFragment.newInstance(model);
                    case 2:
                        return StatisticsFragment.newInstance(model);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0:
                        return "วันนี้";
                    case 1:
                        return "กำหนดการ";
                    case 2:
                        return "สถิติ";
                    default:
                        return "";
                }
            }
        });

        slidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.slidingTabLayout);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setBackgroundColor(getContext().getResources().getColor(R.color.accent));
        slidingTabLayout.setViewPager(viewPager);


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
