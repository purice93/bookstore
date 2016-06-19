package cn.itcast.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.bookstore.order.dao.OrderDao;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	private OrderDao orderDao = new OrderDao();
	
	public void add(Order order){
		try {
			JdbcUtils.beginTransaction();
			orderDao.addOrder(order);
			orderDao.addOrderItemList(order.getOrderItemList());
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			// TODO: handle exception
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e2) {
				// TODO: handle exception
			}
			throw new RuntimeException(e);
		}
	}

	public List<Order> myOrders(String uid) {
		return orderDao.findByUid(uid);
	}

	public Order load(String oid) {
		// TODO Auto-generated method stub
		return orderDao.load(oid);
	}

	public void confirm(String oid) throws orderException {
		// TODO Auto-generated method stub
		int state = orderDao.getStateByOid(oid);
		if(state != 3) throw new orderException("确认收货失败");
		orderDao.updateState(oid, 4);
	}

	public void zhiFu(String oid) {
		// TODO Auto-generated method stub
		int state = orderDao.getStateByOid(oid);
		if(state == 1) {
			// 修改订单状态为2
			orderDao.updateState(oid, 2);
		}
	}
}
