package com.nshmura.recyclertablayout.demo.tabscrolldisabled;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.nshmura.recyclertablayout.demo.ColorItem;
import com.nshmura.recyclertablayout.demo.Demo;
import com.nshmura.recyclertablayout.demo.DemoColorPagerAdapter;
import com.nshmura.recyclertablayout.demo.R;
import com.nshmura.recyclertablayout.demo.utils.DemoData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DemoTabScrollDisabledActivity extends AppCompatActivity {

    protected static final String KEY_DEMO = "demo";

    protected RecyclerTabLayout mRecyclerTabLayout;

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoTabScrollDisabledActivity.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_tab_scroll_disabled);

        Demo demo = Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));

        Toolbar toolbar = findViewById(R.id.toolbar);
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

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        mRecyclerTabLayout = findViewById(R.id.recycler_tab_layout);
        mRecyclerTabLayout.setUpWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}