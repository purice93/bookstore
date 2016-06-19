package cn.itcast.bookstore.category.service;

import java.util.List;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.category.dao.CategoryDao;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.web.servlet.admin.CategoryException;

public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	private BookDao bookDao = new BookDao();

	/*
	 * 查询所有分类
	 */
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	public void add(Category category) {
		// TODO Auto-generated method stub
		categoryDao.add(category);
	}

	public void edit(Category category) {
		// TODO Auto-generated method stub
		categoryDao.edit(category);
	}

	public void delete(String cid) throws CategoryException {
		// 获取该分类下图书的本数
		int count = bookDao.getCountByCid(cid);
		// 如果该分类下存在图书，不让删除，我们抛出异常
		if(count > 0) throw new CategoryException("该分类下还有图书，不能删除！");
		// 删除该分类
		categoryDao.delete(cid);
	}

	public Category load(String cid) {
		// TODO Auto-generated method stub
		return categoryDao.load(cid);
	}
}
