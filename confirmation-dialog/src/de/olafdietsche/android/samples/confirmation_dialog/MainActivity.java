// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.confirmation_dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final Context context = this;
		switch (item.getItemId()) {
		case R.id.menu_delete:
			confirmAction(R.string.confirmation_title,
				      R.string.confirmation_message,
				      new DialogInterface.OnClickListener() {
					      @Override
					      public void onClick(DialogInterface dialog, int id) {
						      Log.d(TAG, "delete confirmed");
						      Toast.makeText(context, R.string.message_delete_confirmed, Toast.LENGTH_LONG).show();
					      }
				      },
				      new DialogInterface.OnClickListener() {
					      @Override
					      public void onClick(DialogInterface dialog, int id) {
						      Log.d(TAG, "delete cancelled");
						      Toast.makeText(context, R.string.message_delete_cancelled, Toast.LENGTH_LONG).show();
					      }
				      });
			return true;
		}

		return false;
	}

	private void confirmAction(int title, int message, DialogInterface.OnClickListener yesAction, DialogInterface.OnClickListener noAction) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(R.string.confirmation_yes, yesAction);
		builder.setNegativeButton(R.string.confirmation_no, noAction);
		builder.show();
	}

	private static final String TAG = MainActivity.class.getSimpleName();
}
