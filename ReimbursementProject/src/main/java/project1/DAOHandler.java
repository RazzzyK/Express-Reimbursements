package project1;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import oracle.*;

public class DAOHandler {
	
	private final String host = "jdbc:oracle:thin:@java-react-free-tier.csedppwlopoz.us-east-1.rds.amazonaws.com:1521:ORCL";
	private final String user = "Admin";                //Save in a flat file
	private final String password = "12345678";
	
	Connection conn;
	
	DAOHandler()
	{

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(host, user, password);
			//System.out.println("HIT!");
			
		}catch (ClassNotFoundException e)
		{
			System.out.println("Unable to load driver class");
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public String validateLogin(String user, String pass)
	{
		System.out.println("Checking Database for Existing User");
		String safeQuery = "Select * from EXPRESS.ERS_USERS where ers_username=?";

		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, user);
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return "INVALID";
			else if(rs.getString(3).equals(pass))
			{
				return(rs.getString(7));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "INVALID";
	}
	
	public void addToReimbursementTable(Ticket t)
	{
		Random r = new Random();
		int low = 10;
		int high = 100000000;
		int result = 0;
		
		
		String safeQuery = "Select * from EXPRESS.ERS_REIMBURSEMENT where reimb_id=?";
		
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			do {
				result = r.nextInt(high-low) + low;
				ps = conn.prepareStatement(safeQuery);
				
				ps.setInt(1, result);
				
				rs = ps.executeQuery();
	
				if(!rs.next())
					break;
			} while(rs.next());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String safeInsert = "INSERT INTO EXPRESS.ERS_REIMBURSEMENT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		PreparedStatement push;
		
		try {
			push = conn.prepareStatement(safeInsert);
			
			push.setString(1, result + "");
			push.setString(2, t.getAmount());		  
			push.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			push.setTimestamp(4, null);
			push.setString(5, t.getDescription());
			push.setString(6, null);
			push.setString(7, t.getAuthor());
			push.setString(8, "UNRESOLVED");
			push.setInt(9, 0);
			push.setString(10, t.getType());
			
			push.executeUpdate();
			
			System.out.println("\tSucessfully added new ticket!");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public ArrayList<Ticket> getTicketsInDB(String userN)
	{
		ArrayList<Ticket> userTickets = new ArrayList<Ticket>();
		
		String safeQuery = "Select * from EXPRESS.ERS_REIMBURSEMENT where reimb_author=?";
		
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, userN);
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return userTickets;
			else
			{

				 do {
					Ticket add = new Ticket();
					add.setID(rs.getString(1));
					add.setAmount(rs.getString(2));
					add.setSubmitted(rs.getTimestamp(3) + "");
					add.setResolved(rs.getTimestamp(4) + "");
					add.setDescription(rs.getString(5));
					//BLOB
					add.setAuthor(rs.getString(7));
					add.setResolver(rs.getString(8));
					add.setStatus(rs.getInt(9));
					add.setType(rs.getString(10));
		
					userTickets.add(add);
				}while(rs.next());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userTickets;
	}
	
	public Boolean checkForUser(String user)
	{
		System.out.println("Checking Database for Existing User");
		String safeQuery = "Select * from EXPRESS.ERS_USERS where ers_username=?";

		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(safeQuery);
			
			ps.setString(1, user);
			
			ResultSet rs = ps.executeQuery();

			if(!rs.next())
				return false;
			else
			{
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}