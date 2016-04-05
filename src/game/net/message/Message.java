package game.net.message;

public class Message {
	

	public static NettyMessage create() {
		NettyMessage message = new BaseCustomTypeLengthMessage();
		
		return message;
	}
}
