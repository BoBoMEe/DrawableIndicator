package com.bobomee.android.drawableindicator.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bobomee.android.drawableindicator.R;


/**
 * Created by bobomee on 16/3/27.
 */
public class DrawableIndicator extends BaseIndicator {

    protected Drawable mIndicatorDrawable;
    protected Drawable mSelectedIndicatorDrawable;

    public DrawableIndicator(Context context) {
        this(context, null);
    }

    public DrawableIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.drawable_indicator_style);
    }

    public DrawableIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public DrawableIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        handleAttrs(context, attrs, defStyleAttr, defStyleRes);
    }

    private void handleAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs == null) return;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableIndicator, defStyleAttr, defStyleRes);

        mIndicatorDrawable = typedArray.getDrawable(R.styleable.DrawableIndicator_indicator_unselect_src);
        mSelectedIndicatorDrawable = typedArray.getDrawable(R.styleable.DrawableIndicator_indicator_select_src);

        typedArray.recycle();
    }

    @Override
    public void OnSetIndicatorView(ImageView mIndicatorView, int position) {

        super.OnSetIndicatorView(mIndicatorView, position);
        if (null != mIndicatorDrawable) {
            mIndicatorView.setImageDrawable(mIndicatorDrawable);
        }

    }

    @Override
    public void OnSetSelectedIndicatorView(ImageView mSelectedIndicatorView) {

        super.OnSetSelectedIndicatorView(mSelectedIndicatorView);
        if (null != mSelectedIndicatorDrawable) {
            mSelectedIndicatorView.setImageDrawable(mSelectedIndicatorDrawable);
        }

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
