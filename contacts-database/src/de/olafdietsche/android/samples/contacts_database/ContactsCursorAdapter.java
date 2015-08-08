// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.contacts_database;

import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.view.ViewGroup;
import android.util.Log;

import de.olafdietsche.android.samples.contacts_database.ContactsTableHelper;

public class ContactsCursorAdapter extends ResourceCursorAdapter {
	public ContactsCursorAdapter(Context context, Cursor cursor) {
		super(context, R.layout.contact_line, cursor, false);
/*
		nameIndex = cursor.getColumnIndex(ContactsTableHelper.COLUMN_NAME_NAME);
		contactIndex = cursor.getColumnIndex(ContactsTableHelper.COLUMN_NAME_CONTACT);
*/
	}

	@Override
	public void bindView (View view, Context context, Cursor cursor) {
		TextView tv;
		String name = cursor.getString(nameIndex);
		tv = (TextView) view.findViewById(R.id.name);
		Log.d(TAG, "tv=" + tv + ", name=" + name);
		tv.setText(name);

		String contact = cursor.getString(contactIndex);
		tv = (TextView) view.findViewById(R.id.contact);
		Log.d(TAG, "tv=" + tv + ", contact=" + contact);
		tv.setText(contact);
	}

	private final int nameIndex = 1, contactIndex = 2;

	private static final String TAG = ContactsCursorAdapter.class.getSimpleName();
}
