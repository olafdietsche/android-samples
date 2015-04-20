// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.widget;

import java.util.Arrays;
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

	public ListAdapterBase(Context context, int resource, T[] data) {
		this(context, resource, Arrays.asList(data));
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
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

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setData(T[] data) {
		setData(Arrays.asList(data));
	}

	private int resource;
	private List<T> data;

	private static LayoutInflater inflater;
}
