import java.io.*;
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
			if(accounts.containsKey(number)) {//if account number exists
				accounts.get(number).showBalance();//show the balance
			}
			else
				System.out.println("The entered account number is incorrect");
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
			if(accounts.containsKey(number)) {//if account number exists
				Customer customer = accounts.get(number);
				if(customer.checkSavings())//if is savings account or not
					takeCash(customer,limit);//call method to take out cash
				else{
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					System.out.println();
					System.out.println("The entered account number is not linked to savings account");
					System.out.println();
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				}
			}
			else{
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println();
				System.out.println("The entered account number is wrong");
				System.out.println();
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
		}
		else if(n == 2) {//if business account
			System.out.println("Please enter your account number");
			int number = scan.nextInt();//get the account number 
			if(accounts.containsKey(number)) {//if account number exists
				Customer customer = accounts.get(number);
				if(!customer.checkSavings())//it is savings or not
					takeCash(customer,limit);//call method to take out cash
				else{
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					System.out.println();
					System.out.println("The entered account number is not linked to business account");
					System.out.println();
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				}
			}
			else{
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println();
				System.out.println("The entered account number is wrong");
				System.out.println();
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
		}
	}
	
	public void takeCash(Customer customer,int limit) {//take cash when it is savings account
		System.out.println("Enter your 5 digit PIN");
		int pin = scan.nextInt();//get the pin from customer
		if(customer.checkPin(pin)) {//check if pin is correct
				System.out.println("Enter the withdrawal amount. Maximum allowed amount is " + limit + " and minimum amount is 10");
				System.out.println("Press a number from 1 to 9 to exit");
				int amount = scan.nextInt();//get the amount
				if(amount >= 10 && customer.isAvailable(amount)) {//if customer has enough balance
					if(cashAvailable >= amount) {//if machine has enough money
						if(amount <= limit) {//if account is within limit
							customer.setBalance(-amount);//set the balance
							customer.printReceipt(-amount);//print the receipt
							accounts.put(customer.getAccountNo(), customer);//put  update information back into the map
							super.writeData();//write to output file
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
				else if(!customer.isAvailable(amount)){
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
	
	public void takeCash(Customer customer) {//take cash when it is business account
		System.out.println("Enter your 5 digit PIN");
		int pin = scan.nextInt();//take the pin
		if(customer.checkPin(pin)) {//check if pin is correct
				System.out.println("Enter the withdrawal amount.There is no upper limit but lower limit is 10");
				System.out.println("Press a number from 1 to 9 to exit");
				int amount = scan.nextInt();//enter the amount
				if(amount >= 10 && customer.isAvailable(amount)) {//if customer has enough balance
					if(cashAvailable >= amount) {//is machine has enough money
						customer.setBalance(-amount);//set balance of customer
						customer.printReceipt(-amount);//print bank receipt
						accounts.put(customer.getAccountNo(), customer);//put back into map
						super.writeData();//write to output file
					}
					else{
						System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
						System.out.println();
						System.out.println("The entered amount is not available in the machine right now.Sorry!");
						System.out.println();
						System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					}
				}
				else if(!customer.isAvailable(amount)){//print statements are enough to explain these cases
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
		if(accounts.containsKey(number)) {//if account number exists
			System.out.println("Enter the amount you want to enter");
			System.out.println("The lower limit is 10 and upper limit is " + limit);
			System.out.println("Press any number between from 1 to 9 to exit");
				int amount = scan.nextInt();//enter the amount
				if(amount <= limit && amount > 9) {//if within allowed limits
					Customer customer = accounts.get(number);//get customer information for update
					customer.setBalance(amount);//set the new balance
					customer.printReceipt(amount);//print the bank receipt
					accounts.put(number,customer);//put back in map
					super.writeData();//write to output file
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
	
	static Map<Integer,Customer> accounts;//map to store customer information
	Scanner scan,sc;
	protected String load;//loading time 
	protected int cashAvailable = 2000000;//initial cash available
	File ifile,ofile;//the files
	PrintWriter output;//to write output

	Atm(){//constructor to initialize fields 
		try {
			ifile = new File("input.txt");
			sc = new Scanner(ifile);
		}
		catch(FileNotFoundException e) {
			System.out.println(e);
			System.exit(0);
		}
		scan = new Scanner(System.in);
		load = "00:00";//the loading time at midnight
		accounts = new HashMap<Integer,Customer>();//initialize the map
		readData();//read from input file
		writeData();//output to file
	}

	public void readData() {//read the data from input file which will store account No,pin,balance
		while(sc.hasNextLine()) {
			int accountNo = sc.nextInt();//input account number
			int pin = sc.nextInt();//input pin
			int balance = sc.nextInt();//input balance
			boolean isSavings = sc.nextBoolean();
			Customer customer = new Customer(accountNo,pin,balance,isSavings);//set the customer attributes
			accounts.put(accountNo,customer);//putting into the map
		}
	}

	public void writeData(){//write the date to output file which will store accountNo,pin and balance
		try {//to handle error in case file is missing
			ofile = new File("output.txt");
			output = new PrintWriter(ofile);
		}
		catch(FileNotFoundException e) {
			System.out.println(e);
			System.exit(0);
		}
		for(Customer customer : accounts.values()){
			output.println(customer.getAccountNo() + " " + customer.getPin() + " " + customer.getBalance() + " " + customer.checkSavings());
		}
		output.close();
	}

	public void startMachine(){//start the machine
		Machine machine = new Machine();
		machine.start();//call the start method
	}

}
