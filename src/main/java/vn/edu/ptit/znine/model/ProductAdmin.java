package vn.edu.ptit.znine.model;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProductAdmin implements Comparable<ProductAdmin> {
	private int idB;
	private String nameB;
	private String author;
	private String category;
	private Date releaseDate;
	private int pages; // số trang
	private int sellNumber;
	public ProductAdmin() {
		
	}
	public ProductAdmin(int idB, String nameB, String author, String category, Date releaseDate, int pages, int sellNumber) {
		this.idB = idB;
		this.nameB = nameB;
		this.author = author;
		this.category = category;
		this.releaseDate = releaseDate;
		this.pages = pages;
		this.sellNumber = sellNumber;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getReleaseDate(){
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) throws ParseException {
		this.releaseDate = new SimpleDateFormat("yyyy/MM/dd").parse(releaseDate);
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getSellNumber() {
		return sellNumber;
	}
	public void setSellNumber(int sellNumber) {
		this.sellNumber = sellNumber;
	}
	@Override
	public String toString() {
		return "ProductAdmin [idB=" + idB + ", nameB=" + nameB + ", author=" + author + ", category=" + category
				+ ", releaseDate=" + releaseDate + ", pages=" + pages + ", sellNumber=" + sellNumber + "]";
	}
	@Override
	public int compareTo(ProductAdmin o) {
		return Integer.compare(o.getIdB(), this.getIdB());
	}
}
