// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.show_authenticators;

import java.util.Arrays;

import android.accounts.AuthenticatorDescription;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.olafdietsche.android.widget.ListAdapterBase;

public class AuthenticatorsListAdapter extends ListAdapterBase<AuthenticatorDescription> {
	public AuthenticatorsListAdapter(Context context, AuthenticatorDescription[] data) {
		super(context, R.layout.row, Arrays.asList(data));
		packageManager = context.getPackageManager();
	}

	@Override
	protected View getView(AuthenticatorDescription item, View view, ViewGroup parent) {

		ImageView iv;
		Drawable icon = packageManager.getDrawable(item.packageName, item.iconId, null);
		iv = (ImageView) view.findViewById(R.id.authenticator_icon);
		iv.setImageDrawable(icon);

		icon = packageManager.getDrawable(item.packageName, item.smallIconId, null);
		iv = (ImageView) view.findViewById(R.id.authenticator_small_icon);
		iv.setImageDrawable(icon);
		
		TextView tv;
		CharSequence label = packageManager.getText(item.packageName, item.labelId, null);
		tv = (TextView) view.findViewById(R.id.authenticator_name);
		tv.setText(label);
		tv = (TextView) view.findViewById(R.id.authenticator_type);
		tv.setText(item.type);
		tv = (TextView) view.findViewById(R.id.authenticator_package);
		tv.setText(item.packageName);
		return view;
	}

	private PackageManager packageManager;
}
