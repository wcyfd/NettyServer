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

	List<BaseDataType> getSeq();

	void resetReaderIndex();

	enum BaseDataType {
		_byte, _boolean, _short, _int, _float, _double, _long, _string
	}

}
