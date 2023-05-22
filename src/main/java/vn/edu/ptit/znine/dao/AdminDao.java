package vn.edu.ptit.znine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import vn.edu.ptit.znine.context.DbContext;
import vn.edu.ptit.znine.model.Product;
import vn.edu.ptit.znine.model.ProductAdmin;

@Repository
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
        		+ "where book.[status] = 1\r\n"
        		+ "GROUP BY Book.NameB, Book.IdB, Category.NameC, Book.releaseDate, Book.pages, Book.author\r\n"
        		+ "ORDER BY Book.idB DESC";
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
//	search sản phẩm
	public List<ProductAdmin> getAllProductAdminSearch(String search) {
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
        		+ "where book.[status]=1 and ( Category.NameC like ? or  Book.NameB like ? or Book.author like ?)\r\n"
        		+ "GROUP BY Book.NameB, Book.IdB, Category.NameC, Book.releaseDate, Book.pages, Book.author";
        try {
            conn = new DbContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1,"%"+search+"%");
            ps.setString(2,"%"+search+"%");
            ps.setString(3,"%"+search+"%");
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
//	xóa sản phẩm
	public int deleteProduct(String idB) {
		int result = 0;
		String query = "Delete from Book where idB = ?";
        try {
        	conn = new DbContext().getConnection();//mo ket noi voi sql
	        ps = conn.prepareStatement(query);
	        ps.setString(1, idB);
	        result = ps.executeUpdate();
	        ps.close();
    		conn.close();
    		rs.close();
        } catch (Exception e) {
        }
        return result;
	}
	public int removeProduct(String idB) {
		int result = 0;
		String query = "UPDATE Book set [status] = 0 where idB = ?";
        try {
        	conn = new DbContext().getConnection();//mo ket noi voi sql
	        ps = conn.prepareStatement(query);
	        ps.setString(1, idB);
	        result = ps.executeUpdate();
	        ps.close();
    		conn.close();
    		rs.close();
        } catch (Exception e) {
        }
        return result;
	}
//	 thêm 1 sản phẩm
	public int addProduct(Product p) {
		int result = 0;
  		String query = "INSERT INTO DBO.Book([NameB], [ImageB], [title], [author], [releaseDate], [pages], [CateID], [status]) \r\n"
  				+ "VALUES(?, ?, ?, ?, ?, ?, ?, 1)";
	      try {
	          conn = new DbContext().getConnection();//mo ket noi voi sql
	          ps = conn.prepareStatement(query);
	          ps.setString(1, p.getNameB());
	          ps.setString(2, p.getImageB());
	          ps.setString(3, p.getTitle());
	          ps.setString(4, p.getAuthor());
	          ps.setDate(5, p.getReleaseDate());
	          ps.setInt(6, p.getPages());
	          ps.setInt(7, p.getCateId());
	          result = ps.executeUpdate();
	          
	          conn.close();
	          ps.close();
	          rs.close();
	      } catch (Exception e) {
	      }
	      return result;
  	}
//	 Cập nhật
	public int updateProduct(Product p) {
		int result =0;
  		String query = " UPDATE DBO.Book SET [NameB] =? , [ImageB] = ?, [title] = ?, [author] = ?, [releaseDate]= ?, [pages] = ?, [CateID] = ?\r\n"
  				+ "where idB= ?";
	      try {
	          conn = new DbContext().getConnection();//mo ket noi voi sql
	          ps = conn.prepareStatement(query);
	          ps.setString(1, p.getNameB());
	          ps.setString(2, p.getImageB());
	          ps.setString(3, p.getTitle());
	          ps.setString(4, p.getAuthor());
	          ps.setDate(5, (java.sql.Date)p.getReleaseDate());
	          ps.setInt(6, p.getPages());
	          ps.setInt(7, p.getCateId());
	          ps.setInt(8, p.getIdB());
	          result = ps.executeUpdate();
	          
	          conn.close();
	          ps.close();
	          rs.close();
	      } catch (Exception e) {
	      }
	      return result;
  	}
	public Product checkProductName(String name, String author, String idB) {
    	String query = "select * from Book where nameB = ? and author = ? and idB <> ?";
        try {
            conn = new DbContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, author);
            ps.setString(3, idB);
            rs = ps.executeQuery();
            while (rs.next()) {
                 return new Product(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getInt(8));
                        
            }
            ps.close();
    		conn.close();
    		rs.close();
        } catch (Exception e) {
        }
        return null;
    }
	public static void main(String[] args) {
		AdminDao dao = new AdminDao();
		List<ProductAdmin> list = dao.getAllProductAdminSearch("như đóa hoa");
		for(ProductAdmin o : list) {
			
			System.out.println(o);
		}
	}
}
