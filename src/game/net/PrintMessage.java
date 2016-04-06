package game.net;

import game.entity.bo.Role;
import game.net.message.NettyMessage;

import java.util.List;

public class PrintMessage {
	public static void printMessage(Role role, NettyMessage message) {
		if (role == null) {
			System.out.println("role is null");
		}
		int protocal = message.getType();
		if (protocal == 0) {
			System.out.println("伪造的协议号");
		} else {
			Integer roleId = role.getId();
			message.resetReaderIndex();
			List<Byte> seq = message.getSeq();

			System.out.println("write back " + roleId + " protocal: " + protocal);

			for (int i = 1; i < seq.size(); i++) {
				Byte b = seq.get(i);
				System.out.print("output--" + roleId + "--");
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
}
