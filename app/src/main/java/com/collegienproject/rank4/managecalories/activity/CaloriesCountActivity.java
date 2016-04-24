package com.collegienproject.rank4.managecalories.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.fragment.CaloriesCountFragment;

public class CaloriesCountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_count);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, CaloriesCountFragment.newInstance())
                    .commit();
        }
    }
}
