package cn.itcast.bookstore.user.service;

import cn.itcast.bookstore.user.dao.UserDao;
import cn.itcast.bookstore.user.domain.User;
/*
 * User业务层
 * 
 */
public class UserService {
	private UserDao userDao = new UserDao();
	
	/*
	 * 注册功能
	 */
	public void regist(User form) throws Exception{
		/*
		 * 检验用户名
		 */
		User user = userDao.findByUsername(form.getUsername());
		if(user != null) throw new UserException("用户名已经被注册");
		
		/*
		 * 检验邮箱
		 */
		user = userDao.findByUsername(form.getEmail());
		if(user != null) throw new UserException("邮箱已经被注册");
		
		/*
		 * 插入用户到数据库
		 */
		userDao.add(form);
	}
	
	/*
	 * 激活功能
	 */
	
	public void active(String code) throws UserException{
		User user = userDao.findByCode(code);
		
		if(user == null) throw new UserException("激活码不存在");
		
		if(user.getState()) throw new UserException("你已经激活过了，请直接登录！");
		
		//修改用户的状态
		userDao.updateState(user.getUid(), 1);
	}
	
	public User login(User form) throws UserException{
		User user = userDao.findByUsername(form.getUsername());
		
		if(user.getUsername() == null) throw new UserException("用户名不存在");
		
		if(!user.getPassword().equals(form.getPassword())) throw new UserException("密码不正确");
		
		if(!user.getState()) throw new UserException("尚未激活");
		
		return user;
	}
}
