// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.http_get_json;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;

import de.olafdietsche.android.widget.ListAdapterBase;
import de.olafdietsche.net.SimpleHttpClient;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		progress = findViewById(R.id.progress);
		form = findViewById(R.id.form);

		final EditText queryView = (EditText) findViewById(R.id.search_for);
		View button = (Button) findViewById(R.id.btn_search);
		button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Log.d(TAG, "Button clicked");
					String query = queryView.getText().toString();
					httpTask = new HttpGetTask();
					httpTask.execute(query);
				}
			});

		ListView list = (ListView) findViewById(R.id.repositories_list);
		View header = list.inflate(this, R.layout.header, null);
		list.addHeaderView(header);
		adapter = new RepositoriesListAdapter(this, null);
		list.setAdapter(adapter);
	}

	private void showProgress() {
		progress.setVisibility(View.VISIBLE);
		form.setVisibility(View.GONE);
	}

	private void hideProgress() {
		progress.setVisibility(View.GONE);
		form.setVisibility(View.VISIBLE);
	}

	private class HttpGetTask extends AsyncTask<String, Void, List<RepositoryItem>> {
		private static final String urlSpec = "https://api.github.com/search/repositories?q=";

		@Override
		protected void onPreExecute() {
			showProgress();
		}

		@Override
		protected List<RepositoryItem> doInBackground(String... query) {
			try {
				SimpleHttpClient client = new SimpleHttpClient(urlSpec + query[0]);
				client.doGet();
				String response = client.readTextResponse();

				JSONObject obj = new JSONObject(response);
				JSONArray items = obj.getJSONArray("items");
				List<RepositoryItem> repositories = new ArrayList<RepositoryItem>();
				for (int i = 0; i < items.length(); ++i) {
					JSONObject item = items.getJSONObject(i);
					String repository = item.getString("name");
					JSONObject owner = item.getJSONObject("owner");
					String login = owner.getString("login");
					RepositoryItem repItem = new RepositoryItem(repository, login);
					repositories.add(repItem);
					Log.d(TAG, "name=" + repository + ", owner=" + login);
				}

				return repositories;
			} catch (MalformedURLException e) {
				Log.d(TAG, "MalformedURLException: " + e);
			} catch (IOException e) {
				Log.d(TAG, "IOException: " + e);
			} catch (JSONException e) {
				Log.d(TAG, "JSONException: " + e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<RepositoryItem> repositories) {
			Log.d(TAG, "onPostExecute()");
			hideProgress();
			httpTask = null;
			adapter.setData(repositories);
			adapter.notifyDataSetChanged();
		}

		@Override
		protected void onCancelled() {
			hideProgress();
			httpTask = null;
		}

		private static final String TAG = "HttpGetTask";
	}

	private View progress;
	private View form;
	private ListAdapterBase adapter;
	private HttpGetTask httpTask;

	private static final String TAG = "MainActivity";
}
