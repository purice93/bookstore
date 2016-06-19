package cn.itcast.bookstore.book.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.book.domain.Book;

public class BookService {
	private BookDao bookDao = new BookDao();

	public List<Book> findAll() {
		// TODO Auto-generated method stub
		return bookDao.findAll();
	}

	public List<Book> findByCategory(String cid) {
		// TODO Auto-generated method stub
		return bookDao.findByCategory(cid);
	}

	public Book load(String bid) {
		// TODO Auto-generated method stub
		return bookDao.findByBid(bid);
	}
}
