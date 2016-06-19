package cn.itcast.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();

	public List<Book> findAll() {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from book";
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}

	public List<Book> findByCategory(String cid) {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from book where cid=?";
			return qr.query(sql, new BeanListHandler<Book>(Book.class), cid);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}

	public Book findByBid(String bid) {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from book where bid=?";
			return qr.query(sql, new BeanHandler<Book>(Book.class), bid);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}

	public int getCountByCid(String cid) {
		try {
			String sql = "select count(*) from book where cid=?";
			Number cnt = (Number) qr.query(sql, new ScalarHandler(), cid);
			return cnt.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
