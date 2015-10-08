// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.contacts_database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ShowEntryActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		id_ = intent.getLongExtra("id", -1);
		Log.d(TAG, "id=" + id_);
		if (id_ < 0) {
			finish();
			return;
		}

		setContentView(R.layout.show_entry);

		DatabaseHelper db = new DatabaseHelper(this);
		ContactsTableHelper helper = new ContactsTableHelper(db);
		Cursor cursor = helper.query(id_, projection_, null, null, null);
		if (cursor == null || !cursor.moveToFirst()) {
			finish();
		} else {
			TextView tv = (TextView) findViewById(R.id.name);
			String name = cursor.getString(eventtimeIndex);
			tv.setText(name);

			tv = (TextView) findViewById(R.id.contact);
			String contact = cursor.getString(eventnameIndex);
			tv.setText(contact);
		}
	}

	public static void start(Context context, long id) {
		if (id >= 0) {
			Intent intent = new Intent(context, ShowEntryActivity.class);
			intent.putExtra("id", id);
			context.startActivity(intent);
		}
	}

	private long id_;
	private final int eventtimeIndex = 0, eventnameIndex = 1;

	private static final String TAG = ShowEntryActivity.class.getName();
	private static final String[] projection_ = new String[] {
		ContactsTableHelper.COLUMN_NAME_NAME,
		ContactsTableHelper.COLUMN_NAME_CONTACT
	};
}
