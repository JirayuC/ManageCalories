package com.collegienproject.rank4.managecalories.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.activity.ActivityInfoActivity;
import com.collegienproject.rank4.managecalories.dao.DateDao;
import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.collegienproject.rank4.managecalories.sqlite.SqlDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ProgramInfoFragment extends Fragment implements NumberPicker.OnValueChangeListener{

    EditText textNameprg, goalNum, weekNum;
    Button btnCreateprg, btnDate;
    CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7;

    public ProgramInfoFragment() {
        super();
    }

    public static ProgramInfoFragment newInstance() {
        ProgramInfoFragment fragment = new ProgramInfoFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_program_info, container, false);
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

        cb1 = (CheckBox) rootView.findViewById(R.id.ck1 );
        cb2 = (CheckBox) rootView.findViewById(R.id.ck2 );
        cb3 = (CheckBox) rootView.findViewById(R.id.ck3 );
        cb4 = (CheckBox) rootView.findViewById(R.id.ck4 );
        cb5 = (CheckBox) rootView.findViewById(R.id.ck5 );
        cb6 = (CheckBox) rootView.findViewById(R.id.ck6 );
        cb7 = (CheckBox) rootView.findViewById(R.id.ck7 );
        textNameprg = (EditText) rootView.findViewById(R.id.input_program_name);
        btnCreateprg = (Button) rootView.findViewById(R.id.btn_create_program);
        btnDate = (Button) rootView.findViewById(R.id.btnDate);
        goalNum = (EditText) rootView.findViewById(R.id.input_goal);
        weekNum = (EditText) rootView.findViewById(R.id.input_week);

        weekNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment picker = new SelectDateFragment();
                picker.show(getActivity().getFragmentManager(), "datePicker");
            }
        });

        btnCreateprg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean didItWork = true;
                try {
                    String prgName = textNameprg.getText().toString();
                    String prgDateStart = btnDate.getText().toString();
                    String goal = goalNum.getText().toString();
                    String week = weekNum.getText().toString();


                    DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
                    int Week = Integer.parseInt(week);
                    Date strdate = df.parse(prgDateStart);
                    Calendar c = Calendar.getInstance(Locale.US);
                    c.setTime(strdate);
                    ArrayList<Date> dateActivityList = new ArrayList<Date>();

                    for (int i = 0; i < Week * 7; i++)
                    {

                        if(cb1.isChecked()){
                            if (c.get(Calendar.DAY_OF_WEEK )== 1){
                                dateActivityList.add(c.getTime());
                            }
                        }

                        if(cb2.isChecked()){
                            if (c.get(Calendar.DAY_OF_WEEK )== 2){
                                dateActivityList.add(c.getTime());
                            }
                        }

                        if(cb3.isChecked()){
                            if (c.get(Calendar.DAY_OF_WEEK )== 3){
                                dateActivityList.add(c.getTime());
                            }
                        }

                        if(cb4.isChecked()){
                            if (c.get(Calendar.DAY_OF_WEEK )== 4){
                                dateActivityList.add(c.getTime());
                            }
                        }

                        if(cb5.isChecked()){
                            if (c.get(Calendar.DAY_OF_WEEK )== 5){
                                dateActivityList.add(c.getTime());
                            }
                        }

                        if(cb6.isChecked()){
                            if (c.get(Calendar.DAY_OF_WEEK )== 6){
                                dateActivityList.add(c.getTime());
                            }
                        }
                        if(cb1.isChecked()){
                            if (c.get(Calendar.DAY_OF_WEEK )== 7){
                                dateActivityList.add(c.getTime());
                            }
                        }

                        c.add(Calendar.DATE,1);
                    }



                    SqlDatabase db = new SqlDatabase(getActivity());
                    db.open();

                    for(int i=0; i < dateActivityList.size(); i++){
                        Date date = dateActivityList.get(i);
                        //เอา date ยัดลง sqlite

                        DateDao dat = new DateDao();
                        dat.setDatetime(date);
                        db.addDate(dat);
                    }


                    ProgramDao prg = new ProgramDao();
                    prg.setProgram_name(prgName);

                    try {
                        Date date1 = df.parse(prgDateStart);
                        prg.setStart_date(date1);
                    }catch (ParseException e){
                        e.printStackTrace();
                    }

                    prg.setGoal(Integer.parseInt(goal));
                    prg.setWeek_num(Integer.parseInt(week));
                    db.addProgram(prg);

                    db.close();



                }catch (SQLException e){
                    didItWork = false;
                    Log.e("ProgramInfoActivity", "Database didn't upgrade when button pressed");

                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    if(didItWork){
                        Toast.makeText(getContext(), "created Program Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),ActivityInfoActivity.class);
                        startActivity(intent);
                    }
                }
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

    private void show() {
        final Dialog d = new Dialog(getActivity());
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.dialog_number);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(10);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                weekNum.setText(String.valueOf(np.getValue()));
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is",""+newVal);

    }


    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();

            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, yy, mm, dd);


            // Subtract 6 days from Calendar updated date
            calendar.add(Calendar.DATE, 0);

            // Set the Calendar new date as minimum date of date picker
            //dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());
            Date newDate = calendar.getTime();
            dpd.getDatePicker().setMinDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
            return  dpd;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm, dd);
        }
        public void populateSetDate(int year, int month, int day) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
            String formattedDate = df.format(chosenDate);

            btnDate.setText(formattedDate);
        }

    }
}
