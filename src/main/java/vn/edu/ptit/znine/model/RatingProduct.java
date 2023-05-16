package vn.edu.ptit.znine.model;

import java.sql.Date;

public class RatingProduct {
	private int idA, idB, idO, rating;
	private String comment;
	private Date dateRating;
	
	public RatingProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RatingProduct(int idA, int idB, int idO, int rating, String comment, Date dateRating) {
		super();
		this.idA = idA;
		this.idB = idB;
		this.idO = idO;
		this.rating = rating;
		this.comment = comment;
		this.dateRating = dateRating;
	}

	public int getIdA() {
		return idA;
	}

	public void setIdA(int idA) {
		this.idA = idA;
	}

	public int getIdB() {
		return idB;
	}

	public void setIdB(int idB) {
		this.idB = idB;
	}

	public int getIdO() {
		return idO;
	}

	public void setIdO(int idO) {
		this.idO = idO;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDateRating() {
		return dateRating;
	}

	public void setDateRating(Date dateRating) {
		this.dateRating = dateRating;
	}
	
	
}
