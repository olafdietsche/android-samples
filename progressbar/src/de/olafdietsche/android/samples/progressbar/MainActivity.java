// -*- mode: c++ -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.progressbar;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		progress = findViewById(R.id.progress);
		form = findViewById(R.id.form);

		final EditText secondsView = (EditText) findViewById(R.id.busy_seconds);
		View button = (Button) findViewById(R.id.show_progress_button);
		button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Log.d(TAG, "Button clicked");
					String seconds = secondsView.getText().toString();
					busyTask = new BusyTask();
					busyTask.execute(Integer.valueOf(seconds));
				}
			});
	}

	private void showProgress() {
		progress.setVisibility(View.VISIBLE);
		form.setVisibility(View.GONE);
	}

	private void hideProgress() {
		progress.setVisibility(View.GONE);
		form.setVisibility(View.VISIBLE);
	}

	private class BusyTask extends AsyncTask<Integer, Void, Void> {
		@Override
		protected void onPreExecute() {
			showProgress();
		}

		@Override
		protected Void doInBackground(Integer... seconds) {
			try {
				Thread.sleep(seconds[0] * 1000);
			} catch (InterruptedException e) {
				Log.d(TAG, "Sleep interrupted");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void unused) {
			hideProgress();
			busyTask = null;
		}

		@Override
		protected void onCancelled() {
			hideProgress();
			busyTask = null;
		}

		private static final String TAG = "BusyTask";
	}

	private View progress;
	private View form;
	private BusyTask busyTask;

	private static final String TAG = "MainActivity";
}
