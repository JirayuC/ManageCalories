package com.collegienproject.rank4.managecalories.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.collegienproject.rank4.managecalories.fragment.MainFragment;
import com.collegienproject.rank4.managecalories.sqlite.DatabaseHelper;

public class MainActivity extends AppCompatActivity
        implements MainFragment.FragmentListener {

    android.support.v7.widget.Toolbar toolbar;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getBoolean(R.bool.portrait_only)){
            //Fix screen orientation to Portrait
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        setContentView(R.layout.activity_main);
        initInstances();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit();
        }

        db = new DatabaseHelper(MainActivity.this);
        if(!db.isAlreadyUser()){
            Intent intent = new Intent(MainActivity.this,SignupActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initInstances() {

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("แผนออกกำลังกายของฉัน");

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.btn_user_profile:
                Intent intent = new Intent(this,UpdateUserActivity.class);
                startActivity(intent);
                return true;
        }
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgramItemClicked(ProgramDao model) {

        Intent intent = new Intent(MainActivity.this, ActivityInfoActivity.class);
        intent.putExtra("model",model);
        startActivity(intent);
        finish();
    }
}
