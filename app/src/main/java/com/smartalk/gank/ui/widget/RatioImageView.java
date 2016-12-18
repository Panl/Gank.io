package com.smartalk.gank.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 方形ImageView
 * 以Width为边长
 * Created by panl on 16/1/4.
 */
public class RatioImageView extends ImageView {
  public RatioImageView(Context context) {
    super(context);
  }

  public RatioImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int length = MeasureSpec.getSize(widthMeasureSpec);
    if (widthMode == MeasureSpec.AT_MOST) {
      length = Math.max(100, length);
    }
    setMeasuredDimension(length, length * 4 / 5);
  }
}
