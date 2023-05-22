package vn.edu.ptit.znine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.ptit.znine.dao.AdminDao;
import vn.edu.ptit.znine.model.Product;
import vn.edu.ptit.znine.model.ProductAdmin;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminDao dAdminDao;
	
	@Override
	public List<ProductAdmin> getAllProductCate() {
		return dAdminDao.getAllProductCate();
	}
	@Override
	public List<ProductAdmin> getAllProductAdminSearch(String search) {
		return dAdminDao.getAllProductAdminSearch(search);
	}
	@Override
	public int addProduct(Product p) {
		return dAdminDao.addProduct(p);
	}
	@Override
	public int updateProduct(Product p) {
		return dAdminDao.updateProduct(p);
	}
	@Override
	public int deleteProduct(String idB) {
		return dAdminDao.deleteProduct(idB);
	}
	@Override
	public int removeProduct(String idB) {
		return dAdminDao.removeProduct(idB);
	}
	@Override
	public Product checkProductName(String name, String author, String idB) {
		return dAdminDao.checkProductName(name, author, idB);
	}
}
