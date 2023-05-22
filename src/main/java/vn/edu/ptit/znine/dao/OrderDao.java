package vn.edu.ptit.znine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import vn.edu.ptit.znine.context.DbContext;
import vn.edu.ptit.znine.model.Order;
import vn.edu.ptit.znine.model.OrderDetails;
import vn.edu.ptit.znine.model.UserProduct;

@Repository
public class OrderDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	public List<UserProduct> getOrderBook(String idA) {
		List<UserProduct> list = new ArrayList<>();
    	String query = "SELECT Book.IdB, [Order].idO, Book.NameB, Book.ImageB, [Order].CreatedDate, [OrderDetails].[Status], dbo.OrderDetails.Amount\r\n"
        		+ "FROM Book\r\n"
        		+ "JOIN OrderDetails ON Book.IdB = OrderDetails.IdB\r\n"
        		+ "JOIN [Order] ON OrderDetails.IdO = [Order].IdO\r\n"
        		+ "WHERE [Order].IdA = ?";
    			
    	try {
    		conn = new DbContext().getConnection();//mo ket noi voi sql
    		ps = conn.prepareStatement(query);
    		ps.setString(1, idA);
    		rs = ps.executeQuery();
    		while(rs.next()) {
    			list.add(new UserProduct(rs.getInt(1),
                		rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getInt(6),
                        rs.getInt(7)));
    		}
    		ps.close();
    		conn.close();
    		rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return list;
    }
    public int addOrderBook(Order o) {
    	int ido =0;
    	String query = "DECLARE @InsertedIds TABLE (IdO int);\r\n"
    			+ "INSERT INTO dbo.[Order] ([CreatedDate], [IdA])\r\n"
    			+ "OUTPUT INSERTED.IdO INTO @InsertedIds\r\n"
    			+ "VALUES (?, ?);\r\n"
    			+ "SELECT IdO FROM @InsertedIds;";
    	try {
    		conn = new DbContext().getConnection();//mo ket noi voi sql
    		ps = conn.prepareStatement(query);
    		ps.setDate(1, o.getDate());
    		ps.setInt(2, o.getIdA());
    		ps.executeUpdate();
    		rs = ps.getGeneratedKeys();
    		if (rs.next()) {
    		    ido = rs.getInt(1);
    		}
    		ps.close();
    		conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return ido;
    }
    
    public int addOrderDetailsBook(OrderDetails o) {
    	int result =0;
    	String query = "insert into dbo.OrderDetails([ido], [idb], [Amount], [status]) values(?, ?, ?, ?)";
    	try {
    		conn = new DbContext().getConnection();//mo ket noi voi sql
    		ps = conn.prepareStatement(query);
    		ps.setInt(1, o.getIdO());
    		ps.setInt(2, o.getIdB());
    		ps.setInt(3, o.getAmount());
    		ps.setInt(4, o.getStatus());
    		result = ps.executeUpdate();
    		ps.close();
    		conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return result;
    }
    public int removeOrderBook(int idO, int idB) {
    	int result = 0;
    	String query = "UPDATE dbo.OrderDetails SET [status] = 1 WHERE [ido] = ? and [idb] = ?";
    	try {
    		conn = new DbContext().getConnection();//mo ket noi voi sql
    		ps = conn.prepareStatement(query);
    		ps.setInt(1, idO);
    		ps.setInt(2, idB);
    	
    		result = ps.executeUpdate();
    		ps.close();
    		conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return result;
    }
    
}
