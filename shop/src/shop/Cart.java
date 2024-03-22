package shop;

import java.util.ArrayList;

public class Cart {
	private ArrayList<Item> cart;
	
	public Cart() {
		cart = new ArrayList<>();
	}
	
	public void addItem(Item item) {
		cart.add(item);
	}
	
	public void delectItem(int itemCode, int count) {
		for(Item item : cart) {
			if(count <= 0)
				break;
			
			int code = item.getCode();
			
			if(code == itemCode) {
				cart.remove(item);
				count --;
			}
		}
	}
	
	public String getCartData() {
		String info = "";
		
		return info;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
