package com.baoyz.loadinganimations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;

public class LoadingPage extends FrameLayout {

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
		mPaint.setColor(Color.GRAY);
		mPaint.setStyle(Paint.Style.FILL);

		LoadingView loadingView = new LoadingView(context);
		loadingView.setItemLength(4);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
//		this.addView(loadingView, params);
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
			mCenterX = w / 2;
			mCenterY = h / 2;
			MAX = (int) (Math.sqrt(h * h * 2) / 2);
			animator = ObjectAnimator.ofFloat(this, "animationProgress", 0, MAX);
			animator.setDuration(800);
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

}
