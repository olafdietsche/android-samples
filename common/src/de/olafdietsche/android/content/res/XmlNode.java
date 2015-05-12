// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.content.res;

public class XmlNode {
	public XmlNode(ResourceChunk chunk, StringPool pool) {
		parseHeader(chunk.buffer, pool);
	}

	private void parseHeader(ByteBuffer buf, StringPool pool) {
		buf.seek(ResourceChunk.HEADER_SIZE);

		lineNumber = buf.getInt();
		int index = buf.getInt();
		comment = pool.getString(index);
	}

	// Line number in original source file at which this element appeared.
	private int lineNumber;

	// Optional XML comment that was associated with this element; null if none.
	private String comment;
}
