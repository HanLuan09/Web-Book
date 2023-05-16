package vn.edu.ptit.znine.model;

import java.sql.Date;

public class RatingAccount extends RatingProduct {
	private String imageA, username;

	public RatingAccount() {
		super();
	}

	public RatingAccount(int idA, int idB, int idO, int rating, String comment, Date dateRating, String imageA, String username) {
		super(idA, idB, idO, rating, comment, dateRating);
		// TODO Auto-generated constructor stub
		this.imageA = imageA;
		this.username = username;
	}

	public String getImageA() {
		return imageA;
	}

	public void setImageA(String imageA) {
		this.imageA = imageA;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
