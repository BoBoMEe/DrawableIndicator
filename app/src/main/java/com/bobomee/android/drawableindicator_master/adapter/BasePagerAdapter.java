package com.bobomee.android.drawableindicator_master.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

/**
 * Created by bobomee on 16/3/27.
 */
public class BasePagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = new View(container.getContext());
        Random random = new Random();
        view.setBackgroundColor(0xff00ff00 | random.nextInt(0x00ff00ff));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
