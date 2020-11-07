public class UpdateInfo extends Atm {
	
	public void start() {//start the updating process
		System.out.println("Press 1 to change your PIN");
		System.out.println("Press 2 to change your registered mobile number");
		System.out.println("Press any other number to go back");
		int n = scan.nextInt();
		if(n == 1) {
			System.out.println("Enter your account number");
			int number = scan.nextInt();
			int[] customer = database.fetch(number);
			if(customer[0] == -1) {
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println();
				System.out.println("The entered account number doesn't exist");
				System.out.println();
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
			else {
				System.out.println("Enter your old pin");
				int pin = scan.nextInt();
				if(customer[1] == pin) {
					changePin(customer);
				}
				else {
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					System.out.println();
					System.out.println("The entered pin was wrong");
					System.out.println();
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				}
			}
		}
		else if(n == 2) {
			System.out.println("Enter your account number");
			int number = scan.nextInt();
			int[] customer = database.fetch(number);
			if(customer[0] == -1) {
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println();
				System.out.println("The entered account number doesn't exist");
				System.out.println();
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
			else {
				System.out.println("Enter your pin");
				int pin = scan.nextInt();
				if(customer[1] == pin) {
					changePhoneNumber(customer);
				}
				else {
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
					System.out.println();
					System.out.println("The entered pin was wrong");
					System.out.println();
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				}
			}
		}
	}
	
	public void changePin(int[] customer) {//method to change PIN
		System.out.println("We have sent you OTP on your registered mobile number.Please enter it");
		int otp = super.showOtpMessage(customer);
		int enter = scan.nextInt();
		if(otp == enter) {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println();
			System.out.println("Enter a new pin. The pin must consist of exactly of 5 digits");
			System.out.println();
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			int newPin = scan.nextInt();
			if(Integer.toString(newPin).length() == 5) {
				database.updatePin(customer[0], Integer.valueOf(newPin));
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println();
				System.out.println("Your pin as been changed succesfully");
				System.out.println();
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
			else {
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println();
				System.out.println("The entered pin is not valid.");
				System.out.println();
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
		}
		else {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println();
			System.out.println("The entered OTP was wrong or not entered");
			System.out.println();
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		}
	}
	
	public void changePhoneNumber(int[] customer) {//method to change phone number
		System.out.println("We have sent you OTP on your old registered mobile number.Please enter it");
		int otp = super.showOtpMessage(customer);
		int enter = scan.nextInt();
		if(otp == enter) {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println();
			System.out.println("Enter a new phone number. The phone number must consist of exactly of 6 digits");
			System.out.println();
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			int newPhone = scan.nextInt();
			if(Integer.toString(newPhone).length() == 6) {
				database.updatePhone(customer[0], Integer.valueOf(newPhone));
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println();
				System.out.println("Your phone as been changed succesfully");
				System.out.println();
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
			else {
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println();
				System.out.println("The entered phone is not valid.");
				System.out.println();
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			}
		}
		else {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			System.out.println();
			System.out.println("The entered OTP was wrong or not entered");
			System.out.println();
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		}
	}
	
}