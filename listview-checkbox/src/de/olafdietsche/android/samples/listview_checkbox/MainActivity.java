// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.listview_checkbox;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import android.util.SparseBooleanArray;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final ListView list = (ListView) findViewById(R.id.list);
		View header = list.inflate(this, R.layout.header, null);
		list.addHeaderView(header);

		String[] names = getResources().getStringArray(R.array.names);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, names);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Log.d(TAG, "onItemClick(" + position + ", " + id + "), view=" + view.getClass().getName());
					SparseBooleanArray positions = list.getCheckedItemPositions();
					for (int i = 0; i < positions.size(); ++i) {
						int key = positions.keyAt(i);
						boolean value = positions.valueAt(i);
						Log.d(TAG, "pos(" + i + ")=" + key + " -> " + value);
					}

					long[] ids = list.getCheckedItemIds();
					Log.d(TAG, "ids.length=" + ids.length);
					for (long i : ids) {
						Log.d(TAG, "id=" + i);
					}
				}
			});
	}

	private static final String TAG = MainActivity.class.getName();
}
