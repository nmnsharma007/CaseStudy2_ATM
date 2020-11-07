class Deposit extends Atm {//class for deposit

	private static final int limit = 50000;//upper limit for cash deposit,can be changed by the bank only
	
	public void giveCash(){//method to deposit the cash
		System.out.println("Enter your account number to deposit cash");
		int number = scan.nextInt();//take the account number
		int[] customer = database.fetch(number);
		if(customer[0] == number) {//if account number exists
			System.out.println("Enter your 5 digit PIN");
			int pin = scan.nextInt();//take the pin
			if(customer[1] == pin) {//check if pin is correct
						System.out.println("We have sent you OTP on your registered mobile number.Please enter it");
						int otp = super.showOtpMessage(customer);
						int enter = scan.nextInt();
						if(otp == enter) {
							System.out.println("Enter the amount you want to enter");
							System.out.println("The lower limit is 10 and upper limit is " + limit);
							System.out.println("Press any number between from 1 to 9 to exit");
								int amount = scan.nextInt();//enter the amount
								if(amount <= limit && amount > 9) {//if within allowed limits
									customer[2] += amount;
									database.updateBalance(number,customer[2]);
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
					else {
						System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
						System.out.println();
						System.out.println("The entered OTP was wrong");
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
		else{//if account number is wrong
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println();
			System.out.println("The entered account number doesn't exist");
			System.out.println();
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		}
	}	
	
}