package com.baoyz.loadinganimations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 
 * @author baoyz
 * @date 2014-6-28
 */
public class LoadingPage extends View {

	private static int MAX = 30;
	private Paint mPaint;

	private ObjectAnimator animator;
	private float mAnimationProgress;

	private float mCenterX;
	private float mCenterY;

	public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public LoadingPage(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.argb(0xA0, 0xC0, 0xC0, 0xC0));
		mPaint.setStyle(Paint.Style.FILL);

	}

	public void setAnimationProgress(float animationProgress) {
		mAnimationProgress = animationProgress;
		Log.i("byz", "progress = " + animationProgress);
		invalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (animator == null) {
			MAX = (int) (Math.sqrt(h * h * 2));
			animator = ObjectAnimator
					.ofFloat(this, "animationProgress", 0, MAX);
			animator.setDuration(500);
			animator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
				}
			});
			animator.start();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// mPaint.setAlpha((int) ((255 / MAX) * mAnimationProgress));
		canvas.drawCircle(mCenterX, mCenterY, mAnimationProgress, mPaint);
		Log.i("byz", "onDraw = " + mAnimationProgress);
	}

	public void setCenterX(int x) {
		mCenterX = x;
	}

	public void setCenterY(int y) {
		mCenterY = y;
	}
}
