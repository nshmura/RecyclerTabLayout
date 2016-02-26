package com.nshmura.recyclertablayout.demo.customview02;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.nshmura.recyclertablayout.demo.DemoImagePagerAdapter;
import com.nshmura.recyclertablayout.demo.R;

/**
 * Created by Shinichi Nishimura on 2015/07/22.
 */
public class DemoCustomView02Adapter
        extends RecyclerTabLayout.Adapter<DemoCustomView02Adapter.ViewHolder> {

    private DemoImagePagerAdapter mAdapater;

    public DemoCustomView02Adapter(ViewPager viewPager) {
        super(viewPager);
        mAdapater = (DemoImagePagerAdapter) mViewPager.getAdapter();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_custom_view02_tab, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Drawable drawable = loadIconWithTint(holder.imageView.getContext(),
                mAdapater.getImageResourceId(position));

        holder.imageView.setImageDrawable(drawable);
        holder.imageView.setSelected(position == getCurrentIndicatorPosition());
    }

    private Drawable loadIconWithTint(Context context, @IdRes int resourceId) {
        Drawable icon = ContextCompat.getDrawable(context, resourceId);
        ColorStateList colorStateList = ContextCompat
                .getColorStateList(context,R.color.custom_view02_tint);
        icon = DrawableCompat.wrap(icon);
        DrawableCompat.setTintList(icon, colorStateList);
        return icon;
    }

    @Override
    public int getItemCount() {
        return mAdapater.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getViewPager().setCurrentItem(getAdapterPosition());
                }
            });
        }
    }
}
