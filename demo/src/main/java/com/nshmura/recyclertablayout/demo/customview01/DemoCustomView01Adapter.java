package com.nshmura.recyclertablayout.demo.customview01;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.nshmura.recyclertablayout.demo.ColorItem;
import com.nshmura.recyclertablayout.demo.DemoColorPagerAdapter;
import com.nshmura.recyclertablayout.demo.R;

import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Shinichi Nishimura on 2015/07/22.
 */
public class DemoCustomView01Adapter extends RecyclerTabLayout.Adapter<DemoCustomView01Adapter.ViewHolder> {

    private DemoColorPagerAdapter mAdapater;

    public DemoCustomView01Adapter(ViewPager viewPager) {
        super(viewPager);
        mAdapater = (DemoColorPagerAdapter) mViewPager.getAdapter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_custom_view01_tab, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ColorItem colorItem = mAdapater.getColorItem(position);
        holder.title.setText(colorItem.name);
        holder.color.setBackgroundColor(colorItem.color);

        SpannableString name = new SpannableString(colorItem.name);
        if (position == getCurrentIndicatorPosition()) {
            name.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), 0);
        }
        holder.title.setText(name);
    }

    @Override
    public int getItemCount() {
        return mAdapater.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View color;
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            color = itemView.findViewById(R.id.color);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getViewPager().setCurrentItem(getAdapterPosition());
                }
            });
        }
    }
}
