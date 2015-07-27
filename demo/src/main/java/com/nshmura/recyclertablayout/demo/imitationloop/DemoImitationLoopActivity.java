package com.nshmura.recyclertablayout.demo.imitationloop;

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

public class DemoImitationLoopActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener {

    protected static final String KEY_DEMO = "demo";

    private int mScrollState;
    private DemoImitationLoopPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private ArrayList<String> mItems;

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoImitationLoopActivity.class);
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

        mItems = new ArrayList<>();
        mItems.add(":)");
        for (int i = 1; i <= 9; i++) {
            mItems.add(String.valueOf(i));
        }

        mAdapter = new DemoImitationLoopPagerAdapter();
        mAdapter.addAll(mItems);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mAdapter.getCenterPosition(0));
        mViewPager.addOnPageChangeListener(this);

        RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout)
                findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithViewPager(mViewPager);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //got to center
        boolean nearLeftEdge = (position <= mItems.size());
        boolean nearRightEdge = (position >= mAdapter.getCount() - mItems.size());
        if (nearLeftEdge || nearRightEdge) {
            mViewPager.setCurrentItem(mAdapter.getCenterPosition(0), false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
    }
}