// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.contacts_database;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;

import de.olafdietsche.android.samples.contacts_database.DatabaseHelper;
import de.olafdietsche.android.samples.contacts_database.ContactsTableHelper;

public class NewEntryActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_entry);
		nameEntry_ = (EditText) findViewById(R.id.name);
		contactEntry_ = (EditText) findViewById(R.id.contact);
	}

	public void addContact(View view) {
		Log.d(TAG, "addContact");
		DatabaseHelper db = new DatabaseHelper(this);
		ContactsTableHelper helper = new ContactsTableHelper(db);
		ContentValues values = new ContentValues(2);

		String name = nameEntry_.getText().toString();
		values.put(ContactsTableHelper.COLUMN_NAME_NAME, name);
		String contact = contactEntry_.getText().toString();
		values.put(ContactsTableHelper.COLUMN_NAME_CONTACT, contact);

		helper.insert(values);
		finish();
	}

	public static void start(Context context) {
		Intent intent = new Intent(context, NewEntryActivity.class);
		context.startActivity(intent);
	}

	private EditText nameEntry_;
	private EditText contactEntry_;

	private static final String TAG = NewEntryActivity.class.getSimpleName();
}
