package com.nshmura.recyclertablayout.demo.years;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.nshmura.recyclertablayout.demo.Demo;
import com.nshmura.recyclertablayout.demo.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DemoYearsActivity extends AppCompatActivity {

    protected static final String KEY_DEMO = "demo";

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoYearsActivity.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_basic);

        Demo demo = Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(demo.titleResId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int startYear = 1900;
        int endYear = 3000;
        int initialYear = Calendar.getInstance().get(Calendar.YEAR);

        List<String> items = new ArrayList<>();
        for (int i = startYear; i <= endYear; i++) {
            items.add(String.valueOf(i));
        }

        DemoYearsPagerAdapter adapter = new DemoYearsPagerAdapter();
        adapter.addAll(items);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(initialYear - startYear);

        RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout)
                findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}