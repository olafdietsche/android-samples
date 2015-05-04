// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.options_menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NewEntryActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_entry);
	}

	public static void start(Context context) {
		Intent intent = new Intent(context, NewEntryActivity.class);
		context.startActivity(intent);
	}

	private static final String TAG = NewEntryActivity.class.getSimpleName();
}
