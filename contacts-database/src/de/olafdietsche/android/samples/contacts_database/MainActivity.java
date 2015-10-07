// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.contacts_database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
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
		registerForContextMenu(list);
		View header = list.inflate(this, R.layout.header, null);
		list.addHeaderView(header);

		adapter_ = new ContactsCursorAdapter(this, null);
		list.setAdapter(adapter_);
	}

	@Override
	public void onResume() {
		super.onResume();
		updateContactsListView(this);
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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		if (info == null || info.id < 0)
			return;

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_delete:
			DatabaseHelper db = new DatabaseHelper(this);
			ContactsTableHelper helper = new ContactsTableHelper(db);
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			helper.delete(info.id, null, null);
			updateContactsListView(this);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void updateContactsListView(final Context context) {
		AsyncTask<Void, Void, Cursor> task = new AsyncTask<Void, Void, Cursor>() {
			@Override
			protected Cursor doInBackground(Void... unused) {
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

	private CursorAdapter adapter_;
	private static final String[] projection = new String[] {
		BaseColumns._ID,
		ContactsTableHelper.COLUMN_NAME_NAME,
		ContactsTableHelper.COLUMN_NAME_CONTACT
	};

	private static final String TAG = MainActivity.class.getName();
}
