package com.bobomee.android.drawableindicator.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import com.bobomee.android.drawableindicator.R;

/**
 * Created on 16/3/27.下午4:20.
 * @author bobomee.
 */
public class BaseIndicator extends ViewGroup {

  protected int mIndicatorWidth = 6;
  protected int mIndicatorHeight = 6;
  protected int mIndicatorMargin = 6;
  protected int mIndicatorGravity = Gravity.CENTER;

  protected ViewPager mViewPager;
  protected int mIndicatorCount;

  protected Drawable mIndicatorBackgroundDrawable;
  protected Drawable mMovingIndicatorBackgroundDrawable;
  private ImageView mMovingIndicatorView;

  protected Drawable mIndicatorSrcDrawable;
  protected Drawable mMovingIndicatorSrcDrawable;

  protected int mCurrentPositon;
  protected int mLastPositon;

  protected boolean mIndicatorIsSnap = true;

  private Interpolator mStartInterpolator = new AccelerateDecelerateInterpolator();

  private OnIndicatorClickListener mOnIndicatorClickListener;

  protected int dp2px(float dp) {
    float scale = getContext().getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5F);
  }

  protected float sp2px(float sp) {
    final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
    return sp * scale;
  }

  public BaseIndicator(Context context) {
    this(context, null);
  }

  public BaseIndicator(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.indicator_style);
  }

  public BaseIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr, 0);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public BaseIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs, defStyleAttr, defStyleRes);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    handleAttrs(context, attrs, defStyleAttr, defStyleRes);
    mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
  }

  private void handleAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    if (attrs == null) return;

    TypedArray typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.BaseIndicator, defStyleAttr, defStyleRes);

    mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.BaseIndicator_indicator_width,
        dp2px(mIndicatorWidth));
    mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.BaseIndicator_indicator_height,
        dp2px(mIndicatorHeight));
    mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.BaseIndicator_indicator_margin,
        dp2px(mIndicatorMargin));
    mIndicatorGravity =
        typedArray.getInt(R.styleable.BaseIndicator_indicator_gravity, mIndicatorGravity);
    mIndicatorBackgroundDrawable =
        typedArray.getDrawable(R.styleable.BaseIndicator_indicator_background);
    if (null == mIndicatorBackgroundDrawable) {
      mIndicatorBackgroundDrawable = new ColorDrawable(0x88ffffff);
    }
    mMovingIndicatorBackgroundDrawable =
        typedArray.getDrawable(R.styleable.BaseIndicator_indicator_moving_background);
    if (null == mMovingIndicatorBackgroundDrawable) {
      mMovingIndicatorBackgroundDrawable = new ColorDrawable(0x88a7d84c);
    }
    mIndicatorSrcDrawable = typedArray.getDrawable(R.styleable.BaseIndicator_indicator_src);
    mMovingIndicatorSrcDrawable =
        typedArray.getDrawable(R.styleable.BaseIndicator_indicator_moving_src);
    mIndicatorIsSnap =
        typedArray.getBoolean(R.styleable.BaseIndicator_indicator_isSnap, mIndicatorIsSnap);

    typedArray.recycle();
  }

  public void setViewPager(@NonNull ViewPager mViewPager) {
    this.mViewPager = mViewPager;
    PagerAdapter mPagerAdapter = mViewPager.getAdapter();

    if (null != mPagerAdapter) {
      this.mIndicatorCount = mPagerAdapter.getCount();

      initViews();

      initListeners();
    }
  }

  private void initViews() {
    for (int i = 0; i < mIndicatorCount; ++i) {
      ImageView mIndicatorView = new ImageView(getContext());
      LayoutParams params = new LayoutParams(mIndicatorWidth, mIndicatorHeight);
      addView(mIndicatorView, params);
    }
    if (mIndicatorCount > 0) {
      mMovingIndicatorView = new ImageView(getContext());
      LayoutParams params = new LayoutParams(mIndicatorWidth, mIndicatorHeight);
      addView(mMovingIndicatorView, params);
    }
  }

  private void initListeners() {
    mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != mMovingIndicatorView) {

          if (mIndicatorIsSnap) {
            translate(mMovingIndicatorView, position, positionOffset);
          }
          BaseIndicator.this.onPageScrolled(mMovingIndicatorView, position, positionOffset,
              positionOffsetPixels);
        }
      }

      @Override public void onPageSelected(int position) {
        mCurrentPositon = position;
        if (null != mMovingIndicatorView) {

          if (!mIndicatorIsSnap) {
            translate(mMovingIndicatorView, position, 0);
          }
          BaseIndicator.this.onPageSelected(mMovingIndicatorView, position);
        }
        mLastPositon = mCurrentPositon;
      }
    });
  }

  private void translate(@NonNull View mSelectedIndicatorView, int position, float positionOffset) {
    View item = getChildAt(position);
    float x =
        item.getX() + (mIndicatorMargin + mIndicatorWidth) * mStartInterpolator.getInterpolation(
            positionOffset);

    mSelectedIndicatorView.setX(x);
  }

  public void onPageScrolled(ImageView mSelectedIndicatorView, int position, float positionOffset,
      int positionOffsetPixels) {
  }

  public void onPageSelected(ImageView mSelectedIndicatorView, int position) {
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int mViewActualWidth =
        mIndicatorMargin * (mIndicatorCount - 1) + mIndicatorWidth * mIndicatorCount;
    int mViewWidth = getMeasurement(widthMeasureSpec, mViewActualWidth);
    int mViewHeight = getMeasurement(heightMeasureSpec, mIndicatorHeight);
    setMeasuredDimension(mViewWidth, mViewHeight);
  }

  private int getMeasurement(int measureSpec, int preferred) {
    int specSize = MeasureSpec.getSize(measureSpec);
    int measurement;

    switch (MeasureSpec.getMode(measureSpec)) {
      case MeasureSpec.EXACTLY:
        measurement = specSize;
        break;
      case MeasureSpec.AT_MOST:
        measurement = Math.min(preferred, specSize);
        break;
      default:
        measurement = preferred;
        break;
    }
    return measurement;
  }

  @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    if (changed) {
      final int width = getMeasuredWidth();
      final int height = getMeasuredHeight();
      layoutIndicators(width, height);
    }
  }

  private void layoutIndicators(final int containerWidth, final int containerHeight) {
    final int startPosition = startDrawPosition(containerWidth);
    final int yCoordinate = (int) (containerHeight * 0.5f);
    int t = yCoordinate - mIndicatorHeight / 2;
    int b = t + mIndicatorHeight;

    for (int i = 0; i < mIndicatorCount; i++) {
      int l = startPosition + (mIndicatorMargin + mIndicatorWidth) * i;
      int r = l + mIndicatorWidth;
      ImageView mIndicatorView = (ImageView) getChildAt(i);
      if (null != mIndicatorView) {
        OnSetIndicatorView(mIndicatorView, i);
        mIndicatorView.layout(l, t, r, b);
      }
    }

    if (mIndicatorCount > 0) {
      ImageView mSelectedIndicatorView = (ImageView) getChildAt(mIndicatorCount);
      if (null != mSelectedIndicatorView) {
        OnSetSelectedIndicatorView(mSelectedIndicatorView);
        mSelectedIndicatorView.layout(startPosition, t, startPosition + mIndicatorWidth, b);
      }
    }
  }

  private int startDrawPosition(final int containerWidth) {
    if (mIndicatorGravity == Gravity.LEFT) {
      return 0;
    }
    float tabItemsLength =
        mIndicatorCount * (mIndicatorWidth + mIndicatorMargin) - mIndicatorMargin;

    if (containerWidth < tabItemsLength) {
      return 0;
    }
    if (mIndicatorGravity == Gravity.CENTER) {
      return (int) ((containerWidth - tabItemsLength) / 2);
    }
    return (int) (containerWidth - tabItemsLength);
  }

  public void OnSetSelectedIndicatorView(@NonNull ImageView mSelectedIndicatorView) {
    if (null != mMovingIndicatorBackgroundDrawable) {
      mSelectedIndicatorView.setBackgroundDrawable(mMovingIndicatorBackgroundDrawable);
    }
    if (null != mMovingIndicatorSrcDrawable) {
      mSelectedIndicatorView.setImageDrawable(mMovingIndicatorSrcDrawable);
    }
  }

  public void OnSetIndicatorView(@NonNull ImageView mIndicatorView, int position) {
    if (null != mIndicatorBackgroundDrawable) {
      mIndicatorView.setBackgroundDrawable(mIndicatorBackgroundDrawable);
    }
    if (null != mIndicatorSrcDrawable) {
      mIndicatorView.setImageDrawable(mIndicatorSrcDrawable);
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG |
        Canvas.CLIP_SAVE_FLAG |
        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
    for (int i = 0; i <= mIndicatorCount; ++i) {
      View view = getChildAt(i);
      canvas.save();
      canvas.translate(view.getX(), view.getY());
      view.getBackground().draw(canvas);
      canvas.restore();
    }

    canvas.restoreToCount(sc);
  }

  private float mDownX;
  private float mDownY;
  private boolean mTouchable;
  private int mTouchSlop;

  @Override public boolean onTouchEvent(MotionEvent event) {
    float x = event.getX();
    float y = event.getY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        if (mTouchable) {
          mDownX = x;
          mDownY = y;
          return true;
        }
        break;
      case MotionEvent.ACTION_UP:
        if (mOnIndicatorClickListener != null) {
          if (Math.abs(x - mDownX) <= mTouchSlop && Math.abs(y - mDownY) <= mTouchSlop) {
            float max = Float.MAX_VALUE;
            int index = 0;
            for (int i = 0; i < getChildCount(); i++) {
              View view = getChildAt(i);
              float offset = Math.abs(view.getX() - x);
              if (offset < max) {
                max = offset;
                index = i;
              }
            }
            mOnIndicatorClickListener.onClick(index);
          }
        }
        break;
      default:
        break;
    }
    return super.onTouchEvent(event);
  }

  public boolean isTouchable() {
    return mTouchable;
  }

  public void setTouchable(boolean touchable) {
    mTouchable = touchable;
  }

  public OnIndicatorClickListener getOnIndicatorClickListener() {
    return mOnIndicatorClickListener;
  }

  public void setOnIndicatorClickListener(OnIndicatorClickListener _onIndicatorClickListener) {
    if (!mTouchable) {
      mTouchable = true;
    }
    mOnIndicatorClickListener = _onIndicatorClickListener;
  }

  public int getmIndicatorWidth() {
    return mIndicatorWidth;
  }

  public void setmIndicatorWidth(int mIndicatorWidth) {
    this.mIndicatorWidth = mIndicatorWidth;
    invalidate();
  }

  public int getmIndicatorHeight() {
    return mIndicatorHeight;
  }

  public void setmIndicatorHeight(int mIndicatorHeight) {
    this.mIndicatorHeight = mIndicatorHeight;
    invalidate();
  }

  public int getmIndicatorMargin() {
    return mIndicatorMargin;
  }

  public void setmIndicatorMargin(int mIndicatorMargin) {
    this.mIndicatorMargin = mIndicatorMargin;
    invalidate();
  }

  public int getmIndicatorGravity() {
    return mIndicatorGravity;
  }

  public void setmIndicatorGravity(int mIndicatorGravity) {
    this.mIndicatorGravity = mIndicatorGravity;
    invalidate();
  }

  public boolean ismIndicatorIsSnap() {
    return mIndicatorIsSnap;
  }

  public void setmIndicatorIsSnap(boolean mIndicatorIsSnap) {
    this.mIndicatorIsSnap = mIndicatorIsSnap;
    invalidate();
  }

  public Drawable getmIndicatorBackgroundDrawable() {
    return mIndicatorBackgroundDrawable;
  }

  public void setmIndicatorBackgroundDrawable(Drawable mIndicatorBackgroundDrawable) {
    this.mIndicatorBackgroundDrawable = mIndicatorBackgroundDrawable;
    invalidate();
  }

  public Drawable getmMovingIndicatorBackgroundDrawable() {
    return mMovingIndicatorBackgroundDrawable;
  }

  public void setmMovingIndicatorBackgroundDrawable(Drawable mMovingIndicatorBackgroundDrawable) {
    this.mMovingIndicatorBackgroundDrawable = mMovingIndicatorBackgroundDrawable;
    invalidate();
  }

  public Drawable getmIndicatorSrcDrawable() {
    return mIndicatorSrcDrawable;
  }

  public void setmIndicatorSrcDrawable(Drawable mIndicatorSrcDrawable) {
    this.mIndicatorSrcDrawable = mIndicatorSrcDrawable;
    invalidate();
  }

  public Drawable getmMovingIndicatorSrcDrawable() {
    return mMovingIndicatorSrcDrawable;
  }

  public void setmMovingIndicatorSrcDrawable(Drawable mMovingIndicatorSrcDrawable) {
    this.mMovingIndicatorSrcDrawable = mMovingIndicatorSrcDrawable;
    invalidate();
  }

  public Interpolator getStartInterpolator() {
    return mStartInterpolator;
  }

  public void setStartInterpolator(Interpolator startInterpolator) {
    mStartInterpolator = startInterpolator;
    if (mStartInterpolator == null) {
      mStartInterpolator = new AccelerateDecelerateInterpolator();
    }
  }
}
