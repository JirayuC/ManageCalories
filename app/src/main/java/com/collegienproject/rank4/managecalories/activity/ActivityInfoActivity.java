package com.collegienproject.rank4.managecalories.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.fragment.ActivityInfoFragment;

public class ActivityInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_info);

        initInstances();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, ActivityInfoFragment.newInstance())
                    .commit();
        }
    }

    private void initInstances() {

    }


}
