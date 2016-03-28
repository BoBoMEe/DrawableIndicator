package com.bobomee.android.drawableindicator.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

        if (null != mIndicatorDrawable) {
            mIndicatorDrawable.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_OVER);
            mIndicatorView.setImageDrawable(mIndicatorDrawable);
        } else {
            super.OnSetIndicatorView(mIndicatorView, position);
        }

    }

    @Override
    public void OnSetSelectedIndicatorView(ImageView mSelectedIndicatorView) {

        if (null != mSelectedIndicatorDrawable) {
            mSelectedIndicatorDrawable.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_OVER);
            mSelectedIndicatorView.setImageDrawable(mSelectedIndicatorDrawable);
        } else {
            super.OnSetSelectedIndicatorView(mSelectedIndicatorView);
        }

    }

    @Override
    public Drawable getmIndicatorDrawable() {
        return mIndicatorDrawable;
    }

    @Override
    public void setmIndicatorDrawable(Drawable mIndicatorDrawable) {
        this.mIndicatorDrawable = mIndicatorDrawable;
        invalidate();
    }

    @Override
    public Drawable getmSelectedIndicatorDrawable() {
        return mSelectedIndicatorDrawable;
    }

    @Override
    public void setmSelectedIndicatorDrawable(Drawable mSelectedIndicatorDrawable) {
        this.mSelectedIndicatorDrawable = mSelectedIndicatorDrawable;
        invalidate();
    }
}
