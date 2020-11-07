import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Machine extends Atm {//class for functioning of machine
	private static final int password = 9845;//password for loading the cash,can be changed by only bank

	public void start() {//method to start the machine
		Withdraw withdraw = new Withdraw();//withdraw object
		Deposit deposit = new Deposit();//deposit object
		UpdateInfo updateinfo = new UpdateInfo();
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
			System.out.println("Press 4 to change your basic bank details");
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
				else if(enter_num == 4) {
					updateinfo.start();
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
