// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.content.res;

public class XmlNamespace extends XmlNode {
	public XmlNamespace(ResourceChunk chunk, StringPool pool) {
		super(chunk, pool);
		assert isNamespace(chunk) : "Bad resource chunk type";
		parseHeader(chunk.buffer, pool);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<namespace ");
		sb.append(prefix);
		sb.append("=\"");
		sb.append(uri);
		sb.append("\">");
		return sb.toString();
	}

	private static boolean isNamespace(ResourceChunk chunk) {
		return chunk.isType(ResourceChunk.RES_XML_START_NAMESPACE_TYPE)
			|| chunk.isType(ResourceChunk.RES_XML_END_NAMESPACE_TYPE);
	}

	private void parseHeader(ByteBuffer buffer, StringPool pool) {
		int index;
		index = buffer.getInt();
		prefix = pool.getString(index);
		index = buffer.getInt();
		uri = pool.getString(index);
	}

	// The prefix of the namespace.
	private String prefix;
    
	// The URI of the namespace.
	private String uri;
}
