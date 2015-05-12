// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.content.res;

import java.util.ArrayList;

public class ResourceChunk {
	public ResourceChunk(ByteBuffer buffer) {
		this.buffer = buffer.slice();
		parseHeader();
	}

	public ResourceChunk(byte[] b) {
		buffer = wrapBytes(b, 0, b.length);
		parseHeader();
	}

	public ResourceChunk(byte[] b, int off, int len) {
		buffer = wrapBytes(b, off, len);
		parseHeader();
	}

	public boolean isType(int expect) { return type == expect; }
	public boolean checkHeaderSize(int expect) { return headerSize == expect; }

	public ArrayList<ResourceChunk> parseNestedChunks() {
		buffer.seek(headerSize);
		return parseNestedChunks(buffer);
	}

	public static ArrayList<ResourceChunk> parseNestedChunks(ByteBuffer buffer) {
		ArrayList<ResourceChunk> chunks = new ArrayList<ResourceChunk>();
		while (buffer.remaining() > 0) {
			ResourceChunk chunk = new ResourceChunk(buffer);
			chunks.add(chunk);
			buffer.skip(chunk.size);
		}

		return chunks;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("@");
		sb.append(Integer.toHexString(hashCode()));
		sb.append("(type=");
		sb.append(type);
		sb.append(", headerSize=");
		sb.append(headerSize);
		sb.append(", chunkSize=");
		sb.append(size);
		sb.append("/");
		sb.append(size - headerSize);
		sb.append(")");
		return sb.toString();
	}

	static ByteBuffer wrapBytes(byte[] b, int off, int len) {
		return new ByteBuffer(b, off, len);
	}

	private void parseHeader() {
		type = buffer.getShort();
		headerSize = buffer.getShort();
		size = buffer.getInt();
		buffer.limit(size);
	}

	public static final int HEADER_SIZE = 8;

	public static final int
		RES_NULL_TYPE = 0x0000,
		RES_STRING_POOL_TYPE = 0x0001,
		RES_TABLE_TYPE = 0x0002,
		RES_XML_TYPE = 0x0003,

		// Chunk types in XML
		RES_XML_FIRST_CHUNK_TYPE = 0x0100,
		RES_XML_START_NAMESPACE_TYPE = 0x0100,
		RES_XML_END_NAMESPACE_TYPE = 0x0101,
		RES_XML_START_ELEMENT_TYPE = 0x0102,
		RES_XML_END_ELEMENT_TYPE = 0x0103,
		RES_XML_CDATA_TYPE = 0x0104,
		RES_XML_LAST_CHUNK_TYPE = 0x017f,
		// This contains a uint32_t array mapping strings in the string
		// pool back to resource identifiers.  It is optional.
		RES_XML_RESOURCE_MAP_TYPE = 0x0180,

		// Chunk types in TABLE
		RES_TABLE_PACKAGE_TYPE = 0x0200,
		RES_TABLE_TYPE_TYPE = 0x0201,
		RES_TABLE_TYPE_SPEC_TYPE = 0x0202,
		RES_TABLE_LIBRARY_TYPE = 0x0203;

	ByteBuffer buffer;
	private int type;
	private int headerSize;
	private int size;
}
