// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.contacts_database;

import android.database.sqlite.SQLiteOpenHelper;
import de.olafdietsche.android.database.TableHelper;

public class ContactsTableHelper extends TableHelper {
	private static final String TABLE_NAME = "contacts";

	public static final String COLUMN_NAME_NAME = "name";
	public static final String COLUMN_NAME_CONTACT = "contact";

	public ContactsTableHelper(SQLiteOpenHelper dbHelper) {
		super(dbHelper, TABLE_NAME);
	}
}
