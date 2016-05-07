package com.collegienproject.rank4.managecalories.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.dao.DateDao;
import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.collegienproject.rank4.managecalories.fragment.ActivityInfoFragment;
import com.collegienproject.rank4.managecalories.fragment.PlanDateListFragment;

public class ActivityInfoActivity extends AppCompatActivity implements PlanDateListFragment.FragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_info);

        initInstances();

        ProgramDao model = getIntent().getParcelableExtra("model");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, ActivityInfoFragment.newInstance(model))
                    .commit();
        }
    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            Intent intent = new Intent(ActivityInfoActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateItemClicked(DateDao model) {

        Intent intent = new Intent(ActivityInfoActivity.this, CreateActActivity.class);
        //intent.putExtra("model",model);
        startActivity(intent);
    }

}
