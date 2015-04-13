// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.show_accounts;

import java.util.Arrays;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.olafdietsche.android.widget.ListAdapterBase;

public class AccountsListAdapter extends ListAdapterBase<Account> {
	public AccountsListAdapter(Context context, Account[] data) {
		super(context, R.layout.row, Arrays.asList(data));
	}

	@Override
	protected View getView(Account item, View view, ViewGroup parent) {
		TextView tv;
		tv = (TextView) view.findViewById(R.id.account_name);
		tv.setText(item.name);
		tv = (TextView) view.findViewById(R.id.account_type);
		tv.setText(item.type);
		return view;
	}
}
