package vn.edu.ptit.znine.model;

import java.sql.Date;

public class UserProduct {
	private int idB;
	private int idO;
	private String nameB;
	private String imageB;
	private Date date;
	private int status;
	private int amount;
	public UserProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserProduct(int idB, int idO, String nameB, String imageB, Date date, int status, int amount) {
		super();
		this.idB = idB;
		this.idO = idO;
		this.nameB = nameB;
		this.imageB = imageB;
		this.date = date;
		this.status = status;
		this.amount = amount;
	}

	public int getIdO() {
		return idO;
	}

	public void setIdO(int idO) {
		this.idO = idO;
	}

	public int getIdB() {
		return idB;
	}

	public void setIdB(int idB) {
		this.idB = idB;
	}

	public String getNameB() {
		return nameB;
	}

	public void setNameB(String nameB) {
		this.nameB = nameB;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getImageB() {
		return imageB;
	}

	public void setImageB(String imageB) {
		this.imageB = imageB;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
