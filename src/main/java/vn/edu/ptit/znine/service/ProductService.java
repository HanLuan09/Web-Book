package vn.edu.ptit.znine.service;

import java.util.List;

import vn.edu.ptit.znine.model.Product;

public interface ProductService {
	public List<Product> getAllProduct();
	public List<Product> getTop10Product();
	public Product getProductById(String id);
	public List<Product> getAllProductByCateId(String cateId);
	public void deleteProduct(String idB);
	public void addProduct(Product p);
	public void updateProduct(Product p);
	public Product checkProductName(String name);
}
