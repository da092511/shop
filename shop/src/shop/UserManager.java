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
	
	public int getSize() {
		return this.list.size();
	}
}
