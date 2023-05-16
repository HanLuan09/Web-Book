package vn.edu.ptit.znine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.ptit.znine.dao.OrderDao;
import vn.edu.ptit.znine.model.Order;
import vn.edu.ptit.znine.model.OrderDetails;
import vn.edu.ptit.znine.model.UserProduct;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderDao daoOrder;
	@Override
	public List<UserProduct> getOrderBook(String idA) {
		return daoOrder.getOrderBook(idA);
	}
	@Override
	public int addOrderBook(Order o) {
		return daoOrder.addOrderBook(o);
	}
	@Override
	public void addOrderDetailsBook(OrderDetails o) {
		daoOrder.addOrderDetailsBook(o);
	}
}
