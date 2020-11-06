import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Database {
	Scanner sc;
	File ifile;
	static Connection conn;
	Database(){
		try {
			ifile = new File("D:\\projects\\CaseStudy2_ATM\\src\\input.txt");
			sc = new Scanner(ifile);
		}
		catch(FileNotFoundException e){
			System.out.println(e);
		}
	}
	
	public void createTable() {
		String url = "jdbc:sqlite:C:/sqlite/customer.db";
		String sql = "CREATE TABLE IF NOT EXISTS accounts(\n"
				+ "AccountNumber integer PRIMARY KEY,\n"
				+ "PIN integer,\n"
				+ "Balance integer,\n"
				+ "Savings integer\n"
				+");";
		try {
			conn = DriverManager.getConnection(url);
			if(conn != null) {
				Statement stmt = conn.createStatement();
				stmt.execute(sql);
				System.out.println("A new database has been created");
				while(sc.hasNextLine()) {
					int accountNo = sc.nextInt();
					int pin = sc.nextInt();
					int balance = sc.nextInt();
					int isSavings = (sc.nextBoolean() == true) ? 1 : 0;
					store(accountNo,pin,balance,isSavings);
				}
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	public void store(int accountNo,int pin,int balance,int isSavings) {
			String insert = "INSERT INTO Accounts(AccountNumber,PIN,Balance,Savings) VALUES(?,?,?,?)";
			try {
				PreparedStatement pstmt = conn.prepareStatement(insert);
				pstmt.setInt(1, accountNo);
				pstmt.setInt(2, pin);
				pstmt.setInt(3, balance);
				pstmt.setInt(4, isSavings);
				pstmt.executeUpdate();
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
			}
	}

	public void update(int accountNo,int balance) {
		String change = "UPDATE accounts SET Balance = ? WHERE AccountNumber = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(change);
			stmt.setInt(1,balance);
			stmt.setInt(2, accountNo);
			stmt.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public int[] fetch(int accountNo) {
		int[] customer = new int[4];
		int i = 0;
		customer[0] = -1;
		String sql = "SELECT AccountNumber, PIN, Balance,Savings FROM accounts";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				if(rs.getInt(1) == accountNo) {
					customer[i++] = accountNo;
					customer[i++] = rs.getInt(2);
					customer[i++] = rs.getInt(3);
					customer[i] = rs.getInt(4);
					break;
				}
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return customer;
	}
	
}
