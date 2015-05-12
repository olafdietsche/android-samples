// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.content.res;

public class XmlElementStart extends XmlNode {
	public XmlElementStart(ResourceChunk chunk, StringPool pool) {
		super(chunk, pool);
		assert chunk.isType(ResourceChunk.RES_XML_START_ELEMENT_TYPE) : "Bad resource chunk type";
		ByteBuffer buf = chunk.buffer;
		parseHeader(buf, pool);
		parseAttributes(buf, pool);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		if (ns != null) {
			sb.append(ns);
			sb.append(":");
		}

		sb.append(name);

		if (attributes != null) {
			for (XmlAttribute a : attributes) {
				sb.append(" ");
				sb.append(a);
			}
		}

		sb.append(">");
		return sb.toString();
	}

	private void parseHeader(ByteBuffer buffer, StringPool pool) {
		int index = buffer.getInt();
		ns = pool.getString(index);
		index = buffer.getInt();
		name = pool.getString(index);

		attributeStart = buffer.getShort();
		attributeSize = buffer.getShort();
		attributeCount = buffer.getShort();

		idIndex = buffer.getShort() - 1;
		classIndex = buffer.getShort() - 1;
		styleIndex = buffer.getShort() - 1;
	}

	private void parseAttributes(ByteBuffer buffer, StringPool pool) {
		attributes = new XmlAttribute[attributeCount];
		for (int i = 0; i < attributeCount; ++i)
			attributes[i] = new XmlAttribute(buffer, pool);
	}

	// String of the full namespace of this element.
	private String ns;
    
	// String name of this node if it is an ELEMENT; the raw
	// character data if this is a CDATA node.
	private String  name;
    
	// Byte offset from the start of this structure where the attributes start.
	private int attributeStart;
    
	// Size of the ResXMLTree_attribute structures that follow.
	private int attributeSize;
    
	// Number of attributes associated with an ELEMENT.  These are
	// available as an array of ResXMLTree_attribute structures
	// immediately following this node.
	private int attributeCount;
    
	// Index (0-based) of the "id" attribute. -1 if none.
	private int idIndex;
    
	// Index (0-based) of the "class" attribute. -1 if none.
	private int classIndex;
    
	// Index (0-based) of the "style" attribute. -1 if none.
	private int styleIndex;

	XmlAttribute[] attributes;
}
