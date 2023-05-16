package vn.edu.ptit.znine.dao;


import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.znine.context.DbContext;
import vn.edu.ptit.znine.model.Product;
import vn.edu.ptit.znine.model.ProductDetail;

@Repository
public class ProductDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
//	lấy tất cả sản phẩm
	public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<Product>();
        String query = "select * from Book where [book].[status] = 1";
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
            ps.close();
    		conn.close();
    		rs.close();
        } catch (Exception e) {
        }
        return list;
    }
//	lấy top 10 sản phẩm
	public List<Product> getTop10Product() {
        List<Product> list = new ArrayList<Product>();
        String query = "SELECT TOP 10\r\n"
        		+ "dbo.Book.idB,\r\n"
        		+ "dbo.Book.nameB,\r\n"
        		+ "dbo.Book.imageB,\r\n"
        		+ "dbo.Book.title,\r\n"
        		+ "dbo.Book.author,\r\n"
        		+ "dbo.Book.releaseDate,\r\n"
        		+ "dbo.Book.pages,\r\n"
        		+ "dbo.Book.CateId,\r\n"
        		+ "SUM(OrderDetails.Amount) AS 'sumPrice'\r\n"
        		+ "FROM Book\r\n"
        		+ "LEFT JOIN OrderDetails ON Book.IdB = OrderDetails.IdB\r\n"
        		+ "where [book].[status] = 1\r\n"
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
            ps.close();
    		conn.close();
    		rs.close();
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
            ps.close();
    		conn.close();
    		rs.close();
        } catch (Exception e) {
        }
        return new Product();
    }
//	
	
//	lấy tất cả sản phẩm theo cateid
	public List<Product> getAllProductByCateId(String cateId) {
		List<Product> list = new ArrayList<Product>();
        String query = "select * from Book where cateId= ? and book.[status]=1";
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
            ps.close();
    		conn.close();
    		rs.close();
        } catch (Exception e) {
        }
        return list;
    }
	

//	sort
//	bán chạy
	public List<Product> getAllProductSelling() {
        List<Product> list = new ArrayList<Product>();
        String query = "SELECT dbo.Book.idB,\r\n"
        		+ "dbo.Book.nameB,\r\n"
        		+ "dbo.Book.imageB,\r\n"
        		+ "dbo.Book.title,\r\n"
        		+ "dbo.Book.author,\r\n"
        		+ "dbo.Book.releaseDate,\r\n"
        		+ "dbo.Book.pages,\r\n"
        		+ "dbo.Book.CateId,\r\n"
        		+ "SUM(OrderDetails.Amount) AS TotalSold\r\n"
        		+ "FROM Book\r\n"
        		+ "LEFT JOIN OrderDetails ON Book.idB = OrderDetails.IdB\r\n"
        		+ "GROUP BY Book.idB, Book.nameB, Book.imageB, Book.title, Book.author, Book.releaseDate, Book.pages, Book.CateId\r\n"
        		+ "ORDER BY TotalSold DESC";
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
            ps.close();
    		conn.close();
    		rs.close();
        } catch (Exception e) {
        }
        return list;
    }
//	mới
	public List<Product> getAllProductNew() {
        List<Product> list = new ArrayList<Product>();
        String query = "SELECT *\r\n"
        		+ "FROM Book\r\n"
        		+ "ORDER BY Book.idB DESC;";
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
            ps.close();
    		conn.close();
    		rs.close();
        } catch (Exception e) {
        }
        return list;
    }
//	yêu thích
	public List<Product> getAllProductLike() {
        List<Product> list = new ArrayList<Product>();
        String query = "SELECT b.*\r\n"
        		+ "FROM Book b\r\n"
        		+ "LEFT JOIN (\r\n"
        		+ "    SELECT IdB, AVG(Evaluate) as avg_evaluate\r\n"
        		+ "    FROM BookValue\r\n"
        		+ "    GROUP BY IdB\r\n"
        		+ ") bv ON b.IdB = bv.IdB\r\n"
        		+ "ORDER BY bv.avg_evaluate DESC";
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
            ps.close();
    		conn.close();
    		rs.close();
        } catch (Exception e) {
        }
        return list;
    }
//	end sort
//	productdetail
	public List<ProductDetail> getAllProductDetail() {
        List<ProductDetail> list = new ArrayList<ProductDetail>();
        String query = "SELECT b.idB,\r\n"
        		+ "b.nameB,\r\n"
        		+ "b.imageB,\r\n"
        		+ "b.title,\r\n"
        		+ "b.author,\r\n"
        		+ "b.releaseDate,\r\n"
        		+ "b.pages,\r\n"
        		+ "b.CateId, SUM(od.Amount) AS TotalSold, CAST(AVG(br.rating * 1.0) AS DECIMAL(10, 1)) AS AverageRating, COUNT(br.rating) AS TotalRating\r\n"
    			+ "FROM Book b\r\n"
    			+ "LEFT JOIN OrderDetails od ON b.IdB = od.IdB\r\n"
    			+ "LEFT JOIN [Order] o ON o.IdO = od.IdO\r\n"
    			+ "LEFT JOIN BookRating br ON b.IdB = br.IdB AND br.IdA = o.IdA and br.idO =od.IdO\r\n"
    			+ "where b.[status] = 1\r\n"
    			+ "GROUP BY b.NameB, b.IdB, b.releaseDate, b.pages, b.author, b.imageB, b.title, b.CateId";
        try {
            conn = new DbContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
            	list.add(new ProductDetail(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getFloat(10),
                        rs.getInt(11)));
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        
        return list;
    }
	public List<ProductDetail> getAllProductDetailByCateId(String cateId) {
        List<ProductDetail> list = new ArrayList<ProductDetail>();
        String query = "SELECT b.idB,\r\n"
        		+ "b.nameB,\r\n"
        		+ "b.imageB,\r\n"
        		+ "b.title,\r\n"
        		+ "b.author,\r\n"
        		+ "b.releaseDate,\r\n"
        		+ "b.pages,\r\n"
        		+ "b.CateId, SUM(od.Amount) AS TotalSold, CAST(AVG(br.rating * 1.0) AS DECIMAL(10, 1)) AS AverageRating, COUNT(br.rating) AS TotalRating\r\n"
    			+ "FROM Book b\r\n"
    			+ "LEFT JOIN OrderDetails od ON b.IdB = od.IdB\r\n"
    			+ "LEFT JOIN [Order] o ON o.IdO = od.IdO\r\n"
    			+ "LEFT JOIN BookRating br ON b.IdB = br.IdB AND br.IdA = o.IdA and br.idO =od.IdO\r\n"
    			+ "where b.[status] = 1 and b.cateId = ?\r\n"
    			+ "GROUP BY b.NameB, b.IdB, b.releaseDate, b.pages, b.author, b.imageB, b.title, b.CateId";
        
        try {
            conn = new DbContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, cateId);
            rs = ps.executeQuery();
        
            while (rs.next()) {
            	list.add(new ProductDetail(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getFloat(10),
                        rs.getInt(11)));
            }
            conn.close();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
        
        return list;
    }
	public ProductDetail getProductDetailById(String id) {
		 String query = "SELECT b.idB,\r\n"
	        		+ "b.nameB,\r\n"
	        		+ "b.imageB,\r\n"
	        		+ "b.title,\r\n"
	        		+ "b.author,\r\n"
	        		+ "b.releaseDate,\r\n"
	        		+ "b.pages,\r\n"
	        		+ "b.CateId, SUM(od.Amount) AS TotalSold, CAST(AVG(br.rating * 1.0) AS DECIMAL(10, 1)) AS AverageRating, COUNT(br.rating) AS TotalRating\r\n"
	    			+ "FROM Book b\r\n"
	    			+ "LEFT JOIN OrderDetails od ON b.IdB = od.IdB\r\n"
	    			+ "LEFT JOIN [Order] o ON o.IdO = od.IdO\r\n"
	    			+ "LEFT JOIN BookRating br ON b.IdB = br.IdB AND br.IdA = o.IdA and br.idO =od.IdO\r\n"
	    			+ "where b.[status] = 1 and b.idB = ?\r\n"
	    			+ "GROUP BY b.NameB, b.IdB, b.releaseDate, b.pages, b.author, b.imageB, b.title, b.CateId";
        try {
            conn = new DbContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                 return new ProductDetail(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getFloat(10),
                        rs.getInt(11));
                        
            }
            ps.close();
    		conn.close();
    		rs.close();
        } catch (Exception e) {
        }
        return null;
    }
	//tìm kiếm sản phẩm
		public List<ProductDetail> getAllProductSearch(String search) {
			List<ProductDetail> list = new ArrayList<>();
			 String query = "SELECT b.idB,\r\n"
		        		+ "b.nameB,\r\n"
		        		+ "b.imageB,\r\n"
		        		+ "b.title,\r\n"
		        		+ "b.author,\r\n"
		        		+ "b.releaseDate,\r\n"
		        		+ "b.pages,\r\n"
		        		+ "b.CateId, SUM(od.Amount) AS TotalSold, CAST(AVG(br.rating * 1.0) AS DECIMAL(10, 1)) AS AverageRating, COUNT(br.rating) AS TotalRating\r\n"
		    			+ "FROM Book b\r\n"
		    			+ "LEFT JOIN OrderDetails od ON b.IdB = od.IdB\r\n"
		    			+ "LEFT JOIN [Order] o ON o.IdO = od.IdO\r\n"
		    			+ "LEFT JOIN BookRating br ON b.IdB = br.IdB AND br.IdA = o.IdA and br.idO =od.IdO\r\n"
		    			+ "WHERE b.[status] = 1 and (B.nameB LIKE ?\r\n"
		        		+ "   OR C.NameC LIKE ?\r\n"
		        		+ "   OR B.author LIKE ?)\r\n"
		    			+ "GROUP BY b.NameB, b.IdB, b.releaseDate, b.pages, b.author, b.imageB, b.title, b.CateId";
	        		
	        try {
	            conn = new DbContext().getConnection();//mo ket noi voi sql
	            ps = conn.prepareStatement(query);
	            ps.setString(1, "%"+search+"%");
	            ps.setString(2, "%"+search+"%");
	            ps.setString(3, "%"+search+"%");
	            rs = ps.executeQuery();
	            while (rs.next()) {
	                 list.add( new ProductDetail(rs.getInt(1),
	                        rs.getString(2),
	                        rs.getString(3),
	                        rs.getString(4),
	                        rs.getString(5),
	                        rs.getDate(6),
	                        rs.getInt(7),
	                        rs.getInt(8),
	                        rs.getInt(9),
	                        rs.getFloat(10),
	                        rs.getInt(11)));
	                        
	            }
	            ps.close();
	    		conn.close();
	    		rs.close();
	        } catch (Exception e) {
	        }
	        return list;
	    }
}
