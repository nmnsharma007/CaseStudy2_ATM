import java.util.*;
import java.time.*;
import java.text.SimpleDateFormat;

class Machine extends Atm {//class for functioning of machine
	private static final int password = 9845;//password for loading the cash,can be changed by only bank

	public void start() {//method to start the machine
		Withdraw withdraw = new Withdraw();//withdraw object
		Deposit deposit = new Deposit();//deposit object
		while(true) {//infinite loop for functioning of machine
			String currentTime = new SimpleDateFormat("HH:mm").format(new Date());//get the current time
			boolean isLoadingTime = currentTime.equals(load);//if is loading time
			if(isLoadingTime) {//if yes, the call load method
				load();//method to load the cash
			}
			//show the welcome screen at every iteration
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			System.out.println();
			System.out.println("Welcome to National Bank");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();
		    System.out.print(formatter.format(date) + "  ");
			System.out.println(LocalDateTime.now().getDayOfWeek());
			System.out.println("Press 1 to view your current balance");
			System.out.println("Press 2 to withdraw cash");
			System.out.println("Press 3 to deposit funds");
			System.out.println();
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			while(true) {
				int enter_num = scan.nextInt();//get the preferred choice
				if(enter_num == 1) {
					currentBalance();//method to show current balance
					break;
				}
				else if(enter_num == 2) {
					withdraw.debit();//method to withdraw cash
					break;
				}
				else if(enter_num == 3) {
					deposit.giveCash();//method to deposit cash
					break;
				}
				else
					break;//if not valid option
			}
		}
	}
	
	public void load() {//loading process into the machine, only can be done by bank authorities at midnight probably
		System.out.println("Currently, the machine has " + cashAvailable + " of cash");
		System.out.println("Enter the password to load cash into the ATM machine");
		for(int i = 3; i >= 1;--i) {
			int pass = scan.nextInt();//get the password
			if(pass == password) {
				System.out.println("Enter the amount of cash loaded");
				int loaded = scan.nextInt();//the amount loaded physically
				cashAvailable += loaded;//change the cash
				System.out.println("Cash successfully loaded");
				break;
			}
			else if(i > 1)
				System.out.println("Incorrect password. You have " + (i-1)  + " tries remaining");
			else {
				System.out.println("Tries exhausted. Contact the main office for restarting the machine.The system will shutdown now");
				System.exit(0);
				break;
			}
		}
	}
	
	public void currentBalance() {//get the current balance of the customer
		System.out.println("Enter the account number");
			int number = scan.nextInt();//get the account number 
			int[] customer = database.fetch(number);
			if(customer[0] == -1)
				System.out.println("The entered account number does not exist");
			else {
				System.out.println("############################################");
				System.out.println();
				System.out.println("             National Bank                ");
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");  
				Date date = new Date();
				System.out.println(formatter.format(date) + " ");
				System.out.println("Account type : " + ((customer[3] == 1) ? "Savings" : "Business"));
				System.out.println("Your Current Balance: " + customer[2]);
				System.out.println();
				System.out.println("############################################");
			}
	}
	
}

class Withdraw extends Atm {//class for withdrawal process
	private final int limit = 50000;//upper limit for withdrawal
	
	public void debit(){//method for withdrawal process
		System.out.println("Press 1 for savings account");
		System.out.println("Press 2 for business account");
		System.out.println("Press any other number to go back");
		int n = scan.nextInt();//get the required option
		if(n == 1) {//if savings account
			System.out.println("Please enter your account number");
			int number = scan.nextInt();//get the account number 
			int[] customer = database.fetch(number);
			if(customer[0] == -1) {
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println();
				System.out.println("The entered account number is wrong");
				System.out.println();
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
			else {
				if(customer[3] == 1) {
					takeCash(customer,limit);
				}
				else {
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					System.out.println();
					System.out.println("The entered account number is not linked to savings account");
					System.out.println();
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				}
			}
		}
		else if(n == 2) {//if business account
			System.out.println("Please enter your account number");
			int number = scan.nextInt();//get the account number 
			int[] customer = database.fetch(number);
			if(customer[0] == -1) {
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println();
				System.out.println("The entered account number is wrong");
				System.out.println();
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
			else {
				if(customer[3] == 0) {
					takeCash(customer);
				}
				else {
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					System.out.println();
					System.out.println("The entered account number is not linked to bussiness account");
					System.out.println();
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				}
			}
		}
	}
	
	public void takeCash(int[] customer,int limit) {//take cash when it is savings account
		System.out.println("Enter your 5 digit PIN");
		int pin = scan.nextInt();//get the pin from customer
		if(customer[1] == pin) {//check if pin is correct
				System.out.println("Enter the withdrawal amount. Maximum allowed amount is " + limit + " and minimum amount is 10");
				System.out.println("Press a number from 1 to 9 to exit");
				int amount = scan.nextInt();//get the amount
				if(amount >= 10 && customer[2] >= amount) {//if customer has enough balance
					if(cashAvailable >= amount) {//if machine has enough money
						if(amount <= limit) {//if account is within limit
							customer[2] -= amount;
							database.update(customer[0],customer[2]);
							super.printReceipt(customer, -amount);
						}
						else{
							System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
							System.out.println();
							System.out.println("You can't withdraw cash beyond the limit specified");
							System.out.println();
							System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
						}
					}
					else{
						System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
						System.out.println();
						System.out.println("The entered amount is not available in the machine right now.Sorry");
						System.out.println();
						System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					}
				}
				else if(customer[2] < amount){
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					System.out.println();
					System.out.println("You don't have enough balance to withdraw the entered amount");
					System.out.println();
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					//print statements are enough to explain these statements
				}
		}
		else{
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println();
			System.out.println("The entered pin was wrong");
			System.out.println();
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		}
	}
	
	public void takeCash(int[] customer) {//take cash when it is business account
		System.out.println("Enter your 5 digit PIN");
		int pin = scan.nextInt();//take the pin
		if(customer[1] == pin) {//check if pin is correct
				System.out.println("Enter the withdrawal amount.There is no upper limit but lower limit is 10");
				System.out.println("Press a number from 1 to 9 to exit");
				int amount = scan.nextInt();//enter the amount
				if(amount >= 10 && customer[2] >= amount) {//if customer has enough balance
					if(cashAvailable >= amount) {//is machine has enough money
						customer[2] -= amount;
						database.update(customer[0], customer[2]);
						super.printReceipt(customer, -amount);
					}
					else{
						System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
						System.out.println();
						System.out.println("The entered amount is not available in the machine right now.Sorry!");
						System.out.println();
						System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					}
				}
				else if(customer[2] < amount){//print statements are enough to explain these cases
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					System.out.println();
					System.out.println("You don't have enough balance to withdraw the entered amount");
					System.out.println();
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				}
		}
		else{
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println();
			System.out.println("The entered PIN was wrong");
			System.out.println();
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		}
	}
	
}

class Deposit extends Atm {//class for deposit

	private static final int limit = 50000;//upper limit for cash deposit,can be changed by the bank only
	
	public void giveCash(){//method to deposit the cash
		System.out.println("Enter your account number to deposit cash");
		int number = scan.nextInt();//take the account number
		int[] customer = database.fetch(number);
		if(customer[0] == -1) {//if account number exists
			System.out.println("Enter the amount you want to enter");
			System.out.println("The lower limit is 10 and upper limit is " + limit);
			System.out.println("Press any number between from 1 to 9 to exit");
				int amount = scan.nextInt();//enter the amount
				if(amount <= limit && amount > 9) {//if within allowed limits
					customer[2] += amount;
					database.update(number,customer[2]);
					super.printReceipt(customer, amount);
				} 
				else if(amount > limit){//if outside limits
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					System.out.println();
					System.out.println("The entered amount is more than specified limit");
					System.out.println();
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				}
		}
		else{//if account number is wrong
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println();
			System.out.println("The entered account number doesn't exist");
			System.out.println();
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		}
	}	
	
}

class Atm {//class for functioning of ATM

	Scanner scan;
	protected String load;//loading time 
	protected int cashAvailable = 2000000;//initial cash available
	static Database database;
	Atm(){//constructor to initialize fields
		scan = new Scanner(System.in);
		load = "00:00";//the loading time at midnight
	}

	public void startMachine(){//start the machine
		Machine machine = new Machine();
		database = new Database();
		database.createTable();//call the database class
		machine.start();//call the start method
	}
	
	public void printReceipt(int[] customer,int amount) {//print the receipt
		System.out.println("***********************************************************");
		System.out.println();
		System.out.println("National Bank");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date date = new Date();
		System.out.println("Account type : " + ((customer[3] == 1) ? "Savings" : "Business"));
	    System.out.println(formatter.format(date) + " ");
		if(amount < 0)
			System.out.println("Amount withdrawn: " + (-amount));
		else
			System.out.println("Amount deposited: " + amount);
		System.out.println("Your current balance: " + customer[2]);
		System.out.println("Thank you for using our bank");
		System.out.println();
		System.out.println("***********************************************************");
	}

}
