// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.list_installed_apps;

import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

		PackageManager pm = getPackageManager();
		List<PackageInfo> pkgs = pm.getInstalledPackages(0);
		
		PackagesListAdapter adapter = new PackagesListAdapter(this, pm, pkgs);
		list.setAdapter(adapter);
	}

	private static final String TAG = MainActivity.class.getName();
}
