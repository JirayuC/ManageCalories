package com.collegienproject.rank4.managecalories.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.dao.UserDao;
import com.collegienproject.rank4.managecalories.sqlite.DatabaseHelper;

import java.util.Date;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class CaloriesCountFragment extends Fragment {

    DatabaseHelper db;
    UserDao  userDao;

    TextView textTimer;
    TextView textCalories;

    TextView txtActivityName;

    Button startButton;
    Button pauseButton;
    Button stopButton;
    Button btnManual;
    private long startTime = 0L;
    Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;

    int DFA_id;
    double BMR;
    float MET;
    String activity_name;

    double calorie_Burn;



    public CaloriesCountFragment() {
        super();
    }

    public static CaloriesCountFragment newInstance(int DFA_id,float met,String activity_name) {
        CaloriesCountFragment fragment = new CaloriesCountFragment();
        Bundle args = new Bundle();
        args.putFloat("met",met);
        args.putString("activity_name",activity_name);
        args.putInt("DFA_id",DFA_id);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        activity_name = getArguments().getString("activity_name");
        MET = getArguments().getFloat("met");
        DFA_id = getArguments().getInt("DFA_id");

        Log.d("Biw","DFA_id = "+DFA_id+" , activity_name = "+activity_name+" , MET = "+MET);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_count_calories, container, false);
        initInstances(rootView, savedInstanceState);

        userDao = db.getUser();
        txtActivityName.setText(activity_name);
        Log.d("Biw"," Sex = "+userDao.getUser_sex()+" , H = "+userDao.getUser_height()+" , W = "+userDao.getUser_weight()+" , birth = "+userDao.getUser_birthdate());

        calculateBMR();


        return rootView;
    }

    private void calculateBMR() {
        Date birth_date = userDao.getUser_birthdate();
        int age =  getAge(birth_date.getYear(),birth_date.getMonth(),birth_date.getDay());
        Log.d("Biw","Age = "+age);

        if(userDao.getUser_sex().equals("male")){
            BMR = 66+(13.7*userDao.getUser_weight())+(5*userDao.getUser_height())-(6.8*age);
        }
        else{
            BMR = 665+(9.6*userDao.getUser_weight())+(1.8*userDao.getUser_height())-(4.7*age);
        }

        Log.d("Biw","BMR = "+BMR);
    }

    public int getAge(int year, int month, int day){
        Date now = new Date();
        int nowMonth = now.getMonth();
        int nowYear = now.getYear();
        int result = nowYear - year;

        if (month > nowMonth) {
            result--;
        }
        else if (month == nowMonth) {
            int nowDay = now.getDate();

            if (day > nowDay) {
                result--;
            }
        }
        return result;
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

        txtActivityName = (TextView) rootView.findViewById(R.id.txt_activity_name);

        textTimer = (TextView) rootView.findViewById(R.id.textTimer);
        textCalories = (TextView) rootView.findViewById(R.id.textCalories);
        startButton = (Button) rootView.findViewById(R.id.btnStart);
        pauseButton = (Button) rootView.findViewById(R.id.btnPause);
        stopButton = (Button) rootView.findViewById(R.id.btnStop);
        btnManual = (Button) rootView.findViewById(R.id.btnManual);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                myHandler.postDelayed(updateTimerMethod,0);
                startButton.setEnabled(false);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwap += timeInMillies;
                myHandler.removeCallbacks(updateTimerMethod);
                startButton.setEnabled(true);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwap = 0;
                timeInMillies = 0;
                myHandler.removeCallbacks(updateTimerMethod);

                displayDialog();
            }
        });

        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayInputDialog();
            }
        });

    }

    private void displayInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("กรอกเวลาที่ทำได้");

        // Set up the input
        final EditText input = new EditText(getActivity());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("เวลา(นาที)");
        builder.setView(input);

            // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String time = input.getText().toString();
                int cal = (int) Calorie_Burn_Manual(Long.parseLong(time));
                db.activitySuccess(DFA_id, Float.parseFloat(String.valueOf(cal)));
                dialog.dismiss();
                getActivity().finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void displayDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Save");
        alertDialog.setMessage("Do you save you burn calories?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Save ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.activitySuccess(DFA_id, (float)calorie_Burn);
                        dialog.dismiss();
                        getActivity().finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        getActivity().finish();
                    }
                });

        alertDialog.show();
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

    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;

            int sum_seconds = (int) (finalTime / 1000);
            Log.d("benz","sum_seconds = "+sum_seconds+" s");


            int minutes = sum_seconds / 60;
            int seconds = sum_seconds % 60;
            minutes = minutes % 60;
            int hours = minutes / 24;
            textTimer.setText("" + String.format("%02d",hours) + ":"
                    + String.format("%02d", minutes) + ":"
                    + String.format("%02d", seconds));
            myHandler.postDelayed(this, 0);


            textCalories.setText(""+ String.format("%.3f",Calorie_Burn(sum_seconds)));

        }

    };

    private double Calorie_Burn(long time){
        calorie_Burn = (BMR/24) * MET * (time/3600.0f);
        Log.d("benz","calorie_Burn = "+calorie_Burn);
        return calorie_Burn;
    }

    private double Calorie_Burn_Manual(long time){
        calorie_Burn = (BMR/24) * MET * (time/60.0f);
        Log.d("benz","calorie_Burn22 = "+calorie_Burn);
        return calorie_Burn;
    }


}
