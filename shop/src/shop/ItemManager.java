package shop;

import java.util.ArrayList;

public class ItemManager {
	private ArrayList<Item> list;
	
	public ItemManager() {
		list = new ArrayList<>();
	}
	
	public void addItem(Item item) {
		list.add(item);
	}
	
	public void removeItem(Item item) {
		list.remove(item);
	}
	
	public void removeItem(int index) {
		if(index >= list.size())
			return;
		
		list.remove(index);
	}
	
	public int checkItemAmount(int itemCode) {
		int amount = 0;
		Item item = checkItemByItemCode(itemCode);
		
		if(item == null)
			return amount;
		
		amount = item.getAmount();
		
		return amount;
	}
	public boolean checkItemAmount(int itemCode, int addItemAmount) {
		Item item = checkItemByItemCode(itemCode);
		
		if(item == null)
			return false;
		else if(item.getAmount()+ addItemAmount < 0 ) 
			return false;
		
		return true;
	}
	
	public boolean updateItemAmount(int itemCode, int addItemAmount) {
		Item item = checkItemByItemCode(itemCode);
		
		if(item == null)
			return false;
		else if(item.getAmount() + addItemAmount < 0 ) 
			return false;
		
		item.updateItemAmount(addItemAmount);
		return true;
	}
	
	public Item checkItemByItemCode(int itemCode) {
		Item target = null;
		
		for(Item item : list)
			if(item.getCode() == itemCode)
				target = item;
		
		return target;
	}
	
	public void updateItemData(int itemCode, String name, int price, int amount) {
		Item target = checkItemByItemCode(itemCode);
		
		if(!name.equals("0"))
			target.setName(name);
		
		if(price >= 0)
			target.setPrice(price);
		
		if(amount >= 0)
			target.setAmount(amount);
	}
	
	public int getItemSize() {
		return list.size();
	}
	
	public Item getItemClone(int index) {
		Item item = list.get(index).clone();
		
		return item;
	}
	
	public String save() {
		String info = "";
		for(Item item : list)
			info+= item.save() + "/" ;
		
		info = info.substring(0,info.length()-1);
		
		return info;
	}
	
	@Override
	public String toString() {
		String info ="";
		int number = 1;
		
		for(Item item: list) {
			info += number++ + ". " + item + String.format("%s", item.getAmount() == 0 ? "" :"[품절]")+"\n";
		}
		
		return info;
	}
}
