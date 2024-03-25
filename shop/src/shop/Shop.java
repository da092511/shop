package shop;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Shop {
	private Scanner scanner = new Scanner(System.in);
	private DecimalFormat dcf = new DecimalFormat("#,###");
	
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

	private final int MY_CART = 1;
	private final int DELECT_MY_ITEM = 2;
	private final int UPDATE_AMOUNT = 3;
	private final int PURCHASE = 4;

	private final int QUIT = 3;
	
	private String name;
	private int totalSales;

	private int managerCode = 1234;
	
	private UserManager userManager;
	private ItemManager itemManager;
	private FileManager fileManager;
	
	private int log = -1;
	private String userName;

	private boolean isRun;

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
		this.fileManager = new FileManager();
		
		/* managerCode
		 * itemSize/itemCode1/itemName1/itemPrice1/itemAmount1/itemCode2/...
		 * 
		 * 
		 * userName1/userId/userPw/itemCode1/itemName1/itemPrice1/itemAmount1/...
		 * userName2/userId2/userPw2/itemCode1/itemName1/itemPrice1/itemAmount1/...
		 */
		
		String info = fileManager.loadData();
		
		if(info == null)
			return;
		
		String[] data = info.split("\n");
		System.out.println(Arrays.toString(data));
		this.managerCode = Integer.parseInt(data[0]);
		
		String[] itemData = data[1].split("/");
		
		if(!itemData[0].equals("0")) {
			for(int i=1;i<itemData.length;i+=4) {
				int itemCode = Integer.parseInt(itemData[i]);
				String itemName = itemData[i+1];
				int itemPrice = Integer.parseInt(itemData[i+2]);
				int itemAmount = Integer.parseInt(itemData[i+3]);
				
				Item item = new Item(itemName,itemCode,itemPrice,itemAmount);
				itemManager.addItem(item);
			}
		}
		
		if(data.length == 2)
			return;
		
		for(int i=2;i<data.length;i++) {
			String[] userData = data[i].split("/");
			
			String name = userData[0];
			String id = userData[1];
			String pw = userData[2];
			
			User user = new User(name,id,pw);
			userManager.add(user);
			
			for(int j=3;j<userData.length;j+=4) {
				int itemCode = Integer.parseInt(userData[j]);
				String itemName = userData[j+1];
				int itemPrice = Integer.parseInt(userData[j+2]);
				int itemAmount = Integer.parseInt(userData[j+3]);
				
				Item item = new Item(itemName,itemCode,itemPrice,itemAmount);
				
				userManager.addItem(i-2, item);
			}
		}
		
		System.out.println("불러오기 성공");
		
	}
	
	private void setSystem() {
		loadFile();
		
		this.userName = "";
		this.isRun = true;
	}
	
	
	private void showMenu() {
		System.out.println("1) 유저");
		System.out.println("2) 관리자");
		System.out.println("3) 종료");
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
	
	private void logout() {
		log = -1;
		System.out.println("로그아웃 성공");
	}
	
	private void shop() {
		System.out.println(itemManager);
		
		int shopItemSize = itemManager.getItemSize();
		if(log == -1) {
			System.err.println("로그인 후 샵 이용 가능합니다.");
			return;
		}else if(shopItemSize <= 0) {
			System.err.println("샵에 상품이 등록되지 않았습니다.");
			return;
		}
		
		int index = inputNumber("구입할 상품 번호")-1;
		
		if(index < 0 || index >= shopItemSize) {
			System.err.println("해당되는 상품이 없습니다.");
			return;
		}
		
		Item item = itemManager.getItemClone(index);
		item.setAmount(0);
		
		int itemCode = item.getCode();
		
		int itemIndex = userManager.checkItemByItemCode(log,itemCode);
		
		if(itemIndex != -1) {
			System.err.println("이미 추가된 상품은 마이페이지에서 수량 수정 가능합니다");
			return;
		}
		
		int addAmount = inputNumber("구입할 수량");
		
		if(addAmount < 1) {
			System.err.println("1개 이상 선택해주십시오");
			return;
		}
		
		item.updateItemAmount(addAmount);
		userManager.addItem(log,item);
		
		System.out.println("장바구니 추가 완료");
	}
	
	private void showMyPage() {
		System.out.println("1) 장바구니 조회");
		System.out.println("2) 항목삭제");
		System.out.println("3) 수량 수정");
		System.out.println("4) 결제");
	}
	
	private void myCart() {
		userManager.showUser(log);
		System.out.println("장바구니 조회 완료");
	}
	
	private void delectMyItem() {
		int itemSize = userManager.getCartSize(log);
		
		if(itemSize <= 0) {
			System.out.println("장바구니 추가 후 이용 가능");
			return;
		}
		
		userManager.showUser(log);
		int itemIndex = inputNumber("장바구니에서 삭제할 상품번호")-1;
		
		if(itemIndex < 0 || itemIndex >= itemSize) {
			System.out.println("해당 상품은 존재하지않습니다");
			return;
		}
		
		Item item = userManager.getItem(log, itemIndex);
		int itemCode = item.getCode();
		
		userManager.removeItem(log, itemCode);
		
		System.out.println("삭제 완료");
	}
	
	private void updateAmount() {
		int itemSize = userManager.getCartSize(log);
		
		if(itemSize <= 0) {
			System.out.println("장바구니 추가 후 이용 가능");
			return;
		}
		
		userManager.showUser(log);
		
		int itemIndex = inputNumber("수량 변경할 상품 번호(코드X): ") - 1;
		
		if(itemIndex < 0 || itemIndex >= itemSize) {
			System.out.println("해당되는 상품이 존재하지 않습니다.");
			return;
		}
		
		Item myItem = userManager.getItem(log,itemIndex);
		
		System.out.println(myItem);
		int addAmount = inputNumber("추가할 수량");
		
		if(addAmount == 0) {
			System.err.println("변경할 정보가 없습니다.");
			return;
		}else if(-addAmount >= myItem.getAmount()) {
			System.err.println("수량이 마이너스 입니다. 항목삭제는 마이페이지에서 이용 가능");
			return;
		}
		
		userManager.updateItemAmount(log, itemIndex, addAmount);
		System.out.println("수량 수정 완료!");
		
		return;
	}
	
	private boolean checkItemAmount() {
		int itemSize = userManager.getCartSize(log);
		
		for(int i=0;i<itemSize;i++) {
			Item myItem = userManager.getItem(log,i);
			int myItemAmount = myItem.getAmount();
			int myItemCode = myItem.getCode();
			
			if(!itemManager.checkItemAmount(myItemCode, myItemAmount)) {
				return false;
			}
		}
		
		return true;
	}
	
	private void showBill(String[] bill, int totalPrice) {
		System.out.println("---------------------------------");
		for(int i=0;i<bill.length;i++) {
			System.out.println(bill[i]);
			System.out.println("---------------------------------");
		}
		System.out.printf("총 금액\t\t\t%d원\n",totalPrice);
		System.out.println("---------------------------------");
		
	}
	
	private void parchase() {
		int itemSize = userManager.getCartSize(log);
		
		if(itemSize == 0) {
			System.out.println("장바구니 추가 후 이용가능");
			return;
		}
		
		userManager.showUser(log);
		int option = inputNumber("구입하시겠습니까?(구입1 취소0");
		
		if(option != 1) {
			System.err.println("취소");
			return;
		}
		
		if(!checkItemAmount()) {
			System.err.println("주문 가능한 수량을 초과하였습니다 구매 수량을 수정하여 다시 시도하세요");
			return;
		}
		
		int total = 0;
		
		String[] bill = new String[itemSize];
		
		for(int i=0;i<itemSize;i++) {
			Item myItem = userManager.getItem(log,i);
			String myItemName = myItem.getName();
			int myItemPrice = myItem.getPrice();
			int myItemAmount = myItem.getAmount();
			int myItemCode = myItem.getCode();
			
			int myTotalPrice = myItemAmount * myItemPrice;
			
			total += myTotalPrice;
			
			itemManager.updateItemAmount(myItemCode, (myItemAmount * -1));
			
			bill[i] = String.format("%s\t%d개\t%d원\t%d원",myItemName, myItemAmount,myItemPrice,myTotalPrice);
			
			userManager.removeItem(log, myItemCode);
		}
		
		this.totalSales += total;
		
		showBill(bill, total);
	}
	
	private void runMyPage(int option) {
		switch(option) {
			case(MY_CART):
				myCart();
				break;
			case(DELECT_MY_ITEM):
				delectMyItem();
				break;
			case(UPDATE_AMOUNT):
				updateAmount();
				break;
			case(PURCHASE):
				parchase();
				break;
		}
		
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
			logout();
		}
		else if(option == USER_SHOP) {
			shop();
		}
		else if(option == USER_MYPAGE ) {
			if(log == -1) {
				System.err.println("로그인 후 마이페이지 이용 가능");
				return;
			}
			showMyPage();
			runMyPage(inputNumber(""));
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
	
	private int makeItemCode() {
		Random random = new Random();
		
		int code = 0;
		
		while(true) {
			code = random.nextInt(9000)+1000;
		
			Item item = itemManager.checkItemByItemCode(code);
			
			if(item == null)
				break;
		}
		
		return code;
	}
	
	private void enrollItem() {
		String itemName = inputString("상품명");
		int price = inputNumber("가격(숫자)");
		int amount = inputNumber("수량");
		
		if(price < 0) {
			System.out.println("가격은 0원이상 등록 가능합니다.");
			return;
		}
		
		int itemCode = makeItemCode();
		
		Item item = new Item(itemName, itemCode, price, amount);
		itemManager.addItem(item);
		
		System.out.println("아이템 추가 완료");
	}
	
	private void delectItem() {
		System.out.println(itemManager);
		
		int index = inputNumber("삭제할 아이템 번호")-1;
		
		if(index < 0 || index >= itemManager.getItemSize()) {
			System.err.println("해당 상품은 존재하지 않습니다.");
			return;
		}
		
		Item item = itemManager.getItemClone(index);
		int itemCode = item.getCode();
		
		itemManager.removeItem(index);
		
		userManager.removeItem(itemCode);
		
		System.out.println("아이템 삭제 완료");
	}
	
	private void updateItem() {
		System.out.println(itemManager);
		
		int index = inputNumber("수정할 번호")-1;
		
		if(index < 0 || index >= itemManager.getItemSize()) {
			System.err.println("해당 상품은 존재하지 않습니다.");
			return;
		}
		
		Item item = itemManager.getItemClone(index);
		int itemCode = item.getCode();
		
		String name = inputString("수정할 이름(미수정 시 0)");
		int price = inputNumber("수정할 가격(미수정 시 a)");
		int amount = inputNumber("수정할 수량(미수정 시 a)");
		
		itemManager.updateItemData(itemCode, name, price, amount);
		
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
		System.out.printf("총 매출 : %d\n", dcf.format(this.totalSales));
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
				if(log != -1)
					System.out.printf("%s(%s)님 로그인 중\n",this.userName, userManager.getId(this.log));
				showUserMenu();
				runUserMenu(inputNumber(""));
				
				break;
			case(MANAGER):
				if(log != -1 || inputNumber("Manager Code") != managerCode)
					return;
				this.userName = "Manager";
				showManagerMenu();
				runManagerMenu(inputNumber(""));
				
				break;
			case(QUIT):
				this.isRun = false;
		}
		
		System.out.println();
	}
	
	public void showUser() {
		if(userManager.getSize() > 0)
			System.out.println(this.userManager);
	}
	
	public void run() {
		setSystem();
		while(this.isRun) {
			System.out.printf("Manager Code : %d\n", this.managerCode);
			showUser();
			showMenu();
			
			int option = inputNumber("menu");
			runMenu(option);
			
			setUserName();
			saveData();
		}
	}
	
	private void saveData() {
		/* managerCode
		 * itemSize/itemCode1/itemName1/itemPrice1/itemAmount1/itemCode2/...
		 * 
		 * 
		 * userName1/userId/userPw/itemCode1/itemName1/itemPrice1/itemAmount1/...
		 * userName2/userId2/userPw2/itemCode1/itemName1/itemPrice1/itemAmount1/...
		 */
		
		String data = "";
		
		data += this.managerCode + "\n";
		int itemSize = itemManager.getItemSize();
		
		data += itemManager.getItemSize()+ "/";
		
		if(itemSize == 0)
			data += "\n";
		else
			data += itemManager.save() + "\n";
		
		int userSize = userManager.getSize();
		
		if(userSize == 0)
			data = data.substring(0,data.length()-2);
		else
			data += userManager.save();
		
		fileManager.saveData(data);
		
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
