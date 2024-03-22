package shop;

import java.util.Scanner;

public class Shop {
	private Scanner scanner = new Scanner(System.in);
	
	private final int USER = 1;
	private final int MANAGER = 2;

	private final int USER_JOIN = 1;
	private final int USER_LEAVE = 1;
	private final int USER_LOGOUT = 2;
	private final int USER_LOGIN = 2;
	private final int USER_SHOP = 3;
	private final int USER_MYPAGE = 4;

	private final int ITEM =1;
	private final int TOTAL_SALES = 2;

	private final int ENROLL_ITEM = 1;
	private final int DELECT_ITEM = 2;
	private final int UPDATE_ITEM = 3;
	
	private String name;
	private int totalSales;

	private int managerCode = 9876;
	
	private UserManager userManager;
	private ItemManager itemManager;
	
	private int log = -1;
	private String userName;
	
	private Shop(String name) {
		this.name = name;
	}
	
	private static Shop shop = new Shop("MEGA");
	
	public static Shop getShop() {
		return shop;
	}
	
	private void loadFile() {
		this.userManager = new UserManager();
		this.itemManager = new ItemManager();
		
	}
	
	private void setSystem() {
		loadFile();
		
		this.userName = "";
	}
	
	
	private void showMenu() {
		System.out.println("1) 유저");
		System.out.println("2) 관리자");
	}
	
	private void showUserMenu() {
		System.out.printf("1) %s\n", log == -1 ? "회원가입" : "탈퇴하기");
		System.out.printf("2) %s\n", log == -1 ? "로그인" : "로그아웃");
		System.out.println("3) 쇼핑하기");
		System.out.println("4) 마이페이지");
	}
	
	private void join() {
		String name = inputString("name");
		String id = inputString("id");
		
		int index = userManager.searchUserById(id);
		
		if(index != -1) {
			System.err.println("이미 존재하는 아이디 입니다.");
			return;
		}
		
		String pw = inputString("pw");
		
		User user = new User(name, id, pw);
		userManager.add(user);
		
		System.out.println("회원가입 완료");
	}
	
	private void leave() {
		String id = inputString("id");
		String pw = inputString("pw");
		
		int index = userManager.searchUserById(id);
		
		if(index == -1) {
			System.err.println("존재하지 않는 회원입니다.");
			return;
		}else if(!pw.equals(userManager.getPw(index))) {
			System.err.println("비밀번호가 틀렸습니다.");
			return;
		}
		
		userManager.remove(index);
		log = -1;
		
		System.out.println("탈퇴완료");
	}
	
	private void login() {
		String id = inputString("id");
		String pw = inputString("pw");
		
		int index = userManager.searchUserById(id);
		
		if(index == -1) {
			System.err.println("존재하지 않는 회원입니다.");
			return;
		}else if(!pw.equals(userManager.getPw(index))) {
			System.err.println("비밀번호가 틀렸습니다.");
			return;
		}
		
		this.log = index;
		this.userName = userManager.getId(index);
		
		System.out.println("로그인 성공");
	}
	
	private void runUserMenu(int option) {
		if(option == USER_JOIN && log == -1) {
			join();
		}
		else if(option == USER_LEAVE && log != -1) {
			leave();
		}
		else if(option == USER_LOGIN && log == -1) {
			login();
		}
		else if(option == USER_LOGOUT && log != -1) {
			
		}
		else if(option == USER_SHOP) {
			
		}
		else if(option == USER_MYPAGE) {
			
		}
		
	}
	
	private void showManagerMenu() {
		System.out.println("1)아이템");
		System.out.println("2)매출 조회");
	}
	
	private void showItemMenu() {
		System.out.println("1) 등록");
		System.out.println("2) 삭제");
		System.out.println("3) 수정");
	}
	
	private void enrollItem() {
		
	}
	
	private void delectItem() {
		
	}
	
	private void updateItem() {
		
	}
	
	private void runItemMenu(int option) {
		switch(option) {
		case(ENROLL_ITEM):
			enrollItem();
			break;
		case(DELECT_ITEM):
			delectItem();
			break;
		case(UPDATE_ITEM):
			updateItem();
			break;
		}
	}
	
	private void showTotalSales() {
		System.out.printf("총 매출 : %d\n", this.totalSales);
	}
	
	private void runManagerMenu(int option) {
		switch(option) {
			case(ITEM):
				showItemMenu();
				runItemMenu(inputNumber(""));
				break;
			case(TOTAL_SALES):
				showTotalSales();
				break;
		}
	}
	
	private void runMenu(int option) {
		switch(option) {
			case(USER):
				showUserMenu();
				runUserMenu(inputNumber(""));
				break;
			case(MANAGER):
				if(log != -1 || inputNumber("Manager Code") != managerCode)
					return;
				showManagerMenu();
				runManagerMenu(inputNumber(""));
				break;
		}
	}
	
	public void showUser() {
		if(userManager.getSize() > 0)
			System.out.println(this.userManager);
	}
	
	public void run() {
		setSystem();
		while(true) {
			System.out.printf("Manager Code : %d\n", this.managerCode);
			showUser();
			showMenu();
			int option = inputNumber("menu");
			runMenu(option);
			setUserName();
		}
	}
	private void setUserName() {
		if(log == -1) 
			this.userName = "";
		
	}
	
	private int inputNumber(String message) {
		int number = -1;
		System.out.printf("%s : ", message);
		
		try {
			String input = scanner.next();
			number = Integer.parseInt(input);
		}catch(Exception e) {
			System.err.println("숫자만 ");
		}
		
		return number;
	}
	
	private String inputString(String message) {
		System.out.printf("%s : ", message);
		return scanner.next();
	}
}
