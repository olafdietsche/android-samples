// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.content.res;

public class XmlAttribute {
	public XmlAttribute(ByteBuffer buffer, StringPool pool) {
		parseHeader(buffer, pool);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (ns != null) {
			sb.append(ns);
			sb.append(":");
		}

		sb.append(name);
		sb.append("=\"");
		sb.append(rawValue);
		sb.append("\"");
		return sb.toString();
	}

	private void parseHeader(ByteBuffer buffer, StringPool pool) {
		int index;
		index = buffer.getInt();
		ns = pool.getString(index);
		index = buffer.getInt();
		name = pool.getString(index);
		index = buffer.getInt();
		rawValue = pool.getString(index);

		// skip 8 bytes (sizeof(Res_value))
		buffer.getInt();
		buffer.getInt();
	}

	// Namespace of this attribute.
	private String ns;
    
	// Name of this attribute.
	private String name;

	// The original raw string value of this attribute.
	private String rawValue;
    
	// FIXME, not yet implemented
	// Processesd typed value of this attribute.
	// struct Res_value typedValue;
}
