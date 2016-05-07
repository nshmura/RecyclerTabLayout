package com.nshmura.recyclertablayout.demo.customview03;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.nshmura.recyclertablayout.demo.DemoImagePagerAdapter;
import com.nshmura.recyclertablayout.demo.R;
import com.squareup.picasso.Picasso;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Shinichi Nishimura on 2015/07/22.
 */
public class DemoCustomView03Adapter
        extends RecyclerTabLayout.Adapter<DemoCustomView03Adapter.ViewHolder> {

    private DemoImagePagerAdapter mAdapater;

    public DemoCustomView03Adapter(ViewPager viewPager) {
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
        holder.imageView.setSelected(position == getCurrentIndicatorPosition());

        Picasso.with(holder.imageView.getContext())
                .load("https://assets-cdn.github.com/images/modules/logos_page/GitHub-Mark.png")
                .into(holder.imageView);
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
