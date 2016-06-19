package cn.itcast.bookstore.order.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.itcast.bookstore.user.domain.User;

public class Order {
	private String oid;
	private Date ordertime;
	private double total;
	private int state;
	private User owner;
	private String adress;
	
	private List<OrderItem> orderItemList = new LinkedList<OrderItem>();
	
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	
	
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	
}
