package vn.edu.ptit.znine.model;

import java.sql.Date;


public class ProductDetail extends Product implements Comparable<ProductDetail>{
	private int sumPrice;
	private float rating;
	private int countRating;
	public ProductDetail() {
		super();
	}
	public ProductDetail(int idB, String nameB, String imageB, String title, String author, Date releaseDate, int pages,
			int cateId, int sumPrice, float rating, int countRating) {
		super(idB, nameB, imageB, title, author, releaseDate, pages, cateId);
		this.sumPrice = sumPrice;
		this.rating = rating;
		this.countRating = countRating;
	}
	public int getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public int getCountRating() {
		return countRating;
	}
	public void setCountRating(int countRating) {
		this.countRating = countRating;
	}
	public String getsLgRating() {
		if(this.countRating >=1000000000) return String.format("%.1f", (double)(this.countRating/1000000000)) +"b";
		if(this.countRating >=1000000) return String.format("%.1f", (double)(this.countRating/1000000)) +"m";
		if(this.countRating >= 1000) return String.format("%.1f", (double)(this.countRating/1000)) +"k";
		return this.countRating+"";
	}
	public String getsLgSold() {
		if(this.sumPrice >=1000000000) return String.format("%.1f", (double)(this.sumPrice/1000000000)) + "b";
		if(this.sumPrice >=1000000) return String.format("%.1f", (double)(this.sumPrice/1000000)) +"m";
		if(this.sumPrice >= 1000) return String.format("%.1f", (double)(this.sumPrice/1000))+"k";
		return this.sumPrice+"";
		
	}
	@Override
	public int compareTo(ProductDetail o) {
		//tỉ lệ đánh giá và số lượng
		
		double popularity1 = this.getCountRating() * this.getSumPrice();
	    double popularity2 = o.getCountRating() * o.getSumPrice();
	    int popularity= Double.compare(popularity2, popularity1);
	    if (popularity!=0) return popularity;
	    
        // so sánh đánh giá
        int compareRating = Double.compare(o.getRating(), this.getRating());
        if (compareRating != 0) {
            return compareRating;
        }

        // so sánh giá
        return Integer.compare(o.getSumPrice(), this.getSumPrice());
    }
}
