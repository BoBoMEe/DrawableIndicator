
# DrawableIndicator_Master

Android drawable indicaotor for viewpager

## ScreenShot

![DrawableIndicator_Master](screen.png "DrawableIndicator")

## Usage

### The Most Complex
in layout xml

``` xml
<com.bobomee.android.drawableindicator.widget.DrawableIndicator
                android:id="@+id/indicator1"
                android:layout_width="match_parent"
                android:layout_height="30dp"
                app:indicator_gravity="right"
                app:indicator_height="8dp"
                app:indicator_margin="8dp"
                app:indicator_select_background="#00000000"
                app:indicator_select_src="@drawable/banner_dot_select"
                app:indicator_unselect_background="#00000000"
                app:indicator_unselect_src="@drawable/banner_dot_unselect"
                app:indicator_isSnap="true"
                app:indicator_width="8dp" />
```

int Java Code

``` Java
private static <T extends View> T find(View view, int id) {
        return (T) view.findViewById(id);
    }
private void initBaseIndicator1() {
        AutoScrollViewPager viewPager = find(decorView, R.id.main_vp1);
        BaseIndicator baseIndicator = find(decorView, R.id.indicator1);
        viewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager()));
        viewPager.startAutoScroll();
        baseIndicator.setViewPagr(viewPager);
    }
```

### The Simplest
in layout xml

``` xml
<com.scrollloopviewpager.autoscrollviewpager.AutoScrollViewPager
                android:id="@+id/main_vp3"
                android:layout_width="match_parent"
                android:layout_height="150dp" />
            <com.bobomee.android.drawableindicator.widget.DrawableIndicator
                android:id="@+id/indicator3"
                android:layout_width="match_parent"
                android:layout_height="30dp" />
```

int Java Code

``` Java
 private void initBaseIndicator3() {
        AutoScrollViewPager viewPager = find(decorView, R.id.main_vp3);
        BaseIndicator baseIndicator = find(decorView, R.id.indicator3);
        viewPager.setAdapter(new BasePagerAdapter());
        viewPager.startAutoScroll();
        baseIndicator.setViewPagr(viewPager);
    }
```

## Attributes

|name|format|description|
|:---:|:---:|:---:|
| indicator_width | dimension |the width of the indicator
| indicator_height | dimension |the height of the indicator
| indicator_margin | dimension |the margin between two indicator
| indicator_gravity | enum |Gravity.CENTER or RIGHT or LEFT,default CENTER
| indicator_select_background | reference or color |indicator select background drawable 
| indicator_unselect_background | reference or color |indicator unselect background drawable 
| indicator_isSnap | boolean | draw offset or not,default true
| indicator_select_src | reference or color |indicator select src drawable
| indicator_unselect_src | reference or color |indicator unselect src drawable


## Thanks

*   [imbryk/LoopingViewPager](https://github.com/imbryk/LoopingViewPager)
*   [Trinea/android-auto-scroll-view-pager](https://github.com/Trinea/android-auto-scroll-view-pager)

