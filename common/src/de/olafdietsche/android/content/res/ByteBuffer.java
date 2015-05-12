// -*- mode: java -*-
// Copyright (c) 2015 Olaf Dietsche

package de.olafdietsche.android.content.res;

import java.nio.charset.Charset;

public class ByteBuffer {
	public ByteBuffer(byte[] b, int off, int len) throws IndexOutOfBoundsException {
		if (off >= b.length || off + len > b.length)
			throw new IndexOutOfBoundsException();

		this.buffer = b;
		this.start = off;
		this.limit = off + len;

		utf16le = Charset.forName("UTF-16LE");
		utf8 = Charset.forName("UTF-8");
		rewind();
	}

	public ByteBuffer slice() throws IndexOutOfBoundsException {
		return new ByteBuffer(buffer, current, limit - current);
	}

	public void rewind() {
		current = start;
	}

	public void skip(int n) throws IndexOutOfBoundsException {
		checkIndex(current + n);
		current += n;
	}

	public void seek(int n) throws IndexOutOfBoundsException {
		checkIndex(start + n);
		current = start + n;
	}

	public void limit(int n) throws IndexOutOfBoundsException {
		checkIndex(start + n);
		limit = start + n;
	}

	public int remaining() {
		return limit - current;
	}

	public byte getByte() throws IndexOutOfBoundsException {
		checkIndex(current + sizeof_byte);
		return (byte) get();
	}

	public short getShort() throws IndexOutOfBoundsException {
		checkIndex(current + sizeof_short);
		int a = get();
		int b = get();
		return (short)(b << 8 | a);
	}

	public int getInt() throws IndexOutOfBoundsException {
		checkIndex(current + sizeof_int);
		int a = get();
		int b = get();
		int c = get();
		int d = get();
		return d << 24 | c << 16 | b << 8 | a;
	}

	public String getString16() throws IndexOutOfBoundsException {
		int len = decodeString16Length();
		len *= sizeof_char;
		checkIndex(current + len);
		return new String(buffer, current, len, utf16le);
	}

	public String getString16(int offset) throws IndexOutOfBoundsException {
		int tmp = current;
		seek(offset);
		String s = getString16();
		current = tmp;
		return s;
	}

	public String getString8() throws IndexOutOfBoundsException {
		int len = decodeString8Length();
		checkIndex(current + len);
		return new String(buffer, current, len, utf8);
	}

	public String getString8(int offset) throws IndexOutOfBoundsException {
		int tmp = current;
		seek(offset);
		String s = getString8();
		current = tmp;
		return s;
	}

	private int decodeString8Length() throws IndexOutOfBoundsException {
		int len = getByte();
		if ((len & 0x80) == 0)
			return len;

		int hi = len & 0x7f;
		int lo = getByte();
		return hi << 16 | lo;
	}

	private int decodeString16Length() throws IndexOutOfBoundsException {
		int len = getShort();
		if ((len & 0x8000) == 0)
			return len;

		int hi = len & 0x7fff;
		int lo = getShort();
		return hi << 16 | lo;
	}

	private int get() {
		return buffer[current++] & 0x00ff;
	}

	private void checkIndex(int i) throws IndexOutOfBoundsException {
		if (i < start || i > limit)
			throw new IndexOutOfBoundsException("ByteBuffer.checkIndex(" + i + "): start=" + start + ", limit=" + limit);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("@");
		sb.append(Integer.toHexString(hashCode()));
		sb.append("(start=");
		sb.append(start);
		sb.append(", limit=");
		sb.append(limit);
		sb.append(", current=");
		sb.append(current);
		sb.append(")");
		return sb.toString();
	}

	private static final int sizeof_byte = 1;
	private static final int sizeof_char = Character.SIZE / Byte.SIZE;
	private static final int sizeof_short = Short.SIZE / Byte.SIZE;
	private static final int sizeof_int = Integer.SIZE / Byte.SIZE;

	private byte[] buffer;
	private int start;
	private int limit;
	private int current;
	private Charset utf8, utf16le;
}
