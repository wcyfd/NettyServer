package game.net.message;

import java.util.List;
import java.nio.charset.Charset;
import java.util.LinkedList;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BaseCustomTypeLengthMessage implements NettyMessage {

	private ByteBuf buf = null;
	private int pos = 0;
	private static final Charset charset = Charset.forName("UTF-8");
	private List<Byte> seq = new LinkedList<>();

	private final static int len_byte = 1;
	private final static int len_boolean = 1;
	private final static int len_short = 2;
	private final static int len_int = 4;
	private final static int len_float = 4;
	private final static int len_double = 8;
	private final static int len_long = 8;

	public BaseCustomTypeLengthMessage() {
		this.buf = Unpooled.buffer();
	}

	@Override
	public void putBoolean(boolean value) {

		this.writeByte((byte) (value ? 1 : 0));
		seq.add(_boolean);
	}

	@Override
	public void putInt(int value) {

		byte[] src = new byte[len_int];

		src[3] = (byte) (value & 0xff);
		src[2] = (byte) ((value >> 8) & 0xff);
		src[1] = (byte) ((value >> 16) & 0xff);
		src[0] = (byte) ((value >> 24) & 0xff);

		this.writeBytes(src, len_int);
		seq.add(_int);
	}

	@Override
	public void putFloat(float value) {

		int temp = Float.floatToRawIntBits(value);
		byte[] src = new byte[len_float];

		src[3] = (byte) (temp & 0xff);
		src[2] = (byte) ((temp >> 8) & 0xff);
		src[1] = (byte) ((temp >> 16) & 0xff);
		src[0] = (byte) ((temp >> 24) & 0xff);

		this.writeBytes(src, len_float);
		seq.add(_float);
	}

	@Override
	public void putDouble(double value) {

		long temp = Double.doubleToRawLongBits(value);
		byte[] src = new byte[len_double];
		src[7] = (byte) (temp & 0xff);
		src[6] = (byte) ((temp >> 8) & 0xff);
		src[5] = (byte) ((temp >> 16) & 0xff);
		src[4] = (byte) ((temp >> 24) & 0xff);
		src[3] = (byte) ((temp >> 32) & 0xff);
		src[2] = (byte) ((temp >> 40) & 0xff);
		src[1] = (byte) ((temp >> 48) & 0xff);
		src[0] = (byte) ((temp >> 56) & 0xff);

		this.writeBytes(src, len_double);
		seq.add(_double);
	}

	@Override
	public void putByte(byte value) {

		this.writeByte(value);
		seq.add(_byte);
	}

	@Override
	public void putLong(long value) {

		byte[] src = new byte[len_long];
		src[7] = (byte) (value & 0xff);
		src[6] = (byte) ((value >> 8) & 0xff);
		src[5] = (byte) ((value >> 16) & 0xff);
		src[4] = (byte) ((value >> 24) & 0xff);
		src[3] = (byte) ((value >> 32) & 0xff);
		src[2] = (byte) ((value >> 40) & 0xff);
		src[1] = (byte) ((value >> 48) & 0xff);
		src[0] = (byte) ((value >> 56) & 0xff);

		this.writeBytes(src, len_long);
		seq.add(_long);
	}

	@Override
	public void putShort(short value) {

		byte[] src = new byte[len_short];
		src[1] = (byte) (value & 0xff);
		src[0] = (byte) ((value >> 8) & 0xff);

		this.writeBytes(src, len_short);
		seq.add(_short);
	}

	@Override
	public void putString(String value) {

		int len = value.length();
		this.putInt(len);
		byte[] src = value.getBytes();
		this.writeBytes(src, len);
		seq.add(_string);
	}

	@Override
	public byte getByte() {

		byte b = this.getByteFromBuf();
		return b;
	}

	@Override
	public boolean getBoolean() {
		byte b = this.getByteFromBuf();
		if (b == 1) {
			return true;
		}
		return false;
	}

	@Override
	public float getFloat() {

		byte[] b = this.getByteFromBuf(len_float);

		int tempInt = 0;
		for (int i = 0; i < len_float; i++) {
			byte oneByte = b[i];
			int temp = this.getBinaryByteFromByte(oneByte);
			tempInt = (tempInt << 8) | temp;
		}

		return Float.intBitsToFloat(tempInt);
	}

	@Override
	public double getDouble() {

		byte[] b = this.getByteFromBuf(len_double);

		long tempLong = 0;
		for (int i = 0; i < len_double; i++) {
			byte oneByte = b[i];
			int tempInt = this.getBinaryByteFromByte(oneByte);
			tempLong = (tempLong << 8) | tempInt;
		}

		return Double.longBitsToDouble(tempLong);
	}

	@Override
	public int getInt() {

		byte[] b = this.getByteFromBuf(len_int);
		int dest = 0;
		for (int i = 0; i < len_int; i++) {
			int tempInt = this.getBinaryByteFromByte(b[i]);
			dest = (dest << 8) | tempInt;
		}

		return dest;
	}

	@Override
	public long getLong() {

		byte[] b = this.getByteFromBuf(len_long);
		long dest = 0;
		for (int i = 0; i < len_long; i++) {
			int tempInt = this.getBinaryByteFromByte(b[i]);
			dest = (dest << 8) | tempInt;
		}
		return dest;
	}

	@Override
	public short getShort() {

		byte[] b = this.getByteFromBuf(len_short);
		short dest = 0;
		for (int i = 0; i < len_short; i++) {
			int tempInt = this.getBinaryByteFromByte(b[i]);
			dest = (short) ((dest << 8) | tempInt);
		}
		return dest;
	}

	@Override
	public String getString() {

		int len = this.getInt();
		byte[] b = this.getByteFromBuf(len);
		String str = new String(b, charset);
		return str;
	}

	@Override
	public ByteBuf getByteBuf() {

		return this.buf;
	}

	private void writeBytes(byte[] src, int len) {
		for (int i = 0; i < len; i++) {
			writeByte(src[i]);
		}
	}

	private void writeByte(byte value) {
		buf.writeByte(value);
		this.pos += 1;
	}

	private byte[] getByteFromBuf(int len) {
		byte[] dest = new byte[len];
		for (int i = 0; i < len; i++) {
			dest[i] = this.getByteFromBuf();
		}
		return dest;
	}

	private byte getByteFromBuf() {
		byte b = buf.getByte(this.pos);
		this.pos += 1;
		return b;
	}

	/**
	 * 
	 * @param oneByte
	 * @return
	 */
	private int getBinaryByteFromByte(byte oneByte) {

		int tempInt = oneByte & 0xff;
		return tempInt;
	}

	@Override
	public void setByteBuf(ByteBuf buf) {
		this.buf = buf;
	}

	@Override
	public List<Byte> getSeq() {

		return this.seq;
	}

	@Override
	public void resetPosition() {
		this.pos = 0;
	}

}
