// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.list_adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.olafdietsche.android.widget.ListAdapterBase;
import de.olafdietsche.android.samples.list_adapter.R;

public class ContactsListAdapter extends ListAdapterBase<ContactItem> {
	public ContactsListAdapter(Context context, List<ContactItem> data) {
		super(context, R.layout.row, data);
	}

	@Override
	protected View getView(ContactItem item, View view, ViewGroup parent) {
		TextView tv;
		tv = (TextView) view.findViewById(R.id.first_name);
		tv.setText(item.firstName);
		tv = (TextView) view.findViewById(R.id.last_name);
		tv.setText(item.lastName);
		tv = (TextView) view.findViewById(R.id.city);
		tv.setText(item.city);
		return view;
	}
}
