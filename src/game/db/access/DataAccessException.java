package game.db.access;

/**
 * 数据库存取异常
 * 
 * @author ming 创建时间：2013-11-19
 */
public class DataAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataAccessException(Throwable cause) {
		super(cause);
	}

}
