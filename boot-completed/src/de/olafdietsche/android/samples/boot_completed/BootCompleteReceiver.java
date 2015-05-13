// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.boot_completed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Boot complete received: " + intent);
		Intent start = new Intent(context, NotifyActivity.class);
		start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(start);
	}

	private static final String TAG = BootCompleteReceiver.class.getName();
}
