package vn.edu.ptit.znine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.znine.context.DbContext;
import vn.edu.ptit.znine.model.Category;
import vn.edu.ptit.znine.model.RatingAccount;
import vn.edu.ptit.znine.model.RatingCount;
import vn.edu.ptit.znine.model.RatingProduct;
import vn.edu.ptit.znine.model.UserProductDetail;

@Repository
public class RatingDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	public RatingProduct getRatingOneProductBook(String ida, String idB, String idO) {
		
    	String query = "select * from BookRating where ida = ? and idb = ? and idO = ?";
    	try {
    		conn = new DbContext().getConnection();//mo ket noi voi sql
    		ps = conn.prepareStatement(query);
    		ps.setString(1, ida);
    		ps.setString(2, idB);
    		ps.setString(3, idO);
    		rs = ps.executeQuery();
    		while(rs.next()) {
    			return new RatingProduct(rs.getInt(1),
	        			  rs.getInt(2),
	        			  rs.getInt(3),
	        			  rs.getInt(4),
	        			  rs.getString(5),
	        			  rs.getDate(6));
    		}
    		ps.close();
    		conn.close();
    		rs.close();
		} catch (Exception e) {
		
		}
    	return new RatingProduct();
    }
	public int addRatingProduct(RatingProduct rP) {
		int result = 0;
    	String query = "INSERT INTO dbo.BookRating([IdA], [IdB], [IdO], [Rating], [comment], [dateRating]) values(?, ?, ?, ?, ?, ?)";
	      try {
	          conn = new DbContext().getConnection();//mo ket noi voi sql
	          ps = conn.prepareStatement(query);
	          ps.setInt(1, rP.getIdA());
	          ps.setInt(2, rP.getIdB());
	          ps.setInt(3, rP.getIdO());
	          ps.setInt(4, rP.getRating());
	          ps.setString(5, rP.getComment());
	          ps.setDate(6, rP.getDateRating());
	          result = ps.executeUpdate();
	          conn.close();
	          ps.close();
	         
	      } catch (Exception e) {
	      }
	      return result;
    }
	public int saveRatingProduct(RatingProduct rP) {
		int result = 0;
    	String query = "update dbo.BookRating set [Rating]= ?, [comment] = ?, [dateRating] = ? where [IdA] = ? and [IdB] = ? and [IdO] = ?";
	      try {
	          conn = new DbContext().getConnection();//mo ket noi voi sql
	          ps = conn.prepareStatement(query);
	          ps.setInt(1, rP.getRating());
	          ps.setString(2, rP.getComment());
	          ps.setDate(3, rP.getDateRating());
	          ps.setInt(4, rP.getIdA());
	          ps.setInt(5, rP.getIdB());
	          ps.setInt(6, rP.getIdO());
	          result = ps.executeUpdate();
	          conn.close();
	          ps.close();
	         
	      } catch (Exception e) {
	      }
	      return result;
    }
	public List<RatingAccount> getRatingAccountBook(String idB) {
		List<RatingAccount> list = new ArrayList<>();
    	String query = "select BR.*, A.imageA, A.Username\r\n"
    			+ "from BookRating AS BR\r\n"
    			+ "LEFT JOIN Account AS A ON A.IdA = BR.IdA\r\n"
    			+ "WHERE BR.IdB = ?";
    	try {
    		conn = new DbContext().getConnection();//mo ket noi voi sql
    		ps = conn.prepareStatement(query);
    		ps.setString(1, idB);
    		rs = ps.executeQuery();
    		while(rs.next()) {
    			list.add(new RatingAccount(rs.getInt(1),
	        			  rs.getInt(2),
	        			  rs.getInt(3),
	        			  rs.getInt(4),
	        			  rs.getString(5),
	        			  rs.getDate(6),
	        			  rs.getString(7),
	        			  rs.getString(8)));
    		}
    		ps.close();
    		conn.close();
    		rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return list;
    }
	
	public List<RatingCount> getRatingCountProduct(String idB) {
	  	List<RatingCount> list = new ArrayList<>();
	  	String query = "WITH Ratings AS (\r\n"
	  			+ "SELECT rating FROM (VALUES (5), (4), (3), (2), (1)) AS R(rating)\r\n"
	  			+ ")\r\n"
	  			+ "SELECT Ratings.rating, COUNT(BookRating.rating) AS Count\r\n"
	  			+ "FROM Ratings\r\n"
	  			+ "LEFT JOIN BOOKRating ON Ratings.rating = BOOKRating.rating AND BOOKRating.IdB = ?\r\n"
	  			+ "GROUP BY Ratings.rating;";
		try {
		          conn = new DbContext().getConnection();//mo ket noi voi sql
		          ps = conn.prepareStatement(query);
		          ps.setString(1, idB);
		          rs = ps.executeQuery();
		          while(rs.next()) {
		        	  list.add(new RatingCount(rs.getInt(1), rs.getInt(2)));
		          }
		          conn.close();
		          ps.close();
		          rs.close();
		         
		} catch (Exception e) {
		}
		return list;
	}
	public UserProductDetail getRatingByIdOanhIdB(String idO, String idB) {
	  	String query = "SELECT B.idB, B.NameB, B.author, OrderDetails.IdO, OrderDetails.Amount, B.imageB, [OrderDetails].[Status]\r\n"
	  			+ "FROM Book AS B\r\n"
	  			+ "INNER JOIN OrderDetails ON b.IdB = OrderDetails.IdB\r\n"
	  			+ "WHERE OrderDetails.IdO = ? and OrderDetails.IdB = ?";
		try {
		          conn = new DbContext().getConnection();//mo ket noi voi sql
		          ps = conn.prepareStatement(query);
		          ps.setString(1, idO);
		          ps.setString(2, idB);
		          rs = ps.executeQuery();
		          while(rs.next()) {
		        	  return new UserProductDetail(rs.getInt(1),
		                		rs.getString(2),
		                        rs.getString(3),
		                        rs.getInt(4),
		                        rs.getInt(5),
		                        rs.getString(6),
		                        rs.getInt(7));
		          }
		          conn.close();
		          ps.close();
		          rs.close();
		         
		} catch (Exception e) {
		}
		return new UserProductDetail();
	}
	public int timeRating(int IdA, int IdO, int IdB) {
	  	String query = "SELECT DATEDIFF(day, dateRating, GETDATE()) AS DateDiff\r\n"
	  			+ "FROM dbo.ProductRating\r\n"
	  			+ "WHERE idA = ? AND idB = ? AND idO = ?";
		try {
		          conn = new DbContext().getConnection();//mo ket noi voi sql
		          ps = conn.prepareStatement(query);
		          ps.setInt(1, IdA);
		          ps.setInt(2, IdB);
		          ps.setInt(3, IdO);
		          rs = ps.executeQuery();
		          while(rs.next()) {
		        	  return rs.getInt(1);
		          }
		          conn.close();
		          ps.close();
		          rs.close();
		         
		} catch (Exception e) {
		}
		return -1;
  }
    public static void main(String[] args) {
    	CategoryDao dao = new CategoryDao();
		List<Category> list =  dao.getAllCategoryExecpt("3");
		for(Category o: list) {
			System.out.println(o);
		}
	}
}
