package vn.edu.ptit.znine.model;

import java.util.List;

public class RatingCount {
	private int rating;
	private int count;
	
	public RatingCount() {
		
	}
	public RatingCount(int rating, int count) {
		this.rating = rating;
		this.count = count;
	}
	public int getRating() {
		return rating;
	}
	public int getCount() {
		return count;
	}
	public int countRating(List<RatingCount> list) {
		int sum=0;
		for(RatingCount o: list) sum += o.count;
		return sum;
	}
}
