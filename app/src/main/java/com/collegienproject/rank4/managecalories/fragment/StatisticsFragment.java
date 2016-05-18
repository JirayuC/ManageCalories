package com.collegienproject.rank4.managecalories.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.collegienproject.rank4.managecalories.sqlite.DatabaseHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class StatisticsFragment extends Fragment {

    private DatabaseHelper db;
    View rootView;

    TextView tv_value_cal;
    TextView tv_value_success;
    TextView tv_value_unsuccess;
    TextView tv_value_miss;
    TextView tv_complete;
    TextView tv_value_cal_max;
    TextView tv_act;
    TextView tv_act_date;

    ProgressBar progressBar_cal;


    ProgramDao model;


    public StatisticsFragment() {
        super();
    }

    public static StatisticsFragment newInstance(ProgramDao model) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putParcelable("model", model);
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
        rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
        initInstances(rootView, savedInstanceState);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {

        // All Cal


    }

    @Override
    public void onResume() {
        super.onResume();
        initInstances();
    }

    private void initInstances() {
    }


    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.closeDB();
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {

        db = new DatabaseHelper(getActivity());

        tv_value_cal = (TextView) rootView.findViewById(R.id.tv_value_cal);
        tv_value_success = (TextView) rootView.findViewById(R.id.tv_value_success);
        tv_value_unsuccess = (TextView) rootView.findViewById(R.id.tv_value_unsuccess);
        tv_value_miss = (TextView) rootView.findViewById(R.id.tv_value_miss);
        tv_complete = (TextView) rootView.findViewById(R.id.tv_complete);
        tv_value_cal_max = (TextView) rootView.findViewById(R.id.tv_max_cal_date);
        tv_act = (TextView) rootView.findViewById(R.id.tv_activity_cal_max);
        tv_act_date = (TextView) rootView.findViewById(R.id.tv_count_activity_date);

        progressBar_cal = (ProgressBar) rootView.findViewById(R.id.progressBar_cal);
//        progressBar_cal.getProgressDrawable().setColorFilter(
//                Color.argb(255,255,140,0), android.graphics.PorterDuff.Mode.SRC_IN);

        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        float cal = db.getStatisticCal(model.getProgram_id());
        tv_value_cal.setText("" + cal);

        // count success.
        int count_success = db.getCountSuccessDate(model.getProgram_id());
        tv_value_success.setText("" + count_success);

        // count unsuccess.
        int count_unsuccess = db.getCountDate(model.getProgram_id()) - count_success;
        tv_value_unsuccess.setText(""+count_unsuccess);

        int count_miss = db.getCountMissDate(model.getProgram_id());
        tv_value_miss.setText(""+count_miss);

        float percent = ((float)count_success/ (count_success + count_unsuccess)) *100;
        tv_complete.setText(""+String.format("%.2f",percent)+"%");

        int goal = db.getGoalCal(model.getProgram_id());
        Log.d("Biw","goal = "+goal);
        progressBar_cal.setProgress((int)((cal/goal)*100));
        progressBar_cal.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        String date = db.getCountActivityMaxOfDate(model.getProgram_id());
        // String date1 = dateThai(date);
        tv_value_cal_max.setText(""+date);

        String act = db.getActivityMaxProgram(model.getProgram_id());
        tv_act.setText(""+act);

        String date_act = db.getDateTakeActMax(model.getProgram_id());
        //String date2 = dateThai(date_act);
        tv_act_date.setText(""+date_act);
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

    public String dateThai(String strDate)
    {

        String Day[] = {
                "วันอาทิตย์ที่", "วันจันทร์ที่", "วันอังคารที่",
                "วันพุธที่", "วันพฤหัสบดีที่", "วันศุกร์ที่", "วันเสาร์ที่"};

        String Months[] = {
                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year=0,month=0,day=0,dayofweek=0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);
            dayofweek = c.get(Calendar.DAY_OF_WEEK);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return String.format("%s %s %s %s", Day[dayofweek-1],day,Months[month],year+543);
    }

}
