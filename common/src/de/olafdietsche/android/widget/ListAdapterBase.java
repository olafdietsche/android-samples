// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ListAdapterBase<T> extends BaseAdapter {
	public ListAdapterBase(Context context, int resource, List<T> data) {
		this.resource = resource;
		this.data = data;

		if (inflater == null)
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	protected abstract View getView(T item, View convertView, ViewGroup parent);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(resource, null);

		T item = data.get(position);
		return getView(item, convertView, parent);
	}

	private int resource;
	private List<T> data;

	private static LayoutInflater inflater;
}
