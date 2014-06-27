package com.baoyz.loadinganimations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 
 * @author baoyz
 * @date 2014-6-28
 */
public class LoadingView extends View {

	private static final float MAX_ALPHA = 255;
	private static float max;
	private Paint mPaint;

	private ObjectAnimator animator;
	private float mAnimationProgress;

	private int mIndex;

	private LoadingItem[] items;
	private int mItemLength = 3;
	private int mItemWidth;
	private int mItemColor = -1;

	public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public LoadingView(Context context) {
		super(context);
		init(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(MeasureSpec.makeMeasureSpec(mItemWidth * mItemLength,
				MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(mItemWidth,
				MeasureSpec.EXACTLY));
	}

	private void init(Context context, AttributeSet attrs) {

		if (mItemWidth <= 0) {
			mItemWidth = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources()
							.getDisplayMetrics());
		}

		items = new LoadingItem[mItemLength];

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(mItemColor > -1 ? mItemColor : Color.RED);
		mPaint.setStyle(Paint.Style.FILL);

		max = mItemWidth * 1.8f;
		animator = ObjectAnimator.ofFloat(this, "animationProgress", 0f, max);
		// animator.setFloatValues(MAX);
		animator.setDuration(400);
		animator.setRepeatCount(ObjectAnimator.INFINITE);
		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationRepeat(Animator animation) {
				if (mIndex < (items.length - 1)) {
					mIndex++;
				} else {
					mIndex = 0;
				}
			}
		});
		animator.start();
	}

	public void setAnimationProgress(float animationProgress) {
		mAnimationProgress = animationProgress;
		invalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int space = w / items.length;
		for (int i = 0; i < items.length; i++) {
			items[i] = new LoadingItem(space, i);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setAlpha((int) (MAX_ALPHA - (MAX_ALPHA / max)
				* mAnimationProgress));
		LoadingItem item = items[mIndex];
		canvas.drawCircle(item.centerX, item.centerY, item.radius
				- mAnimationProgress, mPaint);
	}

	static class LoadingItem {

		public LoadingItem(int space, int index) {
			centerX = space / 2 + space * index;
			centerY = space / 2;
			radius = space / 2;
		}

		int centerX;
		int centerY;
		int radius;
	}

	public void setItemLength(int i) {
		mItemLength = i;
		items = new LoadingItem[mItemLength];
	}

	public void setItemWidth(int w) {
		mItemWidth = w;
	}

	public void setItemColor(int color) {
		mItemColor = color;
	}
}
