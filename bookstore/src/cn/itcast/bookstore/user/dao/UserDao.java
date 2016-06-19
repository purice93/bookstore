package cn.itcast.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.bookstore.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;
/*
 * User持久层
 */
public class UserDao {
	private QueryRunner qr = new TxQueryRunner(); 
	
	/*
	 * 按用户名查询
	 */
	public User findByUsername(String username){
		try {
			String sql = "select * from tb_user where username=?";
			return qr.query(sql, new BeanHandler<User>(User.class), username);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}
	
	/*
	 * 按用户名查询
	 */
	public User findByEmail(String email){
		try {
			String sql = "select * from tb_user where email=?";
			return qr.query(sql, new BeanHandler<User>(User.class), email);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}
	
	/*
	 * 插入User
	 */
	public void add(User user) {
		try {
			String sql = "insert into tb_user value(?,?,?,?,?,?)";
			Object[] params = { user.getUid(), user.getUsername(),
					user.getPassword(), user.getEmail(), user.getCode(),
					user.getState() };
			qr.update(sql, params);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}
	
	
	/*
	 * 按激活码查询
	 */
	public User findByCode(String code){
		try {
			String sql = "select * from tb_user where code=?";
			return qr.query(sql, new BeanHandler<User>(User.class), code);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}
	
	
	/*
	 * 修改指定用户的指定状态
	 */
	
	public void updateState(String uid, int state){
		try {
			String sql = "update tb_user set state=? where uid=?";
			qr.update(sql, state, uid);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}
}
