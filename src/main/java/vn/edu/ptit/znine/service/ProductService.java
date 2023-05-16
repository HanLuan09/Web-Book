package vn.edu.ptit.znine.service;

import java.util.List;

import vn.edu.ptit.znine.model.Product;
import vn.edu.ptit.znine.model.ProductDetail;

public interface ProductService {
	public List<Product> getAllProduct();
	public List<Product> getTop10Product();
	public Product getProductById(String id);
	public List<Product> getAllProductByCateId(String cateId);
	List<ProductDetail> getAllProductSearch(String search);
//	sort
	public List<Product> getAllProductSelling();
	public List<Product> getAllProductNew();
	public List<Product> getAllProductLike();
//	product detail
	public List<ProductDetail> getAllProductDetail();
	public List<ProductDetail> getAllProductDetailByCateId(String cateId);
	public ProductDetail getProductDetailById(String id);
}
