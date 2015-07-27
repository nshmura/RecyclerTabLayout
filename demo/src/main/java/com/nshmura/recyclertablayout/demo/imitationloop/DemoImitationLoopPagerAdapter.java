package com.nshmura.recyclertablayout.demo.imitationloop;

import com.nshmura.recyclertablayout.demo.R;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinichi Nishimura on 2015/07/24.
 */
public class DemoImitationLoopPagerAdapter extends PagerAdapter {

    private static final int NUMBER_OF_LOOPS = 10000;

    private List<String> mItems = new ArrayList<>();

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_page, container, false);

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText("Page: " + getValueAt(position));
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mItems.size() * NUMBER_OF_LOOPS;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public String getPageTitle(int position) {
        return getValueAt(position);
    }

    public void addAll(List<String> items) {
        mItems = new ArrayList<>(items);
    }

    public int getCenterPosition(int position) {
        return mItems.size() * NUMBER_OF_LOOPS / 2 + position;
    }

    public String getValueAt(int position) {
        if (mItems.size() == 0) {
            return null;
        }
        return mItems.get(position % mItems.size());
    }
}
