// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.listview_arrayadapter;

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

		String[] names = getResources().getStringArray(R.array.names);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, names);
		list.setAdapter(adapter);
	}

	private static final String TAG = MainActivity.class.getName();
}
