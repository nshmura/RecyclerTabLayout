package com.nshmura.recyclertablayout.demo.rtl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.nshmura.recyclertablayout.demo.ColorItem;
import com.nshmura.recyclertablayout.demo.Demo;
import com.nshmura.recyclertablayout.demo.DemoColorPagerAdapter;
import com.nshmura.recyclertablayout.demo.R;
import com.nshmura.recyclertablayout.demo.basic.DemoBasicActivity;
import com.nshmura.recyclertablayout.demo.utils.DemoData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DemoRtlActivity extends DemoBasicActivity {

    protected RecyclerTabLayout mRecyclerTabLayout;

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoRtlActivity.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_rtl);

        Demo demo = Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(demo.titleResId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<ColorItem> items = DemoData.loadDemoColorItems(this);
        Collections.sort(items, new Comparator<ColorItem>() {
            @Override
            public int compare(ColorItem lhs, ColorItem rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });

        DemoColorPagerAdapter adapter = new DemoColorPagerAdapter();
        adapter.addAll(items);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        mRecyclerTabLayout = (RecyclerTabLayout)
                findViewById(R.id.recycler_tab_layout);
        mRecyclerTabLayout.setUpWithViewPager(viewPager);
    }
}