package vn.edu.ptit.znine.service;

import java.util.List;

import vn.edu.ptit.znine.model.Order;
import vn.edu.ptit.znine.model.OrderDetails;
import vn.edu.ptit.znine.model.UserProduct;

public interface OrderService {
	public List<UserProduct> getOrderBook(String idA);
	public int addOrderBook(Order o);
	public int addOrderDetailsBook(OrderDetails o);
	public int removeOrderBook(int idO, int idB);
}
