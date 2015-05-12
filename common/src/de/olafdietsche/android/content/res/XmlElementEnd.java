// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.content.res;

public class XmlElementEnd extends XmlNode {
	public XmlElementEnd(ResourceChunk chunk, StringPool pool) {
		super(chunk, pool);
		assert chunk.isType(ResourceChunk.RES_XML_END_ELEMENT_TYPE) : "Bad resource chunk type";
		parseHeader(chunk.buffer, pool);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("</");
		if (ns != null) {
			sb.append(ns);
			sb.append(":");
		}

		sb.append(name);
		sb.append(">");
		return sb.toString();
	}

	private void parseHeader(ByteBuffer buffer, StringPool pool) {
		int index;
		index = buffer.getInt();
		ns = pool.getString(index);
		index = buffer.getInt();
		name = pool.getString(index);
	}

	// String of the full namespace of this element.
	private String ns;
    
	// String name of this node if it is an ELEMENT; the raw
	// character data if this is a CDATA node.
	private String  name;
}
