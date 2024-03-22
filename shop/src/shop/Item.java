package shop;

import java.text.DecimalFormat;

public class Item {
	private DecimalFormat dcf = new DecimalFormat("#,###");
	
	private String name;
	private int code;
	private int price;
	
	private int amount;
	
	public Item(String name, int code, int price, int amount) {
		this.name = name;
		this.code = code;
		this.price = price;
		this.amount = amount;
	}
	
	public Item(String name, int code, int price) {
		this.name = name;
		this.code = code;
		this.price = price;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public int getPrice() {
		return this.price;
	}
	public int getCode() {
		return this.code;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void updateItemAmount(int addItemAmount) {
		this.amount += addItemAmount;
	}
	
	public String save() {
		String info = this.code+ "/" + this.name + "/" + this.price + "/" + this.amount;
		return info;
	}
	
	public Item clone() {
		return new Item(this.name,this.code,this.price,this.amount);
	}
	
	@Override
	public String toString() {
		return String.format("%s [%d] : %s원 (%d개)",this.name,this.code,dcf.format(this.price),this.amount);
	}
	
}
