package com.nshmura.recyclertablayout.demo.autoselect;

import com.nshmura.recyclertablayout.demo.Demo;
import com.nshmura.recyclertablayout.demo.basic.DemoBasicActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DemoAutoSelectActivity extends DemoBasicActivity {

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoAutoSelectActivity.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerTabLayout.setAutoSelectionMode(true);
    }
}