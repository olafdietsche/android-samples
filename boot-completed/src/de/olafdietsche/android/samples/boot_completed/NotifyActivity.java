// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.boot_completed;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class NotifyActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boot_completed);
	}

	private static final String TAG = NotifyActivity.class.getName();
}
