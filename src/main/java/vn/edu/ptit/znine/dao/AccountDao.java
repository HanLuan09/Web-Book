package vn.edu.ptit.znine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

import vn.edu.ptit.znine.context.DbContext;
import vn.edu.ptit.znine.model.Account;

@Repository
public class AccountDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public Account getAccount(String name, String password) {
    	String query = "select * from Account where (Username = ? or email= ?) and [Password] = ?";
	      try {
	          conn = new DbContext().getConnection();//mo ket noi voi sql
	          ps = conn.prepareStatement(query);
	          ps.setString(1, name);
	          ps.setString(2, name);
	          ps.setString(3, password);
	          rs = ps.executeQuery();
	          while (rs.next()) {
	              return new Account(rs.getInt(1),
	            		  rs.getString(2),
	            		  rs.getString(3),
	            		  rs.getString(4),
	            		  rs.getString(5),
	            		  rs.getInt(6));
	          }
	          ps.close();
	          conn.close();
	      } catch (Exception e) {
	      }
    	return null;
    }
//    check Account
    public Account checkAccount(String username) {
    	String query = "select * from Account where Username = ?";
	      try {
	          conn = new DbContext().getConnection();//mo ket noi voi sql
	          ps = conn.prepareStatement(query);
	          ps.setString(1, username);
	          rs = ps.executeQuery();
	          while (rs.next()) {
	              return new Account(rs.getInt(1),
	            		  rs.getString(2),
	            		  rs.getString(3),
	            		  rs.getString(4),
	            		  rs.getString(5),
	            		  rs.getInt(6));
	          }
	      } catch (Exception e) {
	      }
    	return null;
    }
    public Account checkAccountEmail(String email) {
    	String query = "select * from Account where email = ?";
	      try {
	          conn = new DbContext().getConnection();//mo ket noi voi sql
	          ps = conn.prepareStatement(query);
	          ps.setString(1, email);
	          rs = ps.executeQuery();
	          while (rs.next()) {
	              return new Account(rs.getInt(1),
	            		  rs.getString(2),
	            		  rs.getString(3),
	            		  rs.getString(4),
	            		  rs.getString(5),
	            		  rs.getInt(6));
	          }
	          ps.close();
	          conn.close();
	      } catch (Exception e) {
	      }
    	return null;
    }
//    Post Account
    public void postAccount(Account a) {
    	String query = "INSERT INTO Account([imageA], [Username], [email], [Password], [isAdmin]) VALUES('anhuserwebbook.png', ? , ?, ?, ?)";
	      try {
	          conn = new DbContext().getConnection();//mo ket noi voi sql
	          ps = conn.prepareStatement(query);
	          ps.setString(1, a.getUsername());
	          ps.setString(2, a.getEmail());
	          ps.setString(3, a.getPassword());
	          ps.setInt(4, 0);
	          ps.executeUpdate();
	          ps.close();
	          conn.close();
	      } catch (Exception e) {
	      }
    }
//  Post Account
  public void saveImageAccount(String imageA, int idA) {
  	String query = "UPDATE Account SET [imageA] = ? where idA = ?";
	      try {
	          conn = new DbContext().getConnection();//mo ket noi voi sql
	          ps = conn.prepareStatement(query);
	          ps.setString(1, imageA);
	          ps.setInt(2, idA);
	          ps.executeUpdate();
	          ps.close();
	          conn.close();
	          
	      } catch (Exception e) {
	      }
  }
//    end account

    public static void main(String[] args) {
//    	Account a = new Account(1, "anhuserwebbook.png", "luan","luan@gmail.com", "123456", 0);
		AccountDao dao = new AccountDao();
		Account a = dao.getAccount("Tiến Anh", "123456");
		System.out.println(a);
	}
}
