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
	
	public int getCartSize() {
		return cart.getCartSize();
	}
	
	public void addItem(Item item) {
		cart.addItem(item);
	}
	
	public Item getItemData(int itemIndex) {
		return cart.getItem(itemIndex);
	}
	
	public void updateItemAmount(int itemIndex, int addItemAmount) {
		cart.updateItemAmount(itemIndex, addItemAmount);
	}
	
	public void removeItem(int itemCode) {
		cart.removeItem(itemCode);
	}
	
	public int checkItem(int itemCode) {
		return cart.checkItemByItemCode(itemCode);
	}
	
	public String save() {
		String info = this.name +"/"+ this.id + "/" + this.pw;
		
		if(cart.getCartSize() > 0) {
			info += " /" +cart.save();
		}
		
		return info;
	}
	
	@Override
	public String toString() {
		String info = String.format("%s님(%s,%s)\n", this.name, this.id,this.pw);
		info += this.cart;
		
		return info;
	}
	
}
