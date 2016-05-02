package com.collegienproject.rank4.managecalories.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.collegienproject.rank4.managecalories.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class CaloriesCountFragment extends Fragment {

    TextView textTimer;
    TextView textCalories;
    Button startButton;
    Button pauseButton;
    Button stopButton;
    private long startTime = 0L;
    Handler myHandler = new Handler();
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;


    public CaloriesCountFragment() {
        super();
    }

    public static CaloriesCountFragment newInstance() {
        CaloriesCountFragment fragment = new CaloriesCountFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_count_calories, container, false);
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

        textTimer = (TextView) rootView.findViewById(R.id.textTimer);
        textCalories = (TextView) rootView.findViewById(R.id.textCalories);
        startButton = (Button) rootView.findViewById(R.id.btnStart);
        pauseButton = (Button) rootView.findViewById(R.id.btnPause);
        stopButton = (Button) rootView.findViewById(R.id.btnStop);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                myHandler.postDelayed(updateTimerMethod,0);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwap += timeInMillies;
                myHandler.removeCallbacks(updateTimerMethod);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwap = 0;
                timeInMillies = 0;
                myHandler.removeCallbacks(updateTimerMethod);
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

    private Runnable updateTimerMethod = new Runnable() {

        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;

            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;
            int hours = minutes / 24;
            textTimer.setText("" + String.format("%02d",hours) + ":"
                    + String.format("%02d", minutes) + ":"
                    + String.format("%02d", seconds));
            myHandler.postDelayed(this, 0);
        }

    };

    private  Runnable ubdateCalomerMethod = new Runnable() {
        @Override
        public void run() {


        }
    };

}
