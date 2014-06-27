package com.baoyz.loadinganimations;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

/**
 * 
 * @author baoyz
 * @date 2014-6-28
 */
public class LoadingSheet extends Fragment {

	private static final String ARG_SHOW_POINT = "show_point";

	private boolean mDismissed = true;
	private LoadingSheetListener mListener;
	private FrameLayout mPanel;
	private ViewGroup mGroup;
	private boolean isCancel = true;

	public void show(FragmentManager manager, String tag) {
		if (!mDismissed) {
			return;
		}
		mDismissed = false;
		FragmentTransaction ft = manager.beginTransaction();
		ft.add(this, tag);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void dismiss() {
		if (mDismissed) {
			return;
		}
		mDismissed = true;
		getFragmentManager().popBackStack();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.remove(this);
		ft.commit();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			View focusView = getActivity().getCurrentFocus();
			if (focusView != null) {
				imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
			}
		}

		mGroup = (ViewGroup) getActivity().getWindow().getDecorView();

		mPanel = new FrameLayout(getActivity());

		LoadingPage page = new LoadingPage(getActivity());
		Point p = getShowPoint();
		page.setCenterX(p.x);
		page.setCenterY(p.y);
		mPanel.addView(page);

		LoadingView loadingView = new LoadingView(getActivity());
		loadingView.setItemLength(4);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		mPanel.addView(loadingView, params);

		mPanel.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		mGroup.addView(mPanel);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		mGroup.removeView(mPanel);
		if (mListener != null) {
			mListener.onDismiss(this, isCancel);
		}
		super.onDestroyView();
	}

	private Point getShowPoint() {
		return getArguments().getParcelable(ARG_SHOW_POINT);
	}

	public void setLoadingSheetListener(LoadingSheetListener listener) {
		mListener = listener;
	}

	public static Builder createBuilder(Context context,
			FragmentManager fragmentManager) {
		return new Builder(context, fragmentManager);
	}

	public static class Builder {

		private static final Parcelable p = null;
		private Context mContext;
		private FragmentManager mFragmentManager;
		private String mTag = "loading";
		private LoadingSheetListener mListener;
		private Point mShowPoint;

		public Builder(Context context, FragmentManager fragmentManager) {
			mContext = context;
			mFragmentManager = fragmentManager;
		}

		public Builder setTag(String tag) {
			mTag = tag;
			return this;
		}

		public Builder setListener(LoadingSheetListener listener) {
			this.mListener = listener;
			return this;
		}

		public Builder setShowPoint(Point p) {
			mShowPoint = p;
			return this;
		}

		public Bundle prepareArguments() {
			Bundle bundle = new Bundle();
			bundle.putParcelable(ARG_SHOW_POINT, mShowPoint);
			return bundle;
		}

		public LoadingSheet show() {
			LoadingSheet actionSheet = (LoadingSheet) Fragment.instantiate(
					mContext, LoadingSheet.class.getName(), prepareArguments());
			actionSheet.setLoadingSheetListener(mListener);
			actionSheet.show(mFragmentManager, mTag);
			return actionSheet;
		}

	}

	public static interface LoadingSheetListener {

		void onDismiss(LoadingSheet loadingSheet, boolean isCancel);

		void onOtherButtonClick(LoadingSheet loadingSheet, int index);
	}

}
