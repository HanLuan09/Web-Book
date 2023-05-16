package vn.edu.ptit.znine.model;

public class UserProductDetail {
	private int idB;
	private String nameB;
	private String author;
	private int idO;
	private int amount;
	private String imageB;
	public UserProductDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserProductDetail(int idB, String nameB, String author, int idO, int amount, String imageB) {
		super();
		this.idB = idB;
		this.nameB = nameB;
		this.author = author;
		this.idO = idO;
		this.amount = amount;
		this.imageB = imageB;
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
	public int getIdO() {
		return idO;
	}
	public void setIdO(int idO) {
		this.idO = idO;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getImageB() {
		return imageB;
	}
	public void setImageB(String imageB) {
		this.imageB = imageB;
	}
	
}
