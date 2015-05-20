// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.samples.write_external_storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		filenameView = (EditText) findViewById(R.id.filename);
		textView = (EditText) findViewById(R.id.text);
	}

	public void saveText(View unused) throws IOException {
		File dir = getExternalFilesDir(null);
		if (dir == null)
			dir = getFilesDir();

		CharSequence filename = filenameView.getText();
		File file = new File(dir, filename.toString());
		FileWriter fout = new FileWriter(file);
		CharSequence text = textView.getText();
		fout.append(text);
		fout.close();

		Log.d(TAG, "Text saved to " + file);
	}

	private EditText filenameView, textView;

	private static final String TAG = MainActivity.class.getName();
}
