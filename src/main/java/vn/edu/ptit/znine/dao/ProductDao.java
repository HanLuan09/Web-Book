package vn.edu.ptit.znine.dao;


import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.znine.context.DbContext;
import vn.edu.ptit.znine.model.Product;

@Repository
public class ProductDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
//	lấy tất cả sản phẩm
	public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<Product>();
        String query = "select * from Book";
        try {
            conn = new DbContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getInt(8)));
            }
        } catch (Exception e) {
        }
        return list;
    }
//	lấy top 10 sản phẩm
	public List<Product> getTop10Product() {
        List<Product> list = new ArrayList<Product>();
        String query = "SELECT TOP 10\r\n"
        		+ "	   Book. *,\r\n"
        		+ "       SUM(OrderDetails.Amount) AS 'sumPrice'\r\n"
        		+ "FROM Book\r\n"
        		+ "LEFT JOIN OrderDetails ON Book.IdB = OrderDetails.IdB\r\n"
        		+ "GROUP BY Book.NameB, Book.IdB, Book.releaseDate, Book.pages, Book.author, Book.imageB, Book.title, Book.CateId\r\n"
        		+ "order by sumPrice DESC";
        try {
            conn = new DbContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getInt(8)));
            }
        } catch (Exception e) {
        }
        return list;
    }
//	lấy 1 sản phẩm theo mã book
	public Product getProductById(String id) {
    	String query = "select * from Book where idB = ?";
        try {
            conn = new DbContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
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
        } catch (Exception e) {
        }
        return new Product();
    }
//	lấy tất cả sản phẩm theo cateid
	public List<Product> getAllProductByCateId(String cateId) {
		List<Product> list = new ArrayList<Product>();
        String query = "select * from Book where cateId= ?";
        try {
            conn = new DbContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, cateId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getInt(8)));
            }
        } catch (Exception e) {
        }
        return list;
    }
//	xóa sản phẩm
	public void deleteProduct(String idB) {
		String query = "Delete from Book where idB = ?";
        try {
        	conn = new DbContext().getConnection();//mo ket noi voi sql
	        ps = conn.prepareStatement(query);
	        ps.setString(1, idB);
	        ps.executeUpdate();
            
        } catch (Exception e) {
        }
   
	}
//	 thêm 1 sản phẩm
	public void addProduct(Product p) {
		
  		String query = "INSERT INTO DBO.Book([NameB], [ImageB], [title], [author], [releaseDate], [pages], [CateID]) \r\n"
  				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
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
	          ps.executeUpdate();
	          
	          conn.close();
	          ps.close();
	          rs.close();
	      } catch (Exception e) {
	      }
	      
  	}
//	 Cập nhật
	public void updateProduct(Product p) {
		
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
	          ps.executeUpdate();
	          
	          conn.close();
	          ps.close();
	          rs.close();
	      } catch (Exception e) {
	      }
  	}
	public Product checkProductName(String name) {
    	String query = "select * from Book where nameB = ?";
        try {
            conn = new DbContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
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
        } catch (Exception e) {
        }
        return null;
    }
	public static void main(String[] args) {
		ProductDao dao = new ProductDao();
		Product p = dao.getProductById("-1");
		List<Product> list = dao.getTop10Product();
		for(Product o: list) {
			System.out.println(o);
		}
		System.out.println(p);
		
	}
}
