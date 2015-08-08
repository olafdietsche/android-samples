// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.contacts_database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.util.Log;

import de.olafdietsche.android.samples.contacts_database.DatabaseHelper;
import de.olafdietsche.android.samples.contacts_database.ContactsTableHelper;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ListView list = (ListView) findViewById(R.id.contacts_list);
		View header = list.inflate(this, R.layout.header, null);
		list.addHeaderView(header);

		adapter_ = new ContactsCursorAdapter(this, null);
		list.setAdapter(adapter_);
	}

	@Override
	public void onResume() {
		super.onResume();

		AsyncTask<Void, Void, Cursor> task = new AsyncTask<Void, Void, Cursor>() {
			@Override
			protected Cursor doInBackground(Void... unused) {
				Context context = getApplicationContext();
				DatabaseHelper db = new DatabaseHelper(context);
				ContactsTableHelper helper = new ContactsTableHelper(db);
				Cursor cursor = helper.query(projection, null, null, null);
				return cursor;
			}

			@Override
			protected void onPostExecute(Cursor cursor) {
				adapter_.changeCursor(cursor);
			}
		};

		task.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add:
			NewEntryActivity.start(this);
			return true;
		}

		return false;
	}

	private CursorAdapter adapter_;
	private static final String[] projection = new String[] {
		BaseColumns._ID,
		ContactsTableHelper.COLUMN_NAME_NAME,
		ContactsTableHelper.COLUMN_NAME_CONTACT
	};

	private static final String TAG = MainActivity.class.getName();
}