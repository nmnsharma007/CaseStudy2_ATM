import java.text.SimpleDateFormat;
import java.util.Date;

class Customer {//customer class 

	private int accountNo,pin;//account number
	private int balance;//balance
	private boolean isSavings;
	Customer(int accountNo,int pin,int balance,boolean isSavings){//constructor to initialize the customer information
		this.accountNo = accountNo;
		this.pin = pin;
		this.balance = balance;
		this.isSavings = isSavings;
	}
	public boolean checkSavings(){//if account is a savings account
		return isSavings;
	}
	public int getBalance() {//get the balance
		return balance;
	}

	public void setBalance(int change) {//set the balance
		balance += change;
	}

	public void showBalance() {//show the balance
		System.out.println("############################################");
		System.out.println();
		System.out.println("             National Bank                ");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");  
		Date date = new Date();
		System.out.println(formatter.format(date) + " ");
		System.out.println("Account type : " + ((isSavings) ? "Savings" : "Business"));
		System.out.println("Your Current Balance: " + balance);
		System.out.println();
		System.out.println("############################################");
	}

	public boolean isAvailable(int money) {//if enough balance to withdraw money is available 
		if(money <= balance)
			return true;
		else
			return false;
	}

	public boolean checkPin(int pin) {//check if entered pin is correct
		if(this.pin == pin)
			return true;
		else
			return false;
	}

	public int getPin() {//return the pin
		return pin;
	}

	public int getAccountNo() {//return the account number
		return accountNo;
	}

	public void printReceipt(int amount) {//print the receipt
		System.out.println("***********************************************************");
		System.out.println();
		System.out.println("National Bank");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date date = new Date();
		System.out.println("Account type : " + ((isSavings) ? "Savings" : "Business"));
	    System.out.println(formatter.format(date) + " ");
		if(amount < 0)
			System.out.println("Amount withdrawn: " + (-amount));
		else
			System.out.println("Amount deposited: " + amount);
		System.out.println("Your current balance: " + balance);
		System.out.println("Thank you for using our bank");
		System.out.println();
		System.out.println("***********************************************************");
	}
}