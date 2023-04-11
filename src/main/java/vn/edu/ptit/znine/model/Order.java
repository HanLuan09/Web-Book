package vn.edu.ptit.znine.model;

import java.sql.Date;

public class Order {
	private int idO;
	private Date date;
	private int idA;
	private int status;
	public Order() {
	}
	public Order(int idO, Date date, int idA, int status) {
		this.idO = idO;
		this.date = date;
		this.idA = idA;
		this.status = status;
	}
	public int getIdO() {
		return idO;
	}
	public void setIdO(int idO) {
		this.idO = idO;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getIdA() {
		return idA;
	}
	public void setIdA(int idA) {
		this.idA = idA;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
