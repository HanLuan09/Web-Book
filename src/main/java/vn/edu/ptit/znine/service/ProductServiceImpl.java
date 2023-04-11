package vn.edu.ptit.znine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.ptit.znine.dao.ProductDao;
import vn.edu.ptit.znine.model.Product;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao daoP;
	
	@Override
	public List<Product> getAllProduct() {
		List<Product> p = daoP.getAllProduct();
		return p;
	}
	@Override
	public List<Product> getTop10Product() {
		List<Product> p = daoP.getTop10Product();
		return p;
	}
	@Override
	public Product getProductById(String id) {
		return daoP.getProductById(id);
	}
	@Override
	public List<Product> getAllProductByCateId(String cateId) {
		List<Product> p = daoP.getAllProductByCateId(cateId);
		return p;
	}
	@Override
	public List<Product> getAllProductSearch(String search) {
		return daoP.getAllProductSearch(search);
	}
	@Override
	public void addProduct(Product p) {
		daoP.addProduct(p);
	}
	@Override
	public void updateProduct(Product p) {
		daoP.updateProduct(p);
	}
	@Override
	public void deleteProduct(String idB) {
		daoP.deleteProduct(idB);
	}
	@Override
	public Product checkProductName(String name, String idB) {
		return daoP.checkProductName(name, idB);
	}
	// sort
	@Override
	public List<Product> getAllProductSelling() {
		
		return daoP.getAllProductSelling();
	}
	@Override
	public List<Product> getAllProductLike() {
		
		return daoP.getAllProductLike();
	}
	@Override
	public List<Product> getAllProductNew() {
		
		return daoP.getAllProductNew();
	}
}
