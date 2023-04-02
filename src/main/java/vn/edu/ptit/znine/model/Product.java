package vn.edu.ptit.znine.model;

import java.util.Date;

import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.NotEmpty;
@Configuration
public class Product {
	private int idB;
	@NotEmpty(message = "Vui lòng nhập trường này")
	private String nameB;
	private String imageB;
	@NotEmpty(message = "Vui lòng nhập trường này")
	private String title;
	@NotEmpty(message = "Vui lòng nhập trường này")
	private String author;
	@NotEmpty(message = "Vui lòng nhập trường này")
	private Date releaseDate;
	@NotEmpty(message = "Vui lòng nhập trường này")
	private int pages;
	private int cateId;
	
	public Product() {
		
	}

	public Product(int idB, String nameB, String imageB, String title, String author, Date releaseDate,
			int pages, int cateId) {
		this.idB = idB;
		this.nameB = nameB;
		this.imageB = imageB;
		this.title = title;
		this.author = author;
		this.releaseDate = releaseDate;
		this.pages = pages;
		this.cateId = cateId;
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

	public String getImageB() {
		return imageB;
	}

	public void setImageB(String imageB) {
		this.imageB = imageB;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate){
		this.releaseDate = releaseDate;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	@Override
	public String toString() {
		return "Product [idB=" + idB + ", nameB=" + nameB + ", imageB=" + imageB + ", title=" + title + ", author="
				+ author + ", releaseDate=" + releaseDate + ", pages=" + pages + ", cateId=" + cateId + "]";
	}
	
}
