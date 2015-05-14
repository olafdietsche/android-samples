// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.gridlayout;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.util.Log;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		GridView grid = (GridView) findViewById(R.id.grid);
		final Context context = this;
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					PackageInfo pkg = (PackageInfo) view.getTag();
					showAppDetails(context, pkg);
				}
			});

		PackageManager pm = getPackageManager();
		List<PackageInfo> pkgs = pm.getInstalledPackages(0);
		
		PackagesListAdapter adapter = new PackagesListAdapter(this, pm, pkgs);
		grid.setAdapter(adapter);
	}

	private static void showAppDetails(Context context, PackageInfo pkg) {
		Intent intent = new Intent();
		intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", pkg.packageName, null);
		intent.setData(uri);
		context.startActivity(intent);
	}

	private static final String TAG = MainActivity.class.getName();
}
