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
	
	@Override
	public String toString() {
		String info ="";
		int number = 1;
		
		for(Item item: list) {
			info += number++ + ". " + item + "\n";
		}
		
		return info;
	}
}
