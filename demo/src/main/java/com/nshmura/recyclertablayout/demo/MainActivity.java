package com.nshmura.recyclertablayout.demo;

import com.nshmura.recyclertablayout.demo.autoselect.DemoAutoSelectActivity;
import com.nshmura.recyclertablayout.demo.basic.DemoBasicActivity;
import com.nshmura.recyclertablayout.demo.customview01.DemoCustomView01Activity;
import com.nshmura.recyclertablayout.demo.customview02.DemoCustomView02Activity;
import com.nshmura.recyclertablayout.demo.imitationloop.DemoImitationLoopActivity;
import com.nshmura.recyclertablayout.demo.rtl.DemoRtlActivity;
import com.nshmura.recyclertablayout.demo.tabonscreenlimit.DemoTabOnScreenLimitActivity;
import com.nshmura.recyclertablayout.demo.tabscrolldisabled.DemoTabScrollDisabledActivity;
import com.nshmura.recyclertablayout.demo.years.DemoYearsActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        for (Demo demo : Demo.values()) {
            adapter.add(getString(demo.titleResId));
        }

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_github:
                openGitHub();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Demo demo = Demo.values()[position];
        switch (demo) {
            case BASIC:
                DemoBasicActivity.startActivity(this, demo);
                break;

            case AUTO_SELECT:
                DemoAutoSelectActivity.startActivity(this, demo);
                break;

            case CUSTOM_VIEW01:
                DemoCustomView01Activity.startActivity(this, demo);
                break;

            case CUSTOM_VIEW02:
                DemoCustomView02Activity.startActivity(this, demo);
                break;

            case YEARS:
                DemoYearsActivity.startActivity(this, demo);
                break;

            case IMITATION_LOOP:
                DemoImitationLoopActivity.startActivity(this, demo);
                break;

            case RTL:
                DemoRtlActivity.startActivity(this, demo);
                break;

            case TAB_SCROLL_DISABLED:
                DemoTabScrollDisabledActivity.startActivity(this, demo);
                break;

            case TAB_ON_SCREEN_LIMIT:
                DemoTabOnScreenLimitActivity.startActivity(this, demo);
                break;
        }
    }

    private void openGitHub() {
        Uri uri = Uri.parse(getString(R.string.app_github_url));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
