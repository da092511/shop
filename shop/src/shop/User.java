package shop;

import java.util.ArrayList;

public class User {
	private String name;
	private String id;
	private String pw;
	
	private Cart cart;
	
	public User(String name, String id, String pw) {
		this.name = name;
		this.id = id;
		this.pw = pw;
		
		cart = new Cart();
	}
	
	public String getId() {
		return this.id ;
	}
	
	public String getPw() {
		return this.pw;
	}
	
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public void addCart(Item item) {
		cart.addItem(item);
	}
	
	public void delectItem(int itemCode, int count) {
		cart.delectItem(itemCode, count);
	}
	
}
