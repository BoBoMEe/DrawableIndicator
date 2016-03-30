package com.bobomee.android.drawableindicator_master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bobomee.android.drawableindicator.anim.RotateEnter;
import com.bobomee.android.drawableindicator.anim.ZoomInEnter;
import com.bobomee.android.drawableindicator.widget.AnimIndicator;
import com.bobomee.android.drawableindicator.widget.BaseIndicator;
import com.bobomee.android.drawableindicator_master.adapter.BasePagerAdapter;
import com.bobomee.android.drawableindicator_master.adapter.FragmentStateAdapter;
import com.scrollloopviewpager.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity {

    private static <T extends View> T find(View view, int id) {
        return (T) view.findViewById(id);
    }

    private View decorView;

    private AutoScrollViewPager vp2R;
    private AutoScrollViewPager vp2L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        decorView = getWindow().getDecorView();

        vp2R = find(decorView, R.id.main_vp1);
        vp2R.setAdapter(new FragmentStateAdapter(getSupportFragmentManager()));
        vp2R.setDirection(AutoScrollViewPager.RIGHT);
        vp2R.startAutoScroll();

        vp2L = find(decorView, R.id.main_vp2);
        vp2L.setAdapter(new BasePagerAdapter());
        vp2L.setDirection(AutoScrollViewPager.LEFT);
        vp2L.startAutoScroll();

        initBaseIndicator0();
        initBaseIndicator1();
        initBaseIndicator2();
        initBaseIndicator3();
        initBaseIndicator4();
        initBaseIndicator5();
    }

    private void initBaseIndicator0() {
        BaseIndicator baseIndicator0 = find(decorView, R.id.indicator0);
        baseIndicator0.setViewPager(vp2L);
    }

    private void initBaseIndicator1() {
        BaseIndicator baseIndicator1 = find(decorView, R.id.indicator1);
        baseIndicator1.setViewPager(vp2R);
    }

    private void initBaseIndicator2() {
        BaseIndicator baseIndicator = find(decorView, R.id.indicator2);
        baseIndicator.setViewPager(vp2L);
    }

    private void initBaseIndicator3() {
        BaseIndicator baseIndicator = find(decorView, R.id.indicator3);
        baseIndicator.setViewPager(vp2L);
    }

    private void initBaseIndicator4() {
        BaseIndicator baseIndicator = find(decorView, R.id.indicator4);
        baseIndicator.setViewPager(vp2R);
    }

    private void initBaseIndicator5() {
        AnimIndicator baseIndicator = find(decorView, R.id.indicator5);
        baseIndicator.setUnselectAnimClass(RotateEnter.class)
                .setSelectAnimClass(ZoomInEnter.class)
                .setMovingAnimClass(RotateEnter.class)
        ;
        baseIndicator.setViewPager(vp2R);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
