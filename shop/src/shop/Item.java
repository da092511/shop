package shop;

public class Item {
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
	
	public int getCode() {
		return this.code;
	}
	
	public Item clone() {
		return new Item(this.name,this.code,this.price);
	}
	
	@Override
	public String toString() {
		return String.format("%s [%d] : %d원 (%d개)", 
				this.name,this.code,this.price,this.amount);
	}
	
}
