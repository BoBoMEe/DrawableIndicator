package com.bobomee.android.drawableindicator.anim;

import android.animation.ObjectAnimator;
import android.view.View;

public class RotateEnter extends BaseAnimator {
    public RotateEnter() {
        this.mDuration = 200;
    }

    public void setAnimation(View view) {

        float rotation = view.getRotation();
        this.mAnimatorSet.play(ObjectAnimator.ofFloat(view, "rotation", rotation, rotation + 180));
    }
}
