// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.monitor_screen_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;

public class StartReceiverService extends Service {
	@Override
	public void onCreate() {
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		receiver_ = new EventsReceiver();
		registerReceiver(receiver_, filter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver_);
	}

	static void start(Context context) {
		Intent intent = new Intent(context, StartReceiverService.class);
		context.startService(intent);
	}

	EventsReceiver receiver_;

	private static final String TAG = StartReceiverService.class.getName();
}
