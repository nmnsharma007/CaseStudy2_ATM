import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;

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
	
	public int showOtpMessage(int[] customer) {
		int otp = ThreadLocalRandom.current().nextInt(10001,100000);
		System.out.println("########################################################################################");
		System.out.println();
		System.out.println("Your OTP to approve the service you asked for from your account ending with\nXXX"
		+ (customer[0]%1000) + " is " +  otp + ". Please do not share this with anyone.");
		System.out.println();
		System.out.println("########################################################################################");
		return otp;
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
		System.out.println("Thank you for using our bank.");
		System.out.println();
		System.out.println("***********************************************************");
	}

}
