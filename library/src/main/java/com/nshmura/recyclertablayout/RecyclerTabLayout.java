/**
 * Copyright (C) 2015 nshmura
 * Copyright (C) 2015 The Android Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nshmura.recyclertablayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerTabLayout extends RecyclerView {

    protected static final long DEFAULT_SCROLL_DURATION = 200;
    protected static final float DEFAULT_POSITION_THRESHOLD = 0.6f;
    protected static final float POSITION_THRESHOLD_ALLOWABLE = 0.001f;

    protected Paint mIndicatorPaint;
    protected int mTabBackgroundResId;
    protected int mTabOnScreenLimit;
    protected int mTabMinWidth;
    protected int mTabMaxWidth;
    protected int mTabTextAppearance;
    protected int mTabSelectedTextAppearance;
    protected int mTabSelectedTextColor;
    protected boolean mTabSelectedTextColorSet;
    protected int mTabPaddingStart;
    protected int mTabPaddingTop;
    protected int mTabPaddingEnd;
    protected int mTabPaddingBottom;
    protected int mIndicatorHeight;

    protected LinearLayoutManager mLinearLayoutManager;
    protected RecyclerOnScrollListener mRecyclerOnScrollListener;
    protected ViewPager mViewPager;
    protected Adapter<?> mAdapter;

    protected int mIndicatorPosition;
    protected int mIndicatorGap;
    protected int mIndicatorScroll;
    protected float mOldPositionOffset;
    protected float mPositionThreshold;
    protected boolean mRequestScrollToTab;
    protected boolean mScrollEanbled;
    private int mOldPosition;
    private int mOldScrollOffset;

    public RecyclerTabLayout(Context context) {
        this(context, null);
    }

    public RecyclerTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        mIndicatorPaint = new Paint();
        getAttributes(context, attrs, defStyle);
        mLinearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollHorizontally() {
                return mScrollEanbled;
            }
        };
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        setLayoutManager(mLinearLayoutManager);
        setItemAnimator(null);
        mPositionThreshold = DEFAULT_POSITION_THRESHOLD;
    }

    private void getAttributes(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.rtl_RecyclerTabLayout,
                defStyle, R.style.rtl_RecyclerTabLayout);
        setIndicatorColor(a.getColor(R.styleable
                .rtl_RecyclerTabLayout_rtl_tabIndicatorColor, 0));
        setIndicatorHeight(a.getDimensionPixelSize(R.styleable
                .rtl_RecyclerTabLayout_rtl_tabIndicatorHeight, 0));

        mTabTextAppearance = a.getResourceId(R.styleable.rtl_RecyclerTabLayout_rtl_tabTextAppearance,
                R.style.rtl_RecyclerTabLayout_Tab);

        mTabSelectedTextAppearance = a.getResourceId(R.styleable.rtl_RecyclerTabLayout_rtl_tabSelectedTextAppearance,
                R.style.rtl_RecyclerTabLayout_Tab);

        mTabPaddingStart = mTabPaddingTop = mTabPaddingEnd = mTabPaddingBottom = a
                .getDimensionPixelSize(R.styleable.rtl_RecyclerTabLayout_rtl_tabPadding, 0);
        mTabPaddingStart = a.getDimensionPixelSize(
                R.styleable.rtl_RecyclerTabLayout_rtl_tabPaddingStart, mTabPaddingStart);
        mTabPaddingTop = a.getDimensionPixelSize(
                R.styleable.rtl_RecyclerTabLayout_rtl_tabPaddingTop, mTabPaddingTop);
        mTabPaddingEnd = a.getDimensionPixelSize(
                R.styleable.rtl_RecyclerTabLayout_rtl_tabPaddingEnd, mTabPaddingEnd);
        mTabPaddingBottom = a.getDimensionPixelSize(
                R.styleable.rtl_RecyclerTabLayout_rtl_tabPaddingBottom, mTabPaddingBottom);

        if (a.hasValue(R.styleable.rtl_RecyclerTabLayout_rtl_tabSelectedTextColor)) {
            mTabSelectedTextColor = a
                    .getColor(R.styleable.rtl_RecyclerTabLayout_rtl_tabSelectedTextColor, 0);
            mTabSelectedTextColorSet = true;
        }

        mTabOnScreenLimit = a.getInteger(
                R.styleable.rtl_RecyclerTabLayout_rtl_tabOnScreenLimit, 0);
        if (mTabOnScreenLimit == 0) {
            mTabMinWidth = a.getDimensionPixelSize(
                    R.styleable.rtl_RecyclerTabLayout_rtl_tabMinWidth, 0);
            mTabMaxWidth = a.getDimensionPixelSize(
                    R.styleable.rtl_RecyclerTabLayout_rtl_tabMaxWidth, 0);
        }

        mTabBackgroundResId = a
                .getResourceId(R.styleable.rtl_RecyclerTabLayout_rtl_tabBackground, 0);
        mScrollEanbled = a.getBoolean(R.styleable.rtl_RecyclerTabLayout_rtl_scrollEnabled, true);
        a.recycle();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mRecyclerOnScrollListener != null) {
            removeOnScrollListener(mRecyclerOnScrollListener);
            mRecyclerOnScrollListener = null;
        }
        super.onDetachedFromWindow();
    }


    public void setIndicatorColor(int color) {
        mIndicatorPaint.setColor(color);
    }

    public void setIndicatorHeight(int indicatorHeight) {
        mIndicatorHeight = indicatorHeight;
    }

    public void setAutoSelectionMode(boolean autoSelect) {
        if (mRecyclerOnScrollListener != null) {
            removeOnScrollListener(mRecyclerOnScrollListener);
            mRecyclerOnScrollListener = null;
        }
        if (autoSelect) {
            mRecyclerOnScrollListener = new RecyclerOnScrollListener(this, mLinearLayoutManager);
            addOnScrollListener(mRecyclerOnScrollListener);
        }
    }

    public void setPositionThreshold(float positionThreshold) {
        mPositionThreshold = positionThreshold;
    }

    public void setUpWithViewPager(ViewPager viewPager) {
        DefaultAdapter adapter = new DefaultAdapter(viewPager);
        adapter.setTabPadding(mTabPaddingStart, mTabPaddingTop, mTabPaddingEnd, mTabPaddingBottom);
        adapter.setTabTextAppearance(mTabTextAppearance);
        adapter.setTabSelectedTextAppearance(mTabSelectedTextAppearance);
        adapter.setTabSelectedTextColor(mTabSelectedTextColorSet, mTabSelectedTextColor);
        adapter.setTabMaxWidth(mTabMaxWidth);
        adapter.setTabMinWidth(mTabMinWidth);
        adapter.setTabBackgroundResId(mTabBackgroundResId);
        adapter.setTabOnScreenLimit(mTabOnScreenLimit);
        setUpWithAdapter(adapter);
    }

    public void setUpWithAdapter(RecyclerTabLayout.Adapter<?> adapter) {
        mAdapter = adapter;
        mViewPager = adapter.getViewPager();
        if (mViewPager.getAdapter() == null) {
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        }
        mViewPager.addOnPageChangeListener(new ViewPagerOnPageChangeListener(this));
        setAdapter(adapter);
        scrollToTab(mViewPager.getCurrentItem());
    }

    public void setCurrentItem(int position, boolean smoothScroll) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position, smoothScroll);
            scrollToTab(mViewPager.getCurrentItem());
            return;
        }

        if (smoothScroll && position != mIndicatorPosition) {
            startAnimation(position);

        } else {
            scrollToTab(position);
        }
    }

    protected void startAnimation(final int position) {

        float distance = 1;

        View view = mLinearLayoutManager.findViewByPosition(position);
        if (view != null) {
            float currentX = view.getX() + view.getMeasuredWidth() / 2.f;
            float centerX = getMeasuredWidth() / 2.f;
            distance = Math.abs(centerX - currentX) / view.getMeasuredWidth();
        }

        ValueAnimator animator;
        if (position < mIndicatorPosition) {
            animator = ValueAnimator.ofFloat(distance, 0);
        } else {
            animator = ValueAnimator.ofFloat(-distance, 0);
        }
        animator.setDuration(DEFAULT_SCROLL_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scrollToTab(position, (float) animation.getAnimatedValue(), true);
            }
        });
        animator.start();
    }

    protected void scrollToTab(int position) {
        scrollToTab(position, 0, false);
        mAdapter.setCurrentIndicatorPosition(position);
        mAdapter.notifyDataSetChanged();
    }

    protected void scrollToTab(int position, float positionOffset, boolean fitIndicator) {
        int scrollOffset = 0;

        View selectedView = mLinearLayoutManager.findViewByPosition(position);
        View nextView = mLinearLayoutManager.findViewByPosition(position + 1);

        if (selectedView != null) {
            int width = getMeasuredWidth();
            float sLeft = (position == 0) ? 0 : width / 2.f - selectedView.getMeasuredWidth() / 2.f; // left edge of selected tab
            float sRight = sLeft + selectedView.getMeasuredWidth(); // right edge of selected tab

            if (nextView != null) {
                float nLeft = width / 2.f - nextView.getMeasuredWidth() / 2.f; // left edge of next tab
                float distance = sRight - nLeft; // total distance that is needed to distance to next tab
                float dx = distance * positionOffset;
                scrollOffset = (int) (sLeft - dx);

                if (position == 0) {
                    float indicatorGap = (nextView.getMeasuredWidth() - selectedView.getMeasuredWidth()) / 2;
                    mIndicatorGap = (int) (indicatorGap * positionOffset);
                    mIndicatorScroll = (int) ((selectedView.getMeasuredWidth() + indicatorGap) * positionOffset);

                } else {
                    float indicatorGap = (nextView.getMeasuredWidth() - selectedView.getMeasuredWidth()) / 2;
                    mIndicatorGap = (int) (indicatorGap * positionOffset);
                    mIndicatorScroll = (int) dx;
                }

            } else {
                scrollOffset = (int) sLeft;
                mIndicatorScroll = 0;
                mIndicatorGap = 0;
            }
            if (fitIndicator) {
                mIndicatorScroll = 0;
                mIndicatorGap = 0;
            }

        } else {
            if (getMeasuredWidth() > 0 && mTabMaxWidth > 0 && mTabMinWidth == mTabMaxWidth) { //fixed size
                int width = mTabMinWidth;
                int offset = (int) (positionOffset * -width);
                int leftOffset = (int) ((getMeasuredWidth() - width) / 2.f);
                scrollOffset = offset + leftOffset;
            }
            mRequestScrollToTab = true;
        }

        updateCurrentIndicatorPosition(position, positionOffset - mOldPositionOffset, positionOffset);
        mIndicatorPosition = position;

        stopScroll();

        if (position != mOldPosition || scrollOffset != mOldScrollOffset) {
            mLinearLayoutManager.scrollToPositionWithOffset(position, scrollOffset);
        }
        if (mIndicatorHeight > 0) {
            invalidate();
        }

        mOldPosition = position;
        mOldScrollOffset = scrollOffset;
        mOldPositionOffset = positionOffset;
    }

    protected void updateCurrentIndicatorPosition(int position, float dx, float positionOffset) {
        if (mAdapter == null) {
            return;
        }
        int indicatorPosition = -1;
        if (dx > 0 && positionOffset >= mPositionThreshold - POSITION_THRESHOLD_ALLOWABLE) {
            indicatorPosition = position + 1;

        } else if (dx < 0 && positionOffset <= 1 - mPositionThreshold + POSITION_THRESHOLD_ALLOWABLE) {
            indicatorPosition = position;
        }
        if (indicatorPosition >= 0 && indicatorPosition != mAdapter.getCurrentIndicatorPosition()) {
            mAdapter.setCurrentIndicatorPosition(indicatorPosition);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        View view = mLinearLayoutManager.findViewByPosition(mIndicatorPosition);
        if (view == null) {
            if (mRequestScrollToTab) {
                mRequestScrollToTab = false;
                scrollToTab(mViewPager.getCurrentItem());
            }
            return;
        }
        mRequestScrollToTab = false;

        int left;
        int right;
        if (isLayoutRtl()) {
            left = view.getLeft() - mIndicatorScroll - mIndicatorGap;
            right = view.getRight() - mIndicatorScroll + mIndicatorGap;
        } else {
            left = view.getLeft() + mIndicatorScroll - mIndicatorGap;
            right = view.getRight() + mIndicatorScroll + mIndicatorGap;
        }

        int top = getHeight() - mIndicatorHeight;
        int bottom = getHeight();

        canvas.drawRect(left, top, right, bottom, mIndicatorPaint);
    }

    protected boolean isLayoutRtl() {
        return ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

    protected static class RecyclerOnScrollListener extends OnScrollListener {

        public int mDx;
        protected RecyclerTabLayout mRecyclerTabLayout;
        protected LinearLayoutManager mLinearLayoutManager;

        public RecyclerOnScrollListener(RecyclerTabLayout recyclerTabLayout,
                                        LinearLayoutManager linearLayoutManager) {
            mRecyclerTabLayout = recyclerTabLayout;
            mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            mDx += dx;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            switch (newState) {
                case SCROLL_STATE_IDLE:
                    if (mDx > 0) {
                        selectCenterTabForRightScroll();
                    } else {
                        selectCenterTabForLeftScroll();
                    }
                    mDx = 0;
                    break;
                case SCROLL_STATE_DRAGGING:
                case SCROLL_STATE_SETTLING:
            }
        }

        protected void selectCenterTabForRightScroll() {
            int first = mLinearLayoutManager.findFirstVisibleItemPosition();
            int last = mLinearLayoutManager.findLastVisibleItemPosition();
            int center = mRecyclerTabLayout.getWidth() / 2;
            for (int position = first; position <= last; position++) {
                View view = mLinearLayoutManager.findViewByPosition(position);
                if (view.getLeft() + view.getWidth() >= center) {
                    mRecyclerTabLayout.setCurrentItem(position, false);
                    break;
                }
            }
        }

        protected void selectCenterTabForLeftScroll() {
            int first = mLinearLayoutManager.findFirstVisibleItemPosition();
            int last = mLinearLayoutManager.findLastVisibleItemPosition();
            int center = mRecyclerTabLayout.getWidth() / 2;
            for (int position = last; position >= first; position--) {
                View view = mLinearLayoutManager.findViewByPosition(position);
                if (view.getLeft() <= center) {
                    mRecyclerTabLayout.setCurrentItem(position, false);
                    break;
                }
            }
        }
    }

    protected static class ViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private final RecyclerTabLayout mRecyclerTabLayout;
        private int mScrollState;

        public ViewPagerOnPageChangeListener(RecyclerTabLayout recyclerTabLayout) {
            mRecyclerTabLayout = recyclerTabLayout;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mRecyclerTabLayout.scrollToTab(position, positionOffset, false);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                if (mRecyclerTabLayout.mIndicatorPosition != position) {
                    mRecyclerTabLayout.scrollToTab(position);
                }
            }
        }
    }

    public static abstract class Adapter<T extends RecyclerView.ViewHolder>
            extends RecyclerView.Adapter<T> {

        protected ViewPager mViewPager;
        protected int mIndicatorPosition;

        public Adapter(ViewPager viewPager) {
            mViewPager = viewPager;
        }

        public ViewPager getViewPager() {
            return mViewPager;
        }

        public int getCurrentIndicatorPosition() {
            return mIndicatorPosition;
        }

        public void setCurrentIndicatorPosition(int indicatorPosition) {
            mIndicatorPosition = indicatorPosition;
        }
    }

    public static class DefaultAdapter
            extends RecyclerTabLayout.Adapter<DefaultAdapter.ViewHolder> {

        protected static final int MAX_TAB_TEXT_LINES = 2;

        protected int mTabPaddingStart;
        protected int mTabPaddingTop;
        protected int mTabPaddingEnd;
        protected int mTabPaddingBottom;
        protected int mTabTextAppearance;
        protected int mTabSelectedTextAppearance;
        protected boolean mTabSelectedTextColorSet;
        protected int mTabSelectedTextColor;
        private int mTabMaxWidth;
        private int mTabMinWidth;
        private int mTabBackgroundResId;
        private int mTabOnScreenLimit;

        public DefaultAdapter(ViewPager viewPager) {
            super(viewPager);
        }

        @SuppressWarnings("deprecation")
        @Override
        public DefaultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TabTextView tabTextView = new TabTextView(parent.getContext());

            if (mTabSelectedTextColorSet) {
                tabTextView.setTextColor(tabTextView.createColorStateList(
                        tabTextView.getCurrentTextColor(), mTabSelectedTextColor));
            }

            ViewCompat.setPaddingRelative(tabTextView, mTabPaddingStart, mTabPaddingTop,
                    mTabPaddingEnd, mTabPaddingBottom);
            tabTextView.setTextAppearance(parent.getContext(), mTabTextAppearance);
            tabTextView.setGravity(Gravity.CENTER);
            tabTextView.setMaxLines(MAX_TAB_TEXT_LINES);
            tabTextView.setEllipsize(TextUtils.TruncateAt.END);

            if (mTabOnScreenLimit > 0) {
                int width = parent.getMeasuredWidth() / mTabOnScreenLimit;
                tabTextView.setMaxWidth(width);
                tabTextView.setMinWidth(width);

            } else {
                if (mTabMaxWidth > 0) {
                    tabTextView.setMaxWidth(mTabMaxWidth);
                }
                tabTextView.setMinWidth(mTabMinWidth);
            }

            tabTextView.setTextAppearance(tabTextView.getContext(), mTabTextAppearance);
            if (mTabSelectedTextColorSet) {
                tabTextView.setTextColor(tabTextView.createColorStateList(
                        tabTextView.getCurrentTextColor(), mTabSelectedTextColor));

            }
            if (mTabBackgroundResId != 0) {
                tabTextView.setBackgroundDrawable(
                        AppCompatResources.getDrawable(tabTextView.getContext(), mTabBackgroundResId));
            }
            tabTextView.setLayoutParams(createLayoutParamsForTabs());
            return new ViewHolder(tabTextView);
        }

        @Override
        public void onBindViewHolder(DefaultAdapter.ViewHolder holder, int position) {
            CharSequence title = getViewPager().getAdapter().getPageTitle(position);
            holder.title.setText(title);
            holder.title.setSelected(getCurrentIndicatorPosition() == position);
            holder.title.setTextAppearance(holder.title.getContext(),
                    getCurrentIndicatorPosition() == position ?
                            mTabSelectedTextAppearance : mTabTextAppearance);
        }

        @Override
        public int getItemCount() {
            return getViewPager().getAdapter().getCount();
        }

        public void setTabPadding(int tabPaddingStart, int tabPaddingTop, int tabPaddingEnd,
                                  int tabPaddingBottom) {
            mTabPaddingStart = tabPaddingStart;
            mTabPaddingTop = tabPaddingTop;
            mTabPaddingEnd = tabPaddingEnd;
            mTabPaddingBottom = tabPaddingBottom;
        }

        public void setTabTextAppearance(int tabTextAppearance) {
            mTabTextAppearance = tabTextAppearance;
        }

        public void setTabSelectedTextAppearance(int tabSelectedTextAppearance) {
            mTabSelectedTextAppearance = tabSelectedTextAppearance;
        }

        public void setTabSelectedTextColor(boolean tabSelectedTextColorSet,
                                            int tabSelectedTextColor) {
            mTabSelectedTextColorSet = tabSelectedTextColorSet;
            mTabSelectedTextColor = tabSelectedTextColor;
        }

        public void setTabMaxWidth(int tabMaxWidth) {
            mTabMaxWidth = tabMaxWidth;
        }

        public void setTabMinWidth(int tabMinWidth) {
            mTabMinWidth = tabMinWidth;
        }

        public void setTabBackgroundResId(int tabBackgroundResId) {
            mTabBackgroundResId = tabBackgroundResId;
        }

        public void setTabOnScreenLimit(int tabOnScreenLimit) {
            mTabOnScreenLimit = tabOnScreenLimit;
        }

        protected RecyclerView.LayoutParams createLayoutParamsForTabs() {
            return new RecyclerView.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView title;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        if (pos != NO_POSITION) {
                            getViewPager().setCurrentItem(pos, true);
                        }
                    }
                });
            }
        }
    }


    public static class TabTextView extends AppCompatTextView {

        public TabTextView(Context context) {
            super(context);
        }

        public ColorStateList createColorStateList(int defaultColor, int selectedColor) {
            final int[][] states = new int[2][];
            final int[] colors = new int[2];
            states[0] = SELECTED_STATE_SET;
            colors[0] = selectedColor;
            // Default enabled state
            states[1] = EMPTY_STATE_SET;
            colors[1] = defaultColor;
            return new ColorStateList(states, colors);
        }
    }
}
