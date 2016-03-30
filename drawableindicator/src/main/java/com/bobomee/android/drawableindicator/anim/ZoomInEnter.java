package com.bobomee.android.drawableindicator.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


public class ZoomInEnter extends BaseAnimator {
    public ZoomInEnter() {
        this.mDuration = 200;
    }

    public void setAnimation(View view) {

        float scaleX = view.getScaleX();
        float scaleY = view.getScaleY();
        this.mAnimatorSet.playTogether(new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleX", new float[]{scaleX, scaleX + .5F}),
                ObjectAnimator.ofFloat(view, "scaleY", new float[]{scaleY, scaleY + .5F})});
    }
}
