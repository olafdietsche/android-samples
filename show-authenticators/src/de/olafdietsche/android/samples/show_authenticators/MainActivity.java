// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.show_authenticators;

import java.util.ArrayList;

import android.accounts.AuthenticatorDescription;
import android.accounts.AccountManager;
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

		AccountManager am = AccountManager.get(this);
		AuthenticatorDescription[] authenticators = am.getAuthenticatorTypes();

		AuthenticatorsListAdapter adapter = new AuthenticatorsListAdapter(this, authenticators);
		list.setAdapter(adapter);
	}

	private static final String TAG = MainActivity.class.getName();
}
