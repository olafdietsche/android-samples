// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Implements a simple (incomplete) HTTP client class
 *
 * Usage:
 *
 * <code>
 * SimpleHttpClient req = new SimpleHttpClient("http://www.example.com/test.php");
 * req.doPost();
 * req.writeTextBody("{ \"longitude\": \"47.3\", \"latitude\": \"7.5\" }", "text/json", "UTF-8");
 * String response = req.readTextResponse("UTF-8");
 * </code>
 */

public class SimpleHttpClient {
	public SimpleHttpClient(final String urlspec) throws MalformedURLException, IOException {
		openConnection(urlspec);
	}

	public void openConnection(final String urlspec) throws MalformedURLException, IOException {
		URL url = new URL(urlspec);
		conn = (HttpURLConnection) url.openConnection();
	}

	public void doGet() throws ProtocolException {
		// default method is GET
	}

	public void doPost() throws ProtocolException {
		conn.setRequestMethod(HTTP_POST);
	}

	public void doPut() throws ProtocolException {
		conn.setRequestMethod(HTTP_PUT);
	}

	public void doDelete() throws ProtocolException {
		conn.setRequestMethod(HTTP_DELETE);
	}

	public void writeBinaryBody(final byte[] body) throws IOException {
		conn.setDoOutput(true);
		conn.setRequestProperty(HTTP_CONTENT_LENGTH, Integer.toString(body.length));
		OutputStream out = conn.getOutputStream();
		out.write(body);
	}

	public void writeBinaryBody(final byte[] body, final String contentType) throws IOException {
		conn.setDoOutput(true);
		conn.setRequestProperty(HTTP_CONTENT_TYPE, contentType); 
		conn.setRequestProperty(HTTP_CONTENT_LENGTH, Integer.toString(body.length));
		OutputStream out = conn.getOutputStream();
		out.write(body);
	}

	public void writeTextBody(final String body) throws IOException {
		writeTextBody(body, "text/plain", "UTF-8");
	}

	public void writeTextBody(final String body, final String contentType, final String charset) throws IOException {
		byte[] data = body.getBytes(charset);
		writeBinaryBody(data, contentType + "; charset=" + charset);
	}

	public byte[] readBinaryResponse() throws IOException {
		InputStream in = conn.getInputStream();
		return readBinaryResponse(in);
	}

	public String readTextResponse(final String charset) throws IOException {
		InputStream in = conn.getInputStream();
		return readTextResponse(in, charset);
	}

	public String readTextResponse() throws IOException {
		InputStream in = conn.getInputStream();
		final String contentType = conn.getContentType();
		final String charset = extractCharset(contentType);
		return readTextResponse(in, charset);
	}

	private static String extractCharset(final String contentType) {
		if (contentType != null) {
			Matcher m = patternCharset.matcher(contentType);
			if (m.find())
				return m.group(1);
		}

		return defaultCharset;
	}

	private static byte[] readBinaryResponse(final InputStream in) throws IOException {
		ByteArrayOutputStream ret = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		int n;
		while ((n = in.read(buf)) != -1)
			ret.write(buf);

		return ret.toByteArray();
	}

	private static String readTextResponse(final InputStream in, final String charset) throws UnsupportedEncodingException, IOException {
		InputStreamReader reader = new InputStreamReader(in, charset);
		StringBuilder ret = new StringBuilder();
		char[] buf = new char[4096];
		int n;
		while ((n = reader.read(buf)) != -1)
			ret.append(buf, 0, n);

		return ret.toString();
	}

	private HttpURLConnection conn;

	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";
	private static final String HTTP_PUT = "PUT";
	private static final String HTTP_DELETE = "DELETE";
	private static final String HTTP_HEAD = "HEAD";
	private static final String HTTP_OPTIONS = "OPTIONS";
	private static final String HTTP_TRACE = "TRACE";

	private static final String HTTP_CONTENT_TYPE = "Content-Type";
	private static final String HTTP_CONTENT_LENGTH = "Content-Length";

	private static final String defaultCharset = "ISO-8859-1";
	private static final String reCharset = ";\\s*charset\\s*=\\s*\"?([^\\s;\"]+)";
	private static final Pattern patternCharset = Pattern.compile(reCharset);
}
