package game.db.access;

import game.db.convert.base.ResultConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;





/**
 * 数据存取类
 * 
 */
public abstract class DataAccess {


	/**
	 * 插入数据
	 * 
	 * @param sql
	 * @param generatedKeysConverter
	 *            主键映射
	 * @param params
	 * @return 主键
	 * @throws DataAccessException
	 * @throws SQLException
	 * @throws SQLException
	 */
	protected <T> T insert(String sql,
			ResultConverter<T> generatedKeysConverter, Connection conn,
			Object... params) throws DataAccessException, SQLException {
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			setParameters(pstmt, params);
			executeUpdate(pstmt, conn);
			ResultSet rs = pstmt.getGeneratedKeys();
			boolean temp = nextResult(rs);
			if (temp) {
				T aaa = convertResult(rs, generatedKeysConverter);
				rs.close();
				pstmt.close();
				return aaa;
			}
			rs.close();
			pstmt.close();
			return null;
		} finally {
			conn.close();
		}
	}

	/**
	 * 插入数据不关闭连接
	 * 
	 * @param <T>
	 * @param sql
	 * @param generatedKeysConverter
	 * @param conn
	 * @param params
	 * @return
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	protected <T> T insertNotCloseConn(String sql,
			ResultConverter<T> generatedKeysConverter, Connection conn,
			Object... params) throws DataAccessException, SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		setParameters(pstmt, params);
		executeUpdate(pstmt, conn);
		ResultSet rs = pstmt.getGeneratedKeys();
		boolean temp = nextResult(rs);
		if (temp) {
			T aaa = convertResult(rs, generatedKeysConverter);
			rs.close();
			pstmt.close();
			return aaa;
		}
		rs.close();
		pstmt.close();
		return null;
	}

	/**
	 * 更新数据
	 * 
	 * @param sql
	 * @param params
	 * @return 影响行数
	 * @throws DataAccessException
	 */
	protected int update(String sql, Connection conn, Object... params)
			throws DataAccessException, SQLException {
		try {
			PreparedStatement pstmt = getPreparedStatement(sql, conn, params);
			int result = executeUpdate(pstmt, conn);
			pstmt.close();
			return result;
		} finally {
			conn.close();
		}
	}

	/**
	 * 更新数据不关闭连接
	 * 
	 * @param sql
	 * @param conn
	 * @param params
	 * @return
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	protected int updateNotCloseConn(String sql, Connection conn,
			Object... params) throws DataAccessException, SQLException {
		PreparedStatement pstmt = getPreparedStatement(sql, conn, params);
		int result = executeUpdate(pstmt, conn);
		pstmt.close();
		return result;
	}

	/**
	 * 删除数据
	 * 
	 * @param sql
	 * @param conn
	 * @param params
	 * @return
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	protected int delete(String sql, Connection conn, Object... params)
			throws DataAccessException, SQLException {
		try {
			PreparedStatement pstmt = getPreparedStatement(sql, conn, params);
			int result = executeUpdate(pstmt, conn);
			pstmt.close();
			return result;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询单个结果
	 * 
	 * @param <T>
	 * @param sql
	 * @param converter
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	protected <T> T queryForObject(String sql, ResultConverter<T> converter,
			Connection conn, Object... params) throws DataAccessException,
			SQLException {
		try {
			ResultSet rs = executeQuery(sql, conn, params);
			if (nextResult(rs)) {
				T result = convertResult(rs, converter);
				rs.close();
				return result;
			} else {
				rs.close();
				return null;
			}
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询结果列表
	 * 
	 * @param <T>
	 * @param sql
	 * @param converter
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	protected <T> List<T> queryForList(String sql,
			ResultConverter<T> converter, Connection conn, Object... params)
			throws DataAccessException, SQLException {
		try {
			ResultSet rs = executeQuery(sql, conn, params);
			List<T> list = new ArrayList<T>();
			while (nextResult(rs)) {
				list.add(convertResult(rs, converter));
			}
			rs.close();
			return list;
		} finally {
			conn.close();
		}
	}

	/**
	 * @param sql
	 *            SQL语句
	 * @return 预编译声明
	 */
	private PreparedStatement getPreparedStatement(String sql, Connection conn,
			Object... params) throws DataAccessException {
		PreparedStatement pstmt = getPreparedStatement(sql, conn);
		setParameters(pstmt, params);
		return pstmt;
	}

	/**
	 * @param sql
	 *            SQL语句
	 * @return 预编译声明
	 */
	private PreparedStatement getPreparedStatement(String sql, Connection conn)
			throws DataAccessException {
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	/**
	 * 为预编译声明传入参数
	 * 
	 * @param pstmt
	 *            预编译声明
	 * @param params
	 *            参数
	 * @throws DataAccessException
	 */
	private void setParameters(PreparedStatement pstmt, Object... params)
			throws DataAccessException {
		try {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	/**
	 * 执行更新操作
	 * 
	 * @param pstmt
	 * @return 影响行数
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	private int executeUpdate(PreparedStatement pstmt, Connection conn)
			throws DataAccessException, SQLException {
		try {
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	/**
	 * 执行查询操作
	 * 
	 * @param pstmt
	 *            预编译声明
	 * @return 结果集
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	private ResultSet executeQuery(PreparedStatement pstmt, Connection conn)
			throws DataAccessException, SQLException {
		try {
			return pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	/**
	 * 执行查询操作
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 结果集
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	private ResultSet executeQuery(String sql, Connection conn,
			Object... params) throws DataAccessException, SQLException {
		PreparedStatement pstmt = getPreparedStatement(sql, conn, params);
		return executeQuery(pstmt, conn);
	}

	/**
	 * 移动到下一行记录
	 * 
	 * @param rs
	 *            结果集
	 * @return 是否有下一行记录
	 * @throws DataAccessException
	 */
	private boolean nextResult(ResultSet rs) throws DataAccessException {
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	/**
	 * 映射
	 * 
	 * @param rs
	 *            结果集
	 * @return 映射结果
	 * @throws DataAccessException
	 */
	private <T> T convertResult(ResultSet rs, ResultConverter<T> converter)
			throws DataAccessException {
		try {
			return converter.convert(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

}
