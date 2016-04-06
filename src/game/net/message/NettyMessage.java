package game.net.message;

import java.util.List;

import io.netty.buffer.ByteBuf;

public interface NettyMessage {
	void putBoolean(boolean value);

	void putInt(int value);

	void putFloat(float value);

	void putDouble(double value);

	void putByte(byte value);

	void putLong(long value);

	void putShort(short value);

	void putString(String value);

	byte getByte();

	boolean getBoolean();

	float getFloat();

	double getDouble();

	int getInt();

	long getLong();

	short getShort();

	String getString();

	ByteBuf getData();

	void setData(ByteBuf buf);

	void setType(int type);

	int getType();

	void setBody(ByteBuf buf);

	ByteBuf getBody();

	List<Byte> getSeq();

	void resetReaderIndex();

	final static byte _byte = 1;
	final static byte _boolean = 2;
	final static byte _short = 3;
	final static byte _int = 4;
	final static byte _float = 5;
	final static byte _double = 6;
	final static byte _long = 7;
	final static byte _string = 8;

}
