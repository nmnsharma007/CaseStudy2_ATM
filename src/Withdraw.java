public class Withdraw extends Atm {//class for withdrawal process
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
				System.out.println("We have sent you OTP on your registered mobile number.Please enter it");
				int otp = super.showOtpMessage(customer);
				int enter = scan.nextInt();
				if(otp == enter) {
					System.out.println("Enter the withdrawal amount. Maximum allowed amount is " + limit + " and minimum amount is 10");
					System.out.println("Press a number from 1 to 9 to exit");
					int amount = scan.nextInt();//get the amount
					if(amount >= 10 && customer[2] >= amount) {//if customer has enough balance
						if(cashAvailable >= amount) {//if machine has enough money
							if(amount <= limit) {//if account is within limit
								customer[2] -= amount;
								database.updateBalance(customer[0],customer[2]);
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
				else {
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					System.out.println();
					System.out.println("The entered OTP was wrong or was not entered");
					System.out.println();
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
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
				System.out.println("We have sent you OTP on your registered mobile number.Please enter it");
				int otp = super.showOtpMessage(customer);
				int enter = scan.nextInt();
				if(otp == enter) {
				System.out.println("Enter the withdrawal amount.There is no upper limit but lower limit is 10");
				System.out.println("Press a number from 1 to 9 to exit");
				int amount = scan.nextInt();//enter the amount
				if(amount >= 10 && customer[2] >= amount) {//if customer has enough balance
					if(cashAvailable >= amount) {//is machine has enough money
						customer[2] -= amount;
						database.updateBalance(customer[0], customer[2]);
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
			else {
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println();
				System.out.println("The entered OTP was wrong or was not entered");
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