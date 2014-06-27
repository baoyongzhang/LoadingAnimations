package com.baoyz.loadinganimations.sample;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.baoyz.loadinganimations.LoadingSheet;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClick(View v) {
		Point p = new Point(v.getLeft(), v.getTop());
		LoadingSheet.createBuilder(this, getSupportFragmentManager()).setShowPoint(p).show();
	}
}
