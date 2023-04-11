package vn.edu.ptit.znine.model;

public class OrderDetails {
	private int idO;
	private int idB;
	private int amount;
	private int price;
	public OrderDetails() {
	}
	public OrderDetails(int idO, int idB, int amount, int price) {
		super();
		this.idO = idO;
		this.idB = idB;
		this.amount = amount;
		this.price = price;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
