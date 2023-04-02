package vn.edu.ptit.znine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.edu.ptit.znine.context.DbContext;
import vn.edu.ptit.znine.model.ProductAdmin;

public class AdminDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
//	lấy tất cả sản phẩm
	public List<ProductAdmin> getAllProductCate() {
        List<ProductAdmin> list = new ArrayList<ProductAdmin>();
        String query = "SELECT Book.IdB AS 'IdB', \r\n"
        		+ "	   Book.NameB AS 'nameB',\r\n"
        		+ "	   Book.author AS 'author',\r\n"
        		+ "       Category.NameC AS 'nameC',\r\n"
        		+ "	   --Book.Amount AS 'pAmount',\r\n"
        		+ "	   Book.releaseDate AS 'releaseDate',\r\n"
        		+ "	   Book.pages AS 'pages',\r\n"
        		+ "       SUM(OrderDetails.Amount) AS 'sumPrice'\r\n"
        		+ "FROM Book\r\n"
        		+ "INNER JOIN Category ON Book.CateID = Category.CateID\r\n"
        		+ "LEFT JOIN OrderDetails ON Book.IdB = OrderDetails.IdB\r\n"
        		+ "--where Category.NameC like N'%chó%' or  Product.NameP like N'%mèo%'\r\n"
        		+ "GROUP BY Book.NameB, Book.IdB, Category.NameC, Book.releaseDate, Book.pages, Book.author";
        try {
            conn = new DbContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ProductAdmin(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getInt(6),
                        rs.getInt(7)));
            }
        } catch (Exception e) {
        }
        return list;
    }
	public static void main(String[] args) {
		AdminDao dao = new AdminDao();
		List<ProductAdmin> list = dao.getAllProductCate();
		for(ProductAdmin o : list) {
			
			System.out.println(o);
		}
	}
}
