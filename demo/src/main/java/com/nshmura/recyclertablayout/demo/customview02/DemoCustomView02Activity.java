package com.nshmura.recyclertablayout.demo.customview02;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.nshmura.recyclertablayout.demo.Demo;
import com.nshmura.recyclertablayout.demo.DemoImagePagerAdapter;
import com.nshmura.recyclertablayout.demo.R;
import com.nshmura.recyclertablayout.demo.utils.DemoData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class DemoCustomView02Activity extends AppCompatActivity {

    private static final String KEY_DEMO = "demo";

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoCustomView02Activity.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_custom_view02);

        Demo demo = Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(demo.titleResId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DemoImagePagerAdapter adapter = new DemoImagePagerAdapter();
        adapter.addAll(DemoData.loadImageResourceList());

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout)
                findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithAdapter(new DemoCustomView02Adapter(viewPager));
        recyclerTabLayout.setPositionThreshold(0.5f);
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