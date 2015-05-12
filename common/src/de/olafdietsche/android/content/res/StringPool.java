// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.content.res;

import java.nio.charset.Charset;

public class StringPool {
	public StringPool(ResourceChunk chunk) {
		assert chunk.isType(ResourceChunk.RES_STRING_POOL_TYPE) : "Bad resource chunk type";
		assert chunk.checkHeaderSize(HEADER_SIZE) : "Bad resource chunk header size";

		ByteBuffer buf = chunk.buffer;
		buf.seek(ResourceChunk.HEADER_SIZE);

		parseHeader(buf);
		boolean utf8 = (flags & UTF8_FLAG) != 0;
		assert !utf8 : "FIXME: UTF-8 not yet implemented.";

		parseString16Table(buf);
		parseStylesTable(buf);
	}

	public String getString(int index) {
		if (index == -1)
			return null;

		return stringTable[index];
	}

	public int getIndex(String s) {
		int index = -1;
		for (int i = 0; i < stringTable.length; ++i) {
			if (stringTable[i].equals(s)) {
				assert index != -1 : "Duplicate string pool entry";
				index = i;
			}
		}

		return index;
	}

	private void parseHeader(ByteBuffer buf) {
		stringCount = buf.getInt();
		styleCount = buf.getInt();
		flags = buf.getInt();
		stringsStart = buf.getInt();
		stylesStart = HEADER_SIZE + buf.getInt();
	}

	private void parseString16Table(ByteBuffer buf) {
		stringTable = new String[stringCount];

		for (int i = 0; i < stringCount; ++i) {
			int offset = stringsStart + buf.getInt();
			stringTable[i] = buf.getString16(offset);
		}
	}

	private void parseString8Table(ByteBuffer buf) {
		stringTable = new String[stringCount];

		for (int i = 0; i < stringCount; ++i) {
			int offset = stringsStart + buf.getInt();
			stringTable[i] = buf.getString8(offset);
		}
	}

	private void parseStylesTable(ByteBuffer buf) {
		// FIXME, not yet implemented
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("@");
		sb.append(Integer.toHexString(hashCode()));
		sb.append("(\n");
		for (String s : stringTable) {
			sb.append(s);
			sb.append("\n");
		}

		sb.append("\n)");
		return sb.toString();
	}

	private static final int HEADER_SIZE = 0x001c;

	// Number of strings in this pool (number of uint32_t indices that follow
	// in the data).
	private int stringCount;

	// Number of style span arrays in the pool (number of uint32_t indices
	// follow the string indices).
	private int styleCount;

	// Flags.
	private static final int
        // If set, the string index is sorted by the string values (based
        // on strcmp16()).
        SORTED_FLAG = 1 << 0,

        // String pool is encoded in UTF-8
        UTF8_FLAG = 1 << 8;

	private int flags;

	// Index from header of the string data.
	private int stringsStart;

	// Index from header of the style data.
	private int stylesStart;

	private String[] stringTable;
}
