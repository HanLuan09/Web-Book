package vn.edu.ptit.znine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.ptit.znine.dao.ProductDao;
import vn.edu.ptit.znine.model.Product;
import vn.edu.ptit.znine.model.ProductDetail;

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
	public Product getProductById(String id) {
		return daoP.getProductById(id);
	}
	@Override
	public List<Product> getAllProductByCateId(String cateId) {
		List<Product> p = daoP.getAllProductByCateId(cateId);
		return p;
	}
	@Override
	public List<ProductDetail> getAllProductSearch(String search) {
		return daoP.getAllProductSearch(search);
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
//	productdetail
	@Override
	public List<ProductDetail> getAllProductDetail() {
		return daoP.getAllProductDetail();
	}
	@Override
	public List<ProductDetail> getAllProductDetailByCateId(String cateId) {
		return daoP.getAllProductDetailByCateId(cateId);
	}
	@Override
	public ProductDetail getProductDetailById(String id) {
		return daoP.getProductDetailById(id);
	}
	
}
