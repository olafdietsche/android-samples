// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.list_adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ListView list = (ListView) findViewById(R.id.list);
		View header = list.inflate(this, R.layout.header, null);
		list.addHeaderView(header);

		ArrayList<ContactItem> entries = new ArrayList<ContactItem>();
		entries.add(new ContactItem("Alice", "Miller", "Detroit"));
		entries.add(new ContactItem("Bob", "Miller", "Detroit"));
		entries.add(new ContactItem("John", "Doe", "New York"));
		entries.add(new ContactItem("Jane", "Smith", "Washington"));
		entries.add(new ContactItem("Mary", "Smith", "Washington"));

		ContactsListAdapter adapter = new ContactsListAdapter(this, entries);
		list.setAdapter(adapter);
	}

	private static final String TAG = MainActivity.class.getName();
}
