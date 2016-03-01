// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.TextUtils;

public class TableHelper {
	public TableHelper(SQLiteOpenHelper dbHelper, String tableName) {
		dbHelper_ = dbHelper;
		tableName_ = tableName;
	}

	public void close() {
		dbHelper_.close();
	}

	public Cursor rawQuery(String sql, String[] args) {
		SQLiteDatabase db = dbHelper_.getReadableDatabase();
		return db.rawQuery(sql, args);
	}

	public Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper_.getReadableDatabase();
		return db.query(tableName_, projection, selection, selectionArgs, null, null, sortOrder);
	}

	public Cursor query(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		selection = prefixClause(whereId_, selection);
		selectionArgs = insertArg(id, selectionArgs);
		return query(projection, selection, selectionArgs, sortOrder);
	}

	public Cursor query(long id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		return query(Long.toString(id), projection, selection, selectionArgs, sortOrder);
	}

	public int delete(String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper_.getWritableDatabase();
		return db.delete(tableName_, selection, selectionArgs);
	}

	public int delete(String id, String selection, String[] selectionArgs) {
		selection = prefixClause(whereId_, selection);
		selectionArgs = insertArg(id, selectionArgs);
		return delete(selection, selectionArgs);
	}

	public int delete(long id, String selection, String[] selectionArgs) {
		return delete(Long.toString(id), selection, selectionArgs);
	}

	public long insert(ContentValues values) {
		SQLiteDatabase db = dbHelper_.getWritableDatabase();
	        long rowId = db.insert(tableName_, null, values);
	        if (rowId < 0)
        	        throw new SQLException("Failed to insert row");

	        return rowId;
	}

	public int update(ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper_.getWritableDatabase();
		return db.update(tableName_, values, selection, selectionArgs);
	}

	public int update(String id, ContentValues values, String selection, String[] selectionArgs) {
		selection = prefixClause(whereId_, selection);
		selectionArgs = insertArg(id, selectionArgs);
		return update(values, selection, selectionArgs);
	}

	public int update(long id, ContentValues values, String selection, String[] selectionArgs) {
		return update(Long.toString(id), values, selection, selectionArgs);
	}

	private static final String whereId_ = BaseColumns._ID + " = ?";

	public static String prefixClause(String prefix, String selection) {
		if (TextUtils.isEmpty(selection)) {
			return prefix;
		} else {
			StringBuilder builder = new StringBuilder(prefix.length() + selection.length() + 10);
			builder.append("(")
				.append(prefix)
				.append(") and (")
				.append(selection)
				.append(")");
			return builder.toString();
		}
	}

	public static String[] insertArg(String arg, String[] selectionArgs) {
		if (selectionArgs == null)
			return new String[]{arg};

		int n = selectionArgs.length;
		String[] args = new String[n + 1];
		args[0] = arg;
		if (n > 0)
			System.arraycopy(selectionArgs, 0, args, 1, n);

		return args;
	}

	private SQLiteOpenHelper dbHelper_;
	private String tableName_;
}
