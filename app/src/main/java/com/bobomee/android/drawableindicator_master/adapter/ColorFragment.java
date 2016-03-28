package com.bobomee.android.drawableindicator_master.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by bobomee on 2016/3/28.
 */
public class ColorFragment extends Fragment {

    int i = 0;

    public static ColorFragment newInstance(int i) {
        ColorFragment fragment = new ColorFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("p", i);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        i = getArguments().getInt("p");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TextView textView = new TextView(getContext());

        textView.setGravity(Gravity.CENTER);
        Random random = new Random();
        textView.setBackgroundColor(0xff00ff00 | random.nextInt(0x00ff00ff));
        textView.setText(i + "");

        return textView;

    }
}
