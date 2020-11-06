import java.util.Scanner;

public class Bank {

	final static int PIN =  5423;//pin to start the ATM operations
	
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the PIN to start the machine");
		for(int i = 3; i >= 1;--i) {
			int n = scanner.nextInt();
			if(n == PIN) {//if pin was correct , start the ATM
				Atm atm = new Atm();
				atm.startMachine();
				break;
			}
			else if(i > 1)
				System.out.println("Incorrect PIN. You have " + (i-1)  + " tries remaining");
			else {
				System.out.println("Tries exhausted. Contact the main office.");
				break;
			}
		}
		scanner.close();
	}

}