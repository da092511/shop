package shop;

import java.util.ArrayList;

public class UserManager {
	private ArrayList<User> list;
	
	public UserManager() {
		list = new ArrayList<>();
		
	}
	
	public void add(int index, User user) {
		this.list.add(index,user);
	}
	
	public void add(User user) {
		this.list.add(user);
	}
	
	public void remove(int index) {
		list.remove(index);
	}
	
	public void remove(User user) {
		list.remove(user);
	}
	
	public String getPw(int index) {
		User user = list.get(index);
		
		return user.getPw();
	}
	
	public String getId(int index) {
		User user = list.get(index);
		
		return user.getId();
	}
	
	public int searchUserById(String id) {
		int index = -1;
		
		for(int i=0;i<list.size();i++) {
			User user = list.get(i);
			String userId =user.getId();
			
			if(userId.equals(id))
				index = i;
		}
		
		return index;
	}
	
	public Item getItem(int index, int itemIndex) {
		User user = list.get(index);
		Item item = user.getItemData(itemIndex);
		
		return item;
	}
	
	public void addItem(int userIndex, Item item) {
		User user = list.get(userIndex);
		user.addItem(item);
	}

	public void removeItem(int itemCode) {
		for(User user : list) {
			user.removeItem(itemCode);
		}
	}
	
	public void removeItem(int userIndex,int itemCode) {
		User user = list.get(userIndex);
		user.removeItem(itemCode);
	}
	
	public void updateItemAmount(int userIndex, int itemIndex, int addItemAmount) {
		User user = list.get(userIndex);
		user.updateItemAmount(itemIndex, addItemAmount);
	}
	
	public int checkItemByItemCode(int index, int itemCode) {
		User user = list.get(index);
		
		return user.checkItem(itemCode);
	}
	
	public int getSize() {
		return this.list.size();
	}
	
	public int getCartSize(int userIndex) {
		User user = list.get(userIndex);
		int size = user.getCartSize();
		return size;
	}
	
	public void showUser(int index) {
		System.out.print(list.get(index));
	}
	
	public void showUser(String id) {
		int index = searchUserById(id);
		
		System.out.print(list.get(index));
	}
	
	public String save() {
		String info = "";
		
		for(User user : list) {
			info += user.save();
			info +="\n";
		}
		
		if(info.equals(""))
			return info;
					
		//줄바꿈 빼기
		info = info.substring(0,info.length()-1);
		
		return info;
	}
	
	@Override
	public String toString() {
		String info = "";
		
		for(User user : list)
			info += user;
		
		return info;
	}
}
