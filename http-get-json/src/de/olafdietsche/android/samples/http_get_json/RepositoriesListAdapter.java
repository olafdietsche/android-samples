// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.http_get_json;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.olafdietsche.android.widget.ListAdapterBase;

public class RepositoriesListAdapter extends ListAdapterBase<RepositoryItem> {
	public RepositoriesListAdapter(Context context, List<RepositoryItem> data) {
		super(context, R.layout.row, data);
	}

	@Override
	protected View getView(RepositoryItem item, View view, ViewGroup parent) {
		TextView tv;
		tv = (TextView) view.findViewById(R.id.repository_name);
		tv.setText(item.name);
		tv = (TextView) view.findViewById(R.id.repository_owner);
		tv.setText(item.owner);
		return view;
	}
}
