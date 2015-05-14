// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.gridlayout;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.olafdietsche.android.widget.ListAdapterBase;

public class PackagesListAdapter extends ListAdapterBase<PackageInfo> {
	public PackagesListAdapter(Context context, PackageManager pm, List<PackageInfo> data) {
		super(context, R.layout.griditem, data);
		this.pm = pm;
	}

	@Override
	protected View getView(PackageInfo pkg, View view, ViewGroup parent) {
		ApplicationInfo appInfo = pkg.applicationInfo;
		CharSequence label = null;
		Drawable icon = null;
		if (appInfo != null) {
			label = appInfo.loadLabel(pm);
			icon = appInfo.loadIcon(pm);
		}

		ImageView image = (ImageView) view.findViewById(R.id.pkg_icon);
		if (icon != null)
			image.setImageDrawable(icon);

		String name = label != null ? label.toString() : pkg.packageName;
		TextView tv;
		tv = (TextView) view.findViewById(R.id.pkg_name);
		tv.setText(name);

		view.setTag(pkg);
		return view;
	}

	PackageManager pm;
}
