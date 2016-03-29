package com.bobomee.android.drawableindicator_master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bobomee.android.drawableindicator.widget.BaseIndicator;
import com.bobomee.android.drawableindicator_master.adapter.BasePagerAdapter;
import com.bobomee.android.drawableindicator_master.adapter.FragmentStateAdapter;
import com.scrollloopviewpager.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity {

    private static <T extends View> T find(View view, int id) {
        return (T) view.findViewById(id);
    }

    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        decorView = getWindow().getDecorView();

        initBaseIndicator();
        initBaseIndicator1();
        initBaseIndicator2();
        initBaseIndicator3();

    }

    private void initBaseIndicator() {

        AutoScrollViewPager viewPager = find(decorView, R.id.main_vp);
        BaseIndicator baseIndicator = find(decorView, R.id.indicator);

        viewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager()));
        viewPager.startAutoScroll();
        viewPager.setDirection(0);
        baseIndicator.setViewPager(viewPager);

    }

    private void initBaseIndicator1() {

        AutoScrollViewPager viewPager = find(decorView, R.id.main_vp1);
        BaseIndicator baseIndicator = find(decorView, R.id.indicator1);

        viewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager()));
        viewPager.startAutoScroll();
        baseIndicator.setViewPager(viewPager);

    }

    private void initBaseIndicator2() {

        AutoScrollViewPager viewPager = find(decorView, R.id.main_vp2);
        BaseIndicator baseIndicator = find(decorView, R.id.indicator2);

        viewPager.setAdapter(new BasePagerAdapter());
        viewPager.startAutoScroll();
        baseIndicator.setViewPager(viewPager);

    }

    private void initBaseIndicator3() {

        AutoScrollViewPager viewPager = find(decorView, R.id.main_vp3);
        BaseIndicator baseIndicator = find(decorView, R.id.indicator3);

        viewPager.setAdapter(new BasePagerAdapter());
        viewPager.startAutoScroll();
        baseIndicator.setViewPager(viewPager);

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
