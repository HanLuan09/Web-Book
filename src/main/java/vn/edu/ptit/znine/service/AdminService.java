package vn.edu.ptit.znine.service;

import java.util.List;
import vn.edu.ptit.znine.model.Product;
import vn.edu.ptit.znine.model.ProductAdmin;

public interface AdminService {
	public List<ProductAdmin> getAllProductCate(); 
	public int addProduct(Product p);
	public int updateProduct(Product p);
	public int deleteProduct(String idB);
	public int removeProduct(String idB);
	public Product checkProductName(String name, String author, String idB);
	public List<ProductAdmin> getAllProductAdminSearch(String search);
}
