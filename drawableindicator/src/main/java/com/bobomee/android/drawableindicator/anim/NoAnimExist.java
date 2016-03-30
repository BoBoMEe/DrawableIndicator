package com.bobomee.android.drawableindicator.anim;

import android.animation.ObjectAnimator;
import android.view.View;

public class NoAnimExist extends BaseAnimator {
    public NoAnimExist() {
        this.mDuration = 200;
    }

    public void setAnimation(View view) {
        this.mAnimatorSet.play(ObjectAnimator.ofFloat(view, "alpha", 1, 1));
    }
}
