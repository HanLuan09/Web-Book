package vn.edu.ptit.znine.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.edu.ptit.znine.model.Product;
import vn.edu.ptit.znine.model.ProductAdmin;

public interface AdminService {
	public List<ProductAdmin> getAllProductCate(); 
	public void addProduct(Product p);
	public void updateProduct(Product p);
	public void deleteProduct(String idB);
	public void removeProduct(String idB);
	public Product checkProductName(String name, String author, String idB);
	public List<ProductAdmin> getAllProductAdminSearch(String search);
}
