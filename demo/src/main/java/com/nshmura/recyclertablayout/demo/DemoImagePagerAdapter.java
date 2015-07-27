package com.nshmura.recyclertablayout.demo;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DemoImagePagerAdapter extends PagerAdapter {

    private List<Integer> mItems = new ArrayList<>();

    public DemoImagePagerAdapter() {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_page, container, false);

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText("Page: " + position);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public int getImageResourceId(int position) {
        return mItems.get(position);
    }

    public void addAll(List<Integer> items) {
        mItems = new ArrayList<>(items);
    }
}
