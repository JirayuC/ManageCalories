package com.collegienproject.rank4.managecalories.fragment;

import android.annotation.SuppressLint;
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
import com.collegienproject.rank4.managecalories.activity.MainActivity;
import com.collegienproject.rank4.managecalories.dao.DateDao;
import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.collegienproject.rank4.managecalories.sqlite.DatabaseHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ProgramInfoFragment extends Fragment implements NumberPicker.OnValueChangeListener{

    DatabaseHelper db;
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

                if (!validate()) {
                    onCreateFailed();
                    return;
                }

                boolean didItWork = true;
                try {
                    String prgName = textNameprg.getText().toString();
                    String prgDateStart = btnDate.getText().toString();
                    String goal = goalNum.getText().toString();
                    String week = weekNum.getText().toString();


                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    int Week = Integer.parseInt(week);
                    Date strdate = formatter.parse(prgDateStart);
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
                        if(cb7.isChecked()){
                            if (c.get(Calendar.DAY_OF_WEEK )== 7){
                                dateActivityList.add(c.getTime());
                            }
                        }

                        c.add(Calendar.DATE,1);
                    }


                    db = new DatabaseHelper(getActivity());

                    List<Integer> date_pri = new ArrayList<Integer>();
                    for(int i=0; i < dateActivityList.size(); i++){
                        Date date = dateActivityList.get(i);
                        //เอา date ยัดลง sqlite

                        DateDao dat = new DateDao();
                        dat.setDatetime(date);

                        date_pri.add(db.addDate(dat,db.getMaxProgramId()+1));
                    }


                    ProgramDao prg = new ProgramDao();
                    prg.setProgram_name(prgName);

                    try {
                        Date date1 = formatter.parse(prgDateStart);
                        prg.setStart_date(date1);
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    //int[] temp = new int[date_pri.size()] ;
                    //x =  date_pri.toArray(x);
                    //int[] array = list.stream().mapToInt(i->i).toArray();
                    int[] temp = convertIntegers(date_pri);
                    prg.setGoal(Integer.parseInt(goal));
                    prg.setWeek_num(Integer.parseInt(week));
                    db.addProgram(prg,temp);

                    db.closeDB();



                }catch (SQLException e){
                    didItWork = false;
                    Log.e("ProgramInfoActivity", "Database didn't upgrade when button pressed");

                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    if(didItWork){
                        Toast.makeText(getContext(), "created Program Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    }
                }
            }
        });
    }
    public static int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }
    private boolean validate() {
        boolean valid = true;

        String prgName = textNameprg.getText().toString();
        String prgDateStart = btnDate.getText().toString();
        String goal = goalNum.getText().toString();
        String week = weekNum.getText().toString();


        if (prgName.isEmpty() || prgName.length() < 3) {
            textNameprg.setError("กรุณากรอกข้อมูล");
            valid = false;
        } else {
            textNameprg.setError(null);
        }

        if (prgDateStart.isEmpty()) {
            btnDate.setError("กรุณาเลือกวันเริ่มออกกำลังกาย");
            valid = false;
        } else {
            btnDate.setError(null);
        }



        if (week.isEmpty()) {
            weekNum.setError("กรุณาเลือกจำนวนสัปดาห์");
            valid = false;
        } else {
            weekNum.setError(null);
        }

        if(cb1.isChecked()||cb2.isChecked()||cb3.isChecked()||cb4.isChecked()||cb5.isChecked()||cb6.isChecked()||cb7.isChecked()){
            cb1.setError(null);
            cb2.setError(null);
            cb3.setError(null);
            cb4.setError(null);
            cb5.setError(null);
            cb6.setError(null);
            cb7.setError(null);

        }
        else {
            Toast.makeText(getContext(), "กรุณาเลือกวันออกกำลังกาย", Toast.LENGTH_SHORT).show();
            valid = false;

        }

        float valueH = 0;
        try {
            valueH = Float.parseFloat(goalNum.getText().toString());
        }
        catch(NumberFormatException ex) {
            goalNum.setError("กรุณากรอกแคลอรี่ให้น้อยกว่า 10,000 cal");
        }

        if (goal.isEmpty()|| valueH > 10000.1f ) {
            goalNum.setError("กรุณากรอกแคลอรี่ให้น้อยกว่า 10,000 cal");
            valid = false;
        } else {
            goalNum.setError(null);
        }


        return valid;
    }

    private void onCreateFailed() {
        Toast.makeText(getContext(),
                "ข้อมูลไม่สมบูรณ์",
                Toast.LENGTH_SHORT)
                .show();
        btnCreateprg.setEnabled(true);
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


    @SuppressLint("ValidFragment")
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


            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String format = formatter.format(chosenDate);



            btnDate.setText(format);
        }


    }
}
