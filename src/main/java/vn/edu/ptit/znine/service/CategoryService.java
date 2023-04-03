package vn.edu.ptit.znine.service;

import java.util.List;

import vn.edu.ptit.znine.model.Category;

public interface CategoryService {
	public List<Category> getAllCategory();
	public List<Category> getAllCategoryExecpt(String cate);
}
