package cn.itcast.bookstore.user.web.servlet;



import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.user.domain.User;
import cn.itcast.bookstore.user.service.UserException;
import cn.itcast.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
/*
 * User表述层
 */
@SuppressWarnings("serial")
public class UserServlet extends BaseServlet {
	private UserService userService = new UserService();
	
	/*
	 * 退出功能
	 */
	public String quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		return "f:/index.jsp";
	}
	
	/*
	 * 登录功能
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		try {
			User user = userService.login(form);
			request.getSession().setAttribute("session_user", user);
			request.getSession().setAttribute("cart", new Cart());
			return "r:/index.jsp";
		} catch (UserException e) {
			// TODO: handle exception
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
	}
	/*
	 * 激活功能
	 */
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		try {
			userService.active(code);
			request.setAttribute("msg", "恭喜你！激活成功，请马上登录！");
		} catch (UserException e) {
			// TODO: handle exception
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsps/msg.jsp";
	}
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//封装表单数据
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		//补全uid和激活码信息
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid()+CommonUtils.uuid());
		form.setState(false);
		/*
		 * 输入校验，保存错区信息到request中，
		 * 创建一个Map，用来封装错误信息，key为表单字段名称，value为错误信息
		 */
		
		Map<String, String> errors = new HashMap<String, String>();
		
		/*
		 * 将错误信息封装到Map
		 */
		String username = form.getUsername();
		if(username ==null || username.trim().isEmpty()){
			errors.put("username", "用户名不能为null或空");
		} else if(username.length() < 3 || username.length() > 10){
			errors.put("username", "用户名长度必须在3~10之间");
		}
		
		String password = form.getPassword();
		if(password ==null || password.trim().isEmpty()){
			errors.put("password", "密码不能为null或空");
		} else if(password.length() < 3 || password.length() > 10){
			errors.put("password", "密码长度必须在3~10之间");
		}
		
		String email = form.getEmail();
		if(email == null || email.trim().isEmpty()){
			errors.put("email", "邮箱不能为null或空");
		} else if(!email.matches("\\w+@\\w+\\.\\w+")){
			errors.put("email", "邮箱格式不对");
		}
		
		//判断是否存在错误信息
		if(errors.size() > 0){
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}

		try {
			userService.regist(form);
			/*
			 * 执行到这里，表明没有错误信息
			 * 
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}

		/*
		 *发邮件
		 *准备配置文件 
		 */
		//获取配置文件内容
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		String host = props.getProperty("host");
		String uname = props.getProperty("uname");
		String pwd = props.getProperty("pwd");
		String from = props.getProperty("from");
		String to = form.getEmail();
		String subject = props.getProperty("subject");
		String content = props.getProperty("content");
		content = MessageFormat.format(content, form.getCode());//替换占位符，即将{0}修改为激活码code

		Session session = MailUtils.createSession(host, uname, pwd);//得到session
		Mail mail = new Mail(from, to, subject, content);
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* 1》保存成功信息
		 * 2》转发到msg.jsp
		 */
		request.setAttribute("msg", "注册成功! 请马上到邮箱激活");
		return "f:/jsps/msg.jsp";
		
	}
}
