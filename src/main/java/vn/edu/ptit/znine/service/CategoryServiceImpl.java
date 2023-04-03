package vn.edu.ptit.znine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.ptit.znine.dao.CategoryDao;
import vn.edu.ptit.znine.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDao cateDao;
	@Override
	public List<Category> getAllCategory() {
		return cateDao.getAllCategory();
	}
	@Override
	public List<Category> getAllCategoryExecpt(String cate) {
		return cateDao.getAllCategoryExecpt(cate);
	}
}
