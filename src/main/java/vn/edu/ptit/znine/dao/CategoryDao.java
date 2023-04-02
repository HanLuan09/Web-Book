package vn.edu.ptit.znine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.znine.context.DbContext;
import vn.edu.ptit.znine.model.Category;


public class CategoryDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
    public List<Category> getAllCategory() {
    	List<Category> list = new ArrayList<Category>();
    	String query = "select * from Category";
    	try {
    		conn = new DbContext().getConnection();//mo ket noi voi sql
    		ps = conn.prepareStatement(query);
    		rs = ps.executeQuery();
    		while (rs.next()) {
    			list.add(new Category(rs.getInt(1), rs.getString(2)));
    		}
    	} catch (Exception e) {
    	}
    	return list;
	}
    public List<Category> getAllCategoryExecpt(String cate) {
    	List<Category> list = new ArrayList<Category>();
    	String query = "SELECT *\r\n"
    			+ "FROM Category\r\n"
    			+ "ORDER BY \r\n"
    			+ "	CASE \r\n"
    			+ "		WHEN CateID = ? THEN 0\r\n"
    			+ "		ELSE 1 \r\n"
    			+ "	END, CateID";
    	try {
    		conn = new DbContext().getConnection();//mo ket noi voi sql
    		ps = conn.prepareStatement(query);
    		ps.setString(1, cate);
    		rs = ps.executeQuery();
    		while (rs.next()) {
    			list.add(new Category(rs.getInt(1), rs.getString(2)));
    		}
    	} catch (Exception e) {
    	}
    	return list;
	}
    
    public static void main(String[] args) {
    	CategoryDao dao = new CategoryDao();
		List<Category> list =  dao.getAllCategoryExecpt("3");
		for(Category o: list) {
			System.out.println(o);
		}
	}
}
