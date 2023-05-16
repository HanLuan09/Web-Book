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
	public void addProduct(Product p) {
		dAdminDao.addProduct(p);
	}
	@Override
	public void updateProduct(Product p) {
		dAdminDao.updateProduct(p);
	}
	@Override
	public void deleteProduct(String idB) {
		dAdminDao.deleteProduct(idB);
	}
	@Override
	public void removeProduct(String idB) {
		dAdminDao.removeProduct(idB);
	}
	@Override
	public Product checkProductName(String name, String author, String idB) {
		return dAdminDao.checkProductName(name, author, idB);
	}
}
