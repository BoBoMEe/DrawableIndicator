package com.bobomee.android.drawableindicator.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bobomee.android.drawableindicator.R;


/**
 * Created by bobomee on 16/3/27.
 */
public class BaseIndicator extends ViewGroup {

    protected static final int GRAVITY_LEFT = 0;
    protected static final int GRAVITY_CENTER = 1;
    protected static final int GRAVITY_RIGHT = 2;

    protected int mIndicatorWidth = 6;
    protected int mIndicatorHeight = 6;
    protected int mIndicatorMargin = 6;
    protected int mIndicatorGravity = GRAVITY_CENTER;

    protected ViewPager mViewPager;
    protected int mIndicatorCount;

    protected Drawable mIndicatorDrawable;
    protected Drawable mSelectedIndicatorDrawable;

    protected boolean mIndicatorIsSnap = true;

    protected int dp2px(float dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5F);
    }

    protected float sp2px(float sp) {
        final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public BaseIndicator(Context context) {
        this(context, null);
    }

    public BaseIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.indicator_style);
    }

    public BaseIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        handleAttrs(context, attrs, defStyleAttr, defStyleRes);
    }

    private void handleAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs == null) return;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseIndicator, defStyleAttr, defStyleRes);

        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.BaseIndicator_indicator_width, dp2px(mIndicatorWidth));
        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.BaseIndicator_indicator_height, dp2px(mIndicatorHeight));
        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.BaseIndicator_indicator_margin, dp2px(mIndicatorMargin));
        mIndicatorGravity = typedArray.getInt(R.styleable.BaseIndicator_indicator_gravity, mIndicatorGravity);
        mIndicatorDrawable = typedArray.getDrawable(R.styleable.BaseIndicator_indicator_unselect_background);
        if (null == mIndicatorDrawable)
            mIndicatorDrawable = new ColorDrawable(0x88ffffff);
        mSelectedIndicatorDrawable = typedArray.getDrawable(R.styleable.BaseIndicator_indicator_select_background);
        if (null == mSelectedIndicatorDrawable)
            mSelectedIndicatorDrawable = new ColorDrawable(0x88a7d84c);
        mIndicatorIsSnap = typedArray.getBoolean(R.styleable.BaseIndicator_indicator_isSnap, mIndicatorIsSnap);

        typedArray.recycle();
    }

    public void setViewPagr(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
        PagerAdapter mPagerAdapter = mViewPager.getAdapter();

        if (null == mPagerAdapter)
            throw new IllegalStateException("have not set the adapter");

        this.mIndicatorCount = mPagerAdapter.getCount();

        initViews();

        initListeners();
    }

    private void initViews() {
        for (int i = 0; i < mIndicatorCount; ++i) {
            ImageView mIndicatorView = new ImageView(getContext());
            LayoutParams params = new LayoutParams(mIndicatorWidth, mIndicatorHeight);
            addView(mIndicatorView, params);
        }
        ImageView mSelectedIndicatorView = new ImageView(getContext());
        LayoutParams params = new LayoutParams(mIndicatorWidth, mIndicatorHeight);
        addView(mSelectedIndicatorView, params);
    }

    private void initListeners() {
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mIndicatorIsSnap) {
                    translate(position, positionOffset);
                }
                BaseIndicator.this.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if (!mIndicatorIsSnap) {
                    translate(position, 0);
                }
                BaseIndicator.this.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                BaseIndicator.this.onPageScrollStateChanged(state);
            }

        });
    }

    private void translate(int position, float positionOffset) {
        View item = getChildAt(position);
        float x = item.getX() + (mIndicatorMargin + mIndicatorWidth) * positionOffset;
        getChildAt(mIndicatorCount).setX(x);
        invalidate();
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void onPageSelected(int position) {
    }

    public void onPageScrollStateChanged(int state) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mViewActualWidth = mIndicatorMargin * (mIndicatorCount - 1) + mIndicatorWidth * mIndicatorCount;
        int mViewWidth = getMeasurement(widthMeasureSpec, mViewActualWidth);
        int mViewHeight = getMeasurement(heightMeasureSpec, mIndicatorHeight);
        setMeasuredDimension(mViewWidth, mViewHeight);

    }

    private int getMeasurement(int measureSpec, int preferred) {
        int specSize = MeasureSpec.getSize(measureSpec);
        int measurement;

        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.EXACTLY:
                measurement = specSize;
                break;
            case MeasureSpec.AT_MOST:
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }
        return measurement;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        if (changed) {
            final int width = getMeasuredWidth();
            final int height = getMeasuredHeight();
            layoutIndicators(width, height);
        }

    }

    private void layoutIndicators(final int containerWidth, final int containerHeight) {
        final int startPosition = startDrawPosition(containerWidth);
        final int yCoordinate = (int) (containerHeight * 0.5f);
        int t = yCoordinate - mIndicatorHeight / 2;
        int b = t + mIndicatorHeight;

        for (int i = 0; i < mIndicatorCount; i++) {

            int l = startPosition + (mIndicatorMargin + mIndicatorWidth) * i;
            int r = l + mIndicatorWidth;
            ImageView mIndicatorView = (ImageView) getChildAt(i);
            OnSetIndicatorView(mIndicatorView, i);
            mIndicatorView.layout(l, t, r, b);
        }

        ImageView mSelectedIndicatorView = (ImageView) getChildAt(mIndicatorCount);
        OnSetSelectedIndicatorView(mSelectedIndicatorView);
        mSelectedIndicatorView.layout(startPosition, t, startPosition + mIndicatorWidth, b);
    }

    private int startDrawPosition(final int containerWidth) {
        if (mIndicatorGravity == GRAVITY_LEFT) {
            return 0;
        }
        float tabItemsLength = mIndicatorCount * (mIndicatorWidth + mIndicatorMargin) - mIndicatorMargin;

        if (containerWidth < tabItemsLength) {
            return 0;
        }
        if (mIndicatorGravity == GRAVITY_CENTER) {
            return (int) ((containerWidth - tabItemsLength) / 2);
        }
        return (int) (containerWidth - tabItemsLength);
    }

    public void OnSetSelectedIndicatorView(ImageView mSelectedIndicatorView) {
        if (null != mSelectedIndicatorDrawable)
            mSelectedIndicatorView.setBackgroundDrawable(mSelectedIndicatorDrawable);
    }

    public void OnSetIndicatorView(ImageView mIndicatorView, int position) {
        if (null != mIndicatorDrawable)
            mIndicatorView.setBackgroundDrawable(mIndicatorDrawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        for (int i = 0; i <= mIndicatorCount; ++i) {
            View view = getChildAt(i);
            canvas.save();
            canvas.translate(view.getX(), view.getY());
            view.getBackground().draw(canvas);
            canvas.restore();
        }

        canvas.restoreToCount(sc);
    }

    public int getmIndicatorWidth() {
        return mIndicatorWidth;
    }

    public void setmIndicatorWidth(int mIndicatorWidth) {
        this.mIndicatorWidth = mIndicatorWidth;
        invalidate();
    }

    public int getmIndicatorHeight() {
        return mIndicatorHeight;
    }

    public void setmIndicatorHeight(int mIndicatorHeight) {
        this.mIndicatorHeight = mIndicatorHeight;
        invalidate();
    }

    public int getmIndicatorMargin() {
        return mIndicatorMargin;
    }

    public void setmIndicatorMargin(int mIndicatorMargin) {
        this.mIndicatorMargin = mIndicatorMargin;
        invalidate();
    }

    public int getmIndicatorGravity() {
        return mIndicatorGravity;
    }

    public void setmIndicatorGravity(int mIndicatorGravity) {
        this.mIndicatorGravity = mIndicatorGravity;
        invalidate();
    }

    public boolean ismIndicatorIsSnap() {
        return mIndicatorIsSnap;
    }

    public void setmIndicatorIsSnap(boolean mIndicatorIsSnap) {
        this.mIndicatorIsSnap = mIndicatorIsSnap;
        invalidate();
    }

    public Drawable getmIndicatorDrawable() {
        return mIndicatorDrawable;
    }

    public void setmIndicatorDrawable(Drawable mIndicatorDrawable) {
        this.mIndicatorDrawable = mIndicatorDrawable;
        invalidate();
    }

    public Drawable getmSelectedIndicatorDrawable() {
        return mSelectedIndicatorDrawable;
    }

    public void setmSelectedIndicatorDrawable(Drawable mSelectedIndicatorDrawable) {
        this.mSelectedIndicatorDrawable = mSelectedIndicatorDrawable;
        invalidate();
    }
}
