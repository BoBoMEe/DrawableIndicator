package com.bobomee.android.drawableindicator.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bobomee.android.drawableindicator.anim.BaseAnimator;

/**
 * Created by bobomee on 16/3/30.
 */
public class AnimIndicator extends BaseIndicator {

    private Class<? extends BaseAnimator> mSelectAnimClass;
    private Class<? extends BaseAnimator> mUnselectAnimClass;
    private Class<? extends BaseAnimator> mMovingAnimClass;

    public AnimIndicator(Context context) {
        super(context);
    }

    public AnimIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AnimIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 设置显示器选中动画
     */
    public AnimIndicator setSelectAnimClass(Class<? extends BaseAnimator> selectAnimClass) {
        this.mSelectAnimClass = selectAnimClass;
        return this;
    }

    /**
     * 设置显示器未选中动画
     */
    public AnimIndicator setUnselectAnimClass(Class<? extends BaseAnimator> unselectAnimClass) {
        this.mUnselectAnimClass = unselectAnimClass;
        return this;
    }

    /**
     * 设置移动的View的动画
     */
    public AnimIndicator setMovingAnimClass(Class<? extends BaseAnimator> mMovingAnimClass) {
        this.mMovingAnimClass = mMovingAnimClass;
        return this;
    }

    @Override
    public void onPageSelected(ImageView mSelectedIndicatorView, int position) {
        super.onPageSelected(mSelectedIndicatorView, position);

        ImageView lastView = (ImageView) getChildAt(mLastPositon);
        ImageView currView = (ImageView) getChildAt(mCurrentPositon);

        try {
            if (mUnselectAnimClass != null) {
                mUnselectAnimClass.newInstance().playOn(lastView, false);
            }

            if (mSelectAnimClass != null) {
                mSelectAnimClass.newInstance().playOn(currView, false);
            }

            if (mMovingAnimClass != null) {
                mMovingAnimClass.newInstance().playOn(mSelectedIndicatorView, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
