package shop;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cart {
	private ArrayList<Item> cart;
	private DecimalFormat dcf = new DecimalFormat("#,###");
	
	public Cart() {
		cart = new ArrayList<>();
	}
	
	public void addItem(Item item) {
		cart.add(item);
	}
	
	public void removeItem(int itemCode) {
		for(int i=0;i<cart.size();i++) {
			Item item = cart.get(i);
			int code = item.getCode();
			
			if(code == itemCode) {
				cart.remove(item);
			}
		}
	}
	
	public int checkItemByItemCode(int itemCode) {
		int index = -1;
		
		for(int i=0; i<cart.size();i++) {
			Item item = cart.get(i);
			if(item.getCode() == itemCode)
				index = i;
		}
		
		return index;
	}
	
	public Item getItem(int itemIndex) {
		return cart.get(itemIndex);
	}
	
	public void updateItemAmount(int itemIndex, int addItemAmount) {
		Item item = cart.get(itemIndex);
		item.updateItemAmount(addItemAmount);
	}
	
	public int getCartSize() {
		return cart.size();
	}
	
	public String save() {
		// file save
		String info = "";
		for(Item item : cart)
			info+= item.save() + "/" ;
		
		info = info.substring(0,info.length()-1);
		
		
		return info;
	}
	
	@Override
	public String toString() {
		String info ="";
		int number = 1;
		for(Item item : cart) {
			int itemPrice = item.getPrice();
			int itemAmount = item.getAmount();
			
			int totalPrice = itemPrice * itemAmount;
			
			info += number+ ". " + item + " 총 "+ dcf.format(totalPrice)+"원"+"\n";
			number++;
		}
		
		return info;
	}
}
