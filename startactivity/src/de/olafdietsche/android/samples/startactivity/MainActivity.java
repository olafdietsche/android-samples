// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.startactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void clickMe(View notused) {
		Log.d(TAG, "clickMe");
		Intent intent = new Intent(this, AnotherActivity.class);
		this.startActivity(intent);
	}

	private static final String TAG = MainActivity.class.getName();
}
