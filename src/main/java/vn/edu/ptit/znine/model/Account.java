package vn.edu.ptit.znine.model;

import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Configuration
public class Account {
	private int idA;
	@NotEmpty(message = "Vui lòng nhập trường này")
	private String username;
	@NotEmpty(message = "Vui lòng nhập trường này")
	@Email(message = "Vui lòng nhập đúng email")
	private String email;
	@NotEmpty(message = "Vui lòng nhập trường này")
	@Size(min=6, message = "Vui lòng nhập mật khẩu từ 6 ký tự trở lên")
	private String password;
	private int isAdmin;
	public Account() {
		
	}
	public Account(int idA, String username, String email, String password, int isAdmin) {
		super();
		this.idA = idA;
		this.username = username;
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	public int getIdA() {
		return idA;
	}
	public void setIdA(int idA) {
		this.idA = idA;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
