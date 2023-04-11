package vn.edu.ptit.znine.context;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbContext {
	public Connection getConnection()throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection("jdbc:sqlserver://localhost:1433; databaseName=WebBook; user=sa; password=123; encrypt=false;");
	} 
	public static void main(String[] args) {
		try {
			System.out.println(new DbContext().getConnection());
		}catch (Exception e) {
			
			System.out.println("no");
		}
	}
}
