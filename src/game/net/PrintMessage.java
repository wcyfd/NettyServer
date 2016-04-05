package game.net;

import java.util.List;

import game.net.message.NettyMessage;

public class PrintMessage {
	public static void printMessage(NettyMessage message){
		message.resetPosition();
		List<Byte> seq = message.getSeq();
		for (Byte b : seq) {
			switch (b) {
			case NettyMessage._byte:
				System.out.println("Byte:" + message.getByte());
				break;
			case NettyMessage._boolean:
				System.out.println("Boolean:" + message.getBoolean());
				break;
			case NettyMessage._short:
				System.out.println("Short" + message.getShort());
				break;
			case NettyMessage._int:
				System.out.println("Int" + message.getInt());
				break;
			case NettyMessage._float:
				System.out.println("Float" + message.getFloat());
				break;
			case NettyMessage._double:
				System.out.println("Double" + message.getDouble());
				break;
			case NettyMessage._long:
				System.out.println("Long" + message.getLong());
				break;
			case NettyMessage._string:
				System.out.println("String" + message.getString());
				break;
			}
		}
	}
}
